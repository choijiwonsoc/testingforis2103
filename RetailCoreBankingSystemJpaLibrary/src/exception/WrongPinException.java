package exception;



public class WrongPinException extends Exception
{
    public WrongPinException()
    {
    }
    
    
    
    public WrongPinException(String msg)
    {
        super(msg);
    }
}