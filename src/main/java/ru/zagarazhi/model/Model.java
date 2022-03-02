package ru.zagarazhi.model;

import java.util.HashSet;
import java.util.Set;

import ru.zagarazhi.controller.*;

public class Model {
    private static Answear answear = Answear.getInstance();
    private static ErrorObserver errorObserver = ErrorObserver.getInstance();
    private static ConditionSetter conditionSetter = ConditionSetter.getInstance();

    private static int find(Token[] tokens, String index) {
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].getType() == BlockType.DOWNARROW && tokens[i].getValue().equals(index)) {
                return i;
            }
        }
        return -2;
    }

    public static void model(Token[] tokens, boolean isFullRun) {
        Set<Integer> x = new HashSet<Integer>();
        boolean condition = true;
        for (int i = 0; i < tokens.length; i++) {
            if (i == -1) {
                errorObserver.pushError("[Runtime WARN]: Не найдено точки входа");
                break;
            }
            switch (tokens[i].getType()) {
                case DOWNARROW:
                    break;
                case ENDINDEX:
                    break;
                case INDEX:
                    break;
                case STARTINDEX:
                    break;
                case UNDEFINED:
                    break;
                case UPARROW:
                    if (!condition) {
                        i = find(tokens, tokens[i].getValue());
                    }
                    break;
                case WBLOCK:
                    condition = false;
                    break;
                case XBLOCK:
                    if(isFullRun){
                        if (x.contains(Integer.parseInt(tokens[i].getValue()))){
                            errorObserver.pushError("[Runtime WARN]: Бесконечный цикл");
                            return;
                        }
                        x.add(Integer.parseInt(tokens[i].getValue()));
                    }
                    condition = conditionSetter.getCondition("X" + tokens[i].getValue());
                    break;
                case YBLOCK:
                    answear.pushAnswear("Y" + tokens[i].getValue());
                    break;
                default:
                    break;
            }
        }
    }
}
