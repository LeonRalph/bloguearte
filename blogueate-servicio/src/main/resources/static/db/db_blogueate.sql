-- Elimina la base de datos blogueate
DROP DATABASE IF EXISTS db_blogueate;

-- Creación de la base de datos blogueate
CREATE DATABASE IF NOT EXISTS db_blogueate;

-- Uso de la base de datos blogueate
USE db_blogueate;

-- Tabla de roles
CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) UNIQUE NOT NULL,
    descripcion TEXT,
    activo BOOLEAN DEFAULT TRUE,
    -- Campos de auditoría
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_creacion VARCHAR(50) NOT NULL,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    usuario_actualizacion VARCHAR(50) NOT NULL
);

-- Tabla de usuarios
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    ultimo_acceso TIMESTAMP NULL,
    activo BOOLEAN DEFAULT TRUE,
    -- Campos de auditoría
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_creacion VARCHAR(50) NOT NULL,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    usuario_actualizacion VARCHAR(50) NOT NULL
);

-- Relación usuario_roles (muchos a muchos)
CREATE TABLE usuario_roles (
	id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    rol_id INT NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    -- Campos de auditoría
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_creacion VARCHAR(50) NOT NULL,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    usuario_actualizacion VARCHAR(50) NOT NULL,
    UNIQUE KEY (usuario_id, rol_id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (rol_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Tabla de categorías
CREATE TABLE categorias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion VARCHAR(255),
    -- Campos de auditoría
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_creacion VARCHAR(50) NOT NULL,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    usuario_actualizacion VARCHAR(50) NOT NULL
);

-- Tabla de publicaciones
CREATE TABLE publicaciones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    contenido TEXT NOT NULL,
    archivo LONGBLOB NULL,
    nombre_archivo VARCHAR(255),
    tipo_archivo VARCHAR(100),
    ruta_archivo VARCHAR(500) NULL,
    fecha_publicacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_id INT NOT NULL,
    categoria_id INT NOT NULL,
    -- Campos de auditoría
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_creacion VARCHAR(50) NOT NULL,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    usuario_actualizacion VARCHAR(50) NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (categoria_id) REFERENCES categorias(id) ON DELETE CASCADE
);

-- Tabla de comentarios
CREATE TABLE comentarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    contenido TEXT NOT NULL,
    fecha_comentario TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_id INT NOT NULL,
    publicacion_id INT NOT NULL,
    -- Campos de auditoría
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_creacion VARCHAR(50) NOT NULL,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    usuario_actualizacion VARCHAR(50) NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (publicacion_id) REFERENCES publicaciones(id) ON DELETE CASCADE
);

SHOW TABLES;
SHOW CREATE TABLE roles;
SHOW CREATE TABLE usuarios;
SHOW CREATE TABLE usuario_roles;
SHOW CREATE TABLE categorias;
SHOW CREATE TABLE publicaciones;
SHOW CREATE TABLE comentarios;

select * from roles;
select * from usuarios;
select * from usuario_roles;
select * from categorias;
select * from publicaciones;
select * from comentarios;

INSERT INTO roles (nombre, descripcion, usuario_creacion, usuario_actualizacion)
VALUES
('ROLE_ADMIN', 'Administrador con acceso total al sistema', 'system', 'system'),
('ROLE_USER', 'Usuario registrado con permisos de creación de publicaciones y comentarios', 'system', 'system'),
('ROLE_MODERATOR', 'Usuario encargado de revisar y moderar contenido', 'system', 'system');

INSERT INTO categorias (nombre, descripcion, usuario_creacion, usuario_actualizacion)
VALUES
('Tecnología', 'Artículos sobre programación, software y gadgets', 'admin', 'admin'),
('Cocina', 'Recetas, técnicas y consejos culinarios', 'admin', 'admin'),
('Matemáticas', 'Contenido educativo sobre matemáticas en distintos niveles', 'admin', 'admin'),
('Carpintería', 'Tutoriales, proyectos y consejos para trabajar la madera', 'admin', 'admin'),
('Viajes', 'Experiencias, guías y recomendaciones de viajes', 'admin', 'admin');

INSERT INTO usuarios (username, email, password_hash, nombre, apellido, activo, usuario_creacion, usuario_actualizacion)
VALUES
('admin', 'admin@bloguearte.com',
 '$2a$10$Dow1DWrVYzZ3x9BpjYUnhO4sSeZr97oiqMZzM9yGkn41SW/.slc5C',
 'Administrador', 'Sistema', TRUE, 'admin', 'admin'),

