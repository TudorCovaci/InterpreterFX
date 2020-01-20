package Model.Statements;

import Model.Exceptions.ProgramException;
import Model.Expressions.Exp;
import Model.ProgramState.*;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

public class RepeatUntilStmt implements IStmt {
    private IStmt statement;
    private Exp expression;

    public RepeatUntilStmt(IStmt stmt, Exp expression) {
        this.statement = stmt;
        this.expression = expression;
    }

    public PrgState execute(PrgState state) throws ProgramException {
        ExeStack stack = state.getStack();
        SymTable symTable = state.getSymTable();
        Heap heap = state.getHeap();
        Value expValue = expression.eval(symTable, heap);
        if (expValue.getType().equals(new BoolType())) {
            BoolValue boolValue = (BoolValue) expValue;

            if (!boolValue.getValue()) {
                CompStmt compStmt = new CompStmt(statement, new
                        RepeatUntilStmt(statement, expression));
                stack.push(compStmt);
            }
        }
        return null;
    }

    @Override
    public Dictionary<String, Type> typecheck(Dictionary<String, Type> typeEnv) throws ProgramException {
        Type typeExp = expression.typecheck(typeEnv);
        if (typeExp.equals(new BoolType())) {
            statement.typecheck(new Dictionary<String, Type>(typeEnv));
            return typeEnv;
        } else {
            throw new ProgramException("Invalid repeat-until condition type");
        }
    }

    public String toString() {
        return "REPEAT { " + statement + " } UNTIL(" + expression + ")";
    }
}
