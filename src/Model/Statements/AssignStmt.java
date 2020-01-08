package Model.Statements;

import Model.Exceptions.ProgramException;
import Model.Expressions.Exp;
import Model.ProgramState.Dictionary;
import Model.ProgramState.Heap;
import Model.ProgramState.PrgState;
import Model.ProgramState.SymTable;
import Model.Types.Type;
import Model.Values.Value;

public class AssignStmt implements IStmt {
    private String id;
    private Exp exp;

    public AssignStmt(String Id, Exp Exp) {
        this.id = Id;
        this.exp = Exp;
    }

    public String toString() {
        return id + "=" + exp.toString();
    }

    public PrgState execute(PrgState state) throws ProgramException {
        SymTable symbolTable = state.getSymTable();
        Heap heap = state.getHeap();
        Value value = exp.eval(symbolTable, heap);
        if (symbolTable.isDefined(id)) {
            Type typeId = (symbolTable.lookup(id).getType());
            if (value.getType().equals(typeId)) {
                symbolTable.update(id, value);
            } else {
                throw new ProgramException("Type mismatch in assignment of variable " + id);

            }

        } else {
            throw new ProgramException("Undeclared variable " + id);

        }

        return null;
    }

    public Dictionary<String, Type> typecheck(Dictionary<String, Type> typeEnv) throws ProgramException {
        Type typeVar = typeEnv.lookup(id);
        Type typeExp = exp.typecheck(typeEnv);
        if (typeVar.equals(typeExp)) {
            return typeEnv;
        } else {
            throw new ProgramException("AssignStmt: different operand types");
        }
    }
}
