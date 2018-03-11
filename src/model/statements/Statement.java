package model.statements;

import model.prgstate.PrgState;

public interface Statement {
    public PrgState execute(PrgState p);
}
