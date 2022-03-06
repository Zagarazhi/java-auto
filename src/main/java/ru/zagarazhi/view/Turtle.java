package ru.zagarazhi.view;

import java.util.List;
import java.util.concurrent.TimeUnit;

import ru.zagarazhi.controller.*;
import ru.zagarazhi.model.*;

public class Turtle implements EventListener{
    private ErrorObserver errorObserver = ErrorObserver.getInstance();
    private Answear answear = Answear.getInstance();
    private ConditionSetter conditionSetter = ConditionSetter.getInstance();
    private boolean noError = true;
    private boolean first = true;
    private boolean[][] field;
    private boolean[] xValues = new boolean[5];
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
        errorObserver.events.subscribe("error", this);
        answear.events.subscribe("answear", this);
        conditionSetter.events.subscribe("condition", this);
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
        this.startX = startX + 1;
        this.startY = startY + 1;
        this.targetX = endX + 1;
        this.targetY = endY + 1;
        this.currentX = startX + 1;
        this.currentY = startY + 1;
    }

    private void print() {
        for (int y = 1; y < field.length - 1; y++) {
            for (int x = 1; x < field[y].length - 1; x++) {
                if (x == currentX && y == currentY) {
                    switch(direction){
                        case 0:
                            System.out.print("v");
                            break;
                        case 1:
                            System.out.print("<");
                            break;
                        case 2:
                            System.out.print("^");
                            break;
                        case 3:
                            System.out.print(">");
                            break;
                        default:
                            System.out.print("T");
                            break;
                    }
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

    private boolean getCondition(int x, int y) {
        return this.field[y][x];
    }

    private boolean checkToRight(int direction) {
        switch (direction) {
            case 0:
                return getCondition(currentX + 1, currentY);
            case 1:
                return getCondition(currentX, currentY + 1);
            case 2:
                return getCondition(currentX - 1, currentY);
            case 3:
                return getCondition(currentX, currentY - 1);
            default:
                errorObserver.pushError("Ошибка в определении направления");
                break;
        }
        return false;
    }

    private boolean checkToLeft(int direction) {
        switch (direction) {
            case 0:
                return getCondition(currentX - 1, currentY);
            case 1:
                return getCondition(currentX, currentY - 1);
            case 2:
                return getCondition(currentX + 1, currentY);
            case 3:
                return getCondition(currentX, currentY + 1);
            default:
                errorObserver.pushError("Ошибка в определении направления");
                break;
        }
        return false;
    }

    private boolean checkForward(int direction) {
        switch (direction) {
            case 0:
                return getCondition(currentX, currentY + 1);
            case 1:
                return getCondition(currentX - 1, currentY);
            case 2:
                return getCondition(currentX, currentY - 1);
            case 3:
                return getCondition(currentX + 1, currentY);
            default:
                errorObserver.pushError("Ошибка в определении направления");
                break;
        }
        return false;
    }

    private boolean isAtTargetBlock() {
        return ((this.currentX == this.targetX) && (this.currentY == this.targetY));
    }

    private boolean isAtStartBlock() {
        return ((this.currentX == this.startX) && (this.currentY == this.startY));
    }

    private void rotateRight() {
        switch (this.direction) {
            case 0:
                this.direction = 1;
                break;
            case 1:
                this.direction = 2;
                break;
            case 2:
                this.direction = 3;
                break;
            case 3:
                this.direction = 0;
                break;
            default:
                errorObserver.pushError("Ошибка при повороте направо");
        }
    }

    private void rotateLeft() {
        switch (this.direction) {
            case 0:
                this.direction = 3;
                break;
            case 1:
                this.direction = 0;
                break;
            case 2:
                this.direction = 1;
                break;
            case 3:
                this.direction = 2;
                break;
            default:
                errorObserver.pushError("Ошибка при повороте налево");
                break;
        }
    }

    private void moveForward() {
        first = false;
        switch (this.direction) {
            case 0:
                this.currentY++;
                break;
            case 1:
                this.currentX--;
                break;
            case 2:
                this.currentY--;
                break;
            case 3:
                this.currentX++;
                break;
            default:
                errorObserver.pushError("Ошибка при движении вперед");
                break;
        }
    }

    private void move(String message) {
        switch(message){
            case "Y1":
                rotateLeft();
                break;
            case "Y2":
                moveForward();
                break;
            case "Y3":
                rotateRight();
                break;
            case "YН":
                //
                break;
            case "YК":
                //
                break;
            default:
                errorObserver.pushError("Ошибка при выборе действия");
        }
    }

    public void rightHanded() {
        first = true;
        String lsa = "YНD1X4U6X3U2X2U3X1U4Y3Y3X2U3D7X5U6WU1D2Y3Y2WU7D3Y2WU7D4Y1Y2WU7D6YK";
        answear.clearAnswear();
        List<Token> list = LexelAnalyzer.lex(lsa);
        if(noError){
            xValues[0] = !checkToRight(this.direction);
            xValues[1] = !checkForward(this.direction);
            xValues[2] = !checkToLeft(this.direction);
            xValues[3] = isAtTargetBlock();
            xValues[4] = isAtStartBlock();
            Model.model(list.toArray(new Token[list.size()]), false);
        }
        noError = true;
    }

    public void leftHanded() {
        first = true;
        String lsa = "YНD1X4U6X1U2X2U3X3U4Y1Y1X2U3D7X5U6WU1D2Y1Y2WU7D3Y2WU7D4Y3Y2WU7D6YK";
        answear.clearAnswear();
        List<Token> list = LexelAnalyzer.lex(lsa);
        if(noError){
            xValues[0] = !checkToRight(this.direction);
            xValues[1] = !checkForward(this.direction);
            xValues[2] = !checkToLeft(this.direction);
            xValues[3] = isAtTargetBlock();
            xValues[4] = isAtStartBlock();
            Model.model(list.toArray(new Token[list.size()]), false);
        }
        noError = true;
    }

    @Override
    public void update(String eventType, String message) {
        switch(eventType){
            case "error":
                noError = false;
                System.out.println(message);
                break;
            case "answear":
                System.out.println();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(!first && isAtStartBlock()){
                    System.out.println("Выход не найден");
                }
                if(isAtTargetBlock()){
                    System.out.println("Выход успешно найден!");
                } else {
                    move(message);
                }
                xValues[0] = !checkToRight(this.direction);
                xValues[1] = !checkForward(this.direction);
                xValues[2] = !checkToLeft(this.direction);
                xValues[3] = isAtTargetBlock();
                xValues[4] = isAtStartBlock();
                print();
                System.out.println(String.format(
                    "X1: %d, X2: %d, X3: %d, X4: %d, X5: %d, %s", 
                    xValues[0] ? 0 : 1, 
                    xValues[1] ? 0 : 1, 
                    xValues[2] ? 0 : 1,
                    xValues[3] ? 1 : 0,
                    xValues[4] ? 1 : 0,
                    message
                    ));
                break;
            case "condition":
                String ans = message.replace("X", "");
                conditionSetter.setCondition(!xValues[Integer.parseInt(ans) - 1]);
                break;
            default:
                //
                break;
        }
    }
}
