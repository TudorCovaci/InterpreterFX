package Model.Values;

import Model.Types.BoolType;
import Model.Types.Type;

public class BoolValue implements Value {
    private boolean value;

    public BoolValue(boolean Value) {
        this.value = Value;
    }

    public boolean getValue() {
        return value;
    }

    public Type getType() {
        return new BoolType();
    }

    public String toString() {
        return "" + value;

    }

    public boolean equals(Object another) {
        if (another instanceof BoolValue) {
            return this.value == ((BoolValue) another).value;
        }
        return false;
    }
}
