package model.statements;

import model.prgstate.PrgState;
import model.expressions.Expression;

public class PrintStatement implements Statement {
    private Expression expr;
    public PrintStatement(Expression e)
    {
        expr = e;
    }
    public PrgState execute(PrgState p)
    {
        int r = expr.eval(p.getSymbolTable(), p.getHeap());
        p.getOutputList().add(r);
        return null;
    }
    public String toString()
    {
        return "Print(" + expr + ")";
    }
}
