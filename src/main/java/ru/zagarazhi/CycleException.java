package ru.zagarazhi;

public class CycleException extends Exception{
    public CycleException(String message){
        super(message);
    }
    public CycleException(String message, Throwable err){
        super(message, err);
    }
}
