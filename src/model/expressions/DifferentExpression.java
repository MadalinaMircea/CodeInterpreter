package model.expressions;

import model.utils.IDictionary;
import model.utils.IHeap;

public class DifferentExpression implements Expression {
    private Expression expr1, expr2;
    public DifferentExpression(Expression e1, Expression e2)
    {
        expr1 = e1;
        expr2 = e2;
    }
    public int eval(IDictionary<String, Integer> d, IHeap<Integer, Integer> h)
    {
        int e1 = expr1.eval(d,h);
        int e2 = expr2.eval(d,h);

        if(e1 != e2)
            return 1;
        return 0;
    }
    @Override
    public String toString()
    {
        return "" + expr1 + " != " + expr2;
    }
}
