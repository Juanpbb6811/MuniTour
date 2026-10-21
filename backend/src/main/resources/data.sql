-- MuniTour Seed Data (Sabana Centro, Cundinamarca, Colombia)
-- Inserción segura con ON CONFLICT para PostgreSQL / Supabase

-- 1. App Users (Usuario demo con contraseña 'munitour2026' con BCrypt)
INSERT INTO app_users (id, email, password_hash, full_name, origin_city, favorite_coffee_method, avatar_emoji, eco_points, traveler_level, completed_routes_count, visited_places_count, reviews_count)
VALUES (
    'usr_demo_01',
    'juanpablobejarano0@gmail.com',
    '$2a$10$wT0l/eP7Yf.qDskV4h0E3O3zE8Qk7R3xY1iFvCj6y5H6uJ6e3qV.G',
    'Juan Pablo Bejarano',
    'Cajicá / Bogotá',
    'V60 Dripper',
    '🧭',
    420,
    'Explorador Cittaslow',
    4,
    11,
    6
) ON CONFLICT (id) DO NOTHING;

-- 2. Places (Lugares turísticos y gastronómicos)
INSERT INTO places (id, destination_id, municipality, kind, name, tag, emoji, stars, review_count, latitude, longitude, x_ratio, y_ratio, address, distance_km, table_service_dynamics, estimated_wait_minutes, business_hours, price_tier, owner_story)
VALUES
('parque', 'cajica', 'Cajicá', 'cultura', 'Parque Principal de Cajicá', 'Ruta cultural, centro del municipio', '🏛️', 5, 184, 4.9186, -74.0259, 0.52, 0.30, 'Carrera 3 # 3-10, Cajicá', 0.1, 'Plaza peatonal histórica y punto de encuentro', 'Acceso libre', 'Abierto 24 horas', 'Gratis', 'Plaza de la Cultura, escenario de retretas dominicales y festivales tradicionales.')
ON CONFLICT (id) DO NOTHING;

INSERT INTO places (id, destination_id, municipality, kind, name, tag, emoji, stars, review_count, latitude, longitude, x_ratio, y_ratio, address, distance_km, table_service_dynamics, estimated_wait_minutes, business_hours, price_tier, owner_story)
VALUES
('estacion-tren', 'cajica', 'Cajicá', 'cultura', 'Estación del Ferrocarril (Turistren & RegioTram)', 'Patrimonio férreo y punto de atención turística', '🚂', 5, 126, 4.9165, -74.0260, 0.46, 0.36, 'Calle 1A # 0-40, Piso 3', 0.3, 'Oficina de Turismo Municipal y parada del Turistren', 'Atención 9:00 a.m. - 4:00 p.m.', 'Lun - Dom: 9:00 a.m. - 4:00 p.m.', 'Gratis', 'Histórica estación de tren del siglo XX, sede de la Coordinación de Turismo y futura estación del RegioTram del Norte.')
ON CONFLICT (id) DO NOTHING;

INSERT INTO places (id, destination_id, municipality, kind, name, tag, emoji, stars, review_count, latitude, longitude, x_ratio, y_ratio, address, distance_km, table_service_dynamics, estimated_wait_minutes, business_hours, price_tier, owner_story)
VALUES
('casa-cultura', 'cajica', 'Cajicá', 'cultura', 'Centro Cultural Fernando Botero', 'Mega Biblioteca, auditorios y exposiciones', '🎭', 5, 152, 4.9198, -74.0272, 0.38, 0.42, 'Calle 4 # 4-22, Cajicá', 0.4, 'Salas de arte, auditorio para 650 personas y talleres', 'Entrada libre', 'Mar - Dom: 8:00 a.m. - 6:30 p.m.', 'Gratis', 'Inaugurado en 2016, el complejo cultural más grande y moderno de Sabana Centro.')
ON CONFLICT (id) DO NOTHING;

