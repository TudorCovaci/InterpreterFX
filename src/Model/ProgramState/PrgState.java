package Model.ProgramState;

import java.util.concurrent.atomic.AtomicInteger;

import Model.Exceptions.ProgramException;
import Model.Statements.IStmt;

public class PrgState implements PrgStateInterface {
    private ExeStack stack;
    private SymTable symTable;
    private Output output;
    private FileTable fileTable;
    private Heap heap;

    static private AtomicInteger counter = new AtomicInteger(0);
    private int id;

    public PrgState() {
        stack = new ExeStack();
        symTable = new SymTable();
        output = new Output();
        fileTable = new FileTable();
        heap = new Heap();
        
        counter.incrementAndGet();
        
        id = counter.get();

    }
    public AtomicInteger getCounter()
    {
        return counter;
    }
    @Override
    public String toString() {
        return String.valueOf(id);
    }

    @Override
    public ExeStack getStack() {
        return stack;
    }

    public boolean isNotCompleted() {
        return !stack.isEmpty();
    }

    @Override
    public SymTable getSymTable() {
        return symTable;
    }

    @Override
    public Output getOutput() {
        return output;
    }

    @Override
    public FileTable getFileTable() {
        return fileTable;
    }

    @Override
    public Heap getHeap() {
        return heap;
    }

    public int getId() {
        return id;
    }

    public PrgState oneStep() throws ProgramException {

        if (stack.isEmpty()) {
            throw new ProgramException("No statements to run");
        }
        IStmt currentStmt = stack.pop();
        return currentStmt.execute(this);

    }

    public void setHeap(Heap heap) {
        this.heap = heap;
    }

    public void setStack(ExeStack stack) {
        this.stack = stack;
    }

    public void setFileTable(FileTable fileTable) {
        this.fileTable = fileTable;
    }

    public void setOutput(Output output) {
        this.output = output;
    }

    public void setSymTable(SymTable symTable) {
        this.symTable = symTable;
    }



}
