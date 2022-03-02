package ru.zagarazhi;

import ru.zagarazhi.view.*;

public class App {
    public static void main( String[] args ) {
        //CUI cui = new CUI();
        //cui.show();
        boolean[][] test = {{true, false, false, false}, 
                            {false, false, false, false}, 
                            {false, false, false, false}};
        Turtle turtle = new Turtle(test, 3, 1, 2, 3);
        turtle.leftHanded();
    }
}
