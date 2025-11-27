-- 1. Crear la base de datos (si no existe) y usarla
CREATE DATABASE IF NOT EXISTS biblioteca_db;
USE biblioteca_db;

-- 2. Eliminar tablas si existen para empezar limpio (opcional, ten cuidado en producción)
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS authors;

-- 3. Crear la tabla 'authors' (Lado "Uno" de la relación)
CREATE TABLE authors (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         nationality VARCHAR(50)
);

-- 4. Crear la tabla 'books' (Lado "Muchos" de la relación)
CREATE TABLE books (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(150) NOT NULL,
                       genre VARCHAR(50),
    -- Esta columna guardará el ID del autor
                       author_id BIGINT,

    -- Definición de la Clave Foránea (Foreign Key)
    -- Esto conecta 'books' con 'authors'
                       CONSTRAINT fk_book_author
                           FOREIGN KEY (author_id)
                               REFERENCES authors(id)
                               ON DELETE CASCADE -- Si borras el autor, se borran sus libros (opcional)
);

-- 5. (Opcional) Datos de prueba iniciales
INSERT INTO authors (name, nationality) VALUES ('J.R.R. Tolkien', 'British');
INSERT INTO books (title, genre, author_id) VALUES ('The Hobbit', 'Fantasy', 1);