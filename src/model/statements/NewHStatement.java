package model.statements;

import model.expressions.Expression;
import model.prgstate.PrgState;
import model.utils.IDGenerator;
import model.utils.IDictionary;
import model.utils.IHeap;

public class NewHStatement implements Statement {
    private String varName;
    private Expression expr;

    public NewHStatement(String v, Expression e)
    {
        varName = v;
        expr = e;
    }

    public PrgState execute(PrgState state)
    {
        IHeap<Integer, Integer> h = state.getHeap();
        IDictionary<String, Integer> d = state.getSymbolTable();
        int val = expr.eval(d, h);
        int id = IDGenerator.generateID();
        h.add(id, val);

        if(d.contains(varName))
            d.update(varName, id);
        else
            d.add(varName, id);

        return null;
    }

    @Override
    public String toString()
    {
        return "new(" + varName + ", " + expr + ")";
    }

}
