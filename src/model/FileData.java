package model;

import java.io.BufferedReader;

public class FileData {
    private String fileName;
    private BufferedReader reader;
    public FileData(String f, BufferedReader r)
    {
        fileName = f;
        reader = r;
    }
    public String getFileName()
    {
        return fileName;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public void setFileName(String f)
    {
        fileName = f;
    }

    public void setReader(BufferedReader b)
    {
        reader = b;
    }

    @Override
    public String toString()
    {
        return "" + fileName + " " + reader.toString();
    }
}
