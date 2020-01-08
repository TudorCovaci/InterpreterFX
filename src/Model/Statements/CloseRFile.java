package Model.Statements;

import Model.Exceptions.ProgramException;
import Model.Expressions.Exp;
import Model.ProgramState.*;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile implements IStmt {
    private Exp exp;

    public CloseRFile(Exp Expression) {
        this.exp = Expression;
    }

    public PrgState execute(PrgState state) throws ProgramException {
        SymTable symTable = state.getSymTable();
        FileTable fileTable = state.getFileTable();
        Heap heap = state.getHeap();
        Value value = exp.eval(symTable, heap);
        if (value.getType().equals(new StringType())) {
            StringValue strVal = (StringValue) value;
            if (fileTable.isDefined(strVal)) {
                BufferedReader buffer;
                buffer = fileTable.lookup(strVal);
                try {
                    buffer.close();
                } catch (IOException e) {
                    throw new ProgramException(e.getMessage());
                }
                fileTable.delete(strVal);
            } else {
                throw new ProgramException("No file table entry for string: " + strVal.toString());
            }
        } else {
            throw new ProgramException("Invalid value type: " + exp.toString());
        }
        return null;
    }

    public String toString() {
        return "Close(" + exp.toString() + ")";
    }

    @Override
    public Dictionary<String, Type> typecheck(Dictionary<String, Type> typeEnv) throws ProgramException {
        Type typeExp = exp.typecheck(typeEnv);
        if (typeExp.equals(new StringType())) {
            return typeEnv;
        } else {
            throw new ProgramException("CloseRFile: Invalid expression type");
        }
    }
}
