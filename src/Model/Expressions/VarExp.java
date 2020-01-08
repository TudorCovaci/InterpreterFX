package Model.Expressions;

import Model.Exceptions.ProgramException;
import Model.ProgramState.Dictionary;
import Model.ProgramState.Heap;
import Model.ProgramState.SymTable;
import Model.Types.Type;
import Model.Values.Value;

public class VarExp implements Exp {
    private String name;

    public VarExp(String Name) {
        this.name = Name;
    }

    public String toString() {
        return name;
    }

    public Value eval(SymTable symTable, Heap heap) throws ProgramException {
        Value value = symTable.lookup(name);
        if (null == value) {
            throw new ProgramException("Unresolved variable " + name);
        }
        return value;
    }

    public Type typecheck(Dictionary<String, Type> typeEnv) throws ProgramException {
        return typeEnv.lookup(name);
    }

}
