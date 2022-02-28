package ru.zagarazhi;

import ru.zagarazhi.view.CUI;

public class ConditionSetter {

    private static ConditionSetter instance;
    private String conditions;
    private boolean isFull = false;

    private ConditionSetter() {

    }

    private static boolean convertCharToBoolean(char input) {
        return Character.toLowerCase(input) == '1' ? true : false;
    }

    public static ConditionSetter getInstance() {
        if (instance == null) {
            instance = new ConditionSetter();
        }
        return instance;
    }

    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean isFull) {
        this.isFull = isFull;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public void setCondition(boolean condition, int index) {
        char newcon = condition ? '1' : '0';
        this.conditions.toCharArray()[index] = newcon;
    }

    public boolean getCondition(int index, boolean firstIsZero) {
        if (isFull) {
            if (index < conditions.length()) {
                return convertCharToBoolean(firstIsZero ? conditions.charAt(index) : conditions.charAt(index - 1));
            }
        }
        return CUI.getConditionFromUser(index);
    }
}
