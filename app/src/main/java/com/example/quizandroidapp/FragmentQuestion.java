package com.example.quizandroidapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentQuestion extends Fragment {

    private static final String ARG_PARAM1 = "question";
    private static final String ARG_PARAM2 = "colour";

    private String questionParam;
    private Integer colourParam;

    public FragmentQuestion() {}

    public static FragmentQuestion newInstance(String questionParam, Integer colourParam) {
        FragmentQuestion fragment = new FragmentQuestion();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, questionParam);
        args.putInt(ARG_PARAM2, colourParam);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            questionParam = getArguments().getString(ARG_PARAM1);
            colourParam = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_question, container, false);

        TextView questionView = v.findViewById(R.id.question_box);
        questionView.setText(questionParam);
        questionView.setBackgroundResource(colourParam);

        return v;
    }

}