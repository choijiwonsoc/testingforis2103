package exception;



public class UncreatedAtmCardException extends Exception
{
    public UncreatedAtmCardException()
    {
    }
    
    
    
    public UncreatedAtmCardException(String msg)
    {
        super(msg);
    }
}