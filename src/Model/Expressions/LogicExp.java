package Model.Expressions;

import Model.Exceptions.ProgramException;
import Model.ProgramState.Dictionary;
import Model.ProgramState.Heap;
import Model.ProgramState.SymTable;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

public class LogicExp implements Exp {
    private Exp first;
    private Exp second;
    private String operator;

    public LogicExp(Exp First, Exp Second, String Operator) {
        this.first = First;
        this.second = Second;
        this.operator = Operator;
    }

    public String toString() {
        return first.toString() + operator + second.toString();
    }

    public Value eval(SymTable symTable, Heap heap) throws ProgramException {
        Value firstValue, secondValue;
        firstValue = first.eval(symTable, heap);
        if (firstValue.getType().equals(new BoolType())) {
            secondValue = second.eval(symTable, heap);
            if (secondValue.getType().equals(new BoolType())) {
                BoolValue firstBool = (BoolValue) first;
                BoolValue secondBool = (BoolValue) second;
                switch (operator) {
                    case "&&": {
                        return new BoolValue(firstBool.getValue() && secondBool.getValue());
                    }

                    case "||": {
                        return new BoolValue(firstBool.getValue() || secondBool.getValue());
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
