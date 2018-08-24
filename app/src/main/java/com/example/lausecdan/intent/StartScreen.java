package com.example.lausecdan.intent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class StartScreen extends AppCompatActivity {
    private static final int REQUEST_CODE_QUIZ=1;
    public static final String EXTRA_DIFFICULTY = "extraDifficulty";
    private Button mbtnStart;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "keyHighscore";

    private TextView textViewHighscore;
    private Spinner spinnerDifficulty;
    private int highscore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        mbtnStart=findViewById(R.id.button_start_quiz);

        textViewHighscore = findViewById(R.id.text_view_highscore);
        spinnerDifficulty = findViewById(R.id.spinner_difficulty);

//        String[] difficultyLevels = Question.getAllDifficultyLevels();
//
//        ArrayAdapter<String> adapterDifficulty = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, difficultyLevels);
//        adapterDifficulty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerDifficulty.setAdapter(adapterDifficulty);

        loadHighscore();

        loadHighscore();
        mbtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartQuizz();
            }
        });
    }

    private void StartQuizz() {
//        String difficulty = spinnerDifficulty.getSelectedItem().toString();

        Intent intent = new Intent(StartScreen.this, Quiz.class);
//        intent.putExtra(EXTRA_DIFFICULTY, difficulty);
        startActivityForResult(intent, REQUEST_CODE_QUIZ);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_QUIZ) {
            if (resultCode == RESULT_OK) {
                int score = data.getIntExtra(Quiz.EXTRA_SCORE, 0);
                if (score > highscore) {
                    updateHighscore(score);
                }
            }
        }
    }
    private void loadHighscore() {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        highscore = prefs.getInt(KEY_HIGHSCORE, 0);
        textViewHighscore.setText("Điểm cao nhất: " + highscore);
    }

    private void updateHighscore(int highscoreNew) {
        highscore = highscoreNew;
        textViewHighscore.setText("Điểm cao nhất: " + highscore);

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_HIGHSCORE, highscore);
        editor.apply();
    }
}