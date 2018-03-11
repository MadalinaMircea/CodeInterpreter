package model.statements;

import model.expressions.Expression;
import model.prgstate.PrgState;
import model.utils.ExecStack;
import model.utils.IExecStack;

public class WhileStatement implements Statement {
    private Expression e;
    private Statement s;
    public WhileStatement(Expression expr, Statement st)
    {
        e = expr;
        s = st;
    }

    @Override
    public PrgState execute(PrgState p) {
        int val = e.eval(p.getSymbolTable(), p.getHeap());
        if(val != 0)
        {
            p.getExecStack().push(new WhileStatement(e, s));
            s.execute(p);
        }

        return null;
    }

    @Override
    public String toString()
    {
        return "while(" + e + ") " + s;
    }
}
