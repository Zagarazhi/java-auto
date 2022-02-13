package ru.zagarazhi;

import java.util.ArrayList;

import ru.zagarazhi.blocks.Block;
import ru.zagarazhi.blocks.Mark;
import ru.zagarazhi.blocks.StartBlock;
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
                    if (nextBlock.getUpArrowIndex() != 0) {
                        if (checkMark(nextBlock.getUpArrowIndex())) {

                        } else {
                            Mark nextMark = new Mark(nextBlock.getDownArrowIndex());
                            nextMark.addPreviousBlock(nextBlock);
                            this.marks.add(nextMark);
                        }
                    }
                }

                case "XBlock": {

                }

                case "WBlock": {

                }

                case "StartBlock": {
                    nextBlock.setNext(blocks.get(i + 1));
                    blocks.set(i, nextBlock);
                    if (nextBlock.getDownArrowIndex() != 0) {
                        Mark nextMark = new Mark(nextBlock.getDownArrowIndex());
                        nextMark.addPreviousBlock(nextBlock);
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

}
