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
-- Inserts --------------------------------------------------------------------

INSERT INTO users (email, password, name, surname, birth_date, is_administrator, is_publisher, is_developer, is_blocked) VALUES
-- Testing Users
('a', 'a', 'Michael', 'Saves', '1990-05-15', true, true, true, false),
('MichaelSaves03@gmail.com', 'password123', 'Michael', 'Saves', '1990-05-15', true, true, true, false),
('blocked.user@email.com', 'blocked123', 'Blocked', 'User', '1995-03-20', false, false, false, true),
-- Additional Administrators
('admin.sarah@steamdb.com', 'admin2024', 'Sarah', 'Johnson', '1985-07-22', true, false, false, false),
('admin.david@steamdb.com', 'admin2024', 'David', 'Chen', '1988-11-08', true, false, false, false),
-- Publishers
('ubisoft@email.com', 'ubi2024', 'Marie', 'Dubois', '1982-03-15', false, true, false, false),
('activision@email.com', 'activ2024', 'Robert', 'Williams', '1980-09-12', false, true, false, false),
('indie.studio@email.com', 'indie2024', 'Alex', 'Rodriguez', '1990-06-30', false, true, true, false),
('naughty.dog@email.com', 'naughty2024', 'Emma', 'Thompson', '1985-12-05', false, true, true, false),
('bethesda@email.com', 'beth2024', 'Mark', 'Anderson', '1983-04-18', false, true, false, false),
('valve@email.com', 'valve2024', 'Gabe', 'Newell', '1962-11-03', false, true, true, false),
-- Developers
('dev.marco@gmail.com', 'dev2024', 'Marco', 'Rossi', '1992-01-14', false, false, true, false),
('dev.laura@gmail.com', 'dev2024', 'Laura', 'Bianchi', '1989-08-20', false, false, true, false),
('dev.carlos@gmail.com', 'dev2024', 'Carlos', 'Garcia', '1991-05-07', false, false, true, false),
('dev.anna@gmail.com', 'dev2024', 'Anna', 'Mueller', '1987-10-25', false, false, true, false),

-- Regular users (32 more to reach 50 total)
('gamer.alice@email.com', 'alice2024', 'Alice', 'Smith', '1995-02-14', false, false, false, false),
('gamer.bob@email.com', 'bob2024', 'Bob', 'Davis', '1993-07-19', false, false, false, false),
('gamer.charlie@email.com', 'charlie2024', 'Charlie', 'Wilson', '1996-11-23', false, false, false, false),
('gamer.diana@email.com', 'diana2024', 'Diana', 'Taylor', '1994-04-17', false, false, false, false),
('gamer.ethan@email.com', 'ethan2024', 'Ethan', 'Brown', '1997-09-08', false, false, false, false),
('gamer.fiona@email.com', 'fiona2024', 'Fiona', 'Jones', '1992-12-03', false, false, false, false),
('gamer.george@email.com', 'george2024', 'George', 'Miller', '1990-06-12', false, false, false, false),
('gamer.helen@email.com', 'helen2024', 'Helen', 'Moore', '1988-03-27', false, false, false, false),
('gamer.ivan@email.com', 'ivan2024', 'Ivan', 'Jackson', '1991-10-15', false, false, false, false),
('gamer.julia@email.com', 'julia2024', 'Julia', 'White', '1993-05-09', false, false, false, false),
('gamer.kevin@email.com', 'kevin2024', 'Kevin', 'Harris', '1995-08-21', false, false, false, false),
('gamer.lisa@email.com', 'lisa2024', 'Lisa', 'Martin', '1989-01-16', false, false, false, false),
('gamer.mike@email.com', 'mike2024', 'Mike', 'Thompson', '1992-07-04', false, false, false, false),
('gamer.nina@email.com', 'nina2024', 'Nina', 'Garcia', '1994-11-28', false, false, false, false),
('gamer.oscar@email.com', 'oscar2024', 'Oscar', 'Rodriguez', '1996-02-12', false, false, false, false),
('gamer.paula@email.com', 'paula2024', 'Paula', 'Lewis', '1990-09-18', false, false, false, false),
('gamer.quinn@email.com', 'quinn2024', 'Quinn', 'Lee', '1993-04-25', false, false, false, false),
('gamer.rachel@email.com', 'rachel2024', 'Rachel', 'Walker', '1987-12-07', false, false, false, false),
('gamer.steve@email.com', 'steve2024', 'Steve', 'Hall', '1991-06-14', false, false, false, false),
('gamer.tina@email.com', 'tina2024', 'Tina', 'Allen', '1995-03-22', false, false, false, false),
('gamer.victor@email.com', 'victor2024', 'Victor', 'Young', '1988-10-11', false, false, false, false),
('gamer.wendy@email.com', 'wendy2024', 'Wendy', 'King', '1992-01-29', false, false, false, false),
('gamer.xavier@email.com', 'xavier2024', 'Xavier', 'Wright', '1994-08-16', false, false, false, false),
('gamer.yara@email.com', 'yara2024', 'Yara', 'Lopez', '1996-05-03', false, false, false, false),
('gamer.zack@email.com', 'zack2024', 'Zack', 'Hill', '1989-11-20', false, false, false, false),
('gamer.amy@email.com', 'amy2024', 'Amy', 'Scott', '1993-02-08', false, false, false, false),
('gamer.ben@email.com', 'ben2024', 'Ben', 'Green', '1991-07-15', false, false, false, false),
('gamer.chloe@email.com', 'chloe2024', 'Chloe', 'Adams', '1995-12-01', false, false, false, false),
('gamer.dan@email.com', 'dan2024', 'Dan', 'Baker', '1987-04-09', false, false, false, false),
('gamer.eva@email.com', 'eva2024', 'Eva', 'Gonzalez', '1990-09-26', false, false, false, false),
('gamer.finn@email.com', 'finn2024', 'Finn', 'Nelson', '1994-01-13', false, false, false, false),
('gamer.grace@email.com', 'grace2024', 'Grace', 'Carter', '1992-06-30', false, false, false, false);

INSERT INTO videogames (publisherID, title, price, description, requirements, release_date, discount)
SELECT u.userID, v.title, v.price, v.description, v.requirements, v.release_date, v.discount
FROM (
    SELECT 1 AS publisherID, 'The Witcher 3: Wild Hunt' AS title, 49.99 AS price, 'Un gioco di ruolo open-world con una trama avvincente.' AS description,
           'CPU: Intel Core i5, RAM: 8GB, GPU: NVIDIA GTX 970' AS requirements, '2015-05-19' AS release_date, 0 AS discount
    UNION ALL
    SELECT 2, 'Among Us', 4.99, 'Un gioco multiplayer di deduzione sociale ambientato nello spazio.',
           'CPU: Intel Core i3, RAM: 2GB, GPU: NVIDIA GTX 660', '2018-06-15', 0
    UNION ALL
    SELECT 1, 'Sekiro: Shadows Die Twice', 59.99, 'Un gioco di azione-avventura con combattimenti impegnativi e una trama avvincente.',
           'CPU: Intel Core i5, RAM: 8GB, GPU: NVIDIA GTX 970', '2019-03-22', 20
    UNION ALL
    SELECT 1, 'Elden Ring', 59.99, 'Un gioco di ruolo d''azione con un vasto mondo aperto e combattimenti impegnativi.',
           'CPU: Intel Core i5, RAM: 8GB, GPU: NVIDIA GTX 970', '2022-02-25', 30
    UNION ALL
    SELECT 2, 'Dragon Age: Veilguard', 39.99, 'Un gioco di ruolo fantasy con una trama avvincente e personaggi memorabili.',
           'CPU: Intel Core i5, RAM: 8GB, GPU: NVIDIA GTX 970', '2023-05-10', 90
    UNION ALL
    SELECT 7 AS publisherID, 'Assassin''s Creed Valhalla' AS title, 59.99 AS price, 'Vivi l''epoca d''oro dei vichinghi e guida Eivor in una saga leggendaria.' AS description,
           'CPU: Intel Core i5-4460, RAM: 8GB, GPU: NVIDIA GTX 960' AS requirements, '2020-11-10' AS release_date, 25 AS discount
    UNION ALL
    SELECT 8, 'Call of Duty: Modern Warfare II', 69.99, 'Il sequel del rinomato Call of Duty: Modern Warfare con campagna e multiplayer.',
           'CPU: Intel Core i5-6600K, RAM: 12GB, GPU: NVIDIA GTX 1060', '2022-10-28', 15
    UNION ALL
    SELECT 9, 'Hollow Knight', 14.99, 'Un classico Metroidvania 2D con atmosfera gotica e gameplay impegnativo.',
           'CPU: Intel Core 2 Duo E5200, RAM: 4GB, GPU: GeForce 9800GTX', '2017-02-24', 0
    UNION ALL
    SELECT 10, 'The Last of Us Part II', 39.99, 'Sequel dell''acclamato survival horror post-apocalittico.',
           'CPU: Intel Core i5-8400, RAM: 8GB, GPU: NVIDIA GTX 1060', '2020-06-19', 10
    UNION ALL
    SELECT 11, 'The Elder Scrolls V: Skyrim', 19.99, 'Il leggendario RPG fantasy open-world ambientato in Skyrim.',
           'CPU: Intel Core i5-750, RAM: 4GB, GPU: NVIDIA GTX 470', '2011-11-11', 0
    UNION ALL
    SELECT 12, 'Half-Life: Alyx', 59.99, 'L''attesissimo ritorno della serie Half-Life in realtà virtuale.',
           'CPU: Intel Core i5-7500, RAM: 12GB, GPU: NVIDIA GTX 1060', '2020-03-23', 20
    UNION ALL
    SELECT 7, 'Far Cry 6', 49.99, 'Combatti la dittatura nell''isola tropicale fittizia di Yara.',
           'CPU: Intel Core i5-4460, RAM: 8GB, GPU: NVIDIA GTX 960', '2021-10-07', 30
    UNION ALL
    SELECT 8, 'Overwatch 2', 0.00, 'Il sequel free-to-play del famoso hero shooter di Blizzard.',
           'CPU: Intel Core i3, RAM: 6GB, GPU: NVIDIA GTX 600', '2022-10-04', 0
    UNION ALL
    SELECT 9, 'Celeste', 19.99, 'Un platformer challenging con una storia toccante sulla salute mentale.',
           'CPU: Intel Core 2 Duo E5200, RAM: 2GB, GPU: GeForce 9800GTX', '2018-01-25', 0
    UNION ALL
    SELECT 10, 'Uncharted 4: A Thief''s End', 19.99, 'L''ultima avventura di Nathan Drake piena di azione e scoperte.',
           'CPU: Intel Core i5-4430, RAM: 8GB, GPU: NVIDIA GTX 960', '2016-05-10', 0
    UNION ALL
    SELECT 11, 'Fallout 4', 29.99, 'Survival RPG post-apocalittico ambientato nella Boston devastata.',
           'CPU: Intel Core i5-2300, RAM: 8GB, GPU: NVIDIA GTX 550 Ti', '2015-11-10', 0
    UNION ALL
    SELECT 12, 'Portal 2', 9.99, 'Il sequel del rivoluzionario puzzle game con meccaniche innovative.',
           'CPU: Intel Core 2 Duo E6600, RAM: 2GB, GPU: NVIDIA GeForce 7600GS', '2011-04-19', 0
    UNION ALL
    SELECT 9, 'Hades', 24.99, 'Roguelike isometrico ambientato negli Inferi della mitologia greca.',
           'CPU: Dual Core 3.0ghz, RAM: 4GB, GPU: DirectX 10 compatible', '2020-09-17', 0
    UNION ALL
    SELECT 7, 'Watch Dogs: Legion', 39.99, 'Hacker cyberpunk ambientato in una Londra distopica.',
           'CPU: Intel Core i5-4460, RAM: 8GB, GPU: NVIDIA GTX 960', '2020-10-29', 40
    UNION ALL
    SELECT 8, 'Diablo IV', 69.99, 'Il ritorno della leggendaria serie action RPG di Blizzard.',
           'CPU: Intel Core i5-2500K, RAM: 8GB, GPU: NVIDIA GTX 660', '2023-06-06', 0
    UNION ALL
    SELECT 12, 'Counter-Strike 2', 0.00, 'Il nuovo capitolo del leggendario FPS competitivo.',
           'CPU: Intel Core i5-750, RAM: 8GB, GPU: NVIDIA GTX 1060', '2023-09-27', 0
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
    UNION ALL
    SELECT 13, 5 
    UNION ALL
    SELECT 14, 5
    UNION ALL
    SELECT 15, 6
    UNION ALL
    SELECT 16, 7  -- Anna develops Hollow Knight
    UNION ALL
    SELECT 9, 7   -- Indie studio also develops Hollow Knight
    UNION ALL
    SELECT 10, 8  -- Naughty Dog develops The Last of Us Part II
    UNION ALL
    SELECT 13, 9  -- Marco develops Skyrim
    UNION ALL
    SELECT 14, 9  -- Laura also develops Skyrim
    UNION ALL
    SELECT 12, 10 -- Valve develops Half-Life: Alyx
    UNION ALL
    SELECT 15, 11 -- Carlos develops Far Cry 6
    UNION ALL
    SELECT 16, 12 -- Anna develops Overwatch 2
    UNION ALL
    SELECT 9, 13  -- Indie studio develops Celeste
    UNION ALL
    SELECT 10, 14 -- Naughty Dog develops Uncharted 4
    UNION ALL
    SELECT 11, 15 -- Bethesda develops Fallout 4
    UNION ALL
    SELECT 12, 16 -- Valve develops Portal 2
    UNION ALL
    SELECT 9, 17  -- Indie studio develops Hades
    UNION ALL
    SELECT 13, 18 -- Marco develops Watch Dogs: Legion
    UNION ALL
    SELECT 15, 19 -- Carlos develops Diablo IV
    UNION ALL
    SELECT 12, 20 -- Valve develops Counter-Strike 2
) d
JOIN users u ON d.userID = u.userID
JOIN videogames g ON d.gameID = g.gameID
WHERE u.is_developer = TRUE;

INSERT INTO genres (genre, description) VALUES
('Azione', 'Giochi che enfatizzano il combattimento e l''azione rapida.'),
('Avventura', 'Giochi che si concentrano sull''esplorazione e la narrazione.'),
('RPG', 'Giochi di ruolo con elementi di personalizzazione del personaggio.'),
('Strategia', 'Giochi che richiedono pianificazione e tattica.'),
('Simulazione', 'Giochi che simulano situazioni reali o immaginarie.'),
('Sparatutto', 'Giochi che si concentrano sul combattimento con armi da fuoco.'),
('Survival', 'Giochi che enfatizzano la sopravvivenza in ambienti ostili.'),
('Horror', 'Giochi progettati per spaventare e creare tensione.'),
('Sport', 'Simulazioni di sport reali o fantastici.'),
('Corsa', 'Giochi di corse con veicoli di vario tipo.'),
('Puzzle', 'Giochi che richiedono risoluzione di enigmi e ragionamento logico.');

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
    UNION ALL
    SELECT 5 AS gameID, 'Azione' AS genre
    UNION ALL
    SELECT 5, 'Avventura'
    UNION ALL
    SELECT 6, 'Sparatutto'
    UNION ALL
    SELECT 6, 'Azione'
    UNION ALL
    SELECT 7, 'Avventura'
    UNION ALL
    SELECT 7, 'Azione'
    UNION ALL
    SELECT 8, 'Azione'
    UNION ALL
    SELECT 8, 'Survival'
    UNION ALL
    SELECT 9, 'RPG'
    UNION ALL
    SELECT 9, 'Avventura'
    UNION ALL
    SELECT 10, 'Sparatutto'
    UNION ALL
    SELECT 10, 'Azione'
    UNION ALL
    SELECT 11, 'Azione'
    UNION ALL
    SELECT 11, 'Avventura'
    UNION ALL
    SELECT 12, 'Sparatutto'
    UNION ALL
    SELECT 12, 'Azione'
    UNION ALL
    SELECT 13, 'Azione'
    UNION ALL
    SELECT 13, 'Avventura'
    UNION ALL
    SELECT 14, 'Azione'
    UNION ALL
    SELECT 14, 'Avventura'
    UNION ALL
    SELECT 15, 'RPG'
    UNION ALL
    SELECT 15, 'Survival'
    UNION ALL
    SELECT 16, 'Puzzle'
    UNION ALL
    SELECT 16, 'Azione'
    UNION ALL
    SELECT 17, 'Azione'
    UNION ALL
    SELECT 17, 'RPG'
    UNION ALL
    SELECT 18, 'Azione'
    UNION ALL
    SELECT 18, 'Avventura'
    UNION ALL
    SELECT 19, 'RPG'
    UNION ALL
    SELECT 19, 'Azione'
    UNION ALL
    SELECT 20, 'Sparatutto'
    UNION ALL
    SELECT 20, 'Strategia'
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
    UNION ALL
    SELECT 5, 'English'
    UNION ALL
    SELECT 5, 'French'
    UNION ALL
    SELECT 5, 'German'
    UNION ALL
    SELECT 6, 'Italian'
    UNION ALL
    SELECT 6, 'English'
    UNION ALL
    SELECT 6, 'Spanish'
    UNION ALL
    SELECT 7, 'English'
    UNION ALL
    SELECT 8, 'Italian'
    UNION ALL
    SELECT 8, 'English'
    UNION ALL
    SELECT 9, 'Italian'
    UNION ALL
    SELECT 9, 'English'
    UNION ALL
    SELECT 9, 'German'
    UNION ALL
    SELECT 9, 'French'
    UNION ALL
    SELECT 10, 'Italian'
    UNION ALL
    SELECT 10, 'English'
    UNION ALL
    SELECT 11, 'Italian'
    UNION ALL
    SELECT 11, 'English'
    UNION ALL
    SELECT 11, 'French'
    UNION ALL
    SELECT 12, 'Italian'
    UNION ALL
    SELECT 12, 'English'
    UNION ALL
    SELECT 12, 'Spanish'
    UNION ALL
    SELECT 13, 'English'
    UNION ALL
    SELECT 14, 'Italian'
    UNION ALL
    SELECT 14, 'English'
    UNION ALL
    SELECT 15, 'Italian'
    UNION ALL
    SELECT 15, 'English'
    UNION ALL
    SELECT 15, 'German'
    UNION ALL
    SELECT 16, 'Italian'
    UNION ALL
    SELECT 16, 'English'
    UNION ALL
    SELECT 17, 'English'
    UNION ALL
    SELECT 18, 'Italian'
    UNION ALL
    SELECT 18, 'English'
    UNION ALL
    SELECT 18, 'French'
    UNION ALL
    SELECT 19, 'Italian'
    UNION ALL
    SELECT 19, 'English'
    UNION ALL
    SELECT 19, 'Spanish'
    UNION ALL
    SELECT 20, 'Italian'
    UNION ALL
    SELECT 20, 'English'
    UNION ALL
    SELECT 20, 'Russian'
    UNION ALL
    SELECT 20, 'Chinese'
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
    UNION ALL
    SELECT 1, 5, 'Vichingo Leggendario', 'Completa la storia principale di Valhalla.'
    UNION ALL
    SELECT 2, 5, 'Esploratore del Nord', 'Scopri tutte le regioni dell''Inghilterra.'
    UNION ALL
    SELECT 1, 6, 'Soldato d''Elite', 'Completa la campagna in difficoltà Veterano.'
    UNION ALL
    SELECT 2, 6, 'Operatore Tattico', 'Vinci 50 partite multiplayer.'
    UNION ALL
    SELECT 1, 7, 'Cavaliere dell''Ombra', 'Completa il gioco senza morire.'
    UNION ALL
    SELECT 1, 8, 'Sopravvissuto', 'Completa la storia di Ellie e Abby.'
    UNION ALL
    SELECT 1, 9, 'Dovahkiin', 'Completa la missione principale di Skyrim.'
    UNION ALL
    SELECT 2, 9, 'Maestro di Tutte le Arti', 'Raggiungi il livello 100 in tutte le abilità.'
    UNION ALL
    SELECT 1, 10, 'Benvenuto nella Resistenza', 'Completa il prologo di Half-Life: Alyx.'
    UNION ALL
    SELECT 1, 11, 'Rivoluzionario', 'Libera Yara dalla dittatura.'
    UNION ALL
    SELECT 1, 12, 'Eroe di Overwatch', 'Vinci la tua prima partita competitiva.'
    UNION ALL
    SELECT 1, 13, 'Scalatore Supremo', 'Completa tutti i livelli di Celeste.'
    UNION ALL
    SELECT 1, 14, 'Cacciatore di Tesori', 'Trova tutti i tesori nascosti.'
    UNION ALL
    SELECT 1, 15, 'Generale della Minutemen', 'Diventa il leader della fazione Minutemen.'
    UNION ALL
    SELECT 1, 16, 'Genio dei Portali', 'Completa tutti i test di Portal 2.'
    UNION ALL
    SELECT 1, 17, 'Figlio degli Inferi', 'Completa la tua prima fuga da Hades.'
    UNION ALL
    SELECT 1, 18, 'Hacker Supremo', 'Completa tutte le missioni principali.'
    UNION ALL
    SELECT 1, 19, 'Cacciatore di Demoni', 'Raggiungi il livello massimo.'
    UNION ALL
    SELECT 1, 20, 'Leggenda Globale', 'Raggiungi il grado Global Elite.'
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
    UNION ALL
    SELECT 17 AS userID, 249.95 AS total_cost  -- Alice buys multiple games
    UNION ALL
    SELECT 17, 69.99  -- Alice buys another game
    UNION ALL
    SELECT 18, 159.97  -- Bob buys games
    UNION ALL
    SELECT 19, 89.98   -- Charlie buys games
    UNION ALL
    SELECT 20, 199.95  -- Diana buys games
    UNION ALL
    SELECT 21, 49.99   -- Ethan buys one game
    UNION ALL
    SELECT 22, 129.97  -- Fiona buys games
    UNION ALL
    SELECT 23, 79.98   -- George buys games
    UNION ALL
    SELECT 24, 39.99   -- Helen buys one game
    UNION ALL
    SELECT 25, 89.97   -- Ivan buys games
    UNION ALL
    SELECT 26, 149.96  -- Julia buys games
    UNION ALL
    SELECT 27, 59.99   -- Kevin buys games
    UNION ALL
    SELECT 28, 99.98   -- Lisa buys games
    UNION ALL
    SELECT 29, 119.97  -- Mike buys games
    UNION ALL
    SELECT 30, 29.99   -- Nina buys one game
    UNION ALL
    SELECT 31, 179.95  -- Oscar buys games
    UNION ALL
    SELECT 32, 89.98   -- Paula buys games
    UNION ALL
    SELECT 33, 69.99   -- Quinn buys games
    UNION ALL
    SELECT 34, 139.96  -- Rachel buys games
    UNION ALL
    SELECT 35, 49.99   -- Steve buys games
    UNION ALL
    SELECT 36, 99.97   -- Tina buys games
    UNION ALL
    SELECT 37, 79.98   -- Victor buys games
    UNION ALL
    SELECT 38, 159.96  -- Wendy buys games
    UNION ALL
    SELECT 39, 19.99   -- Xavier buys games
    UNION ALL
    SELECT 40, 129.97  -- Yara buys games
    UNION ALL
    SELECT 41, 89.98   -- Zack buys games
    UNION ALL
    SELECT 42, 199.95  -- Amy buys games
    UNION ALL
    SELECT 43, 59.99   -- Ben buys games
    UNION ALL
    SELECT 44, 109.98  -- Chloe buys games
    UNION ALL
    SELECT 45, 39.99   -- Dan buys games
    UNION ALL
    SELECT 46, 149.97  -- Eva buys games
    UNION ALL
    SELECT 47, 79.99   -- Finn buys games
    UNION ALL
    SELECT 48, 119.98  -- Grace buys games
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
    UNION ALL
    SELECT 5, 6
    UNION ALL
    SELECT 5, 7
    UNION ALL
    SELECT 5, 8
    UNION ALL
    SELECT 6, 9
    UNION ALL
    SELECT 7, 10
    UNION ALL
    SELECT 7, 11
    UNION ALL
    SELECT 7, 12
    UNION ALL
    SELECT 8, 13
    UNION ALL
    SELECT 8, 14
    UNION ALL
    SELECT 8, 15
    UNION ALL
    SELECT 9, 5
    UNION ALL
    SELECT 9, 6
    UNION ALL
    SELECT 9, 16
    UNION ALL
    SELECT 10, 17
    UNION ALL
    SELECT 11, 18
    UNION ALL
    SELECT 11, 19
    UNION ALL
    SELECT 11, 9
    UNION ALL
    SELECT 12, 20
    UNION ALL
    SELECT 12, 7
    UNION ALL
    SELECT 12, 13
    UNION ALL
    SELECT 13, 8
    UNION ALL
    SELECT 14, 15
    UNION ALL
    SELECT 14, 16
    UNION ALL
    SELECT 14, 17
    UNION ALL
    SELECT 15, 5
    UNION ALL
    SELECT 15, 9
    UNION ALL
    SELECT 15, 11
    UNION ALL
    SELECT 15, 14
    UNION ALL
    SELECT 16, 6  -- Kevin
    UNION ALL
    SELECT 17, 10  -- Lisa
    UNION ALL
    SELECT 17, 12
    UNION ALL
    SELECT 18, 13  -- Mike
    UNION ALL
    SELECT 18, 15
    UNION ALL
    SELECT 18, 17
    UNION ALL
    SELECT 19, 19  -- Nina
    UNION ALL
    SELECT 20, 5   -- Oscar
    UNION ALL
    SELECT 20, 8
    UNION ALL
    SELECT 20, 11
    UNION ALL
    SELECT 20, 18
    UNION ALL
    SELECT 21, 7   -- Paula
    UNION ALL
    SELECT 21, 14
    UNION ALL
    SELECT 22, 6   -- Quinn
    UNION ALL
    SELECT 23, 9   -- Rachel
    UNION ALL
    SELECT 23, 16
    UNION ALL
    SELECT 23, 20
    UNION ALL
    SELECT 24, 17  -- Steve
    UNION ALL
    SELECT 25, 5   -- Tina
    UNION ALL
    SELECT 25, 13
    UNION ALL
    SELECT 25, 15
    UNION ALL
    SELECT 26, 10  -- Victor
    UNION ALL
    SELECT 26, 12
    UNION ALL
    SELECT 27, 6   -- Wendy
    UNION ALL
    SELECT 27, 8
    UNION ALL
    SELECT 27, 11
    UNION ALL
    SELECT 27, 18
    UNION ALL
    SELECT 28, 7   -- Xavier
    UNION ALL
    SELECT 29, 9   -- Yara
    UNION ALL
    SELECT 29, 14
    UNION ALL
    SELECT 29, 19
    UNION ALL
    SELECT 30, 13  -- Zack
    UNION ALL
    SELECT 30, 16
    UNION ALL
    SELECT 31, 5   -- Amy
    UNION ALL
    SELECT 31, 6
    UNION ALL
    SELECT 31, 8
    UNION ALL
    SELECT 31, 11
    UNION ALL
    SELECT 31, 15
    UNION ALL
    SELECT 32, 17  -- Ben
    UNION ALL
    SELECT 33, 10  -- Chloe
    UNION ALL
    SELECT 33, 12
    UNION ALL
    SELECT 33, 20
    UNION ALL
    SELECT 34, 18  -- Dan
    UNION ALL
    SELECT 35, 5   -- Eva
    UNION ALL
    SELECT 35, 7
    UNION ALL
    SELECT 35, 9
    UNION ALL
    SELECT 35, 14
    UNION ALL
    SELECT 36, 19  -- Finn
    UNION ALL
    SELECT 37, 6   -- Grace
    UNION ALL
    SELECT 37, 11
    UNION ALL
    SELECT 37, 16
    UNION ALL
    SELECT 37, 20
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
    -- Various users with different wishlist preferences
    SELECT 17 AS wishlistID, 19 AS gameID  -- Alice wants Diablo IV
    UNION ALL
    SELECT 17, 20  -- Alice wants Counter-Strike 2
    UNION ALL
    SELECT 18, 5   -- Bob wants Assassin's Creed Valhalla
    UNION ALL
    SELECT 18, 9   -- Bob wants Skyrim
    UNION ALL
    SELECT 19, 10  -- Charlie wants Half-Life: Alyx
    UNION ALL
    SELECT 19, 16  -- Charlie wants Portal 2
    UNION ALL
    SELECT 20, 7   -- Diana wants Hollow Knight
    UNION ALL
    SELECT 20, 13  -- Diana wants Celeste
    UNION ALL
    SELECT 21, 6   -- Ethan wants Call of Duty
    UNION ALL
    SELECT 21, 12  -- Ethan wants Overwatch 2
    UNION ALL
    SELECT 22, 15  -- Fiona wants Fallout 4
    UNION ALL
    SELECT 22, 11  -- Fiona wants Far Cry 6
    UNION ALL
    SELECT 23, 8   -- George wants The Last of Us Part II
    UNION ALL
    SELECT 23, 14  -- George wants Uncharted 4
    UNION ALL
    SELECT 24, 19  -- Helen wants Diablo IV
    UNION ALL
    SELECT 25, 5   -- Ivan wants Assassin's Creed
    UNION ALL
    SELECT 25, 18  -- Ivan wants Watch Dogs
    UNION ALL
    SELECT 26, 16  -- Julia wants Portal 2
    UNION ALL
    SELECT 26, 17  -- Julia wants Hades
    UNION ALL
    SELECT 27, 20  -- Kevin wants Counter-Strike 2
    UNION ALL
    SELECT 28, 7   -- Lisa wants Hollow Knight
    UNION ALL
    SELECT 28, 13  -- Lisa wants Celeste
    UNION ALL
    SELECT 29, 6   -- Mike wants Call of Duty
    UNION ALL
    SELECT 29, 8   -- Mike wants The Last of Us
    UNION ALL
    SELECT 30, 9   -- Nina wants Skyrim
    UNION ALL
    SELECT 31, 10  -- Oscar wants Half-Life
    UNION ALL
    SELECT 31, 12  -- Oscar wants Overwatch 2
    UNION ALL
    SELECT 32, 15  -- Paula wants Fallout 4
    UNION ALL
    SELECT 33, 17  -- Quinn wants Hades
    UNION ALL
    SELECT 34, 11  -- Rachel wants Far Cry 6
    UNION ALL
    SELECT 35, 19  -- Steve wants Diablo IV
    UNION ALL
    SELECT 36, 7   -- Tina wants Hollow Knight
    UNION ALL
    SELECT 37, 16  -- Victor wants Portal 2
    UNION ALL
    SELECT 38, 5   -- Wendy wants Assassin's Creed
    UNION ALL
    SELECT 39, 13  -- Xavier wants Celeste
    UNION ALL
    SELECT 40, 20  -- Yara wants Counter-Strike 2
    UNION ALL
    SELECT 41, 8   -- Zack wants The Last of Us
    UNION ALL
    SELECT 42, 6   -- Amy wants Call of Duty
    UNION ALL
    SELECT 43, 14  -- Ben wants Uncharted 4
    UNION ALL
    SELECT 44, 18  -- Chloe wants Watch Dogs
    UNION ALL
    SELECT 45, 9   -- Dan wants Skyrim
    UNION ALL
    SELECT 46, 17  -- Eva wants Hades
    UNION ALL
    SELECT 47, 10  -- Finn wants Half-Life
    UNION ALL
    SELECT 48, 15  -- Grace wants Fallout 4
) w
JOIN wishlists wl ON w.wishlistID = wl.wishlistID
JOIN videogames g ON w.gameID = g.gameID
WHERE NOT EXISTS (
    SELECT 1 
    FROM transactions t
    JOIN transaction_items ti ON t.transactionID = ti.transactionID
    WHERE t.userID = wl.userID 
    AND ti.gameID = w.gameID
);

