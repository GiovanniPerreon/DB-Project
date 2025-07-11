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

-- Verifica che la struttura delle tabelle sia corretta
SHOW TABLES;