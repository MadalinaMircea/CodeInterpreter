package model.prgstate;

import javafx.util.Pair;
import model.FileData;
import model.exceptions.EmptyExecStackException;
import model.statements.Statement;
import model.utils.*;

import java.util.List;

public class PrgState {
    private IExecStack<Statement> execStack;
    private IExecStack<IDictionary<String, Integer>> symbolTables;
    private IList<Integer> messages;
    private Statement rootProgram;
    private IFileTable<Integer, FileData> fileTable;
    private IHeap<Integer, Integer> heap;
    private IProcTable<String, Pair<List<String>,Statement>> procTable;

    public IProcTable<String, Pair<List<String>, Statement>> getProcTable() {
        return procTable;
    }

    public void setProcTable(IProcTable<String, Pair<List<String>, Statement>> procTable) {
        this.procTable = procTable;
    }

    private int id;

    public int getId() {return id;}

    public PrgState(IExecStack<Statement> e, IExecStack<IDictionary<String, Integer>> d, IList<Integer> m, Statement s,
                    IFileTable<Integer, FileData> f, IHeap<Integer, Integer> h, IProcTable<String, Pair<List<String>,Statement>> pr) {
        execStack = e;
        symbolTables = d;
        messages = m;
        rootProgram = s;
        fileTable = f;
        heap = h;
        procTable = pr;
        id = IDGenerator.generateID();
    }

    public IHeap<Integer, Integer> getHeap()
    {
        return heap;
    }

    public void setHeap(IHeap<Integer, Integer> h)
    {
        heap = h;
    }

    public IFileTable<Integer, FileData> getFileTable()
    {
        return fileTable;
    }

    public void setFileTable(IFileTable<Integer, FileData> f)
    {
        fileTable = f;
    }

    public void setRootProgram(Statement rootP) {
        this.rootProgram = rootP;
    }

    public void setSymbolTables(IExecStack<IDictionary<String, Integer>> symbolTable) {

        this.symbolTables = symbolTable;
    }

    public void setOutputList(IList<Integer> messages) {

        this.messages = messages;
    }

    public void setExecStack(IExecStack<Statement> execStack) {

        this.execStack = execStack;
    }

    public Statement getRootProgram() {

        return rootProgram;
    }

    public IDictionary<String, Integer> getSymbolTable() {

        return symbolTables.peek();
    }

    public IExecStack<IDictionary<String,Integer>> getAllSymbolTables(){
        return symbolTables;
    }

    public IList<Integer> getOutputList() {

        return messages;
    }

    public IExecStack<Statement> getExecStack() {

        return execStack;
    }

    @Override
    public String toString()
    {
        StringBuffer buff = new StringBuffer();
        buff.append("ID: " + id + "\n");
        buff.append(execStack);
        for(IDictionary<String,Integer> e : symbolTables.getAll())
            buff.append(e);
        buff.append(messages);
        buff.append(fileTable);
        buff.append(heap);
        return buff.toString();
    }

    public boolean isNotCompleted()
    {
        return !execStack.isEmpty();
    }

    public PrgState executeOneStep()
    {
        if(execStack.isEmpty())
            throw new EmptyExecStackException("No more steps to execute.");

        try
        {
            Statement stmt = execStack.pop();
            return stmt.execute(this);
        }
        catch(Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }
}