('jlopez', 'jlopez@bloguearte.com',
 '$2a$10$Dow1DWrVYzZ3x9BpjYUnhO4sSeZr97oiqMZzM9yGkn41SW/.slc5C',
 'Jose', 'Lopez', TRUE, 'admin', 'admin'),

('cgarcia', 'cgarcia@bloguearte.com',
 '$2a$10$Dow1DWrVYzZ3x9BpjYUnhO4sSeZr97oiqMZzM9yGkn41SW/.slc5C',
 'Camila', 'Garcia', TRUE, 'admin', 'admin'),

('afernandez', 'afernandez@bloguearte.com',
 '$2a$10$Dow1DWrVYzZ3x9BpjYUnhO4sSeZr97oiqMZzM9yGkn41SW/.slc5C',
 'Anthony', 'Fernandez', TRUE, 'admin', 'admin'),

('nrojas', 'nrojas@bloguearte.com',
 '$2a$10$Dow1DWrVYzZ3x9BpjYUnhO4sSeZr97oiqMZzM9yGkn41SW/.slc5C',
 'Noelia', 'Rojas', TRUE, 'admin', 'admin');
 
 INSERT INTO usuario_roles (usuario_id, rol_id, activo, usuario_creacion, usuario_actualizacion)
VALUES
-- admin es ROLE_ADMIN
(1, 1, TRUE, 'admin', 'admin'),
-- jlopez es ROLE_USER
(2, 2, TRUE, 'admin', 'admin'),
-- cgarcia es ROLE_USER
(3, 2, TRUE, 'admin', 'admin'),
-- afernandez es ROLE_USER
(4, 2, TRUE, 'admin', 'admin'),
-- nrojas es ROLE_USER y ROLE_MODERATOR
(5, 2, TRUE, 'admin', 'admin'),
(5, 3, TRUE, 'admin', 'admin');

INSERT INTO publicaciones (titulo, contenido, nombre_archivo, tipo_archivo, ruta_archivo, usuario_id, categoria_id, usuario_creacion, usuario_actualizacion)
VALUES
-- Tecnología (admin)
('Introducción a Java Spring Boot', 'Guía básica para crear un servicio REST con Spring Boot y buenas prácticas.', 'java_spring.jpg', 'image/jpeg', '/uploads/java_spring.jpg', 1, 1, 'admin', 'admin'),
('Conceptos clave de Angular', 'Exploramos los componentes, servicios y módulos en Angular.', 'angular_componentes.jpg', 'image/jpeg', '/uploads/angular_componentes.jpg', 1, 1, 'admin', 'admin'),
('Cómo desplegar tu app en AWS', 'Explicación paso a paso para desplegar aplicaciones en Amazon Web Services.', 'aws_despliegue.jpg', 'image/jpeg', '/uploads/aws_despliegue.jpg', 1, 1, 'admin', 'admin'),
('Guía rápida de Docker', 'Veremos cómo crear imágenes y contenedores para nuestras apps.', 'docker_guia.jpg', 'image/jpeg', '/uploads/docker_guia.jpg', 1, 1, 'admin', 'admin'),
('Patrones de diseño en Backend', 'Un repaso por los principales patrones usados en arquitecturas modernas.', 'patrones_backend.jpg', 'image/jpeg', '/uploads/patrones_backend.jpg', 1, 1, 'admin', 'admin'),

-- Cocina (jlopez)
('10 recetas fáciles de pasta', 'Recopilación de recetas italianas que puedes hacer en menos de 30 minutos.', 'recetas_pasta.jpg', 'image/jpeg', '/uploads/recetas_pasta.jpg', 2, 2, 'jlopez', 'jlopez'),
('Cómo hacer pan casero', 'Explicación detallada para hacer pan desde cero en casa.', 'pan_casero.jpg', 'image/jpeg', '/uploads/pan_casero.jpg', 2, 2, 'jlopez', 'jlopez'),
('Postres con chocolate', 'Ideas dulces para los amantes del cacao.', 'postres_chocolate.jpg', 'image/jpeg', '/uploads/postres_chocolate.jpg', 2, 2, 'jlopez', 'jlopez'),
('La ciencia detrás del café', 'Un análisis de cómo se tuesta y prepara un buen café.', 'cafe_ciencia.jpg', 'image/jpeg', '/uploads/cafe_ciencia.jpg', 2, 2, 'jlopez', 'jlopez'),
('Recetas vegetarianas rápidas', 'Platos fáciles, saludables y deliciosos sin carne.', 'recetas_vegetarianas.jpg', 'image/jpeg', '/uploads/recetas_vegetarianas.jpg', 2, 2, 'jlopez', 'jlopez'),

