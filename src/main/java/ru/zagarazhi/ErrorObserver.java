package ru.zagarazhi;

import java.util.ArrayList;
import java.util.List;

public class ErrorObserver {
    private static ErrorObserver instance;
    private List<String> lexicalErrors= new ArrayList<>();
    private List<String> syntaxisErrors= new ArrayList<>();

    private ErrorObserver(){

    }

    public static ErrorObserver getInstance(){
        if(instance == null){
            instance = new ErrorObserver();
        }
        return instance;
    }

    public void pushToLexicalErrors(String error){
        lexicalErrors.add(error);
    }

    public void pushToSyntaxisErrors(String error){
        syntaxisErrors.add(error);
    }

    public void clearLexicalErrors(){
        lexicalErrors= new ArrayList<>();
    }

    public void clearSyntaxisErrors(){
        syntaxisErrors= new ArrayList<>();
    }

    public List<String> getLexicalErrors(){
        return lexicalErrors;
    }

    public List<String> getSyntaxisErrors(){
        return syntaxisErrors;
    }
}
