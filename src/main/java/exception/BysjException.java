package exception;
//自定义异常
public class BysjException extends Exception{
    private String message;
    public BysjException(String message){
        super(message);
    }
}
