package ru.zagarazhi.blocks;

import ru.zagarazhi.Answear;

public class YBlock implements Block {
    private Answear answear;
    private String text;
    private Block next;

    public YBlock(Answear answear, String text) {
        this.answear = answear;
        this.text = text;
    }

    public YBlock(Answear answear, String text, Block next) {
        this.answear = answear;
        this.text = text;
        this.next = next;
    }

    public void setNext(Block next){
        this.next = next;
    }

    private void doJob(){
        answear.addTextToAnswear(text);
    }

    @Override
    public Block doJobAndGetNext() {
        doJob();
        return next;
    }
}
