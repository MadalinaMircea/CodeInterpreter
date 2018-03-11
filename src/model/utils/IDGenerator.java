package model.utils;

public class IDGenerator {
    private static int counter = 1;
    public static int generateID()
    {
        return counter++;
    }
}
