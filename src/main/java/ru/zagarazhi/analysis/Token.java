package ru.zagarazhi.analysis;

import java.util.ArrayList;

import ru.zagarazhi.blocks.Block;
import ru.zagarazhi.blocks.EndBlock;
import ru.zagarazhi.blocks.StartBlock;
import ru.zagarazhi.blocks.WBlock;
import ru.zagarazhi.blocks.XBlock;
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

    public ArrayList<Block> tokenListToBlockList(Token[] newTokens) {

        Block prevBlock = null;
        ArrayList<Block> blockList = new ArrayList<>();

        for (Token nextToken : newTokens) {
            switch (nextToken.type.toString()) {
                case "YBLOCK": {
                    if (nextToken.value == "Н") {
                        push(blockList, prevBlock);
                        prevBlock = new StartBlock();
                    } else if (nextToken.value == "К") {
                        push(blockList, new EndBlock());
                    } else {
                        push(blockList, prevBlock);
                        prevBlock = new YBlock("Y" + nextToken.value);
                    }
                }
                case "XBLOCK": {
                    push(blockList, prevBlock);
                    prevBlock = new XBlock();
                }
                case "WBLOCK": {
                    push(blockList, prevBlock);
                    prevBlock = new WBlock();
                }
                case "UPARROW": {
                    prevBlock.setUpArrowIndex(Integer.parseInt(nextToken.value));
                }
                case "DOWNARROW": {
                    prevBlock.setDownArrowIndex(Integer.parseInt(nextToken.value));
                }
            }
            throw new Error("Invalid block type");
        }
        throw new Error("Invalid input");
    }

    private void push(ArrayList<Block> blockList, Block prevBlock) {
        if (prevBlock != null) {
            blockList.add(prevBlock);
        }
    }
}
