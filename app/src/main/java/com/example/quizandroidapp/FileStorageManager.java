package com.example.quizandroidapp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FileStorageManager {
    static String fileName = "Scores.txt";
    FileOutputStream fos;
    FileInputStream fis;

    private String[] readFile(Activity context){
        String[] data = new String[0];
        try{
            fis = context.openFileInput(fileName);
            StringBuffer stringBuffer = new StringBuffer();
            InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
            int read = 0;
            while ((read = inputStreamReader.read())!= -1){
                stringBuffer.append((char)read);
            }
            data = stringBuffer.toString().split(",");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        if(data.length == 0) {
            return new String[]{"0","0","8"};
        }
        return data;
    }

    public String getScoreOnFile(Activity context){
        String[] data = readFile(context);
        return data[0] + " / " + data[1];
    }

    public Integer getMaxQuestionsOnFile(Activity context){
        String[] data = readFile(context);
        return Integer.parseInt(data[2]);
    }

    private void updateFile(Activity context, int correct, int total, int max){
        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write((correct+","+total+","+max).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateScoreOnFile(Activity context, int correct, int total){
        String[] data = readFile(context);
        updateFile(context, correct + Integer.parseInt(data[0]), total + Integer.parseInt(data[1]), Integer.parseInt(data[2]));
    }

    public void updateMaxQuestionsOnFile(Activity context, int max){
        String[] data = readFile(context);
        updateFile(context, Integer.parseInt(data[0]), Integer.parseInt(data[1]), max);
    }

    public void resetScoresOnFile(Activity context){
        String[] data = readFile(context);
        updateFile(context, 0, 0, Integer.parseInt(data[2]));
    }

}
