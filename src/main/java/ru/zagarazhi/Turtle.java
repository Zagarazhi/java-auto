package ru.zagarazhi;

public class Turtle {
    private boolean[][] field;
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private int posX;
    private int posY;

    public Turtle(boolean[][] field, int startX, int startY, int endX, int endY) {
        this.field = new boolean[field.length + 2][field[0].length + 2];
        for(int y = 0; y < this.field.length; y++) {
            for(int x = 0; x < this.field[y].length; x++) {
                if(y == 0 || y == this.field.length - 1 || x == 0 || x == this.field[y].length - 1) {
                    this.field[y][x] = true;
                } else {
                    this.field[y][x] = field[y - 1][x - 1];
                }
            }
        }
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public void print() {
        for(int y = 1; y < field.length - 1; y++) {
            for(int x = 1; x < field[y].length - 1; x++) {
                if(x == posX && y == posY){
                    System.out.print("T");
                } else if (x == startX && y == startY) {
                    System.out.print("S");
                } else if (x == endX && y == endY) {
                    System.out.print("E");
                } else {
                    System.out.print(field[y][x] ? "1" : "0");
                }
            }
            System.out.println();
        }
    }

    public boolean getCondition(int x, int y){
        return this.field[y][x];
    }

    public int lengthX(){
        return this.field[0].length;
    }

    public int lengthY(){
        return this.field.length;
    }
}
