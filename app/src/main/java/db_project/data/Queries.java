package db_project.data;

public final class Queries {

    public static final String USER_LIST =
        """
        select userID, email, password, name, surname, birth_date, is_administrator, is_publisher, is_developer, is_blocked
        from   USERS
        """;
    public static final String FIND_USER =
        """
        select userID, email, password, name, surname, birth_date, is_administrator, is_publisher, is_developer, is_blocked
        from   USERS
        where  userID = ?
        """;
    public static final String FIND_USER_BY_EMAIL_PASSWORD =
        """
        select userID, email, password, name, surname, birth_date, is_administrator, is_publisher, is_developer, is_blocked
        from   USERS
        where  email = ? AND password = ?
        """;
    public static final String ADD_USER =
        """
        insert into USERS (email, password, name, surname, birth_date, is_administrator, is_publisher, is_developer, is_blocked)
        values (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
    public static final String UPDATE_USER_BLOCKED =
        """
        update USERS 
        set is_blocked = ?
        where userID = ?
        """;
    public static final String ADD_VIDEOGAME =
        """
        insert into VIDEOGAMES (publisherID, title, price, description, requirements, average_rating, release_date, discount)
        values (?, ?, ?, ?, ?, ?, ?, ?)
        """;
    public static final String VIDEOGAME_LIST =
        """
        select gameID, publisherID, title, price, description, requirements, average_rating, release_date, discount
        from   VIDEOGAMES
        """;
    public static final String FIND_VIDEOGAME =
        """
        select gameID, publisherID, title, price, description, requirements, average_rating, release_date, discount
        from   VIDEOGAMES
        where  gameID = ?
        """;
    public static final String GENRE_LIST =
        """
        select genre, description
        from   GENRES
        """;
    public static final String FIND_GENRE =
        """
        select genre, description
        from   GENRES
        where  genre = ?
        """;
    public static final String VIDEOGAME_GENRE_LIST =
        """
        select gameID, genre
        from   VIDEOGAME_GENRES
        """;
    public static final String FIND_VIDEOGAME_GENRE =
        """
        select gameID, genre
        from   VIDEOGAME_GENRES
        where  gameID = ? and genre = ?
        """;
    public static final String ADD_VIDEOGAME_GENRE =
        """
        insert into VIDEOGAME_GENRES (gameID, genre)
        values (?, ?)
        """;
    public static final String REMOVE_VIDEOGAME_GENRE =
        """
        delete from VIDEOGAME_GENRES
        where gameID = ? and genre = ?
        """;
    public static final String LANGUAGE_LIST =
        """
        select language_name
        from   LANGUAGES
        """;
    public static final String FIND_LANGUAGE =
        """
        select language_name
        from   LANGUAGES
        where  language_name = ?
        """;
    public static final String VIDEOGAME_LANGUAGE_LIST =
        """
        select gameID, language_name
        from   VIDEOGAME_LANGUAGES
        """;
    public static final String FIND_VIDEOGAME_LANGUAGE =
        """
        select gameID, language_name
        from   VIDEOGAME_LANGUAGES
        where  gameID = ? and language_name = ?
        """;
    public static final String WISHLIST_LIST =
        """
        select wishlistID, userID
        from   WISHLISTS
        """;
    public static final String FIND_WISHLIST =
        """
        select wishlistID, userID
        from   WISHLISTS
        where  wishlistID = ?
        """;
    public static final String FIND_WISHLIST_BY_USER =
        """
        select wishlistID, userID
        from   WISHLISTS
        where  userID = ?
        """;
    public static final String ADD_WISHLIST =
        """
        insert into WISHLISTS (userID)
        values (?)
        """;
    public static final String TRANSACTION_LIST =
        """
        select transactionID, userID, total_cost
        from   TRANSACTIONS
        """;
    public static final String FIND_TRANSACTION =
        """
        select transactionID, userID, total_cost
        from   TRANSACTIONS
        where  transactionID = ?
        """;
    public static final String ADD_TRANSACTION =
        """
        insert into TRANSACTIONS (userID, total_cost)
        values (?, ?)
        """;
    public static final String REVIEW_LIST =
        """
        select userID, gameID, rating, comment
        from   REVIEWS
        """;
    public static final String FIND_REVIEW =
        """
        select userID, gameID, rating, comment
        from   REVIEWS
        where userID = ? and gameID = ?
        """;
    public static final String ADD_REVIEW =
        """
        insert into REVIEWS (userID, gameID, rating, comment)
        values (?, ?, ?, ?)
        """;
    public static final String VIDEOGAME_DEVELOPERS_LIST =
        """
        select developerID, gameID
        from   videogame_developers
        """;
    public static final String FIND_VIDEOGAME_DEVELOPERS =
        """
        select developerID, gameID
        from   videogame_developers
        where  gameID = ?
        """;
    public static final String ADD_VIDEOGAME_DEVELOPER =
        """
        insert into videogame_developers (developerID, gameID)
        values (?, ?)
        """;
    public static final String ACHIEVEMENTS_LIST =
        """
        select achievementID, gameID, title, description
        from   achievements
        """;
    public static final String FIND_ACHIEVEMENTS =
        """
        select achievementID, gameID, title, description
        from   achievements
        where  gameID = ?
        """;
    public static final String ADD_ACHIEVEMENT =
        """
        insert into achievements (achievementID, gameID, title, description)
        values (?, ?, ?, ?)
        """;
    public static final String USER_ACHIEVEMENTS_LIST =
        """
        select a.achievementID, a.gameID, a.title, a.description
        from achievements a
        join achievements_user au on a.achievementID = au.achievementID and a.gameID = au.gameID
        where au.userID = ?
        """;
    public static final String ACHIEVEMENTS_USER_LIST =
        """
        select achievementID, userID, gameID
        from achievements_user
        """;
    public static final String FIND_USER_ACHIEVEMENT =
        """
        select achievementID, userID, gameID
        from achievements_user
        where userID = ? and achievementID = ? and gameID = ?
        """;
    public static final String ADD_USER_ACHIEVEMENT =
        """
        insert into achievements_user (achievementID, userID, gameID)
        values (?, ?, ?)
        """;
    public static final String TRANSACTION_ITEMS_LIST =
        """
        select transactionID, gameID
        from   transaction_items
        """;
    public static final String FIND_TRANSACTION_ITEMS =
        """
        select transactionID, gameID
        from   transaction_items
        where  transactionID = ?
        """;
    public static final String ADD_TRANSACTION_ITEM =
        """
        insert into transaction_items (transactionID, gameID)
        values (?, ?)
        """;
    public static final String WISHLIST_ITEMS_LIST =
        """
        select wishlistID, gameID
        from   wishlist_items
        """;
    public static final String FIND_WISHLIST_ITEMS =
        """
        select wishlistID, gameID
        from   wishlist_items
        where  wishlistID = ?
        """;
    public static final String ADD_WISHLIST_ITEM =
        """
        insert into wishlist_items (wishlistID, gameID)
        values (?, ?)
        """;
    public static final String REMOVE_WISHLIST_ITEM =
        """
        delete from wishlist_items
        where wishlistID = ? and gameID = ?
        """;
    public static final String LEAST_RATED_PUBLISHERS_DEVELOPERS =
        """
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
        ORDER BY avg_rating ASC
        """;
    public static final String UPDATE_GAME_AVERAGE_RATING =
        """
        UPDATE videogames 
        SET average_rating = (
            SELECT AVG(rating) 
            FROM reviews 
            WHERE gameID = ?
        ) 
        WHERE gameID = ?
        """;

    public static final String TOP_10_NEWEST_GAMES =
        """
        SELECT gameID, publisherID, title, price, description, requirements, average_rating, release_date, discount
        FROM videogames
        ORDER BY release_date DESC
        LIMIT 10
        """;
    
    public static final String TOP_10_OLDEST_GAMES =
        """
        SELECT gameID, publisherID, title, price, description, requirements, average_rating, release_date, discount
        FROM videogames
        ORDER BY release_date ASC
        LIMIT 10
        """;
    
    public static final String TOP_10_HIGHEST_RATED_GAMES =
        """
        SELECT gameID, publisherID, title, price, description, requirements, average_rating, release_date, discount
        FROM videogames
        WHERE average_rating IS NOT NULL
        ORDER BY average_rating DESC
        LIMIT 10
        """;
    
    public static final String TOP_10_LOWEST_RATED_GAMES =
        """
        SELECT gameID, publisherID, title, price, description, requirements, average_rating, release_date, discount
        FROM videogames
        WHERE average_rating IS NOT NULL
        ORDER BY average_rating ASC
        LIMIT 10
        """;
    
    public static final String TOP_10_MOST_EXPENSIVE_GAMES =
        """
        SELECT gameID, publisherID, title, price, description, requirements, average_rating, release_date, discount
        FROM videogames
        ORDER BY price DESC
        LIMIT 10
        """;
    
    public static final String TOP_10_CHEAPEST_GAMES =
        """
        SELECT gameID, publisherID, title, price, description, requirements, average_rating, release_date, discount
        FROM videogames
        ORDER BY price ASC
        LIMIT 10
        """;
    
    public static final String TOP_10_MOST_SOLD_GAMES =
        """
        SELECT v.gameID, v.publisherID, v.title, v.price, v.description, v.requirements, v.average_rating, v.release_date, v.discount, COUNT(ti.gameID) as sales_count
        FROM videogames v
        JOIN transaction_items ti ON v.gameID = ti.gameID
        GROUP BY v.gameID, v.publisherID, v.title, v.price, v.description, v.requirements, v.average_rating, v.release_date, v.discount
        ORDER BY sales_count DESC
        LIMIT 10
        """;
    
    public static final String TOP_10_GAMES_BY_GENRE =
        """
        SELECT v.gameID, v.publisherID, v.title, v.price, v.description, v.requirements, v.average_rating, v.release_date, v.discount
        FROM videogames v
        JOIN videogame_genres vg ON v.gameID = vg.gameID
        WHERE vg.genre = ?
        ORDER BY v.average_rating DESC
        LIMIT 10
        """;
    
    public static final String ALL_NEWEST_GAMES =
        """
        SELECT gameID, publisherID, title, price, description, requirements, average_rating, release_date, discount
        FROM videogames
        ORDER BY release_date DESC
        """;
    
    public static final String ALL_OLDEST_GAMES =
        """
        SELECT gameID, publisherID, title, price, description, requirements, average_rating, release_date, discount
        FROM videogames
        ORDER BY release_date ASC
        """;
    
    public static final String ALL_HIGHEST_RATED_GAMES =
        """
        SELECT gameID, publisherID, title, price, description, requirements, average_rating, release_date, discount
        FROM videogames
        WHERE average_rating IS NOT NULL
        ORDER BY average_rating DESC
        """;
    
    public static final String ALL_LOWEST_RATED_GAMES =
        """
        SELECT gameID, publisherID, title, price, description, requirements, average_rating, release_date, discount
        FROM videogames
        WHERE average_rating IS NOT NULL
        ORDER BY average_rating ASC
        """;
    
    public static final String ALL_MOST_EXPENSIVE_GAMES =
        """
        SELECT gameID, publisherID, title, price, description, requirements, average_rating, release_date, discount
        FROM videogames
        ORDER BY price DESC
        """;
    
    public static final String ALL_CHEAPEST_GAMES =
        """
        SELECT gameID, publisherID, title, price, description, requirements, average_rating, release_date, discount
        FROM videogames
        ORDER BY price ASC
        """;
    
    public static final String ALL_MOST_SOLD_GAMES =
        """
        SELECT v.gameID, v.publisherID, v.title, v.price, v.description, v.requirements, v.average_rating, v.release_date, v.discount, COUNT(ti.gameID) as sales_count
        FROM videogames v
        JOIN transaction_items ti ON v.gameID = ti.gameID
        GROUP BY v.gameID, v.publisherID, v.title, v.price, v.description, v.requirements, v.average_rating, v.release_date, v.discount
        ORDER BY sales_count DESC
        """;
    
    public static final String ALL_GAMES_BY_GENRE =
        """
        SELECT v.gameID, v.publisherID, v.title, v.price, v.description, v.requirements, v.average_rating, v.release_date, v.discount
        FROM videogames v
        JOIN videogame_genres vg ON v.gameID = vg.gameID
        WHERE vg.genre = ?
        ORDER BY v.average_rating DESC
        """;
}
