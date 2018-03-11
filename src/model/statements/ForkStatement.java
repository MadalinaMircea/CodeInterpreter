package model.statements;

import model.prgstate.PrgState;
import model.utils.ExecStack;
import model.utils.IDictionary;
import model.utils.IExecStack;

public class ForkStatement implements Statement {
    private Statement stmt;
    public ForkStatement(Statement s)
    {
        stmt = s;
    }
    private IExecStack<IDictionary<String,Integer>> clone (IExecStack<IDictionary<String,Integer>> e)
    {
        IExecStack<IDictionary<String,Integer>> newStack = new ExecStack<>();
        for(IDictionary<String,Integer> d : e.getAll())
        {
            IDictionary<String,Integer> newDict = d.copy();
            newStack.push(newDict);
        }
        return newStack;
    }
    public PrgState execute(PrgState p)
    {
        IExecStack<IDictionary<String, Integer>> dictStack = clone(p.getAllSymbolTables());
        IExecStack<Statement> newStack = new ExecStack<>();
        newStack.push(stmt);
        PrgState state = new PrgState(newStack, dictStack, p.getOutputList(), stmt, p.getFileTable(), p.getHeap(), p.getProcTable());
        return state;
    }
    @Override
    public String toString()
    {
        return "fork(" + stmt + ")";
    }
}
