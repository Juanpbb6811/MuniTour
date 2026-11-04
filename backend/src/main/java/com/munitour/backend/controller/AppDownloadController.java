package com.munitour.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador público para la descarga directa del instalador APK oficial de MuniTour.
 */
@RestController
@Tag(name = "Descarga de la Aplicación Móvil", description = "Endpoint público para descargar el archivo APK oficial de MuniTour")
public class AppDownloadController {

    @GetMapping(value = {"/descargar", "/api/app/descargar", "/app.apk"})
    @Operation(summary = "Descargar APK de MuniTour", description = "Descarga directa del APK oficial de MuniTour con tipos MIME de Android.")
    public ResponseEntity<Resource> descargarApk() {
        Resource apkResource = new ClassPathResource("static/app.apk");
        if (!apkResource.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/vnd.android.package-archive")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"MuniTour-v1.0.0.apk\"")
                .body(apkResource);
    }
}
