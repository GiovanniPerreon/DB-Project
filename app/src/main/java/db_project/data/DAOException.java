package db_project.data;
import java.io.Serial;

/**
 * An exception that is thrown when a data access operation fails.
 */
public final class DAOException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public DAOException(String message) {
        super(message);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
