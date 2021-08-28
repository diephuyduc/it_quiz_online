package com.example.myquiz.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.myquiz.R;

public class ScoreActivity extends AppCompatActivity {
    private TextView textView;
    private LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        textView = findViewById(R.id.tv_marks);
        lottieAnimationView = findViewById(R.id.lottie_score);
        Intent intent = getIntent();

        String marks = intent.getStringExtra("marks");
        double mark = Double.valueOf(marks);
        if(mark<50.0){
            lottieAnimationView.setAnimation(R.raw.lose);
        }
        else{
            lottieAnimationView.setAnimation(R.raw.win);
        }


        textView.setText(marks);
    }
}