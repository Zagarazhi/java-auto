package ru.zagarazhi.blocks;

import ru.zagarazhi.Answear;

public class YBlock implements Block {
    private Answear answear = Answear.getInstance();
    private String text;
    private Block next;
    private int upArrowIndex = 0;
    private int downArrowIndex = 0;

    public YBlock(String text) {
        this.text = text;
    }

    public YBlock(String text, Block next) {
        this.text = text;
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

    public void setNext(Block next) {
        this.next = next;
    }

    private void doJob() {
        answear.addTextToAnswear(text);
    }

    @Override
    public Block doJobAndGetNext() {
        doJob();
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
