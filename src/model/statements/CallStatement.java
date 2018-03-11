package model.statements;

import javafx.util.Pair;
import model.expressions.Expression;
import model.prgstate.PrgState;
import model.utils.Dictionary;
import model.utils.IDictionary;
import model.utils.IProcTable;

import java.util.List;

public class CallStatement implements Statement {
    private String fname;
    private List<Expression> params;

    public CallStatement(String f, List<Expression> par) {
        fname = f;
        params = par;
    }

    @Override
    public PrgState execute(PrgState p) {

        IProcTable<String, Pair<List<String>,Statement>> procTable = p.getProcTable();
        if(!procTable.contains(fname))
            throw new RuntimeException("ProcTable does not contain " + fname);

        IDictionary<String, Integer> newDict = new Dictionary<>();
        List<String> paramList = procTable.get(fname).getKey();
        for(int i = 0; i < paramList.size(); i++)
        {
            newDict.add(paramList.get(i), params.get(i).eval(p.getSymbolTable(),p.getHeap()));
        }

        p.getAllSymbolTables().push(newDict);
        p.getExecStack().push(new ReturnStatement());
        p.getExecStack().push(procTable.get(fname).getValue());

        return null;
    }

    @Override
    public String toString()
    {
        String par = "";
        for(Expression p : params)
            par = par + " " + p + " ";
        return "call " + fname + "(" + par + ") ";
    }
}
