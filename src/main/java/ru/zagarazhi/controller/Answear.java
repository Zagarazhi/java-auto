package ru.zagarazhi.controller;

public class Answear {
    private static Answear instance;
    private String answear;
    public EventManager events;

    private Answear(){
        this.events = new EventManager("answear");
    }

    public static Answear getInstance() {
        if(instance == null){
            instance = new Answear();
        }
        return instance;
    }

    public void clearAnswear() {
        answear = "";
    }

    public String getAnswear(){
        return answear;
    }

    public void pushAnswear(String message) {
        answear += message;
        events.notify("answear", message);
    }
}
