package Model.ProgramState;

import Model.Exceptions.ProgramException;
import Model.Statements.IStmt;

import java.util.ArrayList;
import java.util.Stack;

public class ExeStack implements ExeStackInterface<IStmt> {
    private Stack<IStmt> stack;

    ExeStack() {
        stack = new Stack<IStmt>();
    }

    @Override
    public void push(IStmt statement) {
        stack.push(statement);
    }

    public boolean isEmpty() {
        return stack.empty();
    }

    public IStmt pop() throws ProgramException {
        if (isEmpty()) {
            throw new ProgramException("Empty execution stack");
        }

        return stack.pop();

    }

    public ArrayList<IStmt> getStatementList() {
        ArrayList<IStmt> list = new ArrayList<>(stack);
        return list;
    }


    public String toString() {
        StringBuilder string = new StringBuilder("Execution stack:\n");
        for (Object statement : stack) {
            string.append(statement.toString()).append('\n');
        }
        return string.toString();
    }

}
