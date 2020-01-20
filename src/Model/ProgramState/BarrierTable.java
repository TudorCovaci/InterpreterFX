package Model.ProgramState;

import Model.Values.IntValue;
import javafx.util.Pair;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class BarrierTable extends Dictionary<Integer, Pair<IntValue, List<IntValue>>> {

    private static AtomicInteger counter = new AtomicInteger(0);

    public BarrierTable() {
        super();
    }

    public static int generateKey() {
        return counter.incrementAndGet();
    }

}
