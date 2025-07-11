ALTER USER 'root'@'localhost' IDENTIFIED BY 'password';
FLUSH PRIVILEGES;
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
    title VARCHAR(64) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    description VARCHAR(5000),
    requirements VARCHAR(5000),
    average_rating DECIMAL(2,1) CHECK (average_rating IS NULL OR (average_rating >= 1 AND average_rating <= 5)),
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
-- Inserts --------------------------------------------------------------------

INSERT INTO users (email, password, name, surname, birth_date, is_administrator, is_publisher, is_developer, is_blocked) VALUES
('MichaelSaves03@gmail.com', 'password123', 'Michael', 'Saves', '1990-05-15', true, true, true, false),
('TamburiniTamburelli@ngareign.er', '...', 'John', 'Sql', '2003-01-13', false, false, false, false),
('blocked.user@email.com', 'blocked123', 'Blocked', 'User', '1995-03-20', false, false, false, true);

INSERT INTO videogames (publisherID, title, price, description, requirements, average_rating, release_date, discount)
SELECT u.userID, v.title, v.price, v.description, v.requirements, v.average_rating, v.release_date, v.discount
FROM (
    SELECT 2 AS publisherID, 'The Witcher 3: Wild Hunt' AS title, 49.99 AS price, 'Un gioco di ruolo open-world con una trama avvincente.' AS description,
           'CPU: Intel Core i5, RAM: 8GB, GPU: NVIDIA GTX 970' AS requirements, 4.9 AS average_rating, '2015-05-19' AS release_date, 0 AS discount
    UNION ALL
    SELECT 6, 'Among Us', 4.99, 'Un gioco multiplayer di deduzione sociale ambientato nello spazio.',
           'CPU: Intel Core i3, RAM: 2GB, GPU: NVIDIA GTX 660', 4.5, '2018-06-15', 0
    UNION ALL
    SELECT 1, 'Sekiro: Shadows Die Twice', 59.99, 'Un gioco di azione-avventura con combattimenti impegnativi e una trama avvincente.',
           'CPU: Intel Core i5, RAM: 8GB, GPU: NVIDIA GTX 970', 4.7, '2019-03-22', 20
    UNION ALL
    SELECT 1, 'Elden Ring', 59.99, 'Un gioco di ruolo d''azione con un vasto mondo aperto e combattimenti impegnativi.',
           'CPU: Intel Core i5, RAM: 8GB, GPU: NVIDIA GTX 970', 4.8, '2022-02-25', 30
) v
JOIN users u ON u.userID = v.publisherID
WHERE u.is_publisher = TRUE;

INSERT INTO videogame_developers (developerID, gameID)
SELECT u.userID, g.gameID
FROM (
    SELECT 1 AS userID, 1 AS gameID
    UNION ALL
    SELECT 1, 2
    UNION ALL
    SELECT 1, 3
    UNION ALL
    SELECT 2, 4
) d
JOIN users u ON d.userID = u.userID
JOIN videogames g ON d.gameID = g.gameID;

INSERT INTO genres (genre, description) VALUES
('Azione', 'Giochi che enfatizzano il combattimento e l''azione rapida.'),
('Avventura', 'Giochi che si concentrano sull''esplorazione e la narrazione.'),
('RPG', 'Giochi di ruolo con elementi di personalizzazione del personaggio.'),
('Strategia', 'Giochi che richiedono pianificazione e tattica.'),
('Simulazione', 'Giochi che simulano situazioni reali o immaginarie.');

INSERT INTO videogame_genres (gameID, genre)
SELECT g.gameID, v.genre
FROM (
    SELECT 1 AS gameID, 'Azione' AS genre
    UNION ALL
    SELECT 1, 'Avventura'
    UNION ALL
    SELECT 2, 'Azione'
    UNION ALL
    SELECT 2, 'Strategia'
    UNION ALL
    SELECT 3, 'RPG'
    UNION ALL
    SELECT 4, 'RPG'
) v
JOIN videogames g ON v.gameID = g.gameID;

INSERT INTO languages (language_name) VALUES
('Italian'),
('English'),
('German'),
('French'),
('Spanish'),
('Russian'),
('Chinese'),
('Japanese');

