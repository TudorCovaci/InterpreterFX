package Model.Statements;

import Model.Exceptions.ProgramException;
import Model.Expressions.Exp;
import Model.ProgramState.*;
import Model.Types.Type;

public class PrintStmt implements IStmt {
    private Exp exp;

    public PrintStmt(Exp Expression) {
        this.exp = Expression;
    }

    public String toString() {
        return "print(" + exp.toString() + ")";
    }

    public PrgState execute(PrgState state) throws ProgramException {
        OutputInterface<String> output = state.getOutput();
        SymTable symTable = state.getSymTable();
        Heap heap = state.getHeap();
        output.add(exp.eval(symTable, heap).toString());
        return null;
    }

    public Dictionary<String, Type> typecheck(Dictionary<String, Type> typeEnv) throws ProgramException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }
}
