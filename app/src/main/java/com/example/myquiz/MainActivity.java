package com.example.myquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.myquiz.views.CategoryActivity;

public class MainActivity extends AppCompatActivity {
    private Button playButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playButton = findViewById(R.id.play);
        playButton.setOnClickListener(view -> {
            playButton();
        });
    }

    private void playButton() {
        Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
        startActivity(intent);

    }
}