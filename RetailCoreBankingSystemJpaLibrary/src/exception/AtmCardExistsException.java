package exception;



public class AtmCardExistsException extends Exception
{
    public AtmCardExistsException()
    {
    }
    
    
    
    public AtmCardExistsException(String msg)
    {
        super(msg);
    }
}