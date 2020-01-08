package Model.Expressions;

import Model.Exceptions.ProgramException;
import Model.ProgramState.Dictionary;
import Model.ProgramState.Heap;
import Model.ProgramState.SymTable;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.Value;

public class IneqExp implements Exp {
    private Exp right;
    private Exp left;
    private String operator;

    public IneqExp(String Operator, Exp Right, Exp Left) {
        this.right = Right;
        this.left = Left;
        this.operator = Operator;
    }

    public String toString() {
        return right.toString() + operator + left.toString();
    }

    public Value eval(SymTable symTable, Heap heap) throws ProgramException {
        Value rightValue = right.eval(symTable, heap);
        if (rightValue.getType().equals(new IntType())) {
            Value secondValue = left.eval(symTable, heap);
            if (secondValue.getType().equals(new IntType())) {
                IntValue firstInt = (IntValue) rightValue;
                IntValue secondInt = (IntValue) secondValue;
                int firstNumeric = firstInt.getValue();
                int secondNumeric = secondInt.getValue();
                switch (operator) {
                    case "<": {
                        return new BoolValue(firstNumeric < secondNumeric);
                    }
                    case ">": {
                        return new BoolValue(firstNumeric > secondNumeric);
                    }
                    case "<=": {
                        return new BoolValue(firstNumeric <= secondNumeric);
                    }
                    case ">=": {
                        return new BoolValue(firstNumeric >= secondNumeric);
                    }
                    case "==": {
                        return new BoolValue(firstNumeric == secondNumeric);
                    }
                    case "!=": {
                        return new BoolValue(firstNumeric != secondNumeric);
                    }
                    default: {
                        throw new ProgramException("Invalid operator!");
                    }

                }
            } else {
                throw new ProgramException("Invalid second operand!");
            }
        } else {
            throw new ProgramException("Invalid first operand");
        }

    }

    public Type typecheck(Dictionary<String, Type> typeEnv) throws ProgramException {
        Type type1, type2;
        type1 = right.typecheck(typeEnv);
        type2 = left.typecheck(typeEnv);
        if (type1.equals(new BoolType())) {
            if (type2.equals(new BoolType())) {
                return new BoolType();
            } else {
                throw new ProgramException("Invalid second operand type");
            }
        } else {
            throw new ProgramException("Invalid first operand type");
        }
    }

}
