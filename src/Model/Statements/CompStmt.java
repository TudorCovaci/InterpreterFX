package Model.Statements;

import Model.Exceptions.ProgramException;
import Model.ProgramState.Dictionary;
import Model.ProgramState.ExeStack;
import Model.ProgramState.PrgState;
import Model.Types.Type;

public class CompStmt implements IStmt {
    private IStmt first;
    private IStmt second;

    public CompStmt(IStmt First, IStmt Second) {
        this.first = First;
        this.second = Second;
    }

    public String toString() {
        return first.toString() + ";" + second.toString();
    }

    @Override
    public PrgState execute(PrgState state) throws ProgramException {
        ExeStack stack = state.getStack();
        stack.push(second);
        stack.push(first);
        return null;
    }

    public Dictionary<String, Type> typecheck(Dictionary<String, Type> typeEnv) throws ProgramException {
        return second.typecheck(first.typecheck(typeEnv));
    }

    public IStmt getFirst() {
        return first;
    }

    public IStmt getSecond() {
        return second;
    }
}
