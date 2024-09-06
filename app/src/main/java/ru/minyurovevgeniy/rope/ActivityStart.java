package ru.minyurovevgeniy.rope;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class ActivityStart extends AppCompatActivity {

    String MY_PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String playerOneName = prefs.getString("player_1", "");//"No name defined" is the default value.
        String playerTwoName = prefs.getString("player_2", "");//"No name defined" is the default value.
        int difference = prefs.getInt("difference", 0); //0 is the default value.

        EditText playerOne = findViewById(R.id.first_player_name);
        playerOne.setText(playerOneName);


        EditText playerTwo = findViewById(R.id.second_player_name);
        playerTwo.setText(playerTwoName);

        EditText maxDifference = findViewById(R.id.max_difference);
        maxDifference.setText(Integer.toString(difference));




        Button launchGame= findViewById(R.id.launch_game);
        launchGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                EditText firstPlayerName = findViewById(R.id.first_player_name);
                String playerOneName = firstPlayerName.getText().toString();

                EditText secondPlayerName = findViewById(R.id.second_player_name);
                String playerTwoName = secondPlayerName.getText().toString();

                EditText difference = findViewById(R.id.max_difference);

                int scoreCount = 0;

                String numberAsString = difference.getText().toString();
                if (numberAsString.length()<=0)
                {
                    scoreCount=0;
                }
                else
                {
                    scoreCount=Integer.parseInt(numberAsString);
                }

                if (playerOneName.length()<=0 || playerOneName.replaceAll("\\s","").equals(""))
                {
                    Toast.makeText(ActivityStart.this,"Введите имя игрока №1",Toast.LENGTH_LONG).show();
                }
                else
                if (playerTwoName.length()<=0 || playerTwoName.replaceAll("\\s","").equals(""))
                {
                    Toast.makeText(ActivityStart.this,"Введите имя игрока №2",Toast.LENGTH_LONG).show();
                }
                else
                if(scoreCount<=0)
                {
                    Toast.makeText(ActivityStart.this,"Введите корректную разницу в счете",Toast.LENGTH_LONG).show();
                }
                else
                {
                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putString("player_1", playerOneName.trim());
                    editor.putString("player_2", playerTwoName.trim());
                    editor.putInt("difference", scoreCount);
                    editor.apply();
                    RadioGroup radioButtonGroup = findViewById(R.id.game_mode);
                    int radioButtonID = radioButtonGroup.getCheckedRadioButtonId();
                    RadioButton radioButton = findViewById(radioButtonID);
                    String mode = radioButton.getText().toString();

                    Intent intent = new Intent(ActivityStart.this, Activity_pvp.class);
                    intent.putExtra("player_1", playerOneName);
                    intent.putExtra("player_2", playerTwoName);
                    intent.putExtra("score", scoreCount);
                    intent.putExtra("mode", mode);
                    startActivity(intent);
                }

            }
        });

    }
}