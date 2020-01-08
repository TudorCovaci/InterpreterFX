package Model.ProgramState;

import Model.Exceptions.ProgramException;

import java.util.HashMap;

public interface DictionaryInterface<T, K> {
    HashMap<T, K> getTable();

    K lookup(T Name);

    boolean isDefined(T Id);

    void add(T Name, K Value) throws ProgramException;

    void update(T Name, K NewValue) throws ProgramException;

    void delete(T Name) throws ProgramException;
}
