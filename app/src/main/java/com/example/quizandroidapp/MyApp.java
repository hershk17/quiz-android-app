package com.example.quizandroidapp;

import android.app.Application;

import androidx.fragment.app.FragmentManager;

public class MyApp extends Application {
    QuestionBankManager qm = new QuestionBankManager();
    FileStorageManager fsm = new FileStorageManager();
}
