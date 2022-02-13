package ru.zagarazhi.blocks;

public class StartBlock implements Block {
    private static StartBlock instance;
    private Block next;

    private StartBlock(){
        
    }

    private StartBlock(Block next){
        this.next = next;
    }

    public static StartBlock getInstance(){
        if(instance == null){
            instance = new StartBlock();
        }
        return instance;
    }

    public static StartBlock getInstance(Block next){
        if(instance == null){
            instance = new StartBlock(next);
        }
        return instance;
    }

    public void setNext(Block next) {
        this.next = next;
    }

    @Override
    public Block doJobAndGetNext(){
        return next;
    }
}
