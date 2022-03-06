package ru.zagarazhi.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import ru.zagarazhi.controller.*;
import ru.zagarazhi.model.*;

public class CUI implements EventListener{
    private ErrorObserver errorObserver = ErrorObserver.getInstance();
    private Answear answear = Answear.getInstance();
    private ConditionSetter conditionSetter = ConditionSetter.getInstance();
    private List<String> activeLSA = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private boolean noError = true;
    private int conditionType = 0;
    private int index = 0;
    private String conditions;

    public CUI(){
        errorObserver.events.subscribe("error", this);
        answear.events.subscribe("answear", this);
        conditionSetter.events.subscribe("condition", this);
    }

    private boolean convertCharToBoolean(String input) {
        return !(input.charAt(0) == '0');
    }

    private boolean convertCharToBoolean(char input) {
        return !(input == '0');
    }

    private boolean getConditionFromUser(String x) {
        System.out.println("Введите значение для " + x);
        System.out.print(">> ");
        return convertCharToBoolean(scanner.next());
    }

    private void getFromFile(String adress) {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(adress), Charset.forName("UTF-8")))) {
            String line = null;
            do {
                line = reader.readLine();
                if (line != null && !line.contains("//")) {
                    activeLSA.add(line);
                }
            } while (line != null);
            reader.close();
        } catch (FileNotFoundException ex) {
            System.err.println("Файл не найден");
            return;
        } catch (IOException e) {
            System.err.println("Ошибка чтения");
            return;
        } catch (Exception exception) {
            System.err.println("Ошибка");
            return;
        }
        System.out.println("ЛСА загружены");
    }

    private int countX(Token[] tokens) {
        int count = 0;
        Set<Integer> x = new HashSet<Integer>();
        int index = 0;
        for (int i = 1; i < tokens.length - 1; i++) {
            if (tokens[i].getType() == BlockType.XBLOCK) {
                index = Integer.parseInt(tokens[i].getValue());
                if (!x.contains(index)) {
                    x.add(index);
                    count++;
                }
            }
        }
        return count;
    }

    private String toBinary(int x, int len) {
        StringBuilder result = new StringBuilder();
        for (int i = len - 1; i >= 0; i--) {
            int mask = 1 << i;
            result.append((x & mask) != 0 ? 1 : 0);
        }
        return result.toString();
    }

    private void model(int number) {
        String lsa = activeLSA.get(number - 1);
        List<Token> list = LexelAnalyzer.lex(lsa);
        answear.clearAnswear();
        if (noError) {
            System.out.println("1. Последовательный ввод");
            System.out.println("2. Ввод всех состояний");
            System.out.println("3. Полный перебор");
            System.out.print(">> ");
            switch (scanner.nextInt()) {
                case 1:
                    conditionType = 0;
                    answear.clearAnswear();
                    Model.model(list.toArray(new Token[list.size()]), false);
                    if(noError){
                        System.out.println("Ответ: " + answear.getAnswear());
                    }
                    noError = true;
                    break;
                case 2:
                    conditionType = 1;
                    index = 0;
                    System.out.println("Введите состояния, например '01101'");
                    System.out.print(">> ");
                    conditions = scanner.next();
                    answear.clearAnswear();
                    Model.model(list.toArray(new Token[list.size()]), false);
                    if(noError){
                        System.out.println("Ответ: " + answear.getAnswear());
                    }
                    noError = true;
                    break;
                case 3:
                    conditionType = 2;
                    int count = countX(list.toArray(new Token[list.size()]));
                    for (int i = 0; i < Math.pow(2, count); i++) {
                        index = 0;
                        answear.clearAnswear();
                        conditions = toBinary(i, count);
                        Model.model(list.toArray(new Token[list.size()]), true);
                        if(noError){
                            if(conditions.length() > 0){
                                System.out.println(String.format("Ответ для %s: %s", conditions, answear.getAnswear()));
                            } else {
                                System.out.println("Ответ: " + answear.getAnswear());
                            }
                        } else {
                            System.out.println("Был вызван следующим набором: " + conditions);
                        }
                        noError = true;
                    }
                    break;
                default:
                    System.err.println("Нет такой опции!");
                    break;
            }
        }
        noError = true;
    }

    private void showLSA() {
        if (activeLSA.size() == 0) {
            System.out.println("Нет активных ЛСА");
            return;
        }
        int count = 1;
        for (String lsa : activeLSA) {
            System.out.println(String.format("%d: %s", count, lsa));
            count++;
        }
        int input = -1;
        do {
            System.out.println("Выберете ЛСА для построения модели или 0 для выхода: ");
            System.out.print(">> ");
            try {
                input = scanner.nextInt();
                if (input > 0) {
                    model(input);
                }
            } catch (Exception e) {
                System.err.println("Введенная строка не является числом!");
                input = 0;
            }
        } while (input != 0);
    }

    private void getFromCosole() {
        System.out.println("Введите ЛСА: ");
        System.out.println(">Введите + чтобы добавить ЛСА<");
        System.out.println(">Введите - чтобы отменить последнее изменение<");
        System.out.println(">Введите -- чтобы очистить строку<");
        System.out.println(">Введите ! чтобы вернуться в меню<");
        String output = "";
        Deque<String> stack = new ArrayDeque<>();
        String input = "";
        do {
            System.out.println("Текущая строка: " + output);
            System.out.print(">> ");
            input = scanner.next();
            if (input == null) {
                return;
            }
            if (input.equals("+")) {
                break;
            } else if (input.equals("-")) {
                if (!stack.isEmpty()) {
                    output = output.replace(stack.pop(), "");
                }
            } else if (input.equals("--")) {
                stack = new ArrayDeque<>();
                output = "";
            } else if (input.equals("!")) {
                break;
            } else {
                output += input;
                stack.push(input);
                LexelAnalyzer.lex(output);
            }
        } while (input != "+");
        if (!input.equals("!")) {
            System.out.println(String.format("Строка '%s' добавлена!", output));
            activeLSA.add(output);
        }
    }

    private void turtle() {
        System.out.println("Введите абсолютный путь до файла, содержащего лабиринт");
        System.out.print(">> ");
        noError = true;
        answear.clearAnswear();
        MazeReader mr = new MazeReader();
        mr.readMaze(scanner.next());
        if(noError){
            boolean[][] test = mr.getField();
            Turtle turtle = new Turtle(test, mr.getStartX(), mr.getStartY(), mr.getEndX(), mr.getEndY());
            System.out.println("\n\n");
            System.out.println("Введите режим прохождения лабиринта или 0 для выхода");
            System.out.println("");
            turtle.printDemoMaze();
            System.out.println("");
            System.out.println("1. Алгоритм левой руки");
            System.out.println("2. Алгоритм правой руки");
            System.out.print(">> ");
            int input = -1;
            errorObserver.events.unsubscribe("error", this);
            answear.events.unsubscribe("answear", this);
            conditionSetter.events.unsubscribe("condition", this);
            try {
                input = scanner.nextInt();
            } catch (Exception ex) {
                System.err.println("Введенная строка не является числом!");
                return;
            }
            switch (input) {
                case 1:
                    turtle.run("YНD1X4U6X1U2X2U3X3U4Y1Y1X2U3D7X5U6WU1D2Y1Y2WU7D3Y2WU7D4Y3Y2WU7D6YK");
                    errorObserver.events.unsubscribe("error", turtle);
                    answear.events.unsubscribe("answear", turtle);
                    conditionSetter.events.unsubscribe("condition", turtle);
                    break;
                case 2:
                    turtle.run("YНD1X4U6X3U2X2U3X1U4Y3Y3X2U3D7X5U6WU1D2Y3Y2WU7D3Y2WU7D4Y1Y2WU7D6YK");
                    errorObserver.events.unsubscribe("error", turtle);
                    answear.events.unsubscribe("answear", turtle);
                    conditionSetter.events.unsubscribe("condition", turtle);
                    break;
                case 0:
                    //
                    break;
                default:
                    System.err.println("Нет такой опции!");
                    break;
            }
        }
    }

    public void show() {
        int input = -1;
        do {
            System.out.println("\nЧто вы хотите сделать?");
            System.out.println("1. Загрузить ЛСА из файла");
            System.out.println("2. Ввести ЛСА с консоли");
            System.out.println("3. Операции с загруженными ЛСА");
            System.out.println("4. Прохождение лабиринта");
            System.out.println("0. Выход");
            System.out.print(">> ");
            try {
                input = scanner.nextInt();
            } catch (Exception ex) {
                System.err.println("Введенная строка не является числом!");
                break;
            }
            switch (input) {
                case 1:
                    System.out.println("Введите абсолютный путь до файла:");
                    System.out.print(">> ");
                    getFromFile(scanner.next());
                    break;
                case 2:
                    getFromCosole();
                    break;
                case 3:
                    showLSA();
                    break;
                case 4:
                    turtle();
                    errorObserver.events.subscribe("error", this);
                    answear.events.subscribe("answear", this);
                    conditionSetter.events.subscribe("condition", this);
                    break;
                case 0:
                    break;
                default:
                    System.err.println("Нет такой опции!");
                    break;
            }
        } while (input != 0);
        scanner.close();
    }

    @Override
    public void update(String eventType, String message) {
        switch(eventType){
            case "error":
                noError = false;
                System.out.println(message);
                break;
            case "answear":
                //
                break;
            case "condition":
                switch(conditionType) {
                    case 0:
                        conditionSetter.setCondition(getConditionFromUser(message));
                        break;
                    case 1:
                        if(index < conditions.length()){
                            conditionSetter.setCondition(convertCharToBoolean(conditions.charAt(index)));
                            index++;
                        } else {
                            conditionSetter.setCondition(getConditionFromUser(message));
                        }
                        break;
                    case 2:
                        conditionSetter.setCondition(convertCharToBoolean(conditions.charAt(index)));
                        index++;
                        break;
                }
                
                break;
            default:
                //
                break;
        }
    }
}
