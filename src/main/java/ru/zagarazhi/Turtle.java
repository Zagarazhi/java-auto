package ru.zagarazhi;

public class Turtle {
    private boolean[][] field;
    private int startX = 0;
    private int startY = 0;
    private int targetX = 0;
    private int targetY = 0;
    private int currentX = 0;
    private int currentY = 0;

    private int direction = 0;

    // 0 - вниз
    // 1 - влево
    // 2 - ввверх
    // 3 - вправо

    public Turtle(boolean[][] field, int startX, int startY, int endX, int endY) {
        this.field = new boolean[field.length + 2][field[0].length + 2];
        for (int y = 0; y < this.field.length; y++) {
            for (int x = 0; x < this.field[y].length; x++) {
                if (y == 0 || y == this.field.length - 1 || x == 0 || x == this.field[y].length - 1) {
                    this.field[y][x] = true;
                } else {
                    this.field[y][x] = field[y - 1][x - 1];
                }
            }
        }
        this.startX = startX;
        this.startY = startY;
        this.targetX = endX;
        this.targetY = endY;
    }

    public void print() {
        for (int y = 1; y < field.length - 1; y++) {
            for (int x = 1; x < field[y].length - 1; x++) {
                if (x == currentX && y == currentY) {
                    System.out.print("T");
                } else if (x == startX && y == startY) {
                    System.out.print("S");
                } else if (x == targetX && y == targetY) {
                    System.out.print("E");
                } else {
                    System.out.print(field[y][x] ? "1" : "0");
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public boolean getCondition(int x, int y) {
        return this.field[y][x];
    }

    public void rightHanded() {
        String lsa = "YНD1X4WU2D3X5WU2D4X3Y3Y2WU1D5X2Y2WU1D6X3Y1Y3WU1D7Y1Y1X2Y2WU1D2YK";
    }

    public void leftHanded() {

    }

    public void theOneAndOnly() {

    }

    public boolean checkToRight(int direction) {
        switch (direction) {
            case 0:
                return isBlockEmpty(getCurrentX() - 1, getCurrentY());
            case 1:
                return isBlockEmpty(getCurrentX(), getCurrentY() - 1);
            case 2:
                return isBlockEmpty(getCurrentX() + 1, getCurrentY());
            case 3:
                return isBlockEmpty(getCurrentX(), getCurrentY() + 1);
            default:
                throw new Error("Invalid direction");
        }
    }

    public boolean checkToLeft(int direction) {
        switch (direction) {
            case 0:
                return isBlockEmpty(getCurrentX() + 1, getCurrentY());
            case 1:
                return isBlockEmpty(getCurrentX(), getCurrentY() + 1);
            case 2:
                return isBlockEmpty(getCurrentX() - 1, getCurrentY());
            case 3:
                return isBlockEmpty(getCurrentX(), getCurrentY() - 1);
            default:
                throw new Error("Invalid direction");
        }
    }

    public boolean checkForward(int direction) {
        switch (direction) {
            case 0:
                return isBlockEmpty(getCurrentX(), getCurrentY() + 1);
            case 1:
                return isBlockEmpty(getCurrentX() - 1, getCurrentY());
            case 2:
                return isBlockEmpty(getCurrentX(), getCurrentY() - 1);
            case 3:
                return isBlockEmpty(getCurrentX() + 1, getCurrentY());
            default:
                throw new Error("Invalid direction");
        }
    }

    public boolean isAtTargetBlock() {
        return ((this.currentX == this.targetX) && (this.currentY == this.targetY));
    }

    public boolean isAtStartBlock() {
        return ((this.currentX == this.startX) && (this.currentY == this.startY));
    }

    private boolean isBlockEmpty(int blockX, int blockY) {
        return this.getCondition(blockX, blockY);
    }

    public int lengthX() {
        return this.field[0].length;
    }

    public int lengthY() {
        return this.field.length;
    }

    public int getDirection() {
        return direction;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getTargetX() {
        return targetX;
    }

    public void setTargetX(int targetX) {
        this.targetX = targetX;
    }

    public int getTargetY() {
        return targetY;
    }

    public void setTargetY(int targetY) {
        this.targetY = targetY;
    }

    public int getCurrentX() {
        return currentX;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

}
