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
import java.util.List;
import java.util.Scanner;

import ru.zagarazhi.Answear;
import ru.zagarazhi.ErrorObserver;
import ru.zagarazhi.analysis.LexelAnalyzer;

public class CUI {
    
    private ErrorObserver errorObserver = ErrorObserver.getInstance();
    private Answear answear = Answear.getInstance();
    private List<String> activeLSA = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    private void getFromFile(String adress) {
        try(BufferedReader reader = new BufferedReader(new FileReader(new File(adress), Charset.forName("UTF-8")))){
            String line = null;
            do{
                line = reader.readLine();
                if(line != null && !line.contains("//")){
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

    private void model(int number){
        try{
            String lsa = activeLSA.get(number - 1);
            //Добавить очистку ошибок
            errorObserver.clearLexicalErrors();
            LexelAnalyzer.lex(lsa);
            if(errorObserver.getLexicalErrors().size() == 0){
                //TODO моделирование
                System.out.println("НОРМАС");
                System.out.println(answear.getAnswearText());
            }
            else{
                for(String err : errorObserver.getLexicalErrors()){
                    System.err.println(err);
                }
            }
        } catch (Exception e){
            System.err.println("Не найдено такого элемента!");
        }
    }

    private void showLSA() {
        if(activeLSA.size() == 0) {
            System.out.println("Нет активных ЛСА");
            return;
        }
        int count = 1;
        for(String lsa : activeLSA) {
            System.out.println(String.format("%d: %s", count, lsa));
            count++;
        }
        int input = -1;
        do{
            System.out.println("Выберете ЛСА для построения модели или 0 для выхода: ");
            System.out.print(">> ");
            try{
                input = scanner.nextInt();
                if(input > 0){
                    model(input);
                }
            } catch (Exception e) {
                System.err.println("Введенная строка не является числом!");
                input = 0;
            }
        } while(input != 0);
    }

    private void getFromCosole() {
        System.out.println("Введите ЛСА: ");
        System.out.println(">Введите + чтобы добавить ЛСА<");
        System.out.println(">Введите - чтобы отменить последнее изменение<");
        System.out.println(">Введите -- чтобы очистить строку<");
        System.out.println(">Введите ! чтобы вернуться в меню<");
        String output = "YН";
        Deque<String> stack = new ArrayDeque<>();
        String input = "";
        do{
            System.out.println("Текущая строка: " + output);
            System.out.print(">> ");
            input = scanner.next();
            if(input == null){
                return;
            }
            if(input.equals("+")){
                break;
            } else if (input.equals("-")) {
                if(!stack.isEmpty()){
                    output = output.replace(stack.pop(), "");
                }
            } else if (input.equals("--")) {
                stack = new ArrayDeque<>();
                output = "YН";
            } else if (input.equals("!")) {
                break;
            } else {
                output += input;
                stack.push(input);
                //Добавить очистку ошибок
                errorObserver.clearLexicalErrors();
                LexelAnalyzer.lex(output);
            }
            if(errorObserver.getLexicalErrors().size() > 0){
                for(String err : errorObserver.getLexicalErrors()){
                    System.err.println(err);
                }
            }
        }while (input != "+");
        if(!input.equals("!")) {
            System.out.println(String.format("Строка '%s' добавлена!", output));
            activeLSA.add(output);
        }
    }

    public void show(){
        int input = -1;
        do{
            System.out.println("\nЧто вы хотите сделать?");
            System.out.println("1. Загрузить ЛСА из файла");
            System.out.println("2. Ввести ЛСА с консоли");
            System.out.println("3. Вывести загруженные ЛСА");
            System.out.println("0. Выход");
            System.out.print(">> ");
            try{
                input = scanner.nextInt();
            } catch (Exception ex){
                System.err.println("Введенная строка не является числом!");
                break;
            }
            switch(input){
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
