package model.statements;

import model.prgstate.PrgState;
import model.expressions.Expression;
import model.utils.IDictionary;

public class AssignStatement implements Statement {
    private String varName;
    private Expression expr;

    public AssignStatement(String n, Expression e)
    {
        varName = n;
        expr = e;
    }

    public PrgState execute(PrgState p)
    {
        IDictionary<String,Integer> d = p.getSymbolTable();
        int r = expr.eval(d, p.getHeap());

        if(d.contains(varName))
            d.update(varName, r);
        else
            d.add(varName, r);

        return null;
    }
    public String toString()
    {
        return varName + "=" + expr;
    }
}
