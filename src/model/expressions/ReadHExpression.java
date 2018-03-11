package model.expressions;

import model.exceptions.InvalidVariableException;
import model.utils.IDictionary;
import model.utils.IHeap;

public class ReadHExpression implements Expression {
    private String varName;

    public ReadHExpression(String v)
    {
        varName = v;
    }

    @Override
    public int eval(IDictionary<String, Integer> d, IHeap<Integer, Integer> h)
    {
        if(!d.contains(varName))
            throw new InvalidVariableException("Variable does not exist.");

        int val = d.get(varName);

        if(!h.contains(val))
            throw new InvalidVariableException("Variable address does not exist.");

        return h.get(val);
    }

    @Override
    public String toString()
    {
        return "readH(" + varName + ")";
    }
}
