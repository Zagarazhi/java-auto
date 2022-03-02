package ru.zagarazhi.model;

public class Token {
    private BlockType type;
    private String value;

    public Token(BlockType type, String value) {
        this.type = type;
        this.value = value;
    }

    public BlockType getType() {
        return type;
    }

    public void setType(BlockType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("%s<%s>", this.type.toString(), this.value);
    }
}
