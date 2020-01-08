package Model.ProgramState;

import Model.Values.Value;

import java.util.Map;

public class Heap extends Dictionary<Integer, Value> {
    static private int counter;
  

    public Heap() {
        super();
        counter = 1;
    }

    public static int generateKey() {
        int id;
        writeLock.lock();
        id =  counter++;
        writeLock.unlock();
        return id;
    }


    public String toString() {
        String string = "Heap:\n";
        readLock.lock();
        for (Map.Entry<Integer, Value> entry : table.entrySet()) {

            string = string + entry.getKey() + " -> " + entry.getValue().toString() + '\n';
        }
        readLock.unlock();
        return string;
    }


}
