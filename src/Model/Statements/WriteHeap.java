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

public class WriteHeap implements IStmt {
    private String address;
    private Exp expression;

    public WriteHeap(String address, Exp expression) {
        this.address = address;
        this.expression = expression;
    }

    public String toString() {
        return "WriteHeap(" + address + ", " + expression.toString() + ")";
    }

    public PrgState execute(PrgState state) throws ProgramException {
        SymTable symTable = state.getSymTable();
        Heap heap = state.getHeap();

        if (symTable.isDefined(address)) {
            Value value = symTable.lookup(address);
            // RefType(???)
            if (value instanceof RefValue) {
                RefValue refValue = (RefValue) value;

                RefType valType = (RefType) value.getType();
                if (heap.isDefined(refValue.getAddr())) {
                    Value expValue = expression.eval(symTable, heap);
                    if (expValue.getType().equals(valType.getInner())) {
                        heap.update(refValue.getAddr(), expValue);
                    }
                } else {
                    throw new ProgramException("Invalid reference of heap memory");
                }
            } else {
                throw new ProgramException("Invalid value type !!" + expression.toString() + "address: " + address);
            }
        } else {
            throw new ProgramException("Undeclared variable " + address);
        }
        return null;

    }

    @Override
    public Dictionary<String, Type> typecheck(Dictionary<String, Type> typeEnv) throws ProgramException {
        Type typeVar = typeEnv.lookup(address);
        Type typeExp = expression.typecheck(typeEnv);
        if (typeVar instanceof RefType) {
            RefType refType = (RefType) typeVar;
            if (typeExp.equals(refType.getInner())) {
                return typeEnv;
            } else {
                throw new ProgramException("WriteHeap: Invalid expression type assigned to memory location");
            }
        } else {
            throw new ProgramException("WriteHeap: Invalid address type");
        }
    }
}
