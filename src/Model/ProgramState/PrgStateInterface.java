package Model.ProgramState;

public interface PrgStateInterface {
    String toString();

    boolean isNotCompleted();

    ExeStack getStack();

    SymTable getSymTable();

    Output getOutput();

    FileTable getFileTable();

    Heap getHeap();
}
