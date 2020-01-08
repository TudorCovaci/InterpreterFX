package Model.Statements;

import Model.Exceptions.ProgramException;
import Model.Expressions.Exp;
import Model.ProgramState.Dictionary;
import Model.ProgramState.Heap;
import Model.ProgramState.PrgState;
import Model.ProgramState.SymTable;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFile implements IStmt {
    private Exp expression;

    public OpenRFile(Exp expression) {
        this.expression = expression;
    }

    public PrgState execute(PrgState state) throws ProgramException {

        SymTable table = state.getSymTable();
        Heap heap = state.getHeap();
        Value value = expression.eval(table, heap);
        Dictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        if (value.getType().equals(new StringType())) {
            StringValue stringVal = (StringValue) value;
            String key = stringVal.getValue();
            if (!fileTable.isDefined(stringVal)) {
                BufferedReader buffer;
                try {
                    buffer = new BufferedReader(new FileReader(key));
                } catch (IOException e) {
                    throw new ProgramException(e.getMessage());
                }
                fileTable.add(stringVal, buffer);
            }
        } else {
            throw new ProgramException("Invalid expression type!\n");
        }
        return null;
    }

    public String toString() {
        return "OpenFile(" + expression.toString() + ")";
    }

    @Override
    public Dictionary<String, Type> typecheck(Dictionary<String, Type> typeEnv) throws ProgramException {
        Type typeExp = expression.typecheck(typeEnv);
        if (typeExp.equals(new StringType())) {
            return typeEnv;
        } else {
            throw new ProgramException("OpenRFile: Invalid expression type");
        }
    }
}
