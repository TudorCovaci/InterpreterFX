package Model.Types;

import Model.Values.RefValue;
import Model.Values.Value;

public class RefType implements Type {
    private Type inner;

    public RefType() {

    }

    public RefType(Type inner) {
        this.inner = inner;
    }

    public Type getInner() {
        return inner;
    }

    public boolean equals(Object another) {
        if (another instanceof RefType) {
            return inner.equals(((RefType) another).getInner());

        } else {
            return false;
        }

    }

    public String toString() {
        if (inner != null) {
            return "Ref(" + inner.toString() + ")";
        } else {
            return "Ref()";
        }
    }

    public Value defaultValue() {
        return new RefValue(0, inner);
    }

}
