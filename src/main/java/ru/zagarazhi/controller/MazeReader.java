package ru.zagarazhi.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MazeReader {
    private ErrorObserver errorObserver = ErrorObserver.getInstance();
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private boolean[][] field;

    public MazeReader() {
        this.endX = -100;
        this.endY = -100;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getEndX() {
        return endX;
    }
    
    public int getEndY() {
        return endY;
    }

    public boolean[][] getField() {
        return field;
    }

    private int getMaxX(List<String> ans){
        int maxLength = -1;
        for(String line : ans){
            if(line.length() > maxLength){
                maxLength = line.length();
            }
        }
        return maxLength;
    }

    private void generateField(List<String> ans){
        this.field = new boolean[ans.size()][getMaxX(ans)];
        for(int i = 0; i < this.field.length; i++){
            for(int j = 0; j < this.field[i].length; j++){
                this.field[i][j] = true;
            }
        }
        int count = 0;
        char c;
        boolean wasStart = false;
        for(String temp : ans){
            for(int i = 0; i < temp.length(); i++){
                c = Character.toLowerCase(temp.charAt(i));
                if(c == 's'){
                    startX = i;
                    startY = count;
                    wasStart = true;
                }
                if(c == 'e'){
                    endX = i;
                    endY = count;
                }
                this.field[count][i] = !(c == '0' || c == 's' || c == 'e');
            }
            count++;
        }
        if(!wasStart) {
            errorObserver.pushError("Не найдена точка старта");
        }
    }

    public void readMaze(String fileName) {
        List<String> ans = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(fileName), Charset.forName("UTF-8")))) {
            String line = null;
            do {
                line = reader.readLine();
                if(line != null){
                    ans.add(line.replace(" ", ""));
                }
            } while (line != null);
            reader.close();
        } catch (FileNotFoundException ex) {
            errorObserver.pushError("Файл не найден");
        } catch (IOException e) {
            errorObserver.pushError("Ошибка чтения");
        } catch (Exception exception) {
            errorObserver.pushError("Ошибка");
        }
        generateField(ans);
    }
}
