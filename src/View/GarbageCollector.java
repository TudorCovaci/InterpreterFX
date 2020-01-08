package View;

import Model.ProgramState.Heap;
import Model.ProgramState.PrgState;
import Model.ProgramState.SymTable;
import Model.Values.RefValue;
import Model.Values.Value;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GarbageCollector {


    public HashMap<Integer, Value> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap) {

        Map<Integer, Value> newHeapMap = heap.entrySet().stream()
                .filter(e -> symTableAddr.contains((e.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return new HashMap<>(newHeapMap);
    }

    public List<Integer> getAddrFromSymTable(Collection<Value> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {
                    RefValue v1 = (RefValue) v;
                    return v1.getAddr();
                })
                .collect(Collectors.toList());
    }

    public HashMap<Integer, Value> multithreadedGC(List<PrgState> prgStates) {
        SymTable allSymTable = new SymTable();
        Heap heap = prgStates.get(0).getHeap();
        prgStates.forEach(p -> allSymTable.getContent().putAll(p.getSymTable().getContent()));
        return unsafeGarbageCollector(getAddrFromSymTable(allSymTable.getContent().values()),
                heap.getContent());
    }


}
