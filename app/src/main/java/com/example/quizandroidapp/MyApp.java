package com.example.quizandroidapp;

import android.app.Application;

public class MyApp extends Application {
    QuestionBankManager qm = new QuestionBankManager();
    FileStorageManager fsm = new FileStorageManager();
}
