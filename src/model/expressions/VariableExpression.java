package model.expressions;

import model.exceptions.InvalidVariableException;
import model.utils.IDictionary;
import model.utils.IHeap;

public class VariableExpression implements Expression {
    private String name;
    public VariableExpression(String n)
    {
        name = n;
    }
    public int eval(IDictionary<String,Integer> d, IHeap<Integer, Integer> h)
    {
        if(d.contains(name))
            return d.get(name);

        throw new InvalidVariableException("\nInvalid variable\n.");
    }
    public String toString()
    {
        return name;
    }
}
