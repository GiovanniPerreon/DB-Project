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
        select gameID, publisherID, title, price, description, requirements, average_rating, release_date, discount
        from   VIDEOGAMES
        """;
    public static final String FIND_VIDEOGAME =
        """
        select gameID, publisherID, title, price, description, requirements, average_rating, release_date, discount
        from   VIDEOGAMES
        where  gameID = ?
        """;
}
