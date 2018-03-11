package model.exceptions;

public class EmptyExecStackException extends RuntimeException {
    public EmptyExecStackException(String msg)
    {
        super(msg);
    }
}
