package Model.Expressions;

import Model.Exceptions.ProgramException;
import Model.ProgramState.Dictionary;
import Model.ProgramState.Heap;
import Model.ProgramState.SymTable;
import Model.Types.Type;
import Model.Values.Value;

public interface Exp {
    Value eval(SymTable table, Heap heap) throws ProgramException;

    Type typecheck(Dictionary<String, Type> typeEnv) throws ProgramException;
}