-- Matemáticas (cgarcia)
('Introducción a las derivadas', 'Explicación sencilla de qué son y cómo se calculan las derivadas.', 'derivadas_intro.jpg', 'image/jpeg', '/uploads/derivadas_intro.jpg', 3, 3, 'cgarcia', 'cgarcia'),
('Álgebra lineal aplicada', 'Casos prácticos de álgebra en programación y gráficos.', 'algebra_lineal.jpg', 'image/jpeg', '/uploads/algebra_lineal.jpg', 3, 3, 'cgarcia', 'cgarcia'),
('Ecuaciones diferenciales', 'Ejercicios resueltos paso a paso.', 'ecuaciones_diferenciales.jpg', 'image/jpeg', '/uploads/ecuaciones_diferenciales.jpg', 3, 3, 'cgarcia', 'cgarcia'),
('Probabilidades en la vida diaria', 'Cómo aplicar la probabilidad para tomar mejores decisiones.', 'probabilidades_diaria.jpg', 'image/jpeg', '/uploads/probabilidades_diaria.jpg', 3, 3, 'cgarcia', 'cgarcia'),
('Geometría analítica básica', 'Conceptos clave y ejemplos prácticos.', 'geometria_analitica.jpg', 'image/jpeg', '/uploads/geometria_analitica.jpg', 3, 3, 'cgarcia', 'cgarcia'),

-- Carpintería (afernandez)
('Construcción de una mesa rústica', 'Tutorial paso a paso para fabricar una mesa de madera maciza.', 'mesa_rustica.jpg', 'image/jpeg', '/uploads/mesa_rustica.jpg', 4, 4, 'afernandez', 'afernandez'),
('Herramientas básicas para principiantes', 'Listado de las principales herramientas y su uso.', 'herramientas_basicas.jpg', 'image/jpeg', '/uploads/herramientas_basicas.jpg', 4, 4, 'afernandez', 'afernandez'),
('Cómo hacer un estante flotante', 'Explicación con imágenes y planos incluidos.', 'estante_flotante.jpg', 'image/jpeg', '/uploads/estante_flotante.jpg', 4, 4, 'afernandez', 'afernandez'),
('Tratamientos de la madera', 'Tipos de barnices y aceites para conservar la madera.', 'tratamientos_madera.jpg', 'image/jpeg', '/uploads/tratamientos_madera.jpg', 4, 4, 'afernandez', 'afernandez'),
('Errores comunes en carpintería', 'Lo que debes evitar al empezar en este oficio.', 'errores_carpinteria.jpg', 'image/jpeg', '/uploads/errores_carpinteria.jpg', 4, 4, 'afernandez', 'afernandez'),

-- Viajes (nrojas)
('Guía de viaje a Japón', 'Consejos, lugares imprescindibles y costos aproximados.', 'guia_japon.jpg', 'image/jpeg', '/uploads/guia_japon.jpg', 5, 5, 'nrojas', 'nrojas'),
('Mochileando por Sudamérica', 'Experiencia personal recorriendo varios países con bajo presupuesto.', 'mochileando_sudamerica.jpg', 'image/jpeg', '/uploads/mochileando_sudamerica.jpg', 5, 5, 'nrojas', 'nrojas'),
('Los mejores destinos en Europa', 'Ranking de ciudades europeas para visitar en 2025.', 'destinos_europa.jpg', 'image/jpeg', '/uploads/destinos_europa.jpg', 5, 5, 'nrojas', 'nrojas'),
('Tips para viajar solo', 'Cómo organizar tu viaje, ahorrar dinero y mantenerte seguro.', 'tips_viajar.jpg', 'image/jpeg', '/uploads/tips_viajar.jpg', 5, 5, 'nrojas', 'nrojas'),
('Aplicaciones útiles para viajeros', 'Apps que facilitan la planificación y estadía en el extranjero.', 'apps_viajeros.jpg', 'image/jpeg', '/uploads/apps_viajeros.jpg', 5, 5, 'nrojas', 'nrojas');


