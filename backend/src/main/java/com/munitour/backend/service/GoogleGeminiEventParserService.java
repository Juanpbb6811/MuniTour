package com.munitour.backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.munitour.backend.dto.request.EventAiImportRequestDto;
import com.munitour.backend.entity.CulturalEvent;
import com.munitour.backend.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Servicio inteligente para parsear publicaciones de redes sociales (Instagram / Facebook)
 * de la Alcaldía de Cajicá y el Instituto de Cultura, convirtiéndolas en eventos culturales
 * estructurados mediante Google Gemini AI (o extractor heurístico de respaldo).
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleGeminiEventParserService {

    private final EventRepository eventRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${gemini.api.key:}")
    private String geminiApiKey;

    @Value("${gemini.model:gemini-1.5-flash}")
    private String geminiModel;

    private static final String GEMINI_API_URL_TEMPLATE =
            "https://generativelanguage.googleapis.com/v1beta/models/%s:generateContent?key=%s";

    /**
     * Procesa una publicación de redes sociales y crea/actualiza el evento en la base de datos.
     */
    @Transactional
    public CulturalEvent parseAndSaveEvent(EventAiImportRequestDto request) {
        String postText = (request.getPostText() != null) ? request.getPostText().trim() : "";
        String destinationId = StringUtils.hasText(request.getDestinationId()) ? request.getDestinationId().toLowerCase() : "cajica";

        if (postText.isBlank() && !StringUtils.hasText(request.getImageUrl())) {
            throw new IllegalArgumentException("Debe proporcionar al menos el texto de la publicación o la URL de la imagen");
        }

        log.info("Procesando evento para destino '{}' con IA / parser inteligente...", destinationId);

        CulturalEvent event;
        if (StringUtils.hasText(geminiApiKey)) {
            try {
                event = parseWithGemini(postText, request.getImageUrl(), destinationId);
                log.info("Evento parseado exitosamente con Google Gemini: '{}'", event.getTitle());
            } catch (Exception e) {
                log.warn("Fallo en Google Gemini ({}), usando parser heurístico de respaldo: {}", e.getMessage(), e.toString());
                event = parseWithHeuristics(postText, request.getImageUrl(), destinationId);
            }
        } else {
            log.info("No se configuró GEMINI_API_KEY. Usando parser heurístico integrado para Cajicá...");
            event = parseWithHeuristics(postText, request.getImageUrl(), destinationId);
        }

        // Generar un ID único y legible si no existe
        if (!StringUtils.hasText(event.getId())) {
            String slug = slugify(event.getTitle());
            String id = "e-" + slug + "-" + event.getDay() + "-" + slugify(event.getMonthYear());
            if (id.length() > 36) {
                id = id.substring(0, 36);
            }
            event.setId(id);
        }

        event.setDestinationId(destinationId);
        return eventRepository.save(event);
    }

    /**
     * Invoca la API REST de Google Gemini Flash con formato estructurado JSON.
     */
    private CulturalEvent parseWithGemini(String postText, String imageUrl, String destinationId) throws Exception {
        String apiUrl = String.format(GEMINI_API_URL_TEMPLATE, geminiModel, geminiApiKey);

        String prompt = """
                Eres un asistente experto en eventos y turismo para el municipio de Cajicá, Cundinamarca, Colombia.
                Analiza el siguiente texto de una publicación de redes sociales (Instagram / Facebook de la Alcaldía de Cajicá o Instituto Municipal de Cultura y Turismo)
                y extrae la información del evento cultural o recreativo.
                
                Debes responder ÚNICAMENTE un objeto JSON válido con las siguientes claves:
                {
                  "title": "Nombre claro y atractivo del evento",
                  "day": 28,
                  "day_name": "Sáb",
                  "month_year": "Noviembre 2026",
                  "time": "3:00 p.m. - 6:00 p.m.",
                  "duration": "3 horas",
                  "place": "Lugar específico (ej. Parque Principal, Centro Cultural Fernando Botero, Rincón Santo)",
                  "is_free": true,
                  "emoji": "🎭",
                  "category": "Cultura",
                  "description": "Breve sinopsis atractiva para turistas y residentes (máximo 2 párrafos)"
                }
                
                Reglas obligatorias:
                - Las categorías válidas son: Cultura, Música, Gastronomía, Ecoturismo, Comunidad, Deportes, Danza, Teatro.
                - day_name debe ser abreviatura de 3 letras: Lun, Mar, Mié, Jue, Vie, Sáb, Dom.
                - is_free: true por defecto a menos que se indique tarifa.
                - Si el año no está explícito, asume el año en curso (2026).
                - Selecciona un emoji alusivo adecuado al tema.
                
                Texto de la publicación:
                """ + postText;

        ObjectNode rootNode = objectMapper.createObjectNode();
        ArrayNode contentsArray = rootNode.putArray("contents");
        ObjectNode contentObj = contentsArray.addObject();
        ArrayNode partsArray = contentObj.putArray("parts");
        partsArray.addObject().put("text", prompt);

        // Forzar respuesta JSON estricta desde Gemini
        ObjectNode genConfig = rootNode.putObject("generationConfig");
        genConfig.put("temperature", 0.1);
        genConfig.put("response_mime_type", "application/json");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(rootNode.toString(), headers);
        String responseBody = restTemplate.postForObject(apiUrl, entity, String.class);

        if (responseBody == null) {
            throw new RuntimeException("Respuesta nula desde Gemini API");
        }

        JsonNode geminiResponse = objectMapper.readTree(responseBody);
        JsonNode candidate = geminiResponse.path("candidates").path(0);
        String contentText = candidate.path("content").path("parts").path(0).path("text").asText("");

        if (contentText.isBlank()) {
            throw new RuntimeException("Gemini no retornó contenido en la respuesta");
        }

        JsonNode eventJson = objectMapper.readTree(contentText);

        return CulturalEvent.builder()
                .title(eventJson.path("title").asText("Evento Cultural en Cajicá"))
                .day(eventJson.path("day").asInt(15))
                .dayName(eventJson.path("day_name").asText("Sáb"))
                .monthYear(eventJson.path("month_year").asText("Noviembre 2026"))
                .time(eventJson.path("time").asText("10:00 a.m. - 4:00 p.m."))
                .duration(eventJson.path("duration").asText("Jornada"))
                .place(eventJson.path("place").asText("Centro Cultural Fernando Botero"))
                .isFree(eventJson.path("is_free").asBoolean(true))
                .emoji(eventJson.path("emoji").asText("\uD83C\uDFAD"))
                .category(eventJson.path("category").asText("Cultura"))
                .description(eventJson.path("description").asText(postText.length() > 200 ? postText.substring(0, 200) + "..." : postText))
                .createdAt(LocalDateTime.now())
                .build();
    }

    /**
     * Parser heurístico basado en expresiones regulares para Cajicá cuando no hay API Key o falla internet.
     */
    private CulturalEvent parseWithHeuristics(String text, String imageUrl, String destinationId) {
        String lower = text.toLowerCase();

        // 1. Detección de Categoría y Emoji
        String category = "Cultura";
        String emoji = "\uD83C\uDFAD"; // 🎭

        if (lower.contains("concierto") || lower.contains("sinfónica") || lower.contains("tuna") || lower.contains("música") || lower.contains("banda") || lower.contains("canto")) {
            category = "Música";
            emoji = "\uD83C\uDFB6"; // 🎶
        } else if (lower.contains("almojábana") || lower.contains("amasijo") || lower.contains("gastronom") || lower.contains("dulce") || lower.contains("sabores") || lower.contains("comida")) {
            category = "Gastronomía";
            emoji = "\uD83E\uDD56"; // 🥖
        } else if (lower.contains("siembra") || lower.contains("caminata") || lower.contains("senderismo") || lower.contains("ecopark") || lower.contains("árbol") || lower.contains("río")) {
            category = "Ecoturismo";
            emoji = "\uD83C\uDF33"; // 🌳
        } else if (lower.contains("carrera") || lower.contains("torneo") || lower.contains("deporte") || lower.contains("ciclovía") || lower.contains("patinaje")) {
            category = "Deportes";
            emoji = "\uD83C\uDFC3"; // 🏃
        } else if (lower.contains("mercado") || lower.contains("campesino") || lower.contains("artesanos") || lower.contains("feria")) {
            category = "Comunidad";
            emoji = "\uD83E\uDD66"; // 🥦
        } else if (lower.contains("danza") || lower.contains("baile") || lower.contains("folclor")) {
            category = "Danza";
            emoji = "\uD83D\uDC83"; // 💃
        }

        // 2. Detección del Día (ej: "28 de noviembre", "sábado 15")
        int day = LocalDate.now().getDayOfMonth();
        Pattern dayPattern = Pattern.compile("(\\b(?:el\\s+)?(?:día\\s+)?)([0-3]?[0-9])(\\s+de\\s+([a-záéíóú]+))?", Pattern.CASE_INSENSITIVE);
        Matcher dayMatcher = dayPattern.matcher(text);
        if (dayMatcher.find()) {
            try {
                int extracted = Integer.parseInt(dayMatcher.group(2));
                if (extracted >= 1 && extracted <= 31) {
                    day = extracted;
                }
            } catch (Exception ignored) {}
        }

        // 3. Detección del Nombre del Día
        String dayName = "Sáb";
        if (lower.contains("lunes")) dayName = "Lun";
        else if (lower.contains("martes")) dayName = "Mar";
        else if (lower.contains("miércoles") || lower.contains("miercoles")) dayName = "Mié";
        else if (lower.contains("jueves")) dayName = "Jue";
        else if (lower.contains("viernes")) dayName = "Vie";
        else if (lower.contains("sábado") || lower.contains("sabado")) dayName = "Sáb";
        else if (lower.contains("domingo")) dayName = "Dom";

        // 4. Detección del Mes
        String monthYear = "Noviembre 2026";
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        for (String m : meses) {
            if (lower.contains(m.toLowerCase())) {
                monthYear = m + " 2026";
                break;
            }
        }

        // 5. Detección de Lugar
        String place = "Parque Principal de Cajicá";
        if (lower.contains("centro cultural") || lower.contains("botero") || lower.contains("auditorio")) {
            place = "Centro Cultural Fernando Botero";
        } else if (lower.contains("estación") || lower.contains("ferrocarril") || lower.contains("tren")) {
            place = "Estación del Ferrocarril (Turistren)";
        } else if (lower.contains("periland") || lower.contains("las manas")) {
            place = "Periland Eco Park";
        } else if (lower.contains("rincon santo") || lower.contains("rincón santo")) {
            place = "Polideportivo Rincón Santo";
        } else if (lower.contains("plaza de mercado") || lower.contains("galería")) {
            place = "Plaza de Mercado Municipal";
        }

        // 6. Detección de Gratuidad
        boolean isFree = !lower.contains("boleta:") && !lower.contains("cover") && !lower.contains("inscripción: $");

        // 7. Detección de Título y Horario
        String title = extractTitle(text);
        String time = "3:00 p.m. - 6:00 p.m.";
        Pattern timePattern = Pattern.compile("(\\d{1,2}(?::\\d{2})?\\s*(?:a\\.?\\s*m\\.?|p\\.?\\s*m\\.?|am|pm))", Pattern.CASE_INSENSITIVE);
        Matcher timeMatcher = timePattern.matcher(text);
        if (timeMatcher.find()) {
            time = "A partir de las " + timeMatcher.group(1);
        }

        return CulturalEvent.builder()
                .title(title)
                .day(day)
                .dayName(dayName)
                .monthYear(monthYear)
                .time(time)
                .duration("Jornada")
                .place(place)
                .isFree(isFree)
                .emoji(emoji)
                .category(category)
                .description(text.length() > 300 ? text.substring(0, 300) + "..." : text)
                .createdAt(LocalDateTime.now())
                .build();
    }

    private String extractTitle(String text) {
        String clean = text.replaceAll("[#@][\\w]+", "").trim();
        String[] lines = clean.split("\n");
        for (String line : lines) {
            String l = line.trim();
            if (l.length() >= 8 && l.length() <= 80) {
                return l.replaceAll("^[¡!¿?\\s]+", "").replaceAll("[¡!¿?\\s]+$", "");
            }
        }
        return "Festival Cultural en Cajicá";
    }

    private String slugify(String input) {
        if (input == null) return UUID.randomUUID().toString().substring(0, 8);
        String normalized = Normalizer.normalize(input.trim().replaceAll("\\s+", "-"), Normalizer.Form.NFD);
        String slug = normalized.replaceAll("[^\\w\\-]", "").toLowerCase();
        return slug.length() > 25 ? slug.substring(0, 25) : slug;
    }
}