INSERT INTO places (id, destination_id, municipality, kind, name, tag, emoji, stars, review_count, latitude, longitude, x_ratio, y_ratio, address, distance_km, table_service_dynamics, estimated_wait_minutes, business_hours, price_tier, owner_story)
VALUES
('mirador-cumbre', 'cajica', 'Cajicá', 'cultura', 'Mirador La Cumbre (Cerro Montepincio)', 'Sendero ecológico (2.842 msnm) y Altos de Paito', '⛰️', 5, 94, 4.9310, -74.0385, 0.72, 0.40, 'Avenida Manas-Molino, Sector Altos de Paito', 2.4, 'Sendero ecológico de 11.6 km, zonas de picnic y primeros auxilios', 'Ascenso 1h 30min', 'Fines de semana: 6:00 a.m. - 5:30 p.m.', '$', 'Cima emblemática con vista de 360 grados de toda la Sabana de Bogotá y páramos circundantes.')
ON CONFLICT (id) DO NOTHING;

INSERT INTO places (id, destination_id, municipality, kind, name, tag, emoji, stars, review_count, latitude, longitude, x_ratio, y_ratio, address, distance_km, table_service_dynamics, estimated_wait_minutes, business_hours, price_tier, owner_story)
VALUES
('ruinas-busongote', 'cajica', 'Cajicá', 'cultura', 'Ruinas de Busongote / Fortaleza de Piedra', 'Asentamiento ancestral muisca y muros coloniales', '🏺', 5, 67, 4.9350, -74.0320, 0.65, 0.28, 'Sector Las Manas - Montepincio', 2.8, 'Circuito arqueológico interpretativo', 'Recorrido guiado', 'Visitas con guías patrimoniales', 'Gratis', 'El enclave prehispánico original de Cajicá, conocido en crónicas como la Fortaleza de Piedra de Busongote.')
ON CONFLICT (id) DO NOTHING;

INSERT INTO places (id, destination_id, municipality, kind, name, tag, emoji, stars, review_count, latitude, longitude, x_ratio, y_ratio, address, distance_km, table_service_dynamics, estimated_wait_minutes, business_hours, price_tier, owner_story)
VALUES
('periland', 'cajica', 'Cajicá', 'cultura', 'Periland Eco Park (Cuenca Río Bogotá)', 'Santuario forestal, mirador El Rocinante y siembra', '🌲', 5, 145, 4.9450, -74.0150, 0.85, 0.22, 'Km 4.5 Vía Cajicá - Zipaquirá', 3.6, '6 km de senderos, avistamiento de aves, camping y programa Apadrina un Árbol', 'Tarifa entrada $6.000', 'Sáb, dom y fest: 10:00 a.m. - 5:30 p.m.', '$', '13.4 hectáreas de bosque andino dedicado a la restauración ecológica y reforestación de alisos y cedros.')
ON CONFLICT (id) DO NOTHING;

INSERT INTO places (id, destination_id, municipality, kind, name, tag, emoji, stars, review_count, latitude, longitude, x_ratio, y_ratio, address, distance_km, coffee_methods, coffee_origin, barista_specialty, table_service_dynamics, estimated_wait_minutes, business_hours, price_tier, payment_methods, has_wifi, is_pet_friendly, outdoor_terrace, owner_story)
VALUES
('helados-san-jeronimo', 'cajica', 'Cajicá', 'sabor', 'Helados y Postres San Jerónimo', 'Institución familiar de casi 70 años de tradición', '🍨', 5, 310, 4.9175, -74.0263, 0.49, 0.54, 'Calle 3 # 3-24, Centro Cajicá', 0.2, 'Café con leche espumoso,Tinto campesino', 'Café de origen colombiano', 'Afogattos con helado artesanal de vainilla y maracuyá', 'Atención en barra y salón de mesas tradicional', '3 - 6 min', 'Lun - Dom: 10:00 a.m. - 8:00 p.m.', '$', 'Efectivo,Nequi,Daviplata,Tarjetas', true, true, true, 'Fundado hace casi siete décadas, famoso en toda la sabana por sus postres de arroz con leche, milhojas de arequipe y helados naturales.')
ON CONFLICT (id) DO NOTHING;

