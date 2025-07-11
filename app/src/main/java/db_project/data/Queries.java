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
}
