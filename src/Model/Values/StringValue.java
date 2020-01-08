package Model.Values;

import Model.Types.StringType;
import Model.Types.Type;

public class StringValue implements Value {
    private String value;

    public StringValue(String arg) {
        value = arg;
    }

    public String getValue() {
        return value;
    }

    public Type getType() {
        return new StringType();
    }

    public boolean equals(Object another) {
        if (another instanceof StringValue) {
            return this.value.equals(((StringValue) another).value);
        }
        return false;
    }

    public String toString() {
        return "\"" + value + "\"";
    }


}
