package ru.zagarazhi.analysis;

public enum Type {
    YBLOCK("'Y'"),
    XBLOCK("'X'"),
    WBLOCK("'W'"),
    STARTINDEX("'Н'"),
    INDEX("'индекс'"),
    ENDINDEX("'К'"),
    UPARROW("'U'"),
    DOWNARROW("'D'"),
    UNDEFINED("'пустота'");

    private final String text;

    private Type(String text){
        this.text = text;
    }

    public String getText(){
        return this.text;
    }
}
