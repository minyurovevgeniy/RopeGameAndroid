package ru.minyurovevgeniy.rope;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class Activity_win extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        try
        {
            Intent intent = getIntent();
            String winner = intent.getStringExtra("result");
            TextView winnerText = findViewById(R.id.winner);
            if (winner!=null)
            {
                winnerText.setText(winner + ", это победа!");
            }
        }
        catch (Exception ex)
        {

        }

        TextView play_again = findViewById(R.id.play_again);
        play_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Activity_win.this, ActivityStart.class);
                startActivity(intent);
            }
        });
    }
}