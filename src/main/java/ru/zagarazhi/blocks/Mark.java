package ru.zagarazhi.blocks;

import java.util.ArrayList;

public class Mark implements Block {

    private int index;
    private ArrayList<Block> previousBlocks;
    private Block nextBlock;

    public Mark(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public ArrayList<Block> getPreviousBlocks() {
        return previousBlocks;
    }

    public void setPreviousBlocks(ArrayList<Block> previousBlocks) {
        this.previousBlocks = previousBlocks;
    }

    public void addPreviousBlock(Block previousBlock) {
        this.previousBlocks.add(previousBlock);
    }

    public Block getNext() {
        return nextBlock;
    }

    @Override
    public Block doJobAndGetNext() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setDownArrowIndex(int downArrowIndex) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setUpArrowIndex(int upArrowIndex) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setNext(Block nextBlock) {
        this.nextBlock = nextBlock;

    }

    @Override
    public int getDownArrowIndex() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getUpArrowIndex() {
        // TODO Auto-generated method stub
        return 0;
    }

}
