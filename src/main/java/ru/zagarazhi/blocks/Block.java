package ru.zagarazhi.blocks;

public interface Block {
    public Block doJobAndGetNext();

    public void setDownArrowIndex(int downArrowIndex);

    public void setUpArrowIndex(int upArrowIndex);
}
