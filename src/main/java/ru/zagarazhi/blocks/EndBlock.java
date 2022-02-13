package ru.zagarazhi.blocks;

public class EndBlock implements Block {
    private static EndBlock instance;

    private EndBlock() {

    }

    public static EndBlock getinstance(){
        if(instance == null) {
            instance = new EndBlock();
        }
        return instance;
    }

    @Override
    public Block doJobAndGetNext(){
        return null;
    }
}
