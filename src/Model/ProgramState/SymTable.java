package Model.ProgramState;

import Model.Values.Value;

import java.util.Map;

public class SymTable extends Dictionary<String, Value> {

    public SymTable() {
        super();
    }

    public SymTable(SymTable arg) {
        super(arg);
    }

    public String toString() {
        String string = "Symbol table:\n";
        for (Map.Entry<String, Value> entry : table.entrySet()) {

            string = string + entry.getKey() + " = " + entry.getValue().toString() + '\n';
        }
        return string;
    }
}
