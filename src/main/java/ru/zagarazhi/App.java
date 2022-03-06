package ru.zagarazhi;

import ru.zagarazhi.controller.MazeReader;
import ru.zagarazhi.view.*;

public class App {
    public static void main( String[] args ) {
        //CUI cui = new CUI();
        //cui.show();
        MazeReader mr = new MazeReader();
        mr.readMaze("C:\\Users\\Daniil\\Projects\\java-auto\\src\\main\\java\\ru\\zagarazhi\\Fields\\maze.txt");
        boolean[][] test = mr.getField();
        Turtle turtle = new Turtle(test, mr.getStartX(), mr.getStartY(), mr.getEndX(), mr.getEndY());
        turtle.leftHanded();
    }
}
