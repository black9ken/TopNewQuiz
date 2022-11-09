package com.example.topnewquiz.controller;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.topnewquiz.R;
import com.example.topnewquiz.model.Question;
import com.example.topnewquiz.model.QuestionBank;

import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mQuestion;
    private Button mReponse1;
    private Button mReponse2;
    private Button mReponse3;
    private Button mReponse4;
    private QuestionBank mQuestionBank = generateQuestions();
    private Question mCurrentQuestion;
    private int mRemainingQuestionCount;
    private int mScore;
    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";
    private boolean mEnableTouchEvents;
    public static final String BUNDLE_STATE_SCORE = "BUNDLE_STATE_SCORE";
    public static final String BUNDLE_STATE_QUESTION = "BUNDLE_STATE_QUESTION";

    String msg = "kennedy";

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(BUNDLE_STATE_SCORE, mScore);
        outState.putInt(BUNDLE_STATE_QUESTION, mRemainingQuestionCount);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        mQuestion = findViewById(R.id.game_activity_textview_question);
        mReponse1 =findViewById(R.id.game_activity_button_1);
        mReponse2 =findViewById(R.id.game_activity_button_2);
        mReponse3 =findViewById(R.id.game_activity_button_3);
        mReponse4 =findViewById(R.id.game_activity_button_4);

        // Use the same listener for the four buttons.
// The view id value will be used to distinguish the button triggered
        mReponse1.setOnClickListener(this);
        mReponse2.setOnClickListener(this);
        mReponse3.setOnClickListener(this);
        mReponse4.setOnClickListener(this);

        mEnableTouchEvents = true;

        mCurrentQuestion = mQuestionBank.getCurrentQuestion();


        displayQuestion(mCurrentQuestion);

        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mRemainingQuestionCount = savedInstanceState.getInt(BUNDLE_STATE_QUESTION);
        } else {
            mScore = 0;
            mRemainingQuestionCount = 10;
        }

    }

    private QuestionBank generateQuestions(){
        Question question1 = new Question(
                "Au début de la série, quelle famille est alliée aux Stark",
                Arrays.asList(
                        "BARATHEON",
                        "LANNISTER",
                        "TYRELL",
                        "GREYJOY"
                ),
                0
        );

        Question question2 = new Question(
                "Qui est le roi qui a fabriqué le trône de fer",
                Arrays.asList(
                        "VISERYS TARGARYEN",
                        "AEMON TARGARYEN",
                        "AEGON TARGARYEN",
                        "AERYS TARGARYEN"
                ),
                2
        );

        Question question3 = new Question(
                "Le combat entre Birnne de Torth et le Limier se déroule dans la saison...",
                Arrays.asList(
                        "3",
                        "4",
                        "5",
                        "6"
                ),
                1
        );

        Question question4 = new Question(
                "Quel est le personnage qui a fait le plus d'apparition dans la série ?",
                Arrays.asList(
                        "JON SNOW",
                        "DAENERYS",
                        "CERCEI",
                        "TYRION"
                ),
                3
        );

        Question question5 = new Question(
                "Viserys Targaryen reçoit une couronne d'or par le Khal Drogon dans la...",
                Arrays.asList(
                        "saison 1 épisode 9",
                        "saison 1 épisode 6",
                        "saison 2 épisode 1",
                        "saison 2 épisode 2"
                ),
                1
        );

        Question question6 = new Question(
                "Lequel de ces personnages est surnommé le \"Silure\"",
                Arrays.asList(
                        "stannis baratheon",
                        "brynden tully",
                        "jorah mormont",
                        "petyr baelish"
                ),
                1
        );

        Question question7 = new Question(
                "Comment s'appelle l'auteur des livres \"Game of Thrones\"",
                Arrays.asList(
                        "Michael hirst",
                        "Walter white",
                        "Vince gilligan",
                        "George r.r. martin"
                ),
                3
        );

        Question question8 = new Question(
                "Lequel de ces personnages a dit \"Tous les Hommes doivent mourir. Mais nous ne sommes pas des Hommes.\"",
                Arrays.asList(
                        "cercei lannister",
                        "arya stark",
                        "olenna tyrell",
                        "daenerys targaryen"
                ),
                3
        );

        Question question9 = new Question(
                "Lequel de ces personnages a dit \"De quel droit le loup juge t-il le lion\"",
                Arrays.asList(
                        "jaime lannister",
                        "tywin lannister",
                        "cercei lannister",
                        "joffrey baratheon"
                ),
                0
        );

        Question question10 = new Question(
                "Quel est le nom du premier spin off de Game of Thrones",
                Arrays.asList(
                        "House of fire",
                        "Blood and fire",
                        "House of the dragon",
                        "Wild winter"
                ),
                2
        );


        return new QuestionBank(Arrays.asList(question1, question2, question3, question3, question4, question5, question6, question7, question8, question9, question10));
    }

    private void displayQuestion(final Question question) {
        mQuestion.setText(question.getQuestion());
        mReponse1.setText(question.getChoiceList().get(0));
        mReponse2.setText(question.getChoiceList().get(1));
        mReponse3.setText(question.getChoiceList().get(2));
        mReponse4.setText(question.getChoiceList().get(3));
    }

    @Override
    public void onClick(View view) {
        int index;

        if (view == mReponse1) {
            index = 0;
        } else if (view == mReponse2) {
            index = 1;
        } else if (view == mReponse3) {
            index = 2;
        } else if (view == mReponse4) {
            index = 3;
        } else {
            throw new IllegalStateException("Unknown clicked view : " + view);
        }

        if (index == mQuestionBank.getCurrentQuestion().getAnswerIndex()){
            Toast.makeText(this,"Correct!",Toast.LENGTH_SHORT).show();
            mScore++;
        }else{
            Toast.makeText(this,"Incorrect!",Toast.LENGTH_SHORT).show();
        }

        mEnableTouchEvents = false;

        //Permet de rajouter un temps d'exécution après une question
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRemainingQuestionCount--;

                if(mRemainingQuestionCount>0){
                    mCurrentQuestion = mQuestionBank.getNextQuestion();
                    displayQuestion(mCurrentQuestion);
                } else {
                    endGame();
                }
                mEnableTouchEvents = true;
            }
        }, 2000);

    }

    private void endGame(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Well done!")
                .setMessage("Your score is " + mScore)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Intent intent = new Intent();
                        //intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                        // setResult(RESULT_OK, intent);
                        // finish();
                        Intent intent = new Intent();
                        intent.putExtra("result", mScore);
                        setResult(78, intent);
                        GameActivity.super.onBackPressed();
                    }
                })
                .create()
                .show();


    }
}