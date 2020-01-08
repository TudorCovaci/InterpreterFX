package Model.Statements;

import Model.Exceptions.ProgramException;
import Model.Expressions.Exp;
import Model.ProgramState.*;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;


public class ReadFile implements IStmt {
    private Exp exp;
    private String varName;

    public ReadFile(Exp Exp, String VarName) {
        this.exp = Exp;
        this.varName = VarName;
    }

    public PrgState execute(PrgState state) throws ProgramException {
        SymTable symTable = state.getSymTable();
        FileTable fileTable = state.getFileTable();
        Heap heap = state.getHeap();
        if (symTable.isDefined(varName)) {
            Value value = symTable.lookup(varName);
            if (value.getType().equals(new IntType())) {
                Value expVal = exp.eval(symTable, heap);
                if (expVal.getType().equals(new StringType())) {
                    StringValue stringVal = (StringValue) expVal;
                    if (fileTable.isDefined(stringVal)) {
                        BufferedReader buffer = fileTable.lookup(stringVal);
                        String line;
                        try {
                            line = buffer.readLine();
                        } catch (IOException e) {
                            throw new ProgramException(e.getMessage());
                        }
                        IntValue intVal;
                        if (line == null) {
                            intVal = new IntValue(0);
                        } else {
                            intVal = new IntValue(Integer.parseInt(line));
                        }
                        symTable.update(varName, intVal);
                    } else {
                        throw new ProgramException("Undefined variable " + varName);
                    }

                } else {
                    throw new ProgramException("Invalid expression result type!");
                }

            } else {
                throw new ProgramException("Invalid variable type");
            }
        }
        return null;
    }

    public String toString() {
        return "ReadFile(" + varName + ", " + exp.toString() + ")";
    }

    @Override
    public Dictionary<String, Type> typecheck(Dictionary<String, Type> typeEnv) throws ProgramException {
        Type typeVar = typeEnv.lookup(varName);
        Type typeExp = exp.typecheck(typeEnv);
        if (typeVar.equals(typeExp)) {
            return typeEnv;
        } else {
            throw new ProgramException("ReadFile: Invalid operands' type");
        }
    }
}