INSERT INTO places (id, destination_id, municipality, kind, name, tag, emoji, stars, review_count, latitude, longitude, x_ratio, y_ratio, address, distance_km, coffee_methods, coffee_origin, barista_specialty, table_service_dynamics, estimated_wait_minutes, business_hours, price_tier, payment_methods, has_wifi, is_pet_friendly, outdoor_terrace, owner_story)
VALUES
('cafe-central', 'cajica', 'Cajicá', 'sabor', 'Café Central de Origen', 'Café de especialidad, V60, Chemex y barismo', '☕', 5, 210, 4.9190, -74.0250, 0.58, 0.48, 'Carrera 4 # 2-45, Centro', 0.2, 'V60 Dripper,Chemex,Prensa Francesa,Espresso Barista,Aeropress', 'Variedad Castillo y Geisha de fincas de Cundinamarca (1.950 m.s.n.m.)', 'Barismo de precisión con tueste medio propio y notas a caramelo y frutas amarillas', 'Servicio completo a la mesa con explicación sensorial de cada método por el barista', '6 - 10 min', 'Lun - Sáb: 7:30 a.m. - 8:30 p.m. / Dom: 8:00 a.m. - 7:00 p.m.', '$$', 'Efectivo,Nequi,Daviplata,Tarjetas', true, true, true, 'Tostaduría y cafetería de especialidad fundada por jóvenes baristas de Cajicá para visibilizar el grano regional.')
ON CONFLICT (id) DO NOTHING;

INSERT INTO places (id, destination_id, municipality, kind, name, tag, emoji, stars, review_count, latitude, longitude, x_ratio, y_ratio, address, distance_km, coffee_methods, coffee_origin, barista_specialty, table_service_dynamics, estimated_wait_minutes, business_hours, price_tier, payment_methods, has_wifi, is_pet_friendly, outdoor_terrace, owner_story)
VALUES
('fundo-llanero', 'cajica', 'Cajicá', 'sabor', 'Restaurante Fundo Llanero Cajicá', 'Mamona al carbón, ternera a la llanera y ajiaco', '🥩', 5, 280, 4.9120, -74.0290, 0.38, 0.68, 'Km 2.5 Vía Cajicá - Chía (Sector Capellanía)', 1.2, 'Café campesino de olla', 'Café tostado artesanal', 'Bebidas calientes y jugos naturales de la llanura', 'Servicio campestre a la mesa para grupos y familias', '12 - 18 min', 'Jue - Dom: 11:30 a.m. - 6:30 p.m.', '$$', 'Efectivo,Nequi,Daviplata,Tarjetas', true, true, true, 'Especialistas en la auténtica culinaria de los llanos orientales, carnes asadas a fuego lento en vara de leña.')
ON CONFLICT (id) DO NOTHING;

INSERT INTO places (id, destination_id, municipality, kind, name, tag, emoji, stars, review_count, latitude, longitude, x_ratio, y_ratio, address, distance_km, coffee_methods, coffee_origin, barista_specialty, table_service_dynamics, estimated_wait_minutes, business_hours, price_tier, payment_methods, has_wifi, is_pet_friendly, outdoor_terrace, owner_story)
VALUES
('level-up', 'cajica', 'Cajicá', 'sabor', 'Level Up Gamer Bar & Restaurant', 'Burgers de autor, consolas retro y bolirana', '🎮', 5, 168, 4.9180, -74.0280, 0.42, 0.50, 'Carrera 3 # 2-15, Cajicá', 0.3, 'Espresso,Frappé especial Mario Bros', 'Café de tostión media sabanero', 'Malteadas temáticas y cervezas artesanales', 'Mesas equipadas con consolas PS5, arcades clásicos y zona de bolirana', '10 - 15 min', 'Mar - Dom: 2:00 p.m. - 10:30 p.m.', '$$', 'Efectivo,Nequi,Daviplata,Tarjetas', true, true, true, 'Emprendimiento pionero en entretenimiento gastronómico donde convergen videojuegos, hamburguesas gourmet y juegos típicos colombianos.')
ON CONFLICT (id) DO NOTHING;

