package ru.zagarazhi.blocks;

public class WBlock implements Block {
    private Block next;

    public WBlock(){
        
    }

    public WBlock(Block next){
        this.next = next;
    }

    public void setNext(Block next) {
        this.next = next;
    }

    @Override
    public Block doJobAndGetNext(){
        return next;
    }
}
