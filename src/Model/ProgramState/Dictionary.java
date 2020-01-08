package Model.ProgramState;

import Model.Exceptions.ProgramException;
import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Dictionary<T, K> implements DictionaryInterface<T, K> {

    HashMap<T, K> table;
    protected ReadWriteLock rwLock;
    static protected Lock readLock;
    static protected Lock writeLock;

    public Dictionary() {
        table = new HashMap<T, K>();
        rwLock = new ReentrantReadWriteLock();
        readLock = rwLock.readLock();
        writeLock = rwLock.writeLock();
    }

    public Dictionary(Dictionary<T, K> arg) {
        table = new HashMap<>(arg.getTable());
        rwLock = new ReentrantReadWriteLock();
        readLock = rwLock.readLock();
        writeLock = rwLock.writeLock();
    }

    public Set<Map.Entry<T,K>> getMapTable()
    {
        return table.entrySet();
    }
    public HashMap<T, K> getTable() {
        return table;
    }

    public K lookup(T name) {
        K value;
        readLock.lock();
        value = table.get(name);
        readLock.unlock();
        return value;

    }

    public boolean isDefined(T Id) {
        boolean res;
        readLock.lock();
        res = table.get(Id) != null;
        readLock.unlock();
        return res;
    }

    public void add(T Name, K Value) throws ProgramException {
        if (null != lookup(Name)) {
            throw new ProgramException("Multiple declarations of variable: " + Name);
        }
        writeLock.lock();
        table.put(Name, Value);
        writeLock.unlock();
    }

    public void update(T Name, K NewValue) throws ProgramException {
        K oldValue = lookup(Name);
        if (null == oldValue) {
            throw new ProgramException("Could not resolve variable: " + Name);
        }
        writeLock.lock();
        table.replace(Name, NewValue);
        writeLock.unlock();
    }

    public void delete(T name) throws ProgramException {
        if (isDefined(name)) {
            writeLock.lock();
            table.remove(name);
            writeLock.unlock();
        }

    }

    public HashMap<T, K> getContent() {
        return table;
    }

    public void setContent(HashMap<T, K> newTable) {
        table = newTable;
    }



}