INSERT INTO places (id, destination_id, municipality, kind, name, tag, emoji, stars, review_count, latitude, longitude, x_ratio, y_ratio, address, distance_km, coffee_methods, coffee_origin, barista_specialty, table_service_dynamics, estimated_wait_minutes, business_hours, price_tier, payment_methods, has_wifi, is_pet_friendly, outdoor_terrace, owner_story)
VALUES
('fonda-fogon', 'cajica', 'Cajicá', 'sabor', 'Fonda El Fogón Sabanero', 'Caldos criollos, arepas de chócolo en tiesto', '🍲', 5, 265, 4.9188, -74.0242, 0.62, 0.62, 'Carrera 2 # 1-30, Cajicá', 0.4, 'Café campesino de olla con panela y clavo', 'Café de finca con molienda tradicional', 'Tinto campesino caliente en leña', 'Servicio a la mesa con platos de barro cocido', '10 - 15 min', 'Mié - Dom: 7:00 a.m. - 5:00 p.m.', '$$', 'Efectivo,Nequi,Daviplata', true, true, true, 'Tres generaciones preparando el mejor caldo de costilla y arepas de maíz pelao en tiesto de barro.')
ON CONFLICT (id) DO NOTHING;

INSERT INTO places (id, destination_id, municipality, kind, name, tag, emoji, stars, review_count, latitude, longitude, x_ratio, y_ratio, address, distance_km, table_service_dynamics, estimated_wait_minutes, business_hours, price_tier, owner_story)
VALUES
('catedral-sal', 'zipaquira', 'Zipaquirá', 'cultura', 'Catedral de Sal de Zipaquirá', 'Primera Maravilla de Colombia a 180m bajo tierra', '⛪', 5, 1420, 5.0192, -74.0041, 0.88, 0.10, 'Parque de la Sal, Zipaquirá', 14.2, 'Recorridos subterráneos con audioguías multilingües', 'Tours cada 15 min', 'Lun - Dom: 9:00 a.m. - 5:40 p.m.', '$$$', 'Monumento arquitectónico y religioso tallado en roca salina dentro de las antiguas minas.')
ON CONFLICT (id) DO NOTHING;

INSERT INTO places (id, destination_id, municipality, kind, name, tag, emoji, stars, review_count, latitude, longitude, x_ratio, y_ratio, address, distance_km, coffee_methods, coffee_origin, barista_specialty, table_service_dynamics, estimated_wait_minutes, business_hours, price_tier, owner_story)
VALUES
('andres-chia', 'chia', 'Chía', 'sabor', 'Andrés Carne de Res Chía', 'Icono mundial de la gastronomía y rumba colombiana', '🥩', 5, 2850, 4.8620, -74.0530, 0.28, 0.82, 'Calle 3 # 11A-56, Chía', 7.8, 'Espresso,Café de la casa', 'Café colombiano premium', 'Cócteles de autor y bebidas tradicionales', 'Servicio inmersivo teatral a la mesa con más de 20 ambientes', '20 - 30 min fines de semana', 'Mié - Dom: 12:00 m. - 3:00 a.m.', '$$$', 'El restaurante-bar más icónico de Colombia, fusionando arte popular, parrilla de alta calidad y fiesta tradicional.')
ON CONFLICT (id) DO NOTHING;

