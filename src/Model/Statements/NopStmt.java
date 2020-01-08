package Model.Statements;

import Model.Exceptions.ProgramException;
import Model.ProgramState.Dictionary;
import Model.ProgramState.PrgState;
import Model.Types.Type;

public class NopStmt implements IStmt {

    public String toString() {
        return "NOP";
    }

    public PrgState execute(PrgState state) throws ProgramException {
        return null;
    }

    @Override
    public Dictionary<String, Type> typecheck(Dictionary<String, Type> typeEnv) throws ProgramException {
        return typeEnv;
    }
}
