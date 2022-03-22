package com.example.quizandroidapp;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuestionBankManager {
    private final List<Question> bank = new ArrayList<>();
    private final List<Integer> colours = new ArrayList<>();

    private List<Question> activeQuestions = new ArrayList<>();

    public QuestionBankManager() {
        bank.add(new Question(R.string.question1,Boolean.FALSE));
        bank.add(new Question(R.string.question2,Boolean.TRUE));
        bank.add(new Question(R.string.question3,Boolean.FALSE));
        bank.add(new Question(R.string.question4,Boolean.TRUE));
        bank.add(new Question(R.string.question5,Boolean.FALSE));
        bank.add(new Question(R.string.question6,Boolean.TRUE));
        bank.add(new Question(R.string.question7,Boolean.TRUE));
        bank.add(new Question(R.string.question8,Boolean.FALSE));

        colours.add(R.color.blue);
        colours.add(R.color.yellow);
        colours.add(R.color.red);
        colours.add(R.color.olive);
        colours.add(R.color.purple);
        colours.add(R.color.teal);
        colours.add(R.color.green);
        colours.add(R.color.gray);
    }

    public void setNewQuestions(int qty) {
        Collections.shuffle(bank);
        Collections.shuffle(colours);
        for(int i = 0; i < qty; i++) {
            bank.get(i).setColour(colours.get(i));
        }
        activeQuestions = bank.subList(0,qty);
    }

    public List<Question> getActiveQuestions() {
        return activeQuestions;
    }
}
