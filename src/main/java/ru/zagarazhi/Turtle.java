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

    public void run(String modelId) {
        boolean firstEntry = true;
        while (!this.isAtTargetBlock()) {
            switch (modelId) {
                case "right":
                    try {
                        this.rightHanded(firstEntry);
                    } catch (Exception e) {
                        break;
                    }
                case "left":
                    try {
                        this.leftHanded(firstEntry);
                    } catch (Exception e) {
                        break;
                    }
                case "custom":
                    try {
                        this.theOneAndOnly(firstEntry);
                    } catch (Exception e) {
                        break;
                    }
                default:
                    throw new Error("invalid programm id");
            }
            firstEntry = false;
        }
    }

    public void rightHanded(boolean firstEntry) {
        String lsa = "YНD1X4Y4WU2D3X5WU2D4X3Y3Y2WU1D5X2Y2WU1D6X3Y1Y2WU1D7Y1Y1X2Y2WU1D2YK";
        int[] xValues = {
                isAtTargetBlock() ? 1 : 0,
                isAtStartBlock() ? 1 : 0,
                checkToRight(this.direction) ? 1 : 0,
                checkForward(this.direction) ? 1 : 0,
                checkToLeft(this.direction) ? 1 : 0,
                checkForward(this.getOppositeDirection(this.direction)) ? 1 : 0,
        };
        if (firstEntry) {
            xValues[1] = 0;
        }
        String response = "YHY2YK"; // подвязать на response результат прогона лса
        switch (response) { // Y4 и Y5 - реакции от лса на завершение прогона/возвращение на старт
            case "YHY4YK": {
                System.out.println("Успех!");
            }
            case "YHY5YK": {
                throw new Error("Черепаха вернулась в начальную точку, лабиринт непроходим методом правой руки");
            }
            case "YHY1Y2Yk": {
                this.direction = rotateRight(this.direction);
                moveForward(this.direction);
            }
            case "YHY2YK": {
                moveForward(this.direction);
            }
            case "YHY3Y2YK":
                this.direction = rotateLeft(this.direction);
                moveForward(this.direction);
            case "YHY1Y1YK": {
                throw new Error("Черепаха закрыта со всех соторон и не может двигаться.");
            }
            case "YHY1Y1Y2YK": {
                this.direction = rotateLeft(this.direction);
                this.direction = rotateLeft(this.direction);
                moveForward(this.direction);
            }
            default:
                throw new Error("Ошибка обработки лса");
        }
    }

    public void leftHanded(boolean firstEntry) {

    }

    public void theOneAndOnly(boolean firstEntry) {

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

    private int getOppositeDirection(int direction) {
        switch (direction) {
            case 0:
                return 2;
            case 1:
                return 3;
            case 2:
                return 0;
            case 3:
                return 1;
            default:
                throw new Error("Invalid direction");
        }
    }

    private int rotateRight(int direction) {
        switch (direction) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 0;
            default:
                throw new Error("Invalid direction");
        }
    }

    private int rotateLeft(int direction) {
        switch (direction) {
            case 0:
                return 3;
            case 1:
                return 0;
            case 2:
                return 1;
            case 3:
                return 2;
            default:
                throw new Error("Invalid direction");
        }
    }

    private void moveForward(int direction) {
        switch (direction) {
            case 0:
                this.currentY++;
            case 1:
                this.currentX--;
            case 2:
                this.currentY--;
            case 3:
                this.currentX++;
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