-- 3. Dishes (Gastronomía local)
INSERT INTO dishes (id, destination_id, name, category, place_id, place_name, description, price, emoji, is_signature, wait_time_min)
VALUES
('helado-maracuya', 'cajica', 'Helado Artesanal de Maracuyá & Frutos Rojos', 'reposteria', 'helados-san-jeronimo', 'Helados y Postres San Jerónimo', 'Elaborado con pulpa natural de maracuyá y mora silvestre, receta tradicional desde 1957.', 8500, '🍨', true, 3),
('milhoja-cajica', 'cajica', 'Milhoja Sabanera con Arequipe Casero', 'reposteria', 'helados-san-jeronimo', 'Helados y Postres San Jerónimo', 'Hojaldre crocante de mantequilla relleno de crema pastelera fresca y abundante arequipe de paila de cobre.', 9000, '🍰', true, 4),
('mamona-llanera', 'cajica', 'Ternera a la Llanera (Mamona)', 'parrilla', 'fundo-llanero', 'Restaurante Fundo Llanero Cajicá', 'Cortes tiernos asados a fuego lento en vara de leña, plátano maduro con queso y papa salada.', 39000, '🥩', true, 15),
('mario-burger', 'cajica', 'Mario Burger Gourmet con Papas Rústicas', 'rapida', 'level-up', 'Level Up Gamer Bar & Restaurant', 'Carne de res madurada 180g, tocineta crocante caramelizada, queso cheddar fundido y champiñones al vino.', 28000, '🍔', true, 12),
('arepa-chocolo', 'cajica', 'Arepa de Chócolo en Tiesto de Barro', 'tradicional', 'fonda-fogon', 'Fonda El Fogón Sabanero', 'Maíz tierno molido en casa, asada al carbón en tiesto de barro con queso campesino derretido.', 12000, '🫓', true, 8),
('caldo-costilla', 'cajica', 'Caldo Criollo de Costilla', 'tradicional', 'fonda-fogon', 'Fonda El Fogón Sabanero', 'El clásico reconstituyente para las mañanas frías de la sabana, con papa pastusa y cilantro.', 15000, '🍲', true, 10),
('cafe-v60', 'cajica', 'Café Filtrado V60 Geisha Cundinamarca', 'cafe', 'cafe-central', 'Café Central de Origen', 'Grano cultivado a 1.950 msnm, extraído manualmente en mesa con notas a jazmín y miel de caña.', 9500, '☕', true, 6)
ON CONFLICT (id) DO NOTHING;

-- 4. Cultural Events (Agenda Cultural de Cajicá y Sabana Centro)
INSERT INTO cultural_events (id, destination_id, day, day_name, month_year, title, time, duration, place, is_free, emoji, category, description)
VALUES
('e-busongote', 'cajica', 26, 'Sáb', 'Septiembre 2026', 'Festival de Amasijos y Dulces Busongote', '10:00 a. m. - 6:00 p. m.', 'Jornada completa', 'Parque Principal & Plazoleta Botero', true, '🫓', 'Gastronomía', 'El festival gastronómico cumbre de Cajicá, en honor al cacique Busongote. Concurso de almojábanas y cocineros tradicionales.'),
('e-tunas', 'cajica', 27, 'Dom', 'Septiembre 2026', 'XXVII Festival Internacional de Tunas de Cajicá', '3:00 p. m. - 9:00 p. m.', '6 horas', 'Auditorio Centro Cultural Fernando Botero', true, '🎶', 'Música', 'Tradición ininterrumpida de música de capa y pandereta con tunas universitarias de toda la sabana y delegación internacional.'),
('e-retreta', 'cajica', 28, 'Lun', 'Septiembre 2026', 'Retreta Dominical: Banda Sinfónica Juvenil', '11:00 a. m.', '1 h 30 min', 'Kiosco del Parque Principal', true, '🎺', 'Cultura', 'Concierto abierto al aire libre por los jóvenes talentos formados en las escuelas de música del Centro Cultural.'),
('e-periland', 'cajica', 29, 'Mar', 'Septiembre 2026', 'Jornada de Avistamiento y Siembra: Apadrina un Árbol', '8:30 a. m. - 1:00 p. m.', '4 horas y media', 'Periland Eco Park (Sector Las Manas)', false, '🌱', 'Ecoturismo', 'Siembra participativa de alisos y arrayanes en la cuenca alta del Río Bogotá de la mano de la Fundación Bosques Verdes.'),
('e-mercado', 'cajica', 25, 'Vie', 'Septiembre 2026', 'Mercado Campesino Sostenible y Orgánico', '7:00 a. m. - 2:00 p. m.', '7 horas', 'Plaza de Mercado Municipal de Cajicá', true, '🥕', 'Comunidad', 'Comercio directo del agricultor a la mesa con hortalizas frescas, frutas de la sabana, miel y café tostado local.')
ON CONFLICT (id) DO NOTHING;

-- 5. Culture Routes (Circuitos con audioguías)
INSERT INTO culture_routes (id, destination_id, title, subtitle, duration_text, distance_text, difficulty, places_included, audio_title, audio_duration_sec, audio_narrator, audio_transcript)
VALUES
('r-patrimonio-cajica', 'cajica', 'Circuito Histórico y del Café de Altura', 'Del ferrocarril a las casonas coloniales de Busongote', '1h 45min', '2.8 km', 'Fácil', 'estacion-tren,parque,cafe-central,casa-cultura', 'Audioguía: Cajicá Ciudad Paciente y Fortaleza de Piedra', 185, 'Guía Patrimonial de Cajicá', 'Bienvenidos a Cajicá, municipio modelo de la red mundial Cittaslow. Iniciamos en la Estación del Tren construida a comienzos del siglo XX...')
ON CONFLICT (id) DO NOTHING;

-- 6. Inter-Municipal Routes (Rutas entre municipios)
INSERT INTO inter_municipal_routes (id, title, subtitle, theme, municipalities, total_distance_km, estimated_duration, transport_mode, difficulty, key_stops, description, highlight_tip, emoji)
VALUES
('r-tren-sabana', 'Ruta Férrea de la Sabana Centro', 'Bogotá - Chía - Cajicá - Zipaquirá', 'Ferroviaria', 'Bogotá,Chía,Cajicá,Zipaquirá', 53.0, '2h 15min', 'Tren / Turistren', 'Fácil', 'Estación Usaquén,Estación La Caro,Estación Cajicá,Catedral de Sal Zipaquirá', 'Recorrido patrimonial en tren a vapor recorriendo la campiña cundinamarquesa hasta la Catedral de Sal.', 'Prueba el tinto campesino en el vagón comedor del tren.', '🚂'),
('r-gastronomica-chiquinquira', 'Ruta de Sabores Campesinos y Amasijos', 'Cajicá - Tabio - Tenjo', 'Gastronomía', 'Cajicá,Tabio,Tenjo', 32.5, 'Jornada día', 'Vehículo / Bicicleta', 'Moderada', 'Helados San Jerónimo,Parque de Tabio,Termales El Zipa,Casonas de Tenjo', 'Circuito de postres tradicionales, almojábanas calientes, caldos de paila y baños termales medicinales.', 'Lleva ropa cómoda para sumergirte en los termales de Tabio.', '🍲'),
('r-ecoturismo-majuy', 'Travesía Cerro Majuy & Montepincio', 'Santuario ecológico y miradores de la sabana', 'Ecoturismo', 'Cajicá,Chía,Cota', 21.0, '4 horas', 'Caminata / MTB', 'Exigente', 'Altos de Paito,Mirador La Cumbre,Hacienda El Noviciado,Reserva Majuy', 'Sendero de alta montaña andina entre bosques de niebla y miradores de 360 grados.', 'Inicia el ascenso antes de las 7:30 a.m. para evitar la niebla densa.', '⛰️')
ON CONFLICT (id) DO NOTHING;

-- 7. Reviews (Reseñas iniciales)
INSERT INTO reviews (id, place_id, author_name, rating, date, comment, service_highlight, coffee_rating)
VALUES
('rev_01', 'cafe-central', 'María Camila Torres', 5, 'Hace 2 días', 'El mejor filtrado V60 de toda la sabana. Te explican las notas sensoriales de la variedad Geisha con una pasión admirable.', 'Explicación del barista en mesa', 5),
('rev_02', 'helados-san-jeronimo', 'Carlos Eduardo Rincón', 5, 'Hace 3 días', 'Llevo 25 años viniendo con mi familia. La milhoja crocante con arequipe artesanal sigue siendo inigualable.', 'Atención rápida y postres frescos', 5),
('rev_03', 'fundo-llanero', 'Diana Marcela Peña', 5, 'Hace 1 semana', 'La mamona al carbón es espectacular y el ambiente campestre es ideal para pasar el domingo con niños.', 'Porciones generosas y carne muy tierna', 4)
ON CONFLICT (id) DO NOTHING;
