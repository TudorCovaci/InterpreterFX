package Model.Expressions;

import Model.Exceptions.ProgramException;
import Model.ProgramState.Dictionary;
import Model.ProgramState.Heap;
import Model.ProgramState.SymTable;
import Model.Types.RefType;
import Model.Types.Type;
import Model.Values.RefValue;
import Model.Values.Value;

public class ReadHeap implements Exp {
    private Exp expression;

    public ReadHeap(Exp expression) {
        this.expression = expression;
    }

    public String toString() {
        return "HeapRead(" + expression.toString() + ")";
    }

    public Value eval(SymTable symTable, Heap heap) throws ProgramException {

        Value expResult = expression.eval(symTable, heap);
        if (expResult instanceof RefValue) {

            RefValue refValue = (RefValue) expResult;
            RefType refType = (RefType) refValue.getType();
            if (refType.equals(new RefType(refType.getInner()))) {
                if (heap.isDefined(refValue.getAddr())) {
                    return heap.lookup(refValue.getAddr());
                } else {
                    throw new ProgramException("Invalid reference of memory address " + refValue.getAddr());
                }
            } else {
                throw new ProgramException("Invalid inner type");
            }

        } else {
            throw new ProgramException("Invalid expression type");
        }
    }

    public Type typecheck(Dictionary<String, Type> typeEnv) throws ProgramException {
        Type type = expression.typecheck(typeEnv);
        if (type instanceof RefType) {
            RefType refType = (RefType) type;
            return refType.getInner();
        } else {
            throw new ProgramException("Invalid type of ReadHeap argument");
        }
    }
}
