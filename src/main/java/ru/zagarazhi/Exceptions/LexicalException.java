package ru.zagarazhi.Exceptions;

public class LexicalException extends Exception {
    public LexicalException(String message){
        super(message);
    }
    public LexicalException(String message, Throwable err){
        super(message, err);
    }
}
