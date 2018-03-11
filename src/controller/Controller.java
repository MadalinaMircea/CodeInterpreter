package controller;

import model.FileData;
import model.prgstate.PrgState;
import repository.IPrgStateRepository;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private ExecutorService es;
    private IPrgStateRepository repo;
    public Controller(IPrgStateRepository r)
    {
        repo = r;
    }
    public void addPrgState(PrgState p)
    {
        repo.addPrgState(p);
    }

    public int getNrStates() {return repo.size();}
    public void oneStepForAll(List<PrgState> prgList)
    {
        System.out.println("OneStep\n");
        prgList.forEach(prg ->repo.logPrgStateExec(prg));
        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(() -> {return p.executeOneStep();}))
                .collect(Collectors.toList());
        try
        {
            List<PrgState> newPrgList = es.invokeAll(callList).stream()
                    .map(future -> { try { return future.get();}
                    catch(Exception e) {
                        throw new RuntimeException(e.getMessage());
                    }}).filter(p -> p!=null)
                    .collect(Collectors.toList());
            prgList.addAll(newPrgList);
            prgList.forEach(prg ->repo.logPrgStateExec(prg));
            repo.setPrograms(prgList);
            System.out.println("OneStep DONE\n");
        }
        catch(Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void logToFile()
    {
        for(PrgState p : repo.getPrograms())
            repo.logPrgStateExec(p);
    }

    public List<PrgState> getAll()
    {
        return repo.getPrograms();
    }
    public void executeAll()
    {
        System.out.println("AllStep\n");
        es = Executors.newFixedThreadPool(2);
        List<PrgState> prg = removeCompletedPrg();
        while(prg.size() > 0)
        {
            oneStepForAll(prg);
            prg = removeCompletedPrg();
        }
        es.shutdownNow();
        repo.setPrograms(prg);
        System.out.println("AllStep DONE\n");
    }

    public Map<Integer, FileData> closeAllFiles(Map<Integer, FileData> f)
    {
        f.keySet().stream().forEach(i -> {
                    try {
                        f.get(i).getReader().close();
                        System.out.println("\nClosed file with key " + i);
                    } catch (Exception e) {
                        System.out.println("\nCould not close file with key " + i);
                    }
                }
        );
        return f;
    }
    public Map<Integer,Integer> conservativeGarbageCollector(Collection<Integer> symTableValues, Map<Integer,Integer> heap) {
        return heap.entrySet().stream()
                .filter(e -> symTableValues.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public String toString()
    {
        return "" + repo;
    }

    public List<PrgState> removeCompletedPrg()
    {
        List<PrgState> inPrgList = repo.getPrograms();
        return inPrgList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }

    public PrgState get(int i)
    {
        return repo.get(i);
    }

    public PrgState getById(int i)
    {
        for(int j = 0; j < repo.size(); j++)
            if(repo.get(j).getId() == i)
                return repo.get(j);
        return null;
    }

    public void setPrograms(List<PrgState> l)
    {
        repo.setPrograms(l);
    }
}
