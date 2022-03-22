package com.example.quizandroidapp;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FragmentDialog extends DialogFragment {
    public interface DialogClickListener{
        void dialogListenerSave(Integer input);
    }

    public DialogClickListener listener;

    public static final String Tag = "Update Max Questions";
    private static final String ARG_PARAM1 = "text";
    private String textParam;

    public static FragmentDialog newInstance(String currentMaxParam) {
        FragmentDialog fragment = new FragmentDialog();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, currentMaxParam);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            textParam = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog, container, false);
        TextView textView = v.findViewById(R.id.dialog_text);
        textView.setText(textParam);
        EditText inputText = v.findViewById(R.id.text_input);
        v.findViewById(R.id.button_save).setOnClickListener(view -> {
            listener.dialogListenerSave(Integer.parseInt(inputText.getText().toString()));
            dismiss();
        });
        v.findViewById(R.id.button_cancel).setOnClickListener(view -> {
            dismiss();
        });
        return v;
    }
}