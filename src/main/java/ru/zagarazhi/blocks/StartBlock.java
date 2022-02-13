package ru.zagarazhi.blocks;

public class StartBlock implements Block {

    private int upArrowIndex;
    private int downArrowIndex;

    public int getUpArrowIndex() {
        return upArrowIndex;
    }

    public int getDownArrowIndex() {
        return downArrowIndex;
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
