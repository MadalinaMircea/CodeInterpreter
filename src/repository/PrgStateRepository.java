package repository;

import model.FileData;
import model.exceptions.PrgFileException;
import model.prgstate.PrgState;
import model.statements.Statement;
import model.utils.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class PrgStateRepository implements IPrgStateRepository {
    private List<PrgState> programs;
    private String logFilePath;
    public PrgStateRepository()
    {
        programs = new ArrayList<>();
        logFilePath = "";
    }
    public PrgState get(int i)
    {
        if(i >= programs.size())
            throw new RuntimeException("Index out of bounds.");
        return programs.get(i);
    }
    public int size()
    {
        return programs.size();
    }
    public PrgStateRepository(String s)
    {
        programs = new ArrayList<>();
        logFilePath = s;
    }
    public List<PrgState> getPrograms()
    {
        return programs;
    }
    public void setPrograms(List<PrgState> l)
    {
        programs = l;
    }
    public void addPrgState(PrgState p)
    {
        programs.add(p);
    }
    @Override
    public String toString()
    {
        return "";    }

    public void logPrgStateExec(PrgState state)
    {
        try(PrintWriter logFile= new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true))))
        {
            IExecStack<Statement> stack = state.getExecStack();
            IDictionary<String,Integer> dict = state.getSymbolTable();
            IList<Integer> l = state.getOutputList();
            IFileTable<Integer, FileData> f = state.getFileTable();
            IHeap<Integer, Integer> h = state.getHeap();

            logFile.println("\nID: " + state.getId());
            logFile.println("ExeStack:");
            for(Statement s : stack.getAll())
                logFile.println("" + s);

            logFile.println("SymbolTable:");
            for(String k : dict.getAll())
                logFile.println(k + " --> " + dict.get(k));

            logFile.println("OutputList:");
            for(Integer o : l.getAll())
                logFile.println("" + o);

            logFile.println("FileTable:");
            for(Integer k : f.getAll())
                logFile.println(k + " --> " + f.get(k).getFileName());

            logFile.println("Heap:");
            for(Integer i : h.getAll())
                logFile.println("" + i + " --> " + h.get(i));
        }
        catch(IOException ex)
        {
            throw new PrgFileException("Could not open file.");
        }
    }

}
