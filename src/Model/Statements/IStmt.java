package Model.Statements;

import Model.Exceptions.ProgramException;
import Model.ProgramState.Dictionary;
import Model.ProgramState.PrgState;
import Model.Types.Type;

public interface IStmt {

    PrgState execute(PrgState state) throws ProgramException;

    String toString();

    Dictionary<String, Type> typecheck(Dictionary<String, Type> typeEnv) throws ProgramException;
}
