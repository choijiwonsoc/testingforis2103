package exception;



public class UncreatedDepositAccountException extends Exception
{
    public UncreatedDepositAccountException()
    {
    }
    
    
    
    public UncreatedDepositAccountException(String msg)
    {
        super(msg);
    }
}
