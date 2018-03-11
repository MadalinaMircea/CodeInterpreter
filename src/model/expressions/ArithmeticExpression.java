package model.expressions;

import model.exceptions.DivisionByZeroException;
import model.exceptions.InvalidOperatorException;
import model.utils.IDictionary;
import model.utils.IHeap;

public class ArithmeticExpression implements Expression {
    private Expression left, right;
    private char operator;

    public ArithmeticExpression(char op, Expression l, Expression r)
    {
        operator = op;
        left = l;
        right = r;
    }

    public int eval(IDictionary<String,Integer> d, IHeap<Integer, Integer> h)
    {
        int l = left.eval(d, h);
        int r = right.eval(d, h);

        switch(operator)
        {
            case '+':
                return l + r;
            case '-':
                return l - r;
            case '*':
                return l * r;
            case '/':
                if(r == 0)
                {
                    throw new DivisionByZeroException("\nDivision by zero!\n");
                }
                else
                {
                    return l / r;
                }
            default:
            {
                throw new InvalidOperatorException("\nInvalid operator!\n");
            }
        }
    }
    public String toString()
    {
        return "" + left + operator + right;
    }
}
