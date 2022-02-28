package ru.zagarazhi;

public class TurtleLogic {

    private int targetX = 0;
    private int targetY = 0;
    private int currentX = 0;
    private int currentY = 0;

    private int direction = 0;
    // 0 - вниз
    // 1 - влево
    // 2 - ввверх
    // 3 - вправо

    public int getDirection() {
        return direction;
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

    public void rightHanded() {

    }

    public void leftHanded() {

    }

    public void theOneAndOnly() {

    }

    private boolean checkToRight(int direction) {
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

    private boolean checkToLeft(int direction) {
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

    private boolean checkForward(int direction) {
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

    private boolean isBlockEmpty(int blockX, int blockY) {
        // ToDo: подвязать к логике работы лабиринта
        return true;
    }

}
