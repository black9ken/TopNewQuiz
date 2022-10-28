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
            mRemainingQuestionCount = 2;
        }

    }

    private QuestionBank generateQuestions(){
        Question question1 = new Question(
                "Who is the creator of Android?",
                Arrays.asList(
                        "Andy Rubin",
                        "Steve Wozniak",
                        "Jake Wharton",
                        "Paul Smith"
                ),
                0
        );

        Question question2 = new Question(
                "When did the first man land on the moon?",
                Arrays.asList(
                        "1958",
                        "1962",
                        "1967",
                        "1969"
                ),
                3
        );

        Question question3 = new Question(
                "What is the house number of The Simpsons?",
                Arrays.asList(
                        "42",
                        "101",
                        "666",
                        "742"
                ),
                3
        );

        return new QuestionBank(Arrays.asList(question1, question2, question3));
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