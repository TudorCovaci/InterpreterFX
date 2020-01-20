package Model.Statements;

import Model.Exceptions.ProgramException;
import Model.ProgramState.*;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;
import javafx.util.Pair;

import java.util.List;

public class AwaitStmt implements IStmt {
    private IntValue var;

    public AwaitStmt(IntValue var) {
        this.var = var;
    }

    public PrgState execute(PrgState state) throws ProgramException {
        SymTable symTable = state.getSymTable();
        BarrierTable barrierTable = state.getBarrierTable();
        ExeStack stack = state.getStack();
        Value foundIndex = symTable.lookup(var.toString());
        if (foundIndex == null) {
            throw new ProgramException("AwaitStmt: Could not find var in sym table.");
        }
        if (foundIndex.getType().equals(new IntType())) {
            Pair<IntValue, List<IntValue>> pair = barrierTable.lookup(var.getValue());
            if (pair == null) {
                throw new ProgramException("AwaitStmt: No such pair entry found in Barrier Table.");
            }
            List<IntValue> list = pair.getValue();
            int listLength = list.size();
            if (pair.getKey().getValue() > listLength) {
                IntValue prgStateId = new IntValue(state.getId());
                for (int i = 0; i < listLength; i++) {
                    if (list.get(i).getValue() == prgStateId.getValue()) {
                        stack.push(new AwaitStmt(var));
                        return null;
                    }
                }
                list.add(prgStateId);
                stack.push(new AwaitStmt(var));
                return null;
            }
            return null;
        } else {
            throw new ProgramException("AwaitStmt: Invalid found index type.");
        }
    }

    @Override
    public Dictionary<String, Type> typecheck(Dictionary<String, Type> typeEnv) throws ProgramException {
        Type type1 = var.getType();
        if (type1.equals(new IntType())) {
            typeEnv.add(var.toString(), type1);
            return typeEnv;
        } else {
            throw new ProgramException("AwaitStmt: Invalid var type");
        }
    }
}
