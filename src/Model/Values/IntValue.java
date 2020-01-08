package Model.Values;

import Model.Types.IntType;
import Model.Types.Type;

public class IntValue implements Value {
    private int value;

    public IntValue(int Value) {
        this.value = Value;
    }

    public int getValue() {
        return value;
    }

    public Type getType() {
        return new IntType();
    }

    public String toString() {
        return "" + value;
    }

    public boolean equals(Object another) {
        if (another instanceof IntValue) {
            return this.value == ((IntValue) another).value;
        }
        return false;
    }

}
