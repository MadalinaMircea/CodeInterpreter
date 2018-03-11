package model.statements;

import model.FileData;
import model.exceptions.FileException;
import model.expressions.Expression;
import model.prgstate.PrgState;
import model.utils.FileTable;
import model.utils.IFileTable;

public class CloseRFile implements Statement {
    private Expression expFileId;
    public CloseRFile(Expression e)
    {
        expFileId = e;
    }
    @Override
    public PrgState execute(PrgState p)
    {
        try
        {
            int v = expFileId.eval(p.getSymbolTable(), p.getHeap());
            IFileTable<Integer, FileData> fileTable = p.getFileTable();
            if(!fileTable.contains(v))
                throw new FileException("No such open file.");

            fileTable.get(v).getReader().close();
            fileTable.remove(v);
        }
        catch(Exception ex)
        {
            throw new FileException(ex.getMessage());
        }

        return null;
    }
    @Override
    public String toString()
    {
        return "close(" + expFileId + ")";
    }
}
