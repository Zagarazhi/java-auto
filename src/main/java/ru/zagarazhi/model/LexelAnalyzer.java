package ru.zagarazhi.model;

import java.util.ArrayList;
import java.util.List;

import ru.zagarazhi.controller.ErrorObserver;

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
        Token temp = new Token(BlockType.YBLOCK, "Y");
        for (Token t : start) {
            if (t.getType() == BlockType.WBLOCK) {
                results.add(t);
            } else if (t.getType() == BlockType.INDEX || t.getType() == BlockType.ENDINDEX || t.getType() == BlockType.STARTINDEX) {
                temp.setValue(t.getValue());
                results.add(temp);
            } else {
                temp = t;
            }
        }
        return results;
    }

    private static void pushError(int pos, BlockType expected, BlockType real) {
        if (expected == null || real == null) {
            return;
        }
        errorObserver.pushError(
                String.format("[LEXICAL WARN]: В позиции %d ожидалось %s, но было получено %s",
                        pos + 1,
                        expected.getText(),
                        real.getText())
                    );
    }
    
    public static List<Token> lex(String input) {
        List<Token> results = new ArrayList<>();
        BlockType expected = BlockType.YBLOCK;
        BlockType nextExpected = null;
        BlockType real = null;

        for(int i = 0; i < input.length(); i++) {
            switch (Character.toUpperCase(input.charAt(i))) {
                case 'Y':
                    results.add(new Token(BlockType.YBLOCK, "Y"));
                    nextExpected = BlockType.INDEX;
                    real = BlockType.YBLOCK;
                    break;
                case 'X':
                    results.add(new Token(BlockType.XBLOCK, "X"));
                    real = BlockType.XBLOCK;
                    nextExpected = BlockType.INDEX;
                    break;
                case 'W':
                    results.add(new Token(BlockType.WBLOCK, "W"));
                    real = BlockType.WBLOCK;
                    nextExpected = BlockType.UPARROW;
                    break;
                case 'D':
                    results.add(new Token(BlockType.DOWNARROW, "D"));
                    nextExpected = BlockType.INDEX;
                    break;
                case 'U':
                    results.add(new Token(BlockType.UPARROW, "U"));
                    real = BlockType.UPARROW;
                    nextExpected = BlockType.INDEX;
                    break;
                case 'Н':
                    results.add(new Token(BlockType.STARTINDEX, "Н"));
                    real = BlockType.STARTINDEX;
                    nextExpected = null;
                    break;
                case 'K':
                    results.add(new Token(BlockType.ENDINDEX, "К"));
                    real = BlockType.ENDINDEX;
                    nextExpected = null;
                    break;
                case 'S':
                    results.add(new Token(BlockType.STARTINDEX, "Н"));
                    real = BlockType.STARTINDEX;
                    nextExpected = null;
                    break;
                case 'К':
                    results.add(new Token(BlockType.ENDINDEX, "К"));
                    real = BlockType.ENDINDEX;
                    nextExpected = null;
                    break;
                default:
                    if (Character.isDigit(input.charAt(i))) {
                        String res = getIndex(input, i);
                        i += res.length() - 1;
                        results.add(new Token(BlockType.INDEX, res));
                        real = BlockType.INDEX;
                        nextExpected = null;
                    } else {
                        real = null;
                        pushError(i, expected, BlockType.UNDEFINED);
                    }
                    break;

            }
            if(i == 0){
                nextExpected = BlockType.STARTINDEX;
            } else if(i == input.length() - 2){
                nextExpected = BlockType.ENDINDEX;
            }
            if(real != null){
                if(expected != null){
                    if (expected != real) {
                        pushError(i, expected, real);
                    }
                }
                expected = nextExpected;
            }
            if(input.length() < 4){
                errorObserver.pushError("[LEXICAL WARN]: неполная строка");
            }
        }

        return postConstruct(results);
    }
}
