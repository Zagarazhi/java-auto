package ru.zagarazhi;

import java.util.HashSet;
import java.util.Set;

import ru.zagarazhi.analysis.Token;
import ru.zagarazhi.analysis.Type;

public class Model {

    private static Answear answear = Answear.getInstance();
    private static ErrorObserver errorObserver = ErrorObserver.getInstance();
    private static ConditionSetter conditionSetter = ConditionSetter.getInstance();

    private static int find(Token[] tokens, String index) {
        for (int i = 1; i < tokens.length - 1; i++) {
            if (tokens[i].getType() == Type.DOWNARROW && tokens[i].getValue().equals(index)) {
                return i;
            }
        }
        return -2;
    }

    public static void model(Token[] tokens, boolean isFullRun) {
        boolean condition = true;
        boolean alreadyIn = false;
        boolean firstX = true;
        boolean firstIsZero = true;
        int lastCondition = 0;
        int index = 0;
        Set<Integer> x = new HashSet<Integer>();
        for (int i = 0; i < tokens.length; i++) {
            if (i == -1) {
                errorObserver.pushToSyntaxisErrors("[Syntaxis WARN]: Не найдено точки входа");
                break;
            }
            if (alreadyIn) {
                errorObserver.pushToSyntaxisErrors("[Syntaxis WARN]: Попадение в бесконечный цикл");
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
                    index = tokens[i].getPosition();
                    if (firstX && index != 0) {
                        firstIsZero = false;
                    }
                    if (isFullRun) {
                        if (x.contains(index)) {
                            conditionSetter.setCondition((!conditionSetter.getCondition(lastCondition, firstIsZero)),
                                    lastCondition);
                            lastCondition++;
                            // alreadyIn = true;
                        } else {
                            x.add(index);
                            condition = conditionSetter.getCondition(lastCondition, firstIsZero);
                            lastCondition++;
                        }
                    } else {
                        x.add(index);
                        condition = conditionSetter.getCondition(lastCondition, firstIsZero);
                        lastCondition++;
                    }
                    firstX = false;
                    break;
                case YBLOCK:
                    answear.addTextToAnswear("Y" + tokens[i].getValue());
                    break;
                default:
                    break;
            }
        }
    }
}
