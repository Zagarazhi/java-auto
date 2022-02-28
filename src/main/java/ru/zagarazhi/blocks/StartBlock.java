package ru.zagarazhi.blocks;

public class StartBlock implements Block {

    private int upArrowIndex = 0;
    private int downArrowIndex = 0;
    private Block next;

    @Override
    public int getUpArrowIndex() {
        return upArrowIndex;
    }

    @Override
    public int getDownArrowIndex() {
        return downArrowIndex;
    }

    public Block getNext() {
        return next;
    }

    public void setNext(Block next) {
        this.next = next;
    }

    @Override
    public Block doJobAndGetNext() {
        // TODO Auto-generated method stub
        return null;
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
