package ru.zagarazhi.blocks;

public interface Block {
    public Block doJobAndGetNext();

    public void setDownArrowIndex(int downArrowIndex);

    public void setUpArrowIndex(int upArrowIndex);

    public int getDownArrowIndex();

    public int getUpArrowIndex();

    public void setNext(Block nextBlock);
}
