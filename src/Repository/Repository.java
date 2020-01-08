package Repository;

import Model.Exceptions.ProgramException;
import Model.ProgramState.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Repository implements RepoInterface {
    private ArrayList<PrgState> states;
    private String logFilePath;

    public Repository() {
        states = new ArrayList<>();
    }

    public void add(PrgState state) {
        states.add(state);
    }

    public int getLength()
    {
        return states.size();
    }

    public void logPrgStateExec(PrgState state) throws ProgramException {
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter("output.txt", true)));
            logFile.write(state.toString());
            logFile.flush();
        } catch (Exception ex) {
            throw new ProgramException(ex.getMessage());
        }
    }

    public ArrayList<PrgState> getPrgList() {
        return states;
    }

    public void setPrgList(ArrayList<PrgState> prgStates) {
        states = prgStates;
    }

}
