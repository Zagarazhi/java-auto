package ru.zagarazhi.controller;

public class ErrorObserver {
    private static ErrorObserver instance;
    public EventManager events;

    private ErrorObserver(){
        this.events = new EventManager("error");
    }

    public static ErrorObserver getInstance(){
        if(instance == null){
            instance = new ErrorObserver();
        }
        return instance;
    }

    public void pushError(String message) {
        events.notify("error", message);
    }
}
