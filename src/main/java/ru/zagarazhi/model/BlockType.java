package ru.zagarazhi.model;

public enum BlockType {
    YBLOCK("'Y'"),
    XBLOCK("'X'"),
    WBLOCK("'W'"),
    STARTINDEX("'Н'"),
    INDEX("'индекс'"),
    ENDINDEX("'К'"),
    UPARROW("'U'"),
    DOWNARROW("'D'"),
    UNDEFINED("'неопределенный символ'");

    private final String text;

    private BlockType(String text){
        this.text = text;
    }

    public String getText(){
        return this.text;
    }
}
