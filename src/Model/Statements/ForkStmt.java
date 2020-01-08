package Model.Statements;

import Model.Exceptions.ProgramException;
import Model.ProgramState.Dictionary;
import Model.ProgramState.PrgState;
import Model.ProgramState.SymTable;
import Model.Types.Type;

public class ForkStmt implements IStmt {
    private IStmt statement;

    public ForkStmt(IStmt statement) {
        this.statement = statement;
    }

    public PrgState execute(PrgState state) {
        PrgState newThread = new PrgState();
        SymTable symTable = new SymTable(state.getSymTable());
        newThread.setSymTable(symTable);
        newThread.setFileTable(state.getFileTable());
        newThread.setHeap(state.getHeap());
        newThread.setOutput(state.getOutput());
        newThread.getStack().push(statement);
        return newThread;

    }

    public String toString() {
        return "fork(" + statement.toString() + ")";
    }

    @Override
    public Dictionary<String, Type> typecheck(Dictionary<String, Type> typeEnv) throws ProgramException {
        statement.typecheck(new Dictionary<String, Type>(typeEnv));
        return typeEnv;
    }
}
