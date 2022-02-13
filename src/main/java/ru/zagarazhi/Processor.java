package ru.zagarazhi;

import java.util.ArrayList;

import ru.zagarazhi.blocks.Block;
import ru.zagarazhi.blocks.Mark;
import ru.zagarazhi.blocks.StartBlock;
import ru.zagarazhi.blocks.XBlock;
import ru.zagarazhi.blocks.YBlock;

public class Processor {
    private ArrayList<Block> blocks;
    private ArrayList<Mark> marks;

    public Processor(ArrayList<Block> blocks) {
        this.blocks = blocks;
        this.buildModel();
    }

    public void buildModel() {
        Block nextBlock;
        for (int i = 0; i < blocks.size(); i++) {
            nextBlock = blocks.get(i);
            switch (nextBlock.getClass().getName()) {
                case "YBlock": {
                    nextBlock.setNext(blocks.get(i + 1));
                    blocks.set(i, nextBlock);
                    this.processUpArrow(nextBlock);
                    this.processDownArrow(nextBlock, i);
                }

                case "XBlock": {
                    XBlock newBlock = new XBlock();
                    newBlock.setCondition(true);
                    newBlock.setDownArrowIndex(nextBlock.getDownArrowIndex());
                    newBlock.setUpArrowIndex(nextBlock.getUpArrowIndex());
                    this.processUpArrow(newBlock);
                    this.processDownArrow(newBlock, i);
                    newBlock.setNextTrue(blocks.get(i + 1));
                    newBlock.setNextFalse(marks.get(3).getNext());
                    blocks.set(i, newBlock);
                }

                case "WBlock": {
                    nextBlock.setNext(blocks.get(i + 1));
                    blocks.set(i, nextBlock);
                    this.processUpArrow(nextBlock);
                    this.processDownArrow(nextBlock, i);
                }

                case "StartBlock": {
                    nextBlock.setNext(blocks.get(i + 1));
                    blocks.set(i, nextBlock);
                    if (nextBlock.getDownArrowIndex() != 0) {
                        Mark nextMark = new Mark(nextBlock.getDownArrowIndex());
                        nextMark.addPreviousBlock(nextBlock);
                        nextMark.setNext(blocks.get(i + 1));
                        this.marks.add(nextMark);
                    }
                }

                case "EndBlock": {

                }

                default:
                    throw new Error("Invalid block type");
            }
        }
    }

    private boolean checkMark(int index) {
        for (Mark mark : marks) {
            if (mark.getIndex() == index)
                return true;
        }
        return false;
    }

    private int getIndexOfMark(int markVal) {
        for (Mark mark : marks) {
            if (mark.getIndex() == markVal)
                return mark.getIndex();
        }
        throw new Error("Invalid mark index");
    }

    private void processUpArrow(Block nextBlock) {
        if (nextBlock.getUpArrowIndex() != 0) {
            if (checkMark(nextBlock.getUpArrowIndex())) {
                Mark nextMark = this.marks.get(this.getIndexOfMark(nextBlock.getUpArrowIndex()));
                nextMark.addPreviousBlock(nextBlock);
                this.marks.set(this.getIndexOfMark(nextBlock.getUpArrowIndex()), nextMark);
            } else {
                Mark nextMark = new Mark(nextBlock.getDownArrowIndex());
                nextMark.addPreviousBlock(nextBlock);
                this.marks.add(nextMark);
            }
        }
    }

    private void processDownArrow(Block nextBlock, int i) {
        if (nextBlock.getDownArrowIndex() != 0) {
            if (checkMark(nextBlock.getDownArrowIndex())) {
                Mark nextMark = this.marks.get(this.getIndexOfMark(nextBlock.getDownArrowIndex()));
                nextMark.setNext(blocks.get(i + 1));
                this.marks.set(this.getIndexOfMark(nextBlock.getDownArrowIndex()), nextMark);
            } else {
                Mark nextMark = new Mark(nextBlock.getDownArrowIndex());
                nextMark.setNext(blocks.get(i + 1));
                this.marks.add(nextMark);
            }
        }
    }
}