INSERT INTO videogame_languages (gameID, language_name)
SELECT g.gameID, l.language_name
FROM (
    SELECT 1 AS gameID, 'Italian' AS language_name
    UNION ALL
    SELECT 1, 'English'
    UNION ALL
    SELECT 2, 'Italian'
    UNION ALL
    SELECT 2, 'English'
    UNION ALL
    SELECT 3, 'Italian'
    UNION ALL
    SELECT 3, 'English'
    UNION ALL
    SELECT 4, 'Italian'
    UNION ALL
    SELECT 4, 'English'
) l
JOIN videogames g ON l.gameID = g.gameID;

INSERT INTO achievements (achievementID, gameID, title, description)
SELECT a.achievementID, g.gameID, a.title, a.description
FROM (
    SELECT 1 AS achievementID, 1 AS gameID, 'Inizia l''avventura' AS title, 'Completa il tutorial iniziale.' AS description
    UNION ALL
    SELECT 2, 1, 'Maestro delle spade', 'Sconfiggi 100 nemici con la spada.'
    UNION ALL
    SELECT 1, 2, 'Stratega Supremo', 'Vinci una partita in modalità strategia senza perdere una torre.'
    UNION ALL
    SELECT 1, 3, 'Cacciatore di draghi', 'Sconfiggi un drago leggendario.'
    UNION ALL
    SELECT 1, 4, 'Eroe del regno', 'Completa la campagna principale.'
) a
JOIN videogames g ON a.gameID = g.gameID;

INSERT INTO transactions (userID, total_cost)
SELECT u.userID, t.total_cost
FROM (
    SELECT 1 AS userID, 59.99 AS total_cost
    UNION ALL
    SELECT 1, 24.99
    UNION ALL
    SELECT 1, 14.99
    UNION ALL
    SELECT 2, 49.99
) t
JOIN users u ON u.userID = t.userID;

INSERT INTO transaction_items (transactionID, gameID)
SELECT t.transactionID, g.gameID
FROM (
    SELECT 1 AS transactionID, 1 AS gameID
    UNION ALL
    SELECT 1, 2
    UNION ALL
    SELECT 1, 3
    UNION ALL
    SELECT 2, 4
) t
JOIN transactions tr ON t.transactionID = tr.transactionID
JOIN videogames g ON t.gameID = g.gameID;

INSERT INTO wishlists (userID)
SELECT u.userID
FROM users u
LEFT JOIN wishlists w ON u.userID = w.userID
WHERE w.userID IS NULL;

INSERT INTO wishlist_items (wishlistID, gameID)
SELECT w.wishlistID, g.gameID
FROM (
    SELECT 1 AS wishlistID, 1 AS gameID
    UNION ALL
    SELECT 1, 2
    UNION ALL
    SELECT 1, 3
    UNION ALL
    SELECT 2, 4
) w
JOIN wishlists wl ON w.wishlistID = wl.wishlistID
JOIN videogames g ON w.gameID = g.gameID;

INSERT INTO reviews (userID, gameID, rating, comment)
SELECT r.userID, r.gameID, r.rating, r.comment 
FROM (
    SELECT 1 AS userID, 1 AS gameID, 4.9 AS rating, 'Sono scarso e mi faccio carriare dal mio amico Francesco' AS comment
    UNION ALL
    SELECT 1, 2, 4.2, 'Il gioco è stato rilasciato in uno stato incompleto, ma dopo alcuni aggiornamenti è diventato molto divertente'
) r
JOIN users u ON r.userID = u.userID
JOIN videogames g ON r.gameID = g.gameID;

INSERT INTO achievements_user (achievementID, userID, gameID)
SELECT a.achievementID, u.userID, a.gameID
FROM (
    SELECT 1 AS achievementID, 1 AS userID, 1 AS gameID
    UNION ALL
    SELECT 2, 1, 1
    UNION ALL
    SELECT 1, 1, 2
    UNION ALL
    SELECT 1, 2, 3
) a
JOIN users u ON a.userID = u.userID
JOIN achievements ach ON a.achievementID = ach.achievementID AND a.gameID = ach.gameID;