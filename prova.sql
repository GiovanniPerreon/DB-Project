-- Test queries per verificare il funzionamento del database

-- Connessione al database
USE tables;

-- Verifica tutti gli utenti nella tabella
SELECT * FROM users;

-- Verifica struttura della tabella users
DESCRIBE users;

-- Test query specifica per trovare un utente per ID (come fa FIND_USER)
SELECT userID, email, password, name, surname, birth_date, is_administrator, is_publisher, is_developer
FROM users
WHERE userID = 1;

-- Verifica tutti i videogiochi
SELECT * FROM videogames;

-- Verifica i developers per ogni videogioco
SELECT * FROM videogame_developers;
-- Verifica transazioni
SELECT * FROM transactions;

-- Verifica reviews
SELECT * FROM reviews;

-- Verifica wishlists
SELECT * FROM wishlists;

-- Test per verificare che le foreign key funzionino
SELECT 
    u.name, 
    u.surname, 
    vg.title, 
    vg.price
FROM users u
JOIN videogames vg ON u.userID = vg.publisherID
WHERE u.is_publisher = TRUE;

-- Test join complesso per verificare le relazioni
SELECT 
    u.name AS user_name,
    vg.title AS game_title,
    r.rating,
    r.comment
FROM users u
JOIN reviews r ON u.userID = r.userID
JOIN videogames vg ON r.gameID = vg.gameID;
---
SELECT u.userID, u.name, u.surname, u.email, u.is_publisher, u.is_developer, AVG(v.average_rating) as avg_rating
        FROM users u
        JOIN videogames v ON u.userID = v.publisherID OR u.userID IN (
            SELECT vd.developerID FROM videogame_developers vd WHERE vd.gameID = v.gameID
        )
        WHERE v.average_rating IS NOT NULL AND (u.is_publisher = TRUE OR u.is_developer = TRUE)
        GROUP BY u.userID, u.name, u.surname, u.email, u.is_publisher, u.is_developer
        HAVING AVG(v.average_rating) < (
            SELECT AVG(average_rating) FROM videogames WHERE average_rating IS NOT NULL
        )
        ORDER BY avg_rating ASC;
-- Verifica che la struttura delle tabelle sia corretta
SHOW TABLES;