package Model.Statements;

import Model.Exceptions.ProgramException;
import Model.ProgramState.Dictionary;
import Model.ProgramState.PrgState;
import Model.ProgramState.SymTable;
import Model.Types.*;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.RefValue;
import Model.Values.StringValue;

public class VarDeclStmt implements IStmt {
    private String name;
    private Type type;

    public VarDeclStmt(String Name, Type Type) {
        this.name = Name;
        this.type = Type;
    }

    public String toString() {
        return type.toString() + " " + name;
    }

    public PrgState execute(PrgState state) throws ProgramException {
        SymTable symTable = state.getSymTable();

        if (type.equals(new IntType())) {
            symTable.add(name, new IntValue(0));
        } else if (type.equals(new BoolType())) {
            symTable.add(name, new BoolValue(false));
        } else if (type.equals(new StringType())) {
            symTable.add(name, new StringValue(""));
        } else {
            RefType refType = (RefType) type;

            if (type.equals(new RefType(refType.getInner()))) {

                symTable.add(name, new RefValue(0, refType.getInner()));
            } else {
                throw new ProgramException("Invalid type");
            }
        }

        return null;
    }

    public Dictionary<String, Type> typecheck(Dictionary<String, Type> typeEnv) throws ProgramException {
        typeEnv.add(name, type);
        return typeEnv;
    }
}
