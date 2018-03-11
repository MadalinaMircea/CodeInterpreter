package model.statements;

import model.prgstate.PrgState;

public class ReturnStatement implements Statement {
    public ReturnStatement() {};

    @Override
    public PrgState execute(PrgState p) {
        p.getAllSymbolTables().pop();
        return null;
    }
    @Override
    public String toString()
    {
        return "return";
    }
}
