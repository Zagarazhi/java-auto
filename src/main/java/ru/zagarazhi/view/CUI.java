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

import ru.zagarazhi.Answear;
import ru.zagarazhi.ConditionSetter;
import ru.zagarazhi.ErrorObserver;
import ru.zagarazhi.Model;
import ru.zagarazhi.analysis.LexelAnalyzer;
import ru.zagarazhi.analysis.Token;
import ru.zagarazhi.analysis.Type;

public class CUI {

    private ErrorObserver errorObserver = ErrorObserver.getInstance();
    private static Answear answear = Answear.getInstance();
    private List<String> activeLSA = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private ConditionSetter conditionSetter = ConditionSetter.getInstance();

    private static boolean convertCharToBoolean(String input) {
        return input.charAt(0) == '1' ? true : false;
    }

    public static boolean getConditionFromUser(int index) {
        System.out.println("Текущий ответ: " + answear.getAnswearText());
        System.out.println("Введите значение для X" + index);
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
        } catch (IOException e) {
            System.err.println("Ошибка чтения");
        } catch (Exception exception) {
            System.err.println("Ошибка");
        }
        System.out.println("ЛСА загружены");
    }

    private int countX(Token[] tokens) {
        int count = 0;
        Set<Integer> x = new HashSet<Integer>();
        int index = 0;
        for (int i = 1; i < tokens.length - 1; i++) {
            if (tokens[i].getType() == Type.XBLOCK) {
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
        try {
            String lsa = activeLSA.get(number - 1);
            errorObserver.clearLexicalErrors();
            List<Token> list = LexelAnalyzer.lex(lsa);
            if (errorObserver.getLexicalErrors().size() == 0) {
                System.out.println("1. Последовательный ввод");
                System.out.println("2. Ввод всех состояний");
                System.out.println("3. Полный перебор");
                System.out.print(">> ");
                switch (scanner.nextInt()) {
                    case 1:
                        conditionSetter.setFull(false);
                        errorObserver.clearSyntaxisErrors();
                        answear.clearAnswearText();
                        Model.model(list.toArray(new Token[list.size()]), false);
                        if (errorObserver.getSyntaxisErrors().size() == 0) {
                            System.out.println("Ответ: " + answear.getAnswearText());
                        } else {
                            for (String err : errorObserver.getSyntaxisErrors()) {
                                System.err.println(err);
                            }
                        }
                        break;
                    case 2:
                        conditionSetter.setFull(true);
                        System.out.println("Введите состояния, например '01101'");
                        System.out.print(">> ");
                        conditionSetter.setConditions(scanner.next());
                        errorObserver.clearSyntaxisErrors();
                        answear.clearAnswearText();
                        Model.model(list.toArray(new Token[list.size()]), false);
                        if (errorObserver.getSyntaxisErrors().size() == 0) {
                            System.out.println("Ответ: " + answear.getAnswearText());
                        } else {
                            for (String err : errorObserver.getSyntaxisErrors()) {
                                System.err.println(err);
                            }
                        }
                        break;
                    case 3:
                        int count = countX(list.toArray(new Token[list.size()]));
                        for (int i = 0; i < Math.pow(2, count); i++) {
                            conditionSetter.setFull(true);
                            conditionSetter.setConditions(toBinary(i, count));
                            errorObserver.clearSyntaxisErrors();
                            answear.clearAnswearText();
                            Model.model(list.toArray(new Token[list.size()]), true);
                            if (errorObserver.getSyntaxisErrors().size() == 0) {
                                System.out.println(String.format("Ответ для %s: %s", toBinary(i, count),
                                        answear.getAnswearText()));
                            } else {
                                System.out.println(String.format("Набор %s вызвал ошибки:", toBinary(i, count)));
                                for (String err : errorObserver.getSyntaxisErrors()) {
                                    System.err.println(err);
                                }
                            }
                        }
                        break;
                    default:
                        System.err.println("Нет такой опции!");
                        break;
                }
            } else {
                for (String err : errorObserver.getLexicalErrors()) {
                    System.err.println(err);
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка!");
        }
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
                errorObserver.clearLexicalErrors();
                LexelAnalyzer.lex(output);
            }
            if (errorObserver.getLexicalErrors().size() > 0) {
                for (String err : errorObserver.getLexicalErrors()) {
                    System.err.println(err);
                }
            }
        } while (input != "+");
        if (!input.equals("!")) {
            System.out.println(String.format("Строка '%s' добавлена!", output));
            activeLSA.add(output);
        }
    }

    public void show() {
        int input = -1;
        do {
            System.out.println("\nЧто вы хотите сделать?");
            System.out.println("1. Загрузить ЛСА из файла");
            System.out.println("2. Ввести ЛСА с консоли");
            System.out.println("3. Вывести загруженные ЛСА");
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
                case 0:
                    break;
                default:
                    System.err.println("Нет такой опции!");
                    break;
            }
        } while (input != 0);
        scanner.close();
    }
}
