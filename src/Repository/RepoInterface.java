package Repository;

import Model.Exceptions.ProgramException;
import Model.ProgramState.PrgState;

import java.util.ArrayList;

public interface RepoInterface {
    //PrgState getCrtPrg();

    void add(PrgState State);


    void logPrgStateExec(PrgState state) throws ProgramException;

    ArrayList<PrgState> getPrgList();

    void setPrgList(ArrayList<PrgState> prgStates);

    int getLength();

}
