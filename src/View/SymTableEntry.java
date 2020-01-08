package View;

import Model.ProgramState.SymTable;
import Model.Values.Value;

public class SymTableEntry {
    private String nameColumn;
    private Value valueColumn;

    public SymTableEntry(String nameColumn, Value valueColumn)
    {
        this.nameColumn = nameColumn;
        this.valueColumn = valueColumn;
    }

    public String getNameColumn()
    {
        return nameColumn;
    }

    public Value getValueColumn()
    {
        return valueColumn;
    }
}
