package model.statements;

import model.prgstate.PrgState;
import model.expressions.Expression;

public class IfStatement implements Statement {
    private Statement ifStmt, elseStmt;
    private Expression cond;
    public IfStatement(Statement first, Statement second, Expression e)
    {
        ifStmt = first;
        elseStmt = second;
        cond = e;
    }
    public PrgState execute(PrgState p)
    {
        int r = cond.eval(p.getSymbolTable(), p.getHeap());
        if(r == 0)
            p.getExecStack().push(elseStmt);
        else
            p.getExecStack().push(ifStmt);
        return null;
    }
    public String toString()
    {
        return "if " + cond + " then " + ifStmt + " else " + elseStmt;
    }
}
