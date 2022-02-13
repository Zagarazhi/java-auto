package ru.zagarazhi.analysis;

import java.util.ArrayList;
import java.util.List;

import ru.zagarazhi.ErrorObserver;

public class SyntaxisAnalyzer {
    private static ErrorObserver errorObserver = ErrorObserver.getInstance();

    public static List<Token> synt(List<Token> tokens){
        List<Token> results = new ArrayList<>();

        Type[] availableTypes = {Type.YBLOCK};
        String[] availableValus = {"Н"};

        for(Token t : tokens){
            
        }

        return results;
    }
}
