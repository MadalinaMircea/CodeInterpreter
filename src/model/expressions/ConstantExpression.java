package model.expressions;

import model.utils.IDictionary;
import model.utils.IHeap;

public class ConstantExpression implements Expression {
    private int value;
    public ConstantExpression(int v)
    {
        value = v;
    }
    public int eval(IDictionary<String,Integer> d, IHeap<Integer, Integer> h)
    {
        return value;
    }
    public String toString()
    {
        return "" + value;
    }
}
