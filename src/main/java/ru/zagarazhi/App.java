package ru.zagarazhi;

import java.util.List;

import ru.zagarazhi.analysis.LexelAnalyzer;
import ru.zagarazhi.analysis.Token;

public class App {
    public static void main(String[] args) {
        List<Token> list = LexelAnalyzer.lex("YНD1Y1X1U1Y2YK");
        ErrorObserver errorObserver = ErrorObserver.getInstance();
        for (Token t : list) {
            System.out.println(t.toString());

        }
        List<String> errors = errorObserver.getLexicalErrors();
        for (String error : errors) {
            System.out.println(error);
        }
    }
}
