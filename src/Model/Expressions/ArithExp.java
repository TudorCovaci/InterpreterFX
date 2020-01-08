package Model.Expressions;

import Model.Exceptions.ProgramException;
import Model.ProgramState.Dictionary;
import Model.ProgramState.Heap;
import Model.ProgramState.SymTable;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

public class ArithExp implements Exp {
    private Exp first;
    private Exp second;
    private char operator;

    public ArithExp(Exp First, Exp Second, char Operator) {
        this.first = First;
        this.second = Second;
        this.operator = Operator;
    }

    @Override
    public String toString() {
        return this.first.toString() + this.operator + this.second.toString();
    }

    public Value eval(SymTable symTable, Heap heap) throws ProgramException {
        Value firstVal, secondVal;
        firstVal = first.eval(symTable, heap);
        if (firstVal.getType().equals(new IntType())) {
            secondVal = second.eval(symTable, heap);
            if (secondVal.getType().equals(new IntType())) {
                IntValue firstInt = (IntValue) firstVal;
                IntValue secondInt = (IntValue) secondVal;
                int firstNumericValue, secondNumericValue;
                firstNumericValue = firstInt.getValue();
                secondNumericValue = secondInt.getValue();

                switch (operator) {
                    case '+': {
                        return new IntValue(firstNumericValue + secondNumericValue);
                    }
                    case '-': {
                        return new IntValue(firstNumericValue - secondNumericValue);
                    }
                    case '*': {
                        return new IntValue(firstNumericValue * secondNumericValue);
                    }
                    case '/': {
                        if (secondNumericValue == 0) {
                            throw new ProgramException("Division by zero");
                        }
                        return new IntValue(firstNumericValue / secondNumericValue);
                    }
                    default: {
                        throw new ProgramException("Invalid operator");
                    }
                }

            } else {
                throw new ProgramException("Invalid second operand");
            }
        } else {
            throw new ProgramException("Invalid first operand");
        }
    }

    public Type typecheck(Dictionary<String, Type> typeEnv) throws ProgramException {
        Type type1, type2;
        type1 = first.typecheck(typeEnv);
        type2 = second.typecheck(typeEnv);
        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new IntType();
            } else {
                throw new ProgramException("Invalid type of second operand");
            }
        } else {
            throw new ProgramException("Invalid type of first operand");
        }

    }
}
