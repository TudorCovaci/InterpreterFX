package View;

import Model.Values.Value;

public class HeapTableEntry {
    private Integer addressColumn;
    private Value valueColumn;

    public HeapTableEntry(Integer address, Value value)
    {
        this.addressColumn = address;
        this.valueColumn = value;
    }

    public Integer getAddressColumn() {
        return addressColumn;
    }

    public Value getValueColumn() {
        return valueColumn;
    }
}
