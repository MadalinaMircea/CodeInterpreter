package model.statements;

import model.prgstate.PrgState;

public class CompoundStatement implements Statement {
    private Statement first, second;
    public CompoundStatement(Statement f, Statement s)
    {
        first = f;
        second = s;
    }
    public PrgState execute(PrgState p)
    {
        p.getExecStack().push(second);
        p.getExecStack().push(first);
        return null;
    }
    public String toString()
    {
        return "{" + first + "; " + second  + "}";
    }
}
