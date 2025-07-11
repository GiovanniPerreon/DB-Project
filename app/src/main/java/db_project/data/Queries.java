package db_project.data;

public final class Queries {

    public static final String USER_LIST =
        """
        select userID, email, password, name, surname, birth_date, is_administrator, is_publisher, is_developer
        from   USERS
        """;
    public static final String FIND_USER =
        """
        select userID, email, password, name, surname, birth_date, is_administrator, is_publisher, is_developer
        from   USERS
        where  userID = ?
        """;
    public static final String VIDEOGAME_LIST =
        """
        select gameID, userID, title, price, description, requirements, average_rating, release_date, discount
        from   VIDEOGAMES
        """;
    public static final String FIND_VIDEOGAME =
        """
        select gameID, userID, title, price, description, requirements, average_rating, release_date, discount
        from   VIDEOGAMES
        where  gameID = ?
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
    public static final String WISHLIST_ADD_GAME =
        """
        insert into WISHLIST_GAMES (wishlistID, gameID)
        values (?, ?)
        """;
    public static final String WISHLIST_REMOVE_GAME =
        """
        delete from WISHLIST_GAMES
        where wishlistID = ? and gameID = ?
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
}
