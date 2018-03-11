package model.statements;

import javafx.util.Pair;
import model.prgstate.PrgState;

import java.util.List;

public class ProcedureStatement implements Statement {
    private String fname;
    private List<String> params;
    private Statement statement;
    public ProcedureStatement(String n, List<String> p, Statement s)
    {
        fname = n;
        params = p;
        statement = s;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    public List<String> getParams() {

        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

    public String getFname() {

        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    @Override
    public PrgState execute(PrgState p) {
        p.getProcTable().add(fname, new Pair<>(params, statement));
        return null;
    }

    @Override
    public String toString()
    {
        String par = "";
        for(String p : params)
            par = par + " " + p + " ";
        return fname + "(" + par + ") " + statement;
    }
}