INSERT INTO reviews (userID, gameID, rating, comment)
SELECT r.userID, r.gameID, r.rating, r.comment 
FROM (
    SELECT 1 AS userID, 1 AS gameID, 4.9 AS rating, 'Il gioco è stato rilasciato in uno stato incompleto, ma dopo alcuni aggiornamenti è diventato molto divertente' AS comment
    UNION ALL
    SELECT 17, 6, 4.0, 'Buona campagna ma il multiplayer potrebbe essere migliorato.'
    UNION ALL
    SELECT 17, 7, 5.0, 'Capolavoro assoluto! Atmosfera e gameplay perfetti.'
    UNION ALL
    SELECT 17, 8, 4.8, 'Storia emotivamente intensa, personaggi incredibili.'
    UNION ALL
    
    -- Reviews from Bob (userID 18)
    SELECT 18, 10, 4.7, 'Innovativo uso della VR, esperienza incredibile!'
    UNION ALL
    SELECT 18, 11, 3.5, 'Bello ma troppo ripetitivo dopo un po''.'
    UNION ALL
    SELECT 18, 12, 3.8, 'Divertente ma manca contenuto rispetto al primo.'
    UNION ALL
    
    -- Reviews from Charlie (userID 19)
    SELECT 19, 13, 4.9, 'Perfetto bilanciamento tra sfida e divertimento!'
    UNION ALL
    SELECT 19, 14, 4.6, 'Grafica mozzafiato e gameplay coinvolgente.'
    UNION ALL
    SELECT 19, 15, 4.1, 'Buon RPG ma con alcuni bug al lancio.'
    UNION ALL
    
    -- Reviews from Diana (userID 20)
    SELECT 20, 5, 4.2, 'Bell''ambientazione vichinga ma missioni ripetitive.'
    UNION ALL
    SELECT 20, 6, 3.9, 'Solido shooter ma niente di rivoluzionario.'
    UNION ALL
    SELECT 20, 16, 5.0, 'Geniale! Meccaniche di gioco innovative e divertenti.'
    UNION ALL
    
    -- Reviews from Ethan (userID 21)
    SELECT 21, 17, 4.8, 'Roguelike perfetto con narrazione eccellente!'
    UNION ALL
    
    -- Reviews from other users
    SELECT 22, 18, 3.6, 'Concetto interessante ma esecuzione mediocre.'
    UNION ALL
    SELECT 22, 19, 4.3, 'Ritorno in grande stile per la serie Diablo!'
    UNION ALL
    SELECT 22, 9, 4.9, 'Il miglior RPG mai creato, infinito da esplorare.'
    UNION ALL
    
    SELECT 23, 20, 4.4, 'Miglioramenti grafici notevoli, ottimo competitivo.'
    UNION ALL
    SELECT 23, 7, 4.9, 'Metroidvania perfetto con boss design eccezionale.'
    UNION ALL
    SELECT 23, 13, 4.7, 'Platformer challenging con messaggio importante.'
    UNION ALL
    
    SELECT 24, 8, 4.7, 'Sequel all''altezza del predecessore.'
    UNION ALL
    
    SELECT 25, 15, 4.0, 'Mondo vasto ma alcune missioni sono noiose.'
    UNION ALL
    SELECT 25, 16, 4.9, 'Puzzle brillanti e umorismo fantastico!'
    UNION ALL
    SELECT 25, 17, 4.8, 'Gameplay addictivo con ottima progressione.'
    UNION ALL
    
    SELECT 26, 10, 4.6, 'VR experience straordinaria per i fan di Half-Life.'
    UNION ALL
    SELECT 26, 12, 3.7, 'Versione gratuita che sembra incompleta.'
    UNION ALL
    
    SELECT 27, 6, 4.1, 'Buona campagna single-player, multiplayer solido.'
    UNION ALL
    SELECT 27, 8, 4.5, 'Storia toccante con personaggi ben sviluppati.'
    UNION ALL
    SELECT 27, 11, 3.8, 'Bello visivamente ma gameplay ripetitivo.'
    UNION ALL
    SELECT 27, 18, 3.4, 'Meccaniche interessanti ma mal implementate.'
    UNION ALL
    
    SELECT 28, 7, 5.0, 'Perfetto in ogni aspetto, un must-have!'
    UNION ALL
    
    SELECT 29, 9, 4.8, 'Classico intramontabile con infinite possibilità.'
    UNION ALL
    SELECT 29, 14, 4.4, 'Avventura cinematografica ben realizzata.'
    UNION ALL
    SELECT 29, 19, 4.2, 'Buon ritorno alle origini per Diablo.'
    UNION ALL
    
    SELECT 30, 13, 4.6, 'Difficile ma gratificante, storia commovente.'
    UNION ALL
    SELECT 30, 16, 4.9, 'Puzzle game geniale con dialoghi divertenti.'
    UNION ALL
    
    SELECT 31, 5, 4.3, 'Ambientazione affascinante, troppo lungo però.'
    UNION ALL
    SELECT 31, 6, 3.8, 'Campagna decente, multiplayer competitivo.'
    UNION ALL
    SELECT 31, 8, 4.6, 'Emotivamente devastante ma bellissimo.'
    UNION ALL
    SELECT 31, 11, 3.7, 'Bello da vedere ma gameplay noioso.'
    UNION ALL
    SELECT 31, 15, 4.0, 'Post-apocalittico ben fatto con crafting interessante.'
    UNION ALL
    
    SELECT 32, 17, 4.7, 'Roguelike con narrativa eccellente!'
    UNION ALL
    
    SELECT 33, 10, 4.5, 'Half-Life torna in grande stile in VR.'
    UNION ALL
    SELECT 33, 12, 3.9, 'Divertente ma manca profondità del primo.'
    UNION ALL
    SELECT 33, 20, 4.3, 'Aggiornamento grafico apprezzabile.'
    UNION ALL
    
    SELECT 34, 18, 3.5, 'Concept promettente ma esecuzione lacunosa.'
    UNION ALL
    
    SELECT 35, 5, 4.1, 'Vichinghi e azione, cosa volere di più?'
    UNION ALL
    SELECT 35, 7, 4.8, 'Arte pixelata sublime e gameplay perfetto.'
    UNION ALL
    SELECT 35, 9, 4.7, 'RPG che ridefinisce il genere open-world.'
    UNION ALL
    SELECT 35, 14, 4.3, 'Conclusione degna per Nathan Drake.'
    UNION ALL
    
    SELECT 36, 19, 4.0, 'Diablo è tornato ma con qualche compromesso.'
    UNION ALL
    
    SELECT 37, 6, 4.2, 'Solid FPS con buona varietà di modalità.'
    UNION ALL
    SELECT 37, 11, 3.6, 'Visivamente impressionante ma gameplay ripetitivo.'
    UNION ALL
    SELECT 37, 16, 5.0, 'Capolavoro di game design e scrittura!'
    UNION ALL
    SELECT 37, 20, 4.4, 'Il miglior FPS competitivo disponibile.'
) r
JOIN users u ON r.userID = u.userID
JOIN videogames g ON r.gameID = g.gameID
WHERE EXISTS (
    SELECT 1 FROM transaction_items ti 
    JOIN transactions t ON ti.transactionID = t.transactionID 
    WHERE t.userID = r.userID AND ti.gameID = r.gameID
);

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
    UNION ALL
    SELECT 1 AS achievementID, 17 AS userID, 5 AS gameID
    UNION ALL
    SELECT 2, 17, 5
    UNION ALL
    SELECT 1, 17, 6
    UNION ALL
    SELECT 1, 17, 7
    UNION ALL
    SELECT 1, 17, 8
    UNION ALL
    
    -- Bob's achievements
    SELECT 1, 18, 10
    UNION ALL
    SELECT 1, 18, 11
    UNION ALL
    SELECT 1, 18, 12
    UNION ALL
    
    -- Charlie's achievements
    SELECT 1, 19, 13
    UNION ALL
    SELECT 1, 19, 14
    UNION ALL
    SELECT 1, 19, 15
    UNION ALL
    
    -- Diana's achievements
    SELECT 1, 20, 5
    UNION ALL
    SELECT 1, 20, 6
    UNION ALL
    SELECT 1, 20, 16
    UNION ALL
    
    -- Ethan's achievements
    SELECT 1, 21, 17
    UNION ALL
    
    -- Other users' achievements
    SELECT 1, 22, 18
    UNION ALL
    SELECT 1, 22, 19
    UNION ALL
    SELECT 1, 22, 9
    UNION ALL
    SELECT 2, 22, 9
    UNION ALL
    
    SELECT 1, 23, 20
    UNION ALL
    SELECT 1, 23, 7
    UNION ALL
    SELECT 1, 23, 13
    UNION ALL
    
    SELECT 1, 24, 8
    UNION ALL
    
    SELECT 1, 25, 15
    UNION ALL
    SELECT 1, 25, 16
    UNION ALL
    SELECT 1, 25, 17
    UNION ALL
    
    SELECT 1, 26, 10
    UNION ALL
    SELECT 1, 26, 12
    UNION ALL
    
    SELECT 1, 27, 6
    UNION ALL
    SELECT 2, 27, 6
    UNION ALL
    SELECT 1, 27, 8
    UNION ALL
    SELECT 1, 27, 11
    UNION ALL
    SELECT 1, 27, 18
    UNION ALL
    
    SELECT 1, 28, 7
    UNION ALL
    
    SELECT 1, 29, 9
    UNION ALL
    SELECT 1, 29, 14
    UNION ALL
    SELECT 1, 29, 19
    UNION ALL
    
    SELECT 1, 30, 13
    UNION ALL
    SELECT 1, 30, 16
    UNION ALL
    
    SELECT 1, 31, 5
    UNION ALL
    SELECT 1, 31, 6
    UNION ALL
    SELECT 1, 31, 8
    UNION ALL
    SELECT 1, 31, 11
    UNION ALL
    SELECT 1, 31, 15
    UNION ALL
    
    SELECT 1, 32, 17
    UNION ALL
    
    SELECT 1, 33, 10
    UNION ALL
    SELECT 1, 33, 12
    UNION ALL
    SELECT 1, 33, 20
    UNION ALL
    
    SELECT 1, 34, 18
    UNION ALL
    
    SELECT 1, 35, 5
    UNION ALL
    SELECT 1, 35, 7
    UNION ALL
    SELECT 1, 35, 9
    UNION ALL
    SELECT 2, 35, 9
    UNION ALL
    SELECT 1, 35, 14
    UNION ALL
    
    SELECT 1, 36, 19
    UNION ALL
    
    SELECT 1, 37, 6
    UNION ALL
    SELECT 1, 37, 11
    UNION ALL
    SELECT 1, 37, 16
    UNION ALL
    SELECT 1, 37, 20
) a
JOIN users u ON a.userID = u.userID
JOIN achievements ach ON a.achievementID = ach.achievementID AND a.gameID = ach.gameID
WHERE EXISTS (
    SELECT 1 FROM transaction_items ti 
    JOIN transactions t ON ti.transactionID = t.transactionID 
    WHERE t.userID = a.userID AND ti.gameID = a.gameID
);

UPDATE videogames 
SET average_rating = (
    SELECT ROUND(AVG(rating), 1)
    FROM reviews 
    WHERE reviews.gameID = videogames.gameID
) 
WHERE gameID IN (
    SELECT DISTINCT gameID 
    FROM reviews
);