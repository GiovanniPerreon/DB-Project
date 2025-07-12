USE tables;

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
('dev.marco@gmail.com', 'dev2024', 'Marco', 'Rossi', '1992-01-14', false, true, true, false),
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
    SELECT 1 AS publisherID, 'The Witcher 3: Wild Hunt' AS title, 49.99 AS price, 'An open-world role-playing game with an engaging storyline.' AS description,
           'CPU: Intel Core i5, RAM: 8GB, GPU: NVIDIA GTX 970' AS requirements, '2015-05-19' AS release_date, 0 AS discount
    UNION ALL
    SELECT 2, 'Among Us', 4.99, 'A multiplayer social deduction game set in space.',
           'CPU: Intel Core i3, RAM: 2GB, GPU: NVIDIA GTX 660', '2018-06-15', 0
    UNION ALL
    SELECT 1, 'Sekiro: Shadows Die Twice', 59.99, 'An action-adventure game with challenging combat and an engaging storyline.',
           'CPU: Intel Core i5, RAM: 8GB, GPU: NVIDIA GTX 970', '2019-03-22', 20
    UNION ALL
    SELECT 1, 'Elden Ring', 59.99, 'An action role-playing game with a vast open world and challenging combat.',
           'CPU: Intel Core i5, RAM: 8GB, GPU: NVIDIA GTX 970', '2022-02-25', 30
    UNION ALL
    SELECT 2, 'Dragon Age: Veilguard', 39.99, 'A fantasy role-playing game with an engaging storyline and memorable characters.',
           'CPU: Intel Core i5, RAM: 8GB, GPU: NVIDIA GTX 970', '2023-05-10', 90
    UNION ALL
    SELECT 7, 'Assassin''s Creed Valhalla', 59.99, 'Experience the golden age of Vikings and lead Eivor in a legendary saga.',
           'CPU: Intel Core i5-4460, RAM: 8GB, GPU: NVIDIA GTX 960', '2020-11-10', 25
    UNION ALL
    SELECT 8, 'Call of Duty: Modern Warfare II', 69.99, 'The sequel to the renowned Call of Duty: Modern Warfare with campaign and multiplayer.',
           'CPU: Intel Core i5-6600K, RAM: 12GB, GPU: NVIDIA GTX 1060', '2022-10-28', 15
    UNION ALL
    SELECT 9, 'Hollow Knight', 14.99, 'A classic 2D Metroidvania with gothic atmosphere and challenging gameplay.',
           'CPU: Intel Core 2 Duo E5200, RAM: 4GB, GPU: GeForce 9800GTX', '2017-02-24', 0
    UNION ALL
    SELECT 10, 'The Last of Us Part II', 39.99, 'Sequel to the acclaimed post-apocalyptic survival horror.',
           'CPU: Intel Core i5-8400, RAM: 8GB, GPU: NVIDIA GTX 1060', '2020-06-19', 10
    UNION ALL
    SELECT 11, 'The Elder Scrolls V: Skyrim', 19.99, 'The legendary open-world fantasy RPG set in Skyrim.',
           'CPU: Intel Core i5-750, RAM: 4GB, GPU: NVIDIA GTX 470', '2011-11-11', 0
    UNION ALL
    SELECT 12, 'Half-Life: Alyx', 59.99, 'The long-awaited return of the Half-Life series in virtual reality.',
           'CPU: Intel Core i5-7500, RAM: 12GB, GPU: NVIDIA GTX 1060', '2020-03-23', 20
    UNION ALL
    SELECT 7, 'Far Cry 6', 49.99, 'Fight dictatorship on the fictional tropical island of Yara.',
           'CPU: Intel Core i5-4460, RAM: 8GB, GPU: NVIDIA GTX 960', '2021-10-07', 30
    UNION ALL
    SELECT 8, 'Overwatch 2', 0.00, 'The free-to-play sequel to Blizzard''s famous hero shooter.',
           'CPU: Intel Core i3, RAM: 6GB, GPU: NVIDIA GTX 600', '2022-10-04', 0
    UNION ALL
    SELECT 9, 'Celeste', 19.99, 'A challenging platformer with a touching story about mental health.',
           'CPU: Intel Core 2 Duo E5200, RAM: 2GB, GPU: GeForce 9800GTX', '2018-01-25', 0
    UNION ALL
    SELECT 10, 'Uncharted 4: A Thief''s End', 19.99, 'Nathan Drake''s final adventure full of action and discoveries.',
           'CPU: Intel Core i5-4430, RAM: 8GB, GPU: NVIDIA GTX 960', '2016-05-10', 0
    UNION ALL
    SELECT 11, 'Fallout 4', 29.99, 'Post-apocalyptic survival RPG set in devastated Boston.',
           'CPU: Intel Core i5-2300, RAM: 8GB, GPU: NVIDIA GTX 550 Ti', '2015-11-10', 0
    UNION ALL
    SELECT 12, 'Portal 2', 9.99, 'The sequel to the revolutionary puzzle game with innovative mechanics.',
           'CPU: Intel Core 2 Duo E6600, RAM: 2GB, GPU: NVIDIA GeForce 7600GS', '2011-04-19', 0
    UNION ALL
    SELECT 9, 'Hades', 24.99, 'Isometric roguelike set in the Underworld of Greek mythology.',
           'CPU: Dual Core 3.0ghz, RAM: 4GB, GPU: DirectX 10 compatible', '2020-09-17', 0
    UNION ALL
    SELECT 7, 'Watch Dogs: Legion', 39.99, 'Cyberpunk hacker game set in dystopian London.',
           'CPU: Intel Core i5-4460, RAM: 8GB, GPU: NVIDIA GTX 960', '2020-10-29', 40
    UNION ALL
    SELECT 8, 'Diablo IV', 69.99, 'The return of Blizzard''s legendary action RPG series.',
           'CPU: Intel Core i5-2500K, RAM: 8GB, GPU: NVIDIA GTX 660', '2023-06-06', 0
    UNION ALL
    SELECT 12, 'Counter-Strike 2', 0.00, 'The new chapter of the legendary competitive FPS.',
           'CPU: Intel Core i5-750, RAM: 8GB, GPU: NVIDIA GTX 1060', '2023-09-27', 0
    UNION ALL
    SELECT 1, 'Cyberpunk 2077', 59.99, 'A futuristic open-world RPG with a complex storyline.' AS description,
           'CPU: Intel Core i7, RAM: 16GB, GPU: NVIDIA RTX 2060' AS requirements, '2020-12-10' AS release_date, 10 AS discount
    UNION ALL
    SELECT 2, 'Stardew Valley', 14.99, 'A farming life simulator with RPG elements.',
           'CPU: Intel Core 2 Duo, RAM: 2GB, GPU: Intel HD Graphics 3000', '2016-02-26', 0
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
    SELECT 16, 7
    UNION ALL
    SELECT 9, 7
    UNION ALL
    SELECT 10, 8
    UNION ALL
    SELECT 13, 9
    UNION ALL
    SELECT 14, 9
    UNION ALL
    SELECT 12, 10
    UNION ALL
    SELECT 15, 11
    UNION ALL
    SELECT 16, 12
    UNION ALL
    SELECT 9, 13
    UNION ALL
    SELECT 10, 14
    UNION ALL
    SELECT 11, 15
    UNION ALL
    SELECT 12, 16
    UNION ALL
    SELECT 9, 17
    UNION ALL
    SELECT 13, 18
    UNION ALL
    SELECT 15, 19
    UNION ALL
    SELECT 12, 20
) d
JOIN users u ON d.userID = u.userID
JOIN videogames g ON d.gameID = g.gameID
WHERE u.is_developer = TRUE;

INSERT INTO genres (genre, description) VALUES
('Action', 'Games that emphasize combat and fast-paced action.'),
('Adventure', 'Games that focus on exploration and storytelling.'),
('RPG', 'Role-playing games with character customization elements.'),
('Strategy', 'Games that require planning and tactics.'),
('Simulation', 'Games that simulate real or imaginary situations.'),
('Shooter', 'Games that focus on combat with firearms.'),
('Survival', 'Games that emphasize survival in hostile environments.'),
('Horror', 'Games designed to scare and create tension.'),
('Sports', 'Simulations of real or fantasy sports.'),
('Racing', 'Racing games with various types of vehicles.'),
('Puzzle', 'Games that require solving puzzles and logical reasoning.');

INSERT INTO videogame_genres (gameID, genre)
SELECT g.gameID, v.genre
FROM (
    SELECT 1 AS gameID, 'Action' AS genre
    UNION ALL
    SELECT 1, 'Adventure'
    UNION ALL
    SELECT 2, 'Action'
    UNION ALL
    SELECT 2, 'Strategy'
    UNION ALL
    SELECT 3, 'RPG'
    UNION ALL
    SELECT 4, 'RPG'
    UNION ALL
    SELECT 5 AS gameID, 'Action' AS genre
    UNION ALL
    SELECT 5, 'Adventure'
    UNION ALL
    SELECT 6, 'Shooter'
    UNION ALL
    SELECT 6, 'Action'
    UNION ALL
    SELECT 7, 'Adventure'
    UNION ALL
    SELECT 7, 'Action'
    UNION ALL
    SELECT 8, 'Action'
    UNION ALL
    SELECT 8, 'Survival'
    UNION ALL
    SELECT 9, 'RPG'
    UNION ALL
    SELECT 9, 'Adventure'
    UNION ALL
    SELECT 10, 'Shooter'
    UNION ALL
    SELECT 10, 'Action'
    UNION ALL
    SELECT 11, 'Action'
    UNION ALL
    SELECT 11, 'Adventure'
    UNION ALL
    SELECT 12, 'Shooter'
    UNION ALL
    SELECT 12, 'Action'
    UNION ALL
    SELECT 13, 'Action'
    UNION ALL
    SELECT 13, 'Adventure'
    UNION ALL
    SELECT 14, 'Action'
    UNION ALL
    SELECT 14, 'Adventure'
    UNION ALL
    SELECT 15, 'RPG'
    UNION ALL
    SELECT 15, 'Survival'
    UNION ALL
    SELECT 16, 'Puzzle'
    UNION ALL
    SELECT 16, 'Action'
    UNION ALL
    SELECT 17, 'Action'
    UNION ALL
    SELECT 17, 'RPG'
    UNION ALL
    SELECT 18, 'Action'
    UNION ALL
    SELECT 18, 'Adventure'
    UNION ALL
    SELECT 19, 'RPG'
    UNION ALL
    SELECT 19, 'Action'
    UNION ALL
    SELECT 20, 'Shooter'
    UNION ALL
    SELECT 20, 'Strategy'
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
    SELECT 1 AS achievementID, 1 AS gameID, 'Start the Adventure' AS title, 'Complete the initial tutorial.' AS description
    UNION ALL
    SELECT 2, 1, 'Sword Master', 'Defeat 100 enemies with a sword.'
    UNION ALL
    SELECT 1, 2, 'Supreme Strategist', 'Win a strategy mode match without losing a tower.'
    UNION ALL
    SELECT 1, 3, 'Dragon Hunter', 'Defeat a legendary dragon.'
    UNION ALL
    SELECT 1, 4, 'Hero of the Realm', 'Complete the main campaign.'
    UNION ALL
    SELECT 1, 5, 'Legendary Viking', 'Complete the main story of Valhalla.'
    UNION ALL
    SELECT 2, 5, 'Northern Explorer', 'Discover all regions of England.'
    UNION ALL
    SELECT 1, 6, 'Elite Soldier', 'Complete the campaign on Veteran difficulty.'
    UNION ALL
    SELECT 2, 6, 'Tactical Operator', 'Win 50 multiplayer matches.'
    UNION ALL
    SELECT 1, 7, 'Shadow Knight', 'Complete the game without dying.'
    UNION ALL
    SELECT 1, 8, 'Survivor', 'Complete the story of Ellie and Abby.'
    UNION ALL
    SELECT 1, 9, 'Dovahkiin', 'Complete the main quest of Skyrim.'
    UNION ALL
    SELECT 2, 9, 'Master of All Arts', 'Reach level 100 in all skills.'
    UNION ALL
    SELECT 1, 10, 'Welcome to the Resistance', 'Complete the prologue of Half-Life: Alyx.'
    UNION ALL
    SELECT 1, 11, 'Revolutionary', 'Free Yara from dictatorship.'
    UNION ALL
    SELECT 1, 12, 'Overwatch Hero', 'Win your first competitive match.'
    UNION ALL
    SELECT 1, 13, 'Supreme Climber', 'Complete all levels of Celeste.'
    UNION ALL
    SELECT 1, 14, 'Treasure Hunter', 'Find all hidden treasures.'
    UNION ALL
    SELECT 1, 15, 'Minutemen General', 'Become the leader of the Minutemen faction.'
    UNION ALL
    SELECT 1, 16, 'Portal Genius', 'Complete all Portal 2 tests.'
    UNION ALL
    SELECT 1, 17, 'Son of the Underworld', 'Complete your first escape from Hades.'
    UNION ALL
    SELECT 1, 18, 'Supreme Hacker', 'Complete all main missions.'
    UNION ALL
    SELECT 1, 19, 'Demon Hunter', 'Reach maximum level.'
    UNION ALL
    SELECT 1, 20, 'Global Legend', 'Reach Global Elite rank.'
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
    SELECT 17 AS userID, 249.95 AS total_cost
    UNION ALL
    SELECT 17, 69.99
    UNION ALL
    SELECT 18, 159.97
    UNION ALL
    SELECT 19, 89.98
    UNION ALL
    SELECT 20, 199.95
    UNION ALL
    SELECT 21, 49.99
    UNION ALL
    SELECT 22, 129.97
    UNION ALL
    SELECT 23, 79.98
    UNION ALL
    SELECT 24, 39.99
    UNION ALL
    SELECT 25, 89.97
    UNION ALL
    SELECT 26, 149.96
    UNION ALL
    SELECT 27, 59.99
    UNION ALL
    SELECT 28, 99.98
    UNION ALL
    SELECT 29, 119.97
    UNION ALL
    SELECT 30, 29.99
    UNION ALL
    SELECT 31, 179.95
    UNION ALL
    SELECT 32, 89.98
    UNION ALL
    SELECT 33, 69.99
    UNION ALL
    SELECT 34, 139.96
    UNION ALL
    SELECT 35, 49.99
    UNION ALL
    SELECT 36, 99.97
    UNION ALL
    SELECT 37, 79.98
    UNION ALL
    SELECT 38, 159.96
    UNION ALL
    SELECT 39, 19.99
    UNION ALL
    SELECT 40, 129.97
    UNION ALL
    SELECT 41, 89.98
    UNION ALL
    SELECT 42, 199.95
    UNION ALL
    SELECT 43, 59.99
    UNION ALL
    SELECT 44, 109.98
    UNION ALL
    SELECT 45, 39.99
    UNION ALL
    SELECT 46, 149.97
    UNION ALL
    SELECT 47, 79.99
    UNION ALL
    SELECT 48, 119.98
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
    SELECT 3, 5
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
    SELECT 16, 6
    UNION ALL
    SELECT 17, 10
    UNION ALL
    SELECT 17, 12
    UNION ALL
    SELECT 18, 13
    UNION ALL
    SELECT 18, 15
    UNION ALL
    SELECT 18, 17
    UNION ALL
    SELECT 19, 19
    UNION ALL
    SELECT 20, 5
    UNION ALL
    SELECT 20, 8
    UNION ALL
    SELECT 20, 11
    UNION ALL
    SELECT 20, 18
    UNION ALL
    SELECT 21, 7
    UNION ALL
    SELECT 21, 14
    UNION ALL
    SELECT 22, 6
    UNION ALL
    SELECT 23, 9
    UNION ALL
    SELECT 23, 16
    UNION ALL
    SELECT 23, 20
    UNION ALL
    SELECT 24, 17
    UNION ALL
    SELECT 25, 5
    UNION ALL
    SELECT 25, 13
    UNION ALL
    SELECT 25, 15
    UNION ALL
    SELECT 26, 10
    UNION ALL
    SELECT 26, 12
    UNION ALL
    SELECT 27, 6
    UNION ALL
    SELECT 27, 8
    UNION ALL
    SELECT 27, 11
    UNION ALL
    SELECT 27, 18
    UNION ALL
    SELECT 28, 7
    UNION ALL
    SELECT 29, 9
    UNION ALL
    SELECT 29, 14
    UNION ALL
    SELECT 29, 19
    UNION ALL
    SELECT 30, 13
    UNION ALL
    SELECT 30, 16
    UNION ALL
    SELECT 31, 5
    UNION ALL
    SELECT 31, 6
    UNION ALL
    SELECT 31, 8
    UNION ALL
    SELECT 31, 11
    UNION ALL
    SELECT 31, 15
    UNION ALL
    SELECT 32, 17
    UNION ALL
    SELECT 33, 10
    UNION ALL
    SELECT 33, 12
    UNION ALL
    SELECT 33, 20
    UNION ALL
    SELECT 34, 18
    UNION ALL
    SELECT 35, 5
    UNION ALL
    SELECT 35, 7
    UNION ALL
    SELECT 35, 9
    UNION ALL
    SELECT 35, 14
    UNION ALL
    SELECT 36, 19
    UNION ALL
    SELECT 37, 6
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
    SELECT 17 AS wishlistID, 19 AS gameID
    UNION ALL
    SELECT 17, 20
    UNION ALL
    SELECT 18, 5
    UNION ALL
    SELECT 18, 9
    UNION ALL
    SELECT 19, 10
    UNION ALL
    SELECT 19, 16
    UNION ALL
    SELECT 20, 7
    UNION ALL
    SELECT 20, 13
    UNION ALL
    SELECT 21, 6
    UNION ALL
    SELECT 21, 12
    UNION ALL
    SELECT 22, 15
    UNION ALL
    SELECT 22, 11
    UNION ALL
    SELECT 23, 8
    UNION ALL
    SELECT 23, 14
    UNION ALL
    SELECT 24, 19
    UNION ALL
    SELECT 25, 5
    UNION ALL
    SELECT 25, 18
    UNION ALL
    SELECT 26, 16
    UNION ALL
    SELECT 26, 17
    UNION ALL
    SELECT 27, 20
    UNION ALL
    SELECT 28, 7
    UNION ALL
    SELECT 28, 13
    UNION ALL
    SELECT 29, 6
    UNION ALL
    SELECT 29, 8
    UNION ALL
    SELECT 30, 9
    UNION ALL
    SELECT 31, 10
    UNION ALL
    SELECT 31, 12
    UNION ALL
    SELECT 32, 15
    UNION ALL
    SELECT 33, 17
    UNION ALL
    SELECT 34, 11
    UNION ALL
    SELECT 35, 19
    UNION ALL
    SELECT 36, 7
    UNION ALL
    SELECT 37, 16
    UNION ALL
    SELECT 38, 5
    UNION ALL
    SELECT 39, 13
    UNION ALL
    SELECT 40, 20
    UNION ALL
    SELECT 41, 8
    UNION ALL
    SELECT 42, 6
    UNION ALL
    SELECT 43, 14
    UNION ALL
    SELECT 44, 18
    UNION ALL
    SELECT 45, 9
    UNION ALL
    SELECT 46, 17
    UNION ALL
    SELECT 47, 10
    UNION ALL
    SELECT 48, 15
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
    SELECT 1 AS userID, 1 AS gameID, 4.9 AS rating, 'The game was released in an incomplete state, but after some updates it became very fun' AS comment
    UNION ALL
    SELECT 17, 6, 4.0, 'Good campaign but multiplayer could be improved.'
    UNION ALL
    SELECT 17, 7, 5.0, 'Absolute masterpiece! Perfect atmosphere and gameplay.'
    UNION ALL
    SELECT 17, 8, 4.8, 'Emotionally intense story, incredible characters.'
    UNION ALL
    
    SELECT 18, 10, 4.7, 'Innovative use of VR, incredible experience!'
    UNION ALL
    SELECT 18, 11, 3.5, 'Beautiful but too repetitive after a while.'
    UNION ALL
    SELECT 18, 12, 3.8, 'Fun but lacks content compared to the first.'
    UNION ALL
    
    SELECT 19, 13, 4.9, 'Perfect balance between challenge and fun!'
    UNION ALL
    SELECT 19, 14, 4.6, 'Breathtaking graphics and engaging gameplay.'
    UNION ALL
    SELECT 19, 15, 4.1, 'Good RPG but with some bugs at launch.'
    UNION ALL
    
    SELECT 20, 5, 4.2, 'Beautiful Viking setting but repetitive missions.'
    UNION ALL
    SELECT 20, 6, 3.9, 'Solid shooter but nothing revolutionary.'
    UNION ALL
    SELECT 20, 16, 5.0, 'Brilliant! Innovative and fun game mechanics.'
    UNION ALL
    
    SELECT 21, 17, 4.8, 'Perfect roguelike with excellent storytelling!'
    UNION ALL
    
    SELECT 22, 18, 3.6, 'Interesting concept but mediocre execution.'
    UNION ALL
    SELECT 22, 19, 4.3, 'Great comeback for the Diablo series!'
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
    
    SELECT 29, 9, 4.8, 'Timeless classic with infinite possibilities.'
    UNION ALL
    SELECT 29, 14, 4.4, 'Avventura cinematografica ben realizzata.'
    UNION ALL
    SELECT 29, 19, 4.2, 'Buon ritorno alle origini per Diablo.'
    UNION ALL
    
    SELECT 30, 13, 4.6, 'Difficile ma gratificante, storia commovente.'
    UNION ALL
    SELECT 30, 16, 4.9, 'Puzzle game geniale con dialoghi divertenti.'
    UNION ALL
    
    SELECT 31, 5, 4.3, 'Fascinating setting, too long though.'
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
    SELECT 33, 12, 3.9, 'Fun but lacks depth of the first one.'
    UNION ALL
    SELECT 33, 20, 4.3, 'Aggiornamento grafico apprezzabile.'
    UNION ALL
    
    SELECT 34, 18, 3.5, 'Concept promettente ma esecuzione lacunosa.'
    UNION ALL
    
    SELECT 35, 5, 4.1, 'Vikings and action, what more could you want?'
    UNION ALL
    SELECT 35, 7, 4.8, 'Arte pixelata sublime e gameplay perfetto.'
    UNION ALL
    SELECT 35, 9, 4.7, 'RPG che ridefinisce il genere open-world.'
    UNION ALL
    SELECT 35, 14, 4.3, 'Conclusione degna per Nathan Drake.'
    UNION ALL
    
    SELECT 36, 19, 4.0, 'Diablo is back but with some compromises.'
    UNION ALL
    
    SELECT 37, 6, 4.2, 'Solid FPS with good variety of modes.'
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
SELECT a.achievementID, a.userID, a.gameID
FROM (
    SELECT 1 AS achievementID, 1 AS userID, 1 AS gameID
    UNION ALL
    SELECT 2, 1, 1
    UNION ALL
    SELECT 1, 1, 2
    UNION ALL
    SELECT 1, 2, 3
    UNION ALL
    SELECT 1, 17, 5
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
    
    SELECT 1, 19, 13
    UNION ALL
    SELECT 1, 19, 14
    UNION ALL
    SELECT 1, 19, 15
    UNION ALL
    
    SELECT 1, 20, 5
    UNION ALL
    SELECT 1, 20, 6
    UNION ALL
    SELECT 1, 20, 16
    UNION ALL
    
    SELECT 1, 21, 17
    UNION ALL
    
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

-- Update average ratings based on the inserted reviews
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