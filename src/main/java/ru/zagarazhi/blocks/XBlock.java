package ru.zagarazhi.blocks;

public class XBlock implements Block {
    private boolean condition;
    private Block nextTrue;
    private Block nextFalse;
    private int upArrowIndex;
    private int downArrowIndex;

    public XBlock() {

    }

    public XBlock(boolean condition) {
        this.condition = condition;
    }

    public XBlock(Block nextTrue, Block nextFalse) {
        this.nextTrue = nextTrue;
        this.nextFalse = nextFalse;
    }

    public XBlock(boolean condition, Block nextTrue, Block nextFalse) {
        this.condition = condition;
        this.nextTrue = nextTrue;
        this.nextFalse = nextFalse;
    }

    public int getUpArrowIndex() {
        return upArrowIndex;
    }

    public void setUpArrowIndexes(int upArrowIndex) {
        this.upArrowIndex = upArrowIndex;
    }

    public int getDownArrowIndex() {
        return downArrowIndex;
    }

    public void setDownArrowIndex(int downArrowIndex) {
        this.downArrowIndex = downArrowIndex;
    }

    public void setCondition(boolean condition) {
        this.condition = condition;
    }

    public void setNextTrue(Block nextTrue) {
        this.nextTrue = nextTrue;
    }

    public void setNextFalse(Block nextFalse) {
        this.nextFalse = nextFalse;
    }

    @Override
    public Block doJobAndGetNext() {
        if (condition) {
            return nextTrue;
        }
        return nextFalse;
    }
}
