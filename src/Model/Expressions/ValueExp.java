package Model.Expressions;

import Model.Exceptions.ProgramException;
import Model.ProgramState.Dictionary;
import Model.ProgramState.Heap;
import Model.ProgramState.SymTable;
import Model.Types.Type;
import Model.Values.Value;

public class ValueExp implements Exp {
    private Value value;

    public ValueExp(Value Value) {
        this.value = Value;
    }

    public String toString() {
        return value.toString();
    }

    public Value eval(SymTable Table, Heap heap) throws ProgramException {
        return value;
    }

    public Type typecheck(Dictionary<String, Type> typeEnv) throws ProgramException {
        return value.getType();
    }

}
