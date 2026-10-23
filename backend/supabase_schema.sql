-- =====================================================================
-- MuniTour Supabase PostgreSQL Schema
-- Copia y pega este script en el SQL Editor de tu proyecto en Supabase
-- =====================================================================

-- 1. Tabla de Usuarios (Soporta Autenticación Local + Google OAuth)
CREATE TABLE IF NOT EXISTS app_users (
    id VARCHAR(64) PRIMARY KEY,
    email VARCHAR(128) UNIQUE NOT NULL,
    password_hash VARCHAR(256),
    full_name VARCHAR(128) NOT NULL,
    phone VARCHAR(32) DEFAULT '+57 310 123 4567',
    bio TEXT DEFAULT 'Apasionado por el café de altura y el senderismo en Sabana Centro.',
    preferred_transport VARCHAR(64) DEFAULT 'Tren / RegioTram',
    origin_city VARCHAR(128) DEFAULT 'Cajicá, Cundinamarca',
    favorite_coffee_method VARCHAR(64) DEFAULT 'Filtrado Chemex',
    avatar_emoji VARCHAR(16) DEFAULT '🧭',
    eco_points INTEGER DEFAULT 0,
    google_id VARCHAR(128) UNIQUE,
    traveler_level VARCHAR(64) DEFAULT 'Explorador Cittaslow',
    completed_routes_count INTEGER DEFAULT 0,
    visited_places_count INTEGER DEFAULT 0,
    reviews_count INTEGER DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- 2. Tabla de Atractivos Turísticos y Gastronómicos
CREATE TABLE IF NOT EXISTS places (
    id VARCHAR(64) PRIMARY KEY,
    destination_id VARCHAR(64) NOT NULL DEFAULT 'cajica',
    municipality VARCHAR(64) NOT NULL DEFAULT 'Cajicá',
    kind VARCHAR(32) NOT NULL, -- 'cultura' o 'sabor'
    name VARCHAR(128) NOT NULL,
    tag VARCHAR(128) NOT NULL,
    emoji VARCHAR(16) NOT NULL,
    stars INTEGER DEFAULT 5,
    review_count INTEGER DEFAULT 0,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    x_ratio REAL DEFAULT 0.5,
    y_ratio REAL DEFAULT 0.5,
    address TEXT,
    distance_km REAL DEFAULT 0.8,
    image_url TEXT,
    -- Detalles operativos de barismo y atención
    coffee_methods TEXT, -- Separados por comas
    coffee_origin VARCHAR(128),
    barista_specialty VARCHAR(128),
    table_service_dynamics TEXT DEFAULT 'Servicio a la mesa completo',
    estimated_wait_minutes VARCHAR(32) DEFAULT '10 - 15 min',
    business_hours VARCHAR(128) DEFAULT 'Lun - Dom: 7:00 a.m. - 8:30 p.m.',
    price_tier VARCHAR(8) DEFAULT '$$',
    payment_methods TEXT, -- Separados por comas
    has_wifi BOOLEAN DEFAULT TRUE,
    is_pet_friendly BOOLEAN DEFAULT TRUE,
    outdoor_terrace BOOLEAN DEFAULT TRUE,
    owner_story TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- 3. Tabla de Platos Típicos y Sabores
CREATE TABLE IF NOT EXISTS dishes (
    id VARCHAR(64) PRIMARY KEY,
    destination_id VARCHAR(64) NOT NULL DEFAULT 'cajica',
    name VARCHAR(128) NOT NULL,
    category VARCHAR(64) NOT NULL, -- 'tradicional', 'cafe', 'parrilla', 'rapida', 'reposteria'
    place_id VARCHAR(64) REFERENCES places(id) ON DELETE SET NULL,
    place_name VARCHAR(128),
    description TEXT,
    price BIGINT NOT NULL,
    emoji VARCHAR(16) DEFAULT '🍲',
    is_signature BOOLEAN DEFAULT FALSE,
    wait_time_min INTEGER DEFAULT 15,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- 4. Tabla de Agenda Cultural y Festivales
CREATE TABLE IF NOT EXISTS cultural_events (
    id VARCHAR(64) PRIMARY KEY,
    destination_id VARCHAR(64) NOT NULL DEFAULT 'cajica',
    day INTEGER NOT NULL,
    day_name VARCHAR(16) NOT NULL,
    month_year VARCHAR(32) DEFAULT 'Septiembre 2026',
    title VARCHAR(160) NOT NULL,
    time VARCHAR(64) NOT NULL,
    duration VARCHAR(64) NOT NULL,
    place VARCHAR(128) NOT NULL,
    is_free BOOLEAN DEFAULT TRUE,
    emoji VARCHAR(16) DEFAULT '🎭',
    category VARCHAR(64) DEFAULT 'Cultura',
    description TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- 5. Tabla de Rutas Culturales con Audioguías
CREATE TABLE IF NOT EXISTS culture_routes (
    id VARCHAR(64) PRIMARY KEY,
    destination_id VARCHAR(64) NOT NULL DEFAULT 'cajica',
    title VARCHAR(128) NOT NULL,
    subtitle TEXT,
    duration_text VARCHAR(32),
    distance_text VARCHAR(32),
    difficulty VARCHAR(32) DEFAULT 'Fácil',
    places_included TEXT, -- IDs de lugares separados por comas
    audio_title VARCHAR(128),
    audio_duration_sec INTEGER DEFAULT 185,
    audio_narrator VARCHAR(128) DEFAULT 'Guía Patrimonial Local',
    audio_transcript TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- 6. Tabla de Rutas Intermunicipales
CREATE TABLE IF NOT EXISTS inter_municipal_routes (
    id VARCHAR(64) PRIMARY KEY,
    title VARCHAR(128) NOT NULL,
    subtitle TEXT,
    theme VARCHAR(64),
    municipalities TEXT, -- Separados por comas
    total_distance_km DOUBLE PRECISION,
    estimated_duration VARCHAR(64),
    transport_mode VARCHAR(64),
    difficulty VARCHAR(32),
    key_stops TEXT, -- Separados por comas
    description TEXT,
    highlight_tip TEXT,
    emoji VARCHAR(16) DEFAULT '🛤️',
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- 7. Tabla de Reseñas y Calificaciones
CREATE TABLE IF NOT EXISTS reviews (
    id VARCHAR(64) PRIMARY KEY,
    place_id VARCHAR(64) REFERENCES places(id) ON DELETE CASCADE,
    author_name VARCHAR(128) NOT NULL,
    rating INTEGER CHECK (rating >= 1 AND rating <= 5),
    date VARCHAR(64) DEFAULT 'Hoy',
    comment TEXT NOT NULL,
    service_highlight VARCHAR(128) DEFAULT 'Excelente atención',
    coffee_rating INTEGER DEFAULT 5,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);
