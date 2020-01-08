package Model.Statements;

import Model.Exceptions.ProgramException;
import Model.Expressions.Exp;
import Model.ProgramState.Dictionary;
import Model.ProgramState.Heap;
import Model.ProgramState.PrgState;
import Model.ProgramState.SymTable;
import Model.Types.RefType;
import Model.Types.Type;
import Model.Values.RefValue;
import Model.Values.Value;

public class NewStmt implements IStmt {
    private String varName;
    private Exp expression;

    public NewStmt(String varName, Exp expression) {
        this.varName = varName;
        this.expression = expression;
    }

    public String toString() {
        return "new(" + varName + " " + expression.toString() + ")";
    }

    public PrgState execute(PrgState state) throws ProgramException {
        SymTable symTable = state.getSymTable();
        Heap heap = state.getHeap();

        if (symTable.isDefined(varName)) {
            Value val = symTable.lookup(varName);
            Value expResult = expression.eval(symTable, heap);
            RefType refType = (RefType) val.getType();
            if (val.getType().equals(new RefType(refType.getInner()))) {
                RefType valType = (RefType) val.getType();
                if (expResult.getType().equals(valType.getInner())) {
                    int key = Heap.generateKey();
                    RefValue refValue = new RefValue(key, expResult.getType());
                    heap.add(key, expResult);
                    symTable.update(varName, refValue);
                } else {
                    throw new ProgramException("Invalid expression type");
                }
            } else {
                throw new ProgramException("Invalid value type: " + expResult.toString() + " " + expression.toString());
            }
        } else {
            throw new ProgramException("Cannot find referenced variable " + varName);
        }
        return null;

    }

    @Override
    public Dictionary<String, Type> typecheck(Dictionary<String, Type> typeEnv) throws ProgramException {
        Type typeVar = typeEnv.lookup(varName);
        Type typeExp = expression.typecheck(typeEnv);
        if (typeVar.equals(new RefType(typeExp))) {
            return typeEnv;

        } else {
            throw new ProgramException("New statement: invalid operand types");
        }
    }
}
