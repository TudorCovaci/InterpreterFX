package Model.Statements;

import Model.Exceptions.ProgramException;
import Model.Expressions.Exp;
import Model.ProgramState.*;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class NewBarrierStmt implements IStmt {
    private Exp expression;
    private IntValue var;

    public NewBarrierStmt(Exp expression, IntValue var) {
        this.expression = expression;
        this.var = var;
    }

    public PrgState execute(PrgState state) throws ProgramException {
        SymTable symTable = state.getSymTable();
        Heap heap = state.getHeap();
        BarrierTable barrierTable = state.getBarrierTable();
        Value expValue = expression.eval(symTable, heap);
        if (expValue.getType().equals(new IntType())) {
            IntValue intValue = (IntValue) expValue;
            Pair<IntValue, List<IntValue>> newPair = new Pair<>(intValue, new ArrayList<>());
            IntValue newFreeLocation = new IntValue(BarrierTable.generateKey());
            barrierTable.add(BarrierTable.generateKey(), newPair);
            Value value = symTable.lookup(var.toString());
            if (value != null && value.getType().equals(new IntType())) {
                symTable.update(var.toString(), newFreeLocation);
            } else {
                throw new ProgramException("NewBarrier: Unable to find " + var + " in symbol table.");
            }

        } else {
            throw new ProgramException("NewBarrier: Invalid expression type.");
        }
        return null;
    }

    @Override
    public Dictionary<String, Type> typecheck(Dictionary<String, Type> typeEnv) throws ProgramException {
        Type type1 = var.getType();
        Type type2 = expression.typecheck(typeEnv);
        if (type1.equals(new IntType())) {
            typeEnv.add(var.toString(), type1);
            if (type2.equals(new IntType())) {
                return typeEnv;
            } else {
                throw new ProgramException("NewBarrier: Invalid expression type (not int).");
            }
        } else {
            throw new ProgramException("NewBarrier: Invalid var type (not int)");
        }
    }

    public String toString() {
        return "NewBarrier(" + var + ", " + expression + ")";
    }
}
