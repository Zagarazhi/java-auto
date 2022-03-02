package ru.zagarazhi.controller;

public class ConditionSetter {
    private static ConditionSetter instance;
    private boolean condition;
    public EventManager events;

    private ConditionSetter() {
        this.events = new EventManager("condition");
        this.condition = true;
    }
    
    public static ConditionSetter getInstance() {
        if (instance == null) {
            instance = new ConditionSetter();
        }
        return instance;
    }

    public boolean getCondition(String message) {
        events.notify("condition", message);
        return condition;
    }

    public void setCondition(boolean condition) {
        this.condition = condition;
    }
}
