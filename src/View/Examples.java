package View;

import Model.Expressions.*;
import Model.Statements.*;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.RefType;
import Model.Types.StringType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;

import java.util.Stack;

public class Examples {

    private static IStmt listToCompStm(Stack<IStmt> list) {
        if (list.isEmpty()) {
            return null;
        } else if (list.size() == 1) {
            return list.pop();
        }
        var firstStatement = list.pop();
        var secondStatement = list.pop();
        CompStmt compSt = new CompStmt(secondStatement, firstStatement);
        while (!list.isEmpty()) {
            compSt = new CompStmt(list.pop(), compSt);
        }
        return compSt;
    }

    public IStmt HeapAllocationEx() {

        Stack<IStmt> list = new Stack<IStmt>();
        list.push(new VarDeclStmt("v", new RefType(new IntType())));
        list.push(new NewStmt("v", new ValueExp(new IntValue(20))));
        list.push(new VarDeclStmt("a", new RefType(new RefType(new IntType()))));
        list.push(new NewStmt("a", new VarExp("v")));
        list.push(new PrintStmt(new VarExp("v")));
        list.push(new PrintStmt(new VarExp("a")));
        return listToCompStm(list);


    }

    public IStmt HeapReadingEx() {

        Stack<IStmt> list = new Stack<IStmt>();
        list.push(new VarDeclStmt("v", new RefType(new IntType())));
        list.push(new NewStmt("v", new ValueExp(new IntValue(20))));
        list.push(new VarDeclStmt("a", new RefType(new RefType(new IntType()))));
        list.push(new NewStmt("a", new VarExp("v")));
        list.push(new PrintStmt(new ReadHeap(new VarExp("v"))));
        list.push(new PrintStmt(new ArithExp(new ReadHeap(new ReadHeap(new VarExp("a"))), new ValueExp(new IntValue(5)), '+')));
        return listToCompStm(list);
    }

    public IStmt WhileExample() {
        Stack<IStmt> list = new Stack<IStmt>();
        Stack<IStmt> whileStmt = new Stack<IStmt>();
        whileStmt.push(new PrintStmt(new VarExp("v")));
        whileStmt.push(new AssignStmt("v", new ArithExp(new VarExp("v"), new ValueExp(new IntValue(1)), '-')));
        list.push(new VarDeclStmt("v", new IntType()));
        list.push(new AssignStmt("v", new ValueExp(new IntValue(4))));
        list.push(new WhileStmt(new IneqExp(">", new VarExp("v"), new ValueExp(new IntValue(0))), listToCompStm(whileStmt)));
        list.push(new PrintStmt(new VarExp("v")));
        return listToCompStm(list);
    }

    public IStmt ThreadExample() {

        Stack<IStmt> list = new Stack<IStmt>();
        Stack<IStmt> forkStmts = new Stack<IStmt>();

        forkStmts.push(new WriteHeap("a", new ValueExp(new IntValue(30))));
        forkStmts.push(new AssignStmt("v", new ValueExp(new IntValue(32))));
        forkStmts.push(new PrintStmt(new VarExp("v")));
        forkStmts.push(new PrintStmt(new ReadHeap(new VarExp("a"))));

        list.push(new VarDeclStmt("v", new IntType()));
        list.push(new VarDeclStmt("a", new RefType(new IntType())));
        list.push(new AssignStmt("v", new ValueExp(new IntValue(10))));
        list.push(new NewStmt("a", new ValueExp(new IntValue(22))));
        list.push(new ForkStmt(listToCompStm(forkStmts)));
        list.push(new PrintStmt(new VarExp("v")));
        list.push(new PrintStmt(new ReadHeap(new VarExp("a"))));
        return listToCompStm(list);
    }
    public static IStmt FirstExample() {
        return new CompStmt(
                new VarDeclStmt("v", new IntType()), //int v
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(2))), //v=2
                        new PrintStmt(new VarExp("v")) // print(v)
                )
        );
    }

    public static IStmt SecondExample() {
        return new CompStmt(
                new VarDeclStmt("a", new IntType()), // int a
                new CompStmt(
                        new VarDeclStmt("b", new IntType()), // int b
                        new CompStmt(
                                new AssignStmt("a",  // a = 2 + 3*5
                                        new ArithExp(
                                                new ValueExp(
                                                        new IntValue(2)),
                                                new ArithExp(
                                                        new ValueExp(
                                                                new IntValue(3)),
                                                        new ValueExp(new IntValue(5)),
                                                        '*'
                                                ), // 3*5
                                                '+'
                                        ) // 2+3*5
                                ),
                                new CompStmt(
                                        new AssignStmt(
                                                "b", // b = b+1
                                                new ArithExp(
                                                        new VarExp("a"),
                                                        new ValueExp(
                                                                new IntValue(1)),
                                                        '+')), // a + 1
                                        new PrintStmt(new VarExp("b")))))); //print b

    }

    public static IStmt ThirdExample() {
        return new CompStmt(
                new VarDeclStmt("a", new BoolType()),
                new CompStmt(
                        new VarDeclStmt("v", new IntType()),
                        new CompStmt(
                                new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(
                                        new IfStmt(
                                                new VarExp("a"),
                                                new AssignStmt("v", new ValueExp(new IntValue(2))),
                                                new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                        new PrintStmt(new VarExp("v"))
                                )
                        )
                )
        );


    }

    public static IStmt FourthExample() {
        return new CompStmt(
                new VarDeclStmt("varf", new StringType()),
                new CompStmt(
                        new AssignStmt("varf", new ValueExp(new StringValue("test.txt"))),
                        new CompStmt(
                                new OpenRFile(new VarExp("varf")),
                                new CompStmt(
                                        new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(
                                                new ReadFile(new VarExp("varf"), "varc"),
                                                new CompStmt(
                                                        new ReadFile(new VarExp("varf"), "varc"),
                                                        new CloseRFile(new VarExp("varf"))
                                                )
                                        )
                                )
                        )));
    }

    public IStmt RepeatUntilEx() {
        Stack<IStmt> list = new Stack<IStmt>();
        Stack<IStmt> forkStmts = new Stack<IStmt>();
        Stack<IStmt> repeatStmts = new Stack<>();

        forkStmts.push(new PrintStmt(new VarExp("v")));
        forkStmts.push(new AssignStmt("v", new ArithExp(new VarExp("v"), new ValueExp(new IntValue(1)), '-')));

        repeatStmts.push(new ForkStmt(listToCompStm(forkStmts)));
        repeatStmts.push(new AssignStmt("v", new ArithExp(new VarExp("v"), new ValueExp(new IntValue(1)), '+')));
        list.push(new VarDeclStmt("v", new IntType()));
        list.push(new VarDeclStmt("x", new IntType()));
        list.push(new VarDeclStmt("y", new IntType()));
        list.push(new AssignStmt("v", new ValueExp(new IntValue(0))));
        list.push(new RepeatUntilStmt(listToCompStm(repeatStmts), new IneqExp("==", new VarExp("v"), new ValueExp(new IntValue(3)))));
        list.push(new AssignStmt("x", new ValueExp(new IntValue(1))));
        list.push(new NopStmt());
        list.push(new AssignStmt("y", new ValueExp(new IntValue(3))));
        list.push(new NopStmt());
        list.push(new PrintStmt(new ArithExp(new VarExp("v"), new ValueExp(new IntValue(10)), '*')));

        return listToCompStm(list);
    }
}
