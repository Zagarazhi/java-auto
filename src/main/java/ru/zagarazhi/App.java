package ru.zagarazhi;

import java.util.ArrayList;
import java.util.List;

import ru.zagarazhi.analysis.LexelAnalyzer;
import ru.zagarazhi.analysis.Token;
import ru.zagarazhi.blocks.Block;
import ru.zagarazhi.view.CUI;

public class App {
    public static void main(String[] args) {
        CUI cui = new CUI();
        cui.show();
        /*
        List<Token> list = LexelAnalyzer.lex("YНD1Y1X1U1Y2YK");
        ErrorObserver errorObserver = ErrorObserver.getInstance();
        for (Token t : list) {
            System.out.println(t.toString());

        }
        ArrayList<Block> blocks = Token.tokenListToBlockList(list.toArray(new Token[list.size()]));
        Processor processor = new Processor(blocks);
        List<String> errors = errorObserver.getLexicalErrors();
        for (String error : errors) {
            System.out.println(error);
        }
        */
    }
}
