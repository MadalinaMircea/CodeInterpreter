package model.statements;

import model.FileData;
import model.exceptions.FileException;
import model.expressions.Expression;
import model.prgstate.PrgState;
import model.utils.FileTable;
import model.utils.IDictionary;
import model.utils.IFileTable;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements Statement{
    private Expression expFileId;
    private String varName;
    public ReadFileStatement(Expression e, String v)
    {
        expFileId = e;
        varName = v;
    }

    @Override
    public PrgState execute(PrgState p)
    {
        int v = expFileId.eval(p.getSymbolTable(), p.getHeap());
        IFileTable<Integer, FileData> fileTable = p.getFileTable();

        if(!fileTable.contains(v))
            throw new FileException("File not open.");

        BufferedReader br = fileTable.get(v).getReader();

        try
        {
            String lineString = br.readLine();
            int line;
            if(lineString.isEmpty())
                line = 0;
            else
                line = Integer.parseInt(lineString);

            IDictionary<String, Integer> symbolTable = p.getSymbolTable();
            if(symbolTable.contains(varName))
                symbolTable.update(varName, line);
            else
                symbolTable.add(varName, line);

        }
        catch(IOException ex)
        {
            throw new FileException(ex.getMessage());
        }

        return null;
    }
    @Override
    public String toString()
    {
        return "read(" + expFileId + " , " + varName + ")";
    }
}
