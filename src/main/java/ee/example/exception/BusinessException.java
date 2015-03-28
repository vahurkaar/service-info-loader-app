package ee.example.exception;

/**
 * General business exception, which may be thrown, when an unexpected error or exception occurs
 * during the execution of business rules.
 *
 * @author Vahur Kaar
 * @since 25.03.2015
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
