package repository;

import model.prgstate.PrgState;

import java.util.List;

public interface IPrgStateRepository {
    void addPrgState(PrgState p);

    void logPrgStateExec(PrgState p);

    void setPrograms(List<PrgState> l);

    List<PrgState> getPrograms();

    int size();

    PrgState get(int i);
}
