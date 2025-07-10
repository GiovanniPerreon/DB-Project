package db_project.data;

public final class Queries {

    public static final String USER_LIST =
        """
        select id, email, password, name, surname, birth_date
        from   USERS
        """;
    public static final String FIND_USER =
        """
        select id, email, password, name, surname, birth_date
        from   USERS
        where  id = ?
        """;
    public static final String VIDEOGAME_LIST =
        """
        select id, title, price, release_date, description
        from   VIDEOGAMES
        """;
    public static final String FIND_VIDEOGAME =
        """
        select id, title, price, release_date, description
        from   VIDEOGAMES
        where  id = ?
        """;
}
