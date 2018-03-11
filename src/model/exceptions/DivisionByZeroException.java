package model.exceptions;

public class DivisionByZeroException extends RuntimeException {
    public DivisionByZeroException(String m)
    {
        super(m);
    }
}
