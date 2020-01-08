package Model.ProgramState;

import Model.Exceptions.ProgramException;

public interface ExeStackInterface<T> {
    void push(T Statement);

    T pop() throws ProgramException;

    boolean isEmpty();
}
