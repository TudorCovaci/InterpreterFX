package Model.Types;


import Model.Values.IntValue;
import Model.Values.Value;

public class IntType implements Type {

    public IntType() {

    }

    public boolean equals(Object another) {
        return another instanceof IntType;
    }

    public String toString() {
        return "int";
    }

    public Value defaultValue() {
        return new IntValue(0);
    }
}

