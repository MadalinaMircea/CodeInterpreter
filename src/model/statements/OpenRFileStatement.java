package model.statements;

import model.FileData;
import model.prgstate.PrgState;
import model.utils.IDGenerator;
import model.utils.IDictionary;
import model.exceptions.FileException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFileStatement implements Statement{
    private String varName;
    private String filename;
    public OpenRFileStatement(String v, String f)
    {
        varName = v;
        filename = f;
    }

    private boolean isOpen(PrgState state)
    {
        for(FileData f : state.getFileTable().getValues())
            if(f.getFileName().equals(filename))
                return true;
        return false;
    }
    public PrgState execute(PrgState state)
    {
        if(isOpen(state))
            throw new FileException("File is already open.");

        try
        {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            FileData fd = new FileData(filename, br);
            int id = IDGenerator.generateID();
            state.getFileTable().add(id, fd);
            IDictionary<String, Integer> symbolTable = state.getSymbolTable();
            if(symbolTable.contains(varName))
                symbolTable.update(varName, id);
            else
                symbolTable.add(varName, id);
        }
        catch(IOException ex)
        {
            throw new FileException("Error opening file.\n" + ex.getMessage() + '\n');
        }

        return null;
    }
    @Override
    public String toString()
    {
        return "open(" + varName + " , " + filename + ")";
    }
}
