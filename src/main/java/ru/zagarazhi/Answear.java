package ru.zagarazhi;

public class Answear {
    private static Answear instance;
    private StringBuilder answearText;

    private Answear(){
        answearText = new StringBuilder("");
    }

    public static Answear getInstance() {
        if(instance == null){
            instance = new Answear();
        }
        return instance;
    }

    public void addTextToAnswear(String text) {
        answearText.append(text);
    }

    public String getAnswearText() {
        return answearText.toString();
    }
}
