package com.example.topnewquiz.controller;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.topnewquiz.R;
import com.example.topnewquiz.model.User;

public class MainActivity extends AppCompatActivity {

    private TextView mGreetingTextView;
    private EditText mNameEditText;
    private Button mPlayButton;
    private User mUser;
    private static final int GAME_ACTIVITY_REQUEST_CODE = 42;
    private static final String SHARED_PREF_USER_INFO = "SHARED_PREF_USER_INFO";
    private static final String SHARED_PREF_USER_INFO_NAME = "SHARED_PREF_USER_INFO_NAME";
    private static final String SHARED_PREF_SCORE_INFO = "SHARED_PREF_SCORE_INFO";
    private static final String SHARED_PREF_SCORE_INFO_NUMBER = "SHARED_PREF_SCORE_INFO_NUMBER";
    private static final String TAG = "MainActivity";
    public int mScore;
    private String mRelou;
    private String firstName ;

    String msg = "kennedy";


    ActivityResultLauncher<Intent> activityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.d(TAG, "onActivityResult: ");

                    if(result.getResultCode() == 78){
                        Intent intent= result.getData();

                        if(intent!= null){
                            mScore =intent.getIntExtra("result", 99);
                            getSharedPreferences(SHARED_PREF_SCORE_INFO, MODE_PRIVATE)
                                    .edit()
                                    .putInt(SHARED_PREF_SCORE_INFO_NUMBER, mScore)
                                    .apply();

                            mGreetingTextView.setText(mNameEditText.getText()+ " votre dernier score est "+ mScore);


                        }
                    }
                }
            }
    );

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode) {
            // Fetch the score from the Intent
            int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 9);


        }

    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mGreetingTextView = findViewById(R.id.main_textview_greeting);
        mNameEditText = findViewById(R.id.main_edittext_name);
        mPlayButton = findViewById(R.id.main_button_play);

        if (mNameEditText.length() != 0)
        {
            mPlayButton.setEnabled(true);
        }else {
            mPlayButton.setEnabled(false);
        }

        firstName = getSharedPreferences(SHARED_PREF_USER_INFO,MODE_PRIVATE).getString(SHARED_PREF_USER_INFO_NAME, null);
        mScore =  getSharedPreferences(SHARED_PREF_SCORE_INFO, MODE_PRIVATE).getInt(SHARED_PREF_SCORE_INFO_NUMBER, 0);

        if (firstName != null){
            mGreetingTextView.setText("bonjour "+firstName+" et voila le resultat "+mScore);
            mNameEditText.setText(firstName);
        }

        mNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // This is where we'll check the user input
                mPlayButton.setEnabled(!s.toString().isEmpty());
            }
        });
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE)
                        .edit()
                        .putString(SHARED_PREF_USER_INFO_NAME, mNameEditText.getText().toString())
                        .apply();

                // The user just clicked
                //mUser.setFirstName(mNameEditText.getText().toString());
                //Intent gameActivityIntent = new Intent(MainActivity.this, GameActivity.class);
                //startActivity(gameActivityIntent);
                //startActivityForResult(gameActivityIntent, GAME_ACTIVITY_REQUEST_CODE);

                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                activityLauncher.launch(intent);

            }
        });


    }


}