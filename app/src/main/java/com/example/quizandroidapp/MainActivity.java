package com.example.quizandroidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, FragmentDialog.DialogClickListener {

    private AlertDialog.Builder builder;

    private final FragmentManager fm = getSupportFragmentManager();
    private QuestionBankManager qm;
    private FileStorageManager fsm;

    private int activeQuestionIdx = 0;
    private int maxQuestions;
    private int correct = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        builder = new AlertDialog.Builder(this);
        qm = ((MyApp)getApplication()).qm;
        fsm = ((MyApp)getApplication()).fsm;

        if (savedInstanceState != null) {
            activeQuestionIdx = savedInstanceState.getInt("activeQuestionIdx");
            maxQuestions = savedInstanceState.getInt("maxQuestions");
            correct = savedInstanceState.getInt("correct");
        }
        else {
            maxQuestions = fsm.getMaxQuestionsOnFile(this);
            qm.setNewQuestions(maxQuestions);
        }

        findViewById(R.id.button_true).setOnClickListener(this);
        findViewById(R.id.button_false).setOnClickListener(this);

        ((ProgressBar)findViewById(R.id.progressbar)).setMax(maxQuestions);
        ((ProgressBar)findViewById(R.id.progressbar)).setProgress(correct);

        updateQuestionFragment();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("activeQuestionIdx",activeQuestionIdx);
        outState.putInt("maxQuestions",maxQuestions);
        outState.putInt("correct",correct);
    }

    @Override
    public void onClick(View view) {
        ((ProgressBar)findViewById(R.id.progressbar)).setProgress(activeQuestionIdx + 1);

        boolean choice = view.getId() == R.id.button_true;
        if(choice == qm.getActiveQuestions().get(activeQuestionIdx).getAnswer()) {
            correct++;
            Toast.makeText(this,"Correct!",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this,"Incorrect",Toast.LENGTH_SHORT).show();
        }
        activeQuestionIdx++;

        if(activeQuestionIdx == maxQuestions) {
            builder.setTitle(getString(R.string.result_title))
                    .setMessage(getString(R.string.result_score_a) + " " + correct + " " + getString(R.string.result_score_b) + " " + maxQuestions)
                    .setCancelable(true)
                    .setPositiveButton(getString(R.string.save), (dialog, id) -> {
                        fsm.updateScoreOnFile(this, correct, maxQuestions);
                    })
                    .setNegativeButton(getString(R.string.cancel), null)
                    .setOnDismissListener(dialog -> resetQuiz())
                    .show();
        }
        else {
            updateQuestionFragment();
        }
    }

    public void updateQuestionFragment() {
        Question q = qm.getActiveQuestions().get(activeQuestionIdx);
        FragmentQuestion fragmentQuestion = FragmentQuestion.newInstance(getString(q.getDescription()), q.getColour());
        fm.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fm.beginTransaction().replace(R.id.question_container, fragmentQuestion).commit();
    }

    public void resetQuiz() {
        maxQuestions = fsm.getMaxQuestionsOnFile(this);
        qm.setNewQuestions(maxQuestions);
        activeQuestionIdx = 0;
        correct = 0;
        ((ProgressBar)findViewById(R.id.progressbar)).setMax(maxQuestions);
        ((ProgressBar)findViewById(R.id.progressbar)).setProgress(0);
        updateQuestionFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()) {
            case R.id.cumulative_score:
                builder.setTitle(getString(R.string.cumulative_score_title))
                        .setMessage(getString(R.string.summary) + " " + fsm.getScoreOnFile(this))
                        .setCancelable(true)
                        .setPositiveButton("OK", null)
                        .setNegativeButton(null,null)
                        .show();
                break;
            case R.id.update_max:
                FragmentDialog dialog = FragmentDialog.newInstance(getString(R.string.update_prompt) + " " + maxQuestions);
                dialog.show(fm,FragmentDialog.Tag);
                dialog.listener = this;
                break;
            case R.id.reset_score:
                fsm.resetScoresOnFile(this);
                break;
        }
        return true;
    }

    @Override
    public void dialogListenerSave(Integer input) {
        if(input < maxQuestions) {
            fsm.updateMaxQuestionsOnFile(this, input);
            resetQuiz();
        }
        else {
            builder.setTitle(getString(R.string.error))
                    .setMessage(getString(R.string.error_prompt_text) + " " + maxQuestions)
                    .setCancelable(true)
                    .setPositiveButton(getString(R.string.ok), null)
                    .setNegativeButton(null,null)
                    .show();
        }
    }
}