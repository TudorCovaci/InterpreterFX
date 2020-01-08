package Model.Statements;

import Model.Exceptions.ProgramException;
import Model.Expressions.Exp;
import Model.ProgramState.*;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

public class WhileStmt implements IStmt {
    private Exp expression;
    private IStmt statement;

    public WhileStmt(Exp expression, IStmt statement) {
        this.expression = expression;
        this.statement = statement;
    }

    public String toString() {
        return "WHILE(" + expression.toString() + ")" + statement.toString();
    }

    public PrgState execute(PrgState state) throws ProgramException {
        ExeStack stack = state.getStack();
        SymTable symTable = state.getSymTable();
        Heap heap = state.getHeap();

        Value expValue = expression.eval(symTable, heap);
        if (expValue.getType().equals(new BoolType())) {
            BoolValue boolValue = (BoolValue) expValue;
            if (!boolValue.getValue()) {
                return null;
            } else {
                stack.push(this);
                stack.push(statement);
                statement.execute(state);
                return null;
            }
        } else {
            throw new ProgramException("Invalid expression type");
        }
    }

    @Override
    public Dictionary<String, Type> typecheck(Dictionary<String, Type> typeEnv) throws ProgramException {
        Type typeExp = expression.typecheck(typeEnv);
        if (typeExp.equals(new BoolType())) {
            statement.typecheck(new Dictionary<String, Type>(typeEnv));
            return typeEnv;
        } else {
            throw new ProgramException("Invalid while expression type");
        }
    }
}
