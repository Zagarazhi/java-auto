package ru.zagarazhi.analysis;

import java.util.ArrayList;
import java.util.List;

import ru.zagarazhi.ErrorObserver;

public class LexelAnalyzer {

    private static ErrorObserver errorObserver = ErrorObserver.getInstance();

    private static String getIndex(String input, int i) {
        int j = i;
        for (; j < input.length();) {
            if (Character.isDigit(input.charAt(j))) {
                j++;
            } else {
                return input.substring(i, j);
            }
        }
        return input.substring(i, j);
    }

    private static List<Token> postConstruct(List<Token> start) {
        List<Token> results = new ArrayList<>();
        Token temp = new Token(Type.YBLOCK, "Y", 0);
        for (Token t : start) {
            if (t.getType() == Type.WBLOCK) {
                results.add(t);
            } else if (t.getType() == Type.INDEX || t.getType() == Type.ENDINDEX || t.getType() == Type.STARTINDEX) {
                temp.setValue(t.getValue());
                results.add(temp);
            } else {
                temp = t;
            }
        }

        return results;
    }

    private static void pushError(int pos, Type expected, Type real) {
        if (expected == null || real == null) {
            return;
        }
        errorObserver.pushToLexicalErrors(
                String.format("[LEXICAL WARN]: В позиции %d ожидалось %s, но было получено %s",
                        pos + 1,
                        expected.getText(),
                        real.getText()));
    }

    public static List<Token> lex(String input) {
        int lastX = 0, lastY = 0, lastW = 0;
        int length = input.length();
        List<Token> results = new ArrayList<>();
        boolean isStart = true;
        Type expected = Type.YBLOCK;
        Type nextExpected = null;
        Type real = null;
        for (int i = 0; i < length; i++) {
            switch (Character.toUpperCase(input.charAt(i))) {
                case 'Y':
                    if (isStart) {
                        results.add(new Token(Type.YBLOCK, "Y", lastY));
                        lastY++;
                        isStart = false;
                    } else {

                    }
                    results.add(new Token(Type.YBLOCK, "Y", lastY));
                    lastY++;
                    real = Type.YBLOCK;
                    if (i == 0) {
                        nextExpected = Type.STARTINDEX;
                    } else if (i == length - 2) {
                        nextExpected = Type.ENDINDEX;
                    } else {
                        nextExpected = Type.INDEX;
                    }
                    break;
                case 'X':
                    results.add(new Token(Type.XBLOCK, "X", lastX));
                    lastX++;
                    real = Type.XBLOCK;
                    nextExpected = Type.INDEX;
                    break;
                case 'W':
                    results.add(new Token(Type.WBLOCK, "W", lastW));
                    lastW++;
                    real = Type.WBLOCK;
                    nextExpected = Type.UPARROW;
                    break;
                case 'D':
                    results.add(new Token(Type.DOWNARROW, "D", i));
                    real = Type.DOWNARROW;
                    nextExpected = Type.INDEX;
                    break;
                case 'U':
                    results.add(new Token(Type.UPARROW, "U", i));
                    real = Type.UPARROW;
                    nextExpected = Type.INDEX;
                    break;
                case 'Н':
                    results.add(new Token(Type.STARTINDEX, "Н", i));
                    real = Type.STARTINDEX;
                    nextExpected = null;
                    break;
                case 'K':
                    results.add(new Token(Type.ENDINDEX, "К", i));
                    real = Type.ENDINDEX;
                    nextExpected = null;
                    break;
                case 'S':
                    results.add(new Token(Type.STARTINDEX, "Н", i));
                    real = Type.STARTINDEX;
                    nextExpected = null;
                    break;
                case 'К':
                    results.add(new Token(Type.ENDINDEX, "К", i));
                    real = Type.ENDINDEX;
                    nextExpected = null;
                    break;
                default:
                    if (Character.isDigit(input.charAt(i))) {
                        String res = getIndex(input, i);
                        i += res.length() - 1;
                        results.add(new Token(Type.INDEX, res, i));
                        real = Type.INDEX;
                        nextExpected = null;
                    } else {
                        real = null;
                        pushError(i, expected, Type.UNDEFINED);
                    }
                    break;

            }
            if (real != null) {
                if (i == length - 2 && real != Type.YBLOCK) {
                    pushError(length, Type.YBLOCK, Type.UNDEFINED);
                    pushError(length + 1, Type.ENDINDEX, Type.UNDEFINED);
                }

                if (expected != null) {
                    if (expected != real) {
                        pushError(i, expected, real);
                    }
                }
                expected = nextExpected;
            }
        }
        return postConstruct(results);
    }
}
