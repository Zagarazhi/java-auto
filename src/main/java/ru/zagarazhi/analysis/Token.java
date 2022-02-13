package ru.zagarazhi.analysis;

import java.sql.Blob;

import ru.zagarazhi.blocks.Block;
import ru.zagarazhi.blocks.EndBlock;
import ru.zagarazhi.blocks.StartBlock;
import ru.zagarazhi.blocks.YBlock;

public class Token {
    private Type type;
    private String value;

    public Token(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("%s<%s>", this.type.toString(), this.value);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Block toBlock(Token nextToken) {
        switch (nextToken.type.toString()) {
            case "YBLOCK": {
                if (nextToken.value == "Н") {
                    return new StartBlock();
                } else if (nextToken.value == "К") {
                    return new EndBlock();
                } else {
                    return new YBlock("Y" + nextToken.value);
                }
            }
            case "XBLOCK": {

            }
            case "WBLOCK": {

            }
        }
        throw new Error("Invalid block type");
    }
}
