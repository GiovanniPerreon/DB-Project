ALTER USER 'root'@'localhost' IDENTIFIED BY 'password';
FLUSH PRIVILEGES;
-- Database Deletion -------------------------------------------------------------
DROP DATABASE IF EXISTS tables;

-- Tables creation -------------------------------------------------------------

CREATE DATABASE if NOT EXISTS tables;
USE tables;

CREATE TABLE if NOT EXISTS users (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(128) NOT NULL UNIQUE,
    password VARCHAR(32) NOT NULL,
    name VARCHAR(64) NOT NULL,
    surname VARCHAR(64) NOT NULL,
    birth_date DATE NOT NULL
);

CREATE TABLE if NOT EXISTS videogames (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(64) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    release_date DATE NOT NULL,
    description VARCHAR(5000)
);

CREATE TABLE if NOT EXISTS purchases (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    videogame_id INT NOT NULL,
    purchase_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (videogame_id) REFERENCES videogames(id)
);

CREATE TABLE IF NOT EXISTS wishlists (
    user_id INT NOT NULL,
    videogame_id INT NOT NULL,
    PRIMARY KEY (user_id, videogame_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (videogame_id) REFERENCES videogames(id)
);

CREATE TABLE IF NOT EXISTS reviews (
    user_id INT NOT NULL,
    videogame_id INT NOT NULL,
    rating INT CHECK (rating >= 1 AND rating <= 5),
    comment VARCHAR(1000),
    review_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, videogame_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (videogame_id) REFERENCES videogames(id)
);

-- Inserts --------------------------------------------------------------------

INSERT INTO users (email, password, name, surname, birth_date) VALUES
('MichaelSaves03@gmail.com', 'password123', 'Michael', 'Saves', '1990-05-15'),
('TamburiniTamburelli@ngareign.er', '...', 'John', 'Sql', '2003-01-13');

INSERT INTO videogames (title, price, release_date, description) VALUES
('Elden Ring', 59.99, '2020-06-19', 'An open-world action RPG set in a dark fantasy world.');

INSERT INTO purchases (user_id, videogame_id) VALUES
(1, 1);

INSERT INTO wishlists (user_id, videogame_id) VALUES
(1, 1);

INSERT INTO reviews (user_id, videogame_id, rating, comment) VALUES
(1, 1, 5, 'Sono scarso al gioco quindi chiedo al mio amico Francesco di carriarmi');