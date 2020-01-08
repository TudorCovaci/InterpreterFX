package Model.Statements;

import Model.Exceptions.ProgramException;
import Model.Expressions.Exp;
import Model.ProgramState.Dictionary;
import Model.ProgramState.Heap;
import Model.ProgramState.PrgState;
import Model.ProgramState.SymTable;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;


public class IfStmt implements IStmt {
    private Exp exp;
    private IStmt thenStmt;
    private IStmt elseStmt;

    public IfStmt(Exp Expression, IStmt ThenStmt, IStmt ElseStmt) {
        this.exp = Expression;
        this.thenStmt = ThenStmt;
        this.elseStmt = ElseStmt;
    }

    public String toString() {
        return "IF(" + exp.toString() + ") THEN(" + thenStmt.toString() +
                ")ELSE(" + elseStmt.toString() + ")";
    }

    public PrgState execute(PrgState state) throws ProgramException {
        SymTable symTable = state.getSymTable();
        Heap heap = state.getHeap();
        Value expResult = exp.eval(symTable, heap);
        if (expResult.getType().equals(new BoolType())) {
            BoolValue boolResult = (BoolValue) expResult;
            if (boolResult.getValue()) {
                thenStmt.execute(state);

            } else if (elseStmt != null) {
                elseStmt.execute(state);
            }
        } else {
            throw new ProgramException("Invalid expression type");

        }
        return null;
    }

    public Dictionary<String, Type> typecheck(Dictionary<String, Type> typeEnv) throws ProgramException {
        Type typeExp = exp.typecheck(typeEnv);
        if (typeExp.equals(new BoolType())) {
            thenStmt.typecheck(new Dictionary<String, Type>(typeEnv));
            elseStmt.typecheck(new Dictionary<String, Type>(typeEnv));
            return typeEnv;
        } else {
            throw new ProgramException("Invalid type of if condition");
        }
    }
}
