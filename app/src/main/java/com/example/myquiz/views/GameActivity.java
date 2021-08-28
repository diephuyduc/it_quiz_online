package com.example.myquiz.views;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.myquiz.R;
import com.example.myquiz.models.Category;
import com.example.myquiz.models.Question;
import com.example.myquiz.models.SetClass;
import com.example.myquiz.networks.GetDataFirebase;
import com.example.myquiz.ultis.NumberFormats;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView index, timer, question;
    private Button optionA, optionB, optionC, optionD;
    List<Question> questionList = new ArrayList<>();
    private ImageView img;
    int qCount, qPresent;
    int score = 0;
    double mark = 0;
    CountDownTimer countDownTimer;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        init();
    }

    private void init() {
        index = findViewById(R.id.index);
        timer = findViewById(R.id.timer);
        lottieAnimationView = findViewById(R.id.loading);
        question = findViewById(R.id.question);
        optionA = findViewById(R.id.option_A);
        optionB = findViewById(R.id.option_B);
        optionC = findViewById(R.id.option_C);
        optionD = findViewById(R.id.option_D);
        img = findViewById(R.id.img_question);
        optionA.setOnClickListener(this);
        optionB.setOnClickListener(this);
        optionC.setOnClickListener(this);
        optionD.setOnClickListener(this);
        qPresent = 0;

        getQuestion();



    }

    private void getQuestion() {
        Bundle bundle = getIntent().getBundleExtra("item_set");
        Category categoryFather = (Category) bundle.get("cat_id");
        SetClass setFather = (SetClass) bundle.get("item_set");

        GetDataFirebase.getFirebaseFirestoreInstance().collection("QUIZ").document(categoryFather.id).collection(setFather.id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot documentSnapshot = task.getResult();
                    for (QueryDocumentSnapshot doc : documentSnapshot
                    ) {
                        questionList.add(new Question(doc.getData().get("q").toString(),
                                doc.getData().get("iq").toString(),
                                doc.getData().get("a").toString(),
                                doc.getData().get("b").toString(),
                                doc.getData().get("c").toString(),
                                doc.getData().get("d").toString(),
                                doc.getData().get("an").toString()

                                ));

                    }
                    lottieAnimationView.setVisibility(View.GONE);
                    qCount = questionList.size();
                    setQuestion();
                }
            }
        });

    }

    @Override
    protected void onPause() {
        countDownTimer.cancel();
        super.onPause();
    }

    private boolean checkNull(String a){
        if(a ==null || a.equals("")){
            return false;
        }
        return true;
    }
    private void setQuestion() {
        defaultColor();
        timer.setText(String.valueOf(10));
        question.setText(questionList.get(qPresent).getQuestion().toString().trim());
        if(!questionList.get(qPresent).getImageUrlQuestion().equals("")){
            Glide.with(this).load(questionList.get(qPresent).getImageUrlQuestion()).into(img);
        }
        else{
            img.setVisibility(View.GONE);
        }
        if(checkNull(questionList.get(qPresent).getOptionA().trim().toString())){
            optionA.setText(questionList.get(qPresent).getOptionA().trim().toString());
        }
        else{
            optionA.setVisibility(View.GONE);
        }
        if(checkNull(questionList.get(qPresent).getOptionB().trim().toString())){
            optionB.setText(questionList.get(qPresent).getOptionB().trim().toString());
        }
        else{
            optionB.setVisibility(View.GONE);
        }
        if(checkNull(questionList.get(qPresent).getOptionC().trim().toString())){
            optionC.setText(questionList.get(qPresent).getOptionC().trim().toString());
        }
        else{
            optionC.setVisibility(View.GONE);
        }
        if(checkNull(questionList.get(qPresent).getOptionD().trim().toString())){
            optionD.setText(questionList.get(qPresent).getOptionD().trim().toString());
        }
        else{
            optionD.setVisibility(View.GONE);
        }

//        optionB.setText(questionList.get(qPresent).getOptionB().trim().toString());
//        optionC.setText(questionList.get(qPresent).getOptionC().trim().toString());
//        optionD.setText(questionList.get(qPresent).getOptionD().trim().toString());
        index.setText(String.valueOf(qPresent + 1) + "/" + String.valueOf(qCount));

        startTimer();
    }

    private void defaultColor() {
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.bg_white_corner_16);
        optionA.setBackground(drawable);
        optionB.setBackground(drawable);
        optionC.setBackground(drawable);
        optionD.setBackground(drawable);
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long l) {
                timer.setText(String.valueOf(l / 1000) + "s");
            }

            @Override
            public void onFinish() {
                nextQuestion();
            }
        };
        countDownTimer.start();
    }

    private void nextQuestion() {

        if (qPresent < qCount - 1) {
            qPresent++;
            setQuestion();

        } else {
            // finish game
            gotoScore();
        }

    }

    private void gotoScore() {
        Intent intent = new Intent(GameActivity.this, ScoreActivity.class);

        String marks = String.valueOf(NumberFormats.double2Decimal((score * 1.0 / qCount) * 100));
        intent.putExtra("marks", marks);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        countDownTimer.cancel();
        String selectOption;
        switch (view.getId()) {
            case R.id.option_A:
                selectOption = optionA.getText().toString().trim();
                break;
            case R.id.option_B:
                selectOption = optionB.getText().toString().trim();
                break;
            case R.id.option_C:
                selectOption = optionC.getText().toString().trim();
                break;
            case R.id.option_D:
                selectOption = optionD.getText().toString().trim();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.bg_green_corner_16);
        view.setBackground(drawable);

        checkAnswer(selectOption);
    }

    private void checkAnswer(String selectOption) {
        if (selectOption.equals(questionList.get(qPresent).getCorrectAnswer())) {
            score++;

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    nextQuestion();
                }
            }, 1000);

        } else {
            showCorrectAnswer();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    gotoScore();
                }
            }, 4000);

        }
    }

    private void showCorrectAnswer() {
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.bg_corner_red_16);
        if (optionA.getText().toString().trim().equals(questionList.get(qPresent).getCorrectAnswer())) {
            optionA.setBackground(drawable);
        } else if (optionB.getText().toString().trim().equals(questionList.get(qPresent).getCorrectAnswer())) {
            optionB.setBackground(drawable);
        } else if (optionC.getText().toString().trim().equals(questionList.get(qPresent).getCorrectAnswer())) {
            optionC.setBackground(drawable);
        } else {
            optionD.setBackground(drawable);
        }

    }
}