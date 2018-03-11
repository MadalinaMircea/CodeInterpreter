package model.statements;

import model.exceptions.InvalidVariableException;
import model.expressions.Expression;
import model.prgstate.PrgState;
import model.utils.IDictionary;
import model.utils.IHeap;

public class WriteHStatement implements Statement {
    private String varName;
    private Expression expr;

    public WriteHStatement(String v, Expression e)
    {
        varName = v;
        expr = e;
    }

    @Override
    public PrgState execute(PrgState p)
    {
        IHeap<Integer, Integer> h = p.getHeap();
        IDictionary<String, Integer> d = p.getSymbolTable();

        if(!d.contains(varName))
            throw new InvalidVariableException("Variable does not exist.");

        int val = d.get(varName);

        if(!h.contains(val))
            throw new InvalidVariableException("Variable address does not exist.");

        h.update(val, expr.eval(d, h));

        return null;
    }

    @Override
    public String toString()
    {
        return "writeH(" + expr + ")";
    }
}
