package com.example.gameapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    private Button playAgainButton;
    private TextView scoreTextView;
    private String score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        playAgainButton = (Button)findViewById(R.id.playAgainButton);
        scoreTextView = (TextView)findViewById(R.id.score_textView);

        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOverActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        score = getIntent().getExtras().get("score").toString();
        scoreTextView.setText("Score: " + score);



    }
}
