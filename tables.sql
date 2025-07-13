-- ALTER USER 'root'@'localhost' IDENTIFIED BY 'password';
-- FLUSH PRIVILEGES;
-- Database Deletion -------------------------------------------------------------
DROP DATABASE IF EXISTS tables;

-- Tables creation -------------------------------------------------------------

CREATE DATABASE if NOT EXISTS tables;
USE tables;

CREATE TABLE if NOT EXISTS users (
    userID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(128) NOT NULL UNIQUE,
    password VARCHAR(32) NOT NULL,
    name VARCHAR(64) NOT NULL,
    surname VARCHAR(64) NOT NULL,
    birth_date DATE,
    is_administrator BOOLEAN NOT NULL,
    is_publisher BOOLEAN NOT NULL,
    is_developer BOOLEAN NOT NULL,
    is_blocked BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE if NOT EXISTS videogames (
    gameID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    publisherID INT NOT NULL,
    title VARCHAR(64) NOT NULL UNIQUE,
    price DECIMAL(10, 2) NOT NULL,
    description VARCHAR(5000),
    requirements VARCHAR(5000),
    average_rating DECIMAL(3,2) DEFAULT 2.50 CHECK (average_rating >= 1 AND average_rating <= 5),
    release_date DATE NOT NULL, 
    discount INT,
    FOREIGN KEY (publisherID) REFERENCES users(userID)
);

CREATE TABLE if NOT EXISTS videogame_developers (
    developerID INT NOT NULL,
    gameID INT NOT NULL,
    PRIMARY KEY (developerID, gameID),
    FOREIGN KEY (developerID) REFERENCES users(userID),
    FOREIGN KEY (gameID) REFERENCES videogames(gameID)
);

CREATE TABLE if NOT EXISTS genres (
    genre VARCHAR(64) NOT NULL PRIMARY KEY,
    description VARCHAR(5000)
);
CREATE TABLE if NOT EXISTS videogame_genres (
    gameID INT NOT NULL,
    genre VARCHAR(64) NOT NULL,
    PRIMARY KEY (gameID, genre),
    FOREIGN KEY (gameID) REFERENCES videogames(gameID),
    FOREIGN KEY (genre) REFERENCES genres(genre)
);

CREATE TABLE if NOT EXISTS languages (
    language_name VARCHAR(64) NOT NULL PRIMARY KEY
);

CREATE TABLE if NOT EXISTS videogame_languages (
    gameID INT NOT NULL,
    language_name VARCHAR(64) NOT NULL,
    PRIMARY KEY (gameID, language_name),
    FOREIGN KEY (gameID) REFERENCES videogames(gameID),
    FOREIGN KEY (language_name) REFERENCES languages(language_name)
);

CREATE TABLE if NOT EXISTS achievements (
    achievementID INT NOT NULL,
    gameID INT NOT NULL,
    title VARCHAR(64) NOT NULL,
    description VARCHAR(5000),
    PRIMARY KEY (achievementID, gameID),
    FOREIGN KEY (gameID) REFERENCES videogames(gameID)
);

CREATE TABLE if NOT EXISTS achievements_user (
    achievementID INT NOT NULL,
    userID INT NOT NULL,
    gameID INT NOT NULL,
    PRIMARY KEY (achievementID, userID, gameID),
    FOREIGN KEY (achievementID) REFERENCES achievements(achievementID),
    FOREIGN KEY (userID) REFERENCES users(userID),
    FOREIGN KEY (gameID) REFERENCES achievements(gameID)
);

CREATE TABLE if NOT EXISTS transactions (
	transactionID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	userID INT NOT NULL,
	total_cost INT NOT NULL,
	FOREIGN KEY (userID) REFERENCES users(userID)
);

CREATE TABLE IF NOT EXISTS transaction_items (
    transactionID INT NOT NULL,
    gameID INT NOT NULL,
    PRIMARY KEY (transactionID, gameID),
    FOREIGN KEY (transactionID) REFERENCES transactions(transactionID),
    FOREIGN KEY (gameID) REFERENCES videogames(gameID)
);

CREATE TABLE IF NOT EXISTS wishlists (
    wishlistID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    userID INT NOT NULL UNIQUE,
    FOREIGN KEY (userID) REFERENCES users(userID)
);

CREATE TABLE IF NOT EXISTS wishlist_items (
    wishlistID INT NOT NULL,
    gameID INT NOT NULL,
    PRIMARY KEY (wishlistID, gameID),
    FOREIGN KEY (wishlistID) REFERENCES wishlists(wishlistID),
    FOREIGN KEY (gameID) REFERENCES videogames(gameID)
);

CREATE TABLE IF NOT EXISTS reviews (
    userID INT NOT NULL,
    gameID INT NOT NULL,
    rating DECIMAL(2,1) CHECK (rating >= 1 AND rating <= 5),
    comment VARCHAR(1000),
    PRIMARY KEY (userID, gameID),
    FOREIGN KEY (userID) REFERENCES users(userID),
    FOREIGN KEY (gameID) REFERENCES videogames(gameID)
);