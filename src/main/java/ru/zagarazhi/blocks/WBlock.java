package ru.zagarazhi.blocks;

public class WBlock implements Block {
    private Block next;
    private int upArrowIndex = 0;
    private int downArrowIndex = 0;

    public WBlock() {

    }

    public WBlock(Block next) {
        this.next = next;
    }

    public void setNext(Block next) {
        this.next = next;
    }

    @Override
    public int getUpArrowIndex() {
        return upArrowIndex;
    }

    @Override
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
