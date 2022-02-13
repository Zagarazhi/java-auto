package ru.zagarazhi.blocks;

public class WBlock implements Block {
    private Block next;
    private int upArrowIndex;
    private int downArrowIndex;

    public WBlock() {

    }

    public WBlock(Block next) {
        this.next = next;
    }

    public void setNext(Block next) {
        this.next = next;
    }

    public int getUpArrowIndex() {
        return upArrowIndex;
    }

    public int getDownArrowIndex() {
        return downArrowIndex;
    }

    @Override
    public Block doJobAndGetNext() {
        return next;
    }

    @Override
    public void setUpArrowIndex(int upArrowIndex) {
        this.upArrowIndex = upArrowIndex;
    }

    @Override
    public void setDownArrowIndex(int downArrowIndex) {
        this.downArrowIndex = downArrowIndex;
    }
}
