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
    is_developer BOOLEAN NOT NULL
);

CREATE TABLE if NOT EXISTS videogames (
    gameID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    userID INT NOT NULL,
    title VARCHAR(64) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    description VARCHAR(5000),
    requirements VARCHAR(5000),
    average_rating DECIMAL(2,1) CHECK (average_rating >= 1 AND average_rating <= 10),
    release_date DATE NOT NULL, 
    discount INT,
    FOREIGN KEY (userID) REFERENCES users(userID)
);

CREATE TABLE if NOT EXISTS transactions (
	transactionID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	userID INT NOT NULL,
	total_cost INT NOT NULL,
	FOREIGN KEY (userID) REFERENCES users(userID)
);

CREATE TABLE IF NOT EXISTS wishlists (
    wishlistID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    userID INT NOT NULL UNIQUE,
    FOREIGN KEY (userID) REFERENCES users(userID)
);

CREATE TABLE IF NOT EXISTS reviews (
    userID INT NOT NULL,
    gameID INT NOT NULL,
    rating DECIMAL(2,1) CHECK (rating >= 1 AND rating <= 10),
    comment VARCHAR(1000),
    PRIMARY KEY (userID, gameID),
    FOREIGN KEY (userID) REFERENCES users(userID),
    FOREIGN KEY (gameID) REFERENCES videogames(gameID)
);
-- Inserts --------------------------------------------------------------------

INSERT INTO users (email, password, name, surname, birth_date, is_administrator, is_publisher, is_developer) VALUES
('MichaelSaves03@gmail.com', 'password123', 'Michael', 'Saves', '1990-05-15', true, true, true),
('TamburiniTamburelli@ngareign.er', '...', 'John', 'Sql', '2003-01-13', false, false, false);

INSERT INTO videogames (userID, title, price, description, requirements, average_rating, release_date, discount)
SELECT u.userID, v.title, v.price, v.description, v.requirements, v.average_rating, v.release_date, v.discount
FROM (
    SELECT 2 AS userID, 'The Witcher 3: Wild Hunt' AS title, 49.99 AS price, 'Un gioco di ruolo open-world con una trama avvincente.' AS description,
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
JOIN users u ON u.userID = v.userID
WHERE u.is_developer = TRUE;

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


INSERT INTO wishlists (userID)
SELECT u.userID
FROM users u
LEFT JOIN wishlists w ON u.userID = w.userID
WHERE w.userID IS NULL;

INSERT INTO reviews (userID, gameID, rating, comment)
SELECT r.userID, r.gameID, r.rating, r.comment 
FROM (
    SELECT 1 AS userID, 1 AS gameID, 4.9 AS rating, 'Sono scarso e mi faccio carriare dal mio amico Francesco' AS comment
    UNION ALL
    SELECT 1, 2, 4.2, 'Il gioco è stato rilasciato in uno stato incompleto, ma dopo alcuni aggiornamenti è diventato molto divertente'
) r
JOIN users u ON r.userID = u.userID
JOIN videogames g ON r.gameID = g.gameID;
