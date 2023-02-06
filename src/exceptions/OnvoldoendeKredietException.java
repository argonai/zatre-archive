package exceptions;
/**
 * Deze klasse bevat exceptions voor het domein
 * @author G48
 * @version 1.0
 *
 */
public class OnvoldoendeKredietException extends RuntimeException
{
    public OnvoldoendeKredietException()
    {
    }

    public OnvoldoendeKredietException(String message)
    {
        super(message);
    }

    public OnvoldoendeKredietException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public OnvoldoendeKredietException(Throwable cause)
    {
        super(cause);
    }

    public OnvoldoendeKredietException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}