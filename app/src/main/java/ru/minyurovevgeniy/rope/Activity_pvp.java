package ru.minyurovevgeniy.rope;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_pvp extends AppCompatActivity {

    int width;
    int height;

    int ropeWidth;
    int ropeHeight;

    int marginLeft;
    int marginRight;

    int marginTop;
    int marginBottom;
    int marginBottomAndTop;


    int planeWidth;
    int planeHeight;

    int marginStep=20;

    int halfHeight=0;
    int halfWidth=0;

    ImageView rope;
    ImageView separator;

    TextView gameState;

    String playerOneName;
    String playerTwoName;

    byte playerOneVictoryCount;
    byte playerTwoVictoryCount;
    int score;
    String mode;
    TextView playerOneState;
    TextView playerTwoState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pvp);

        final LinearLayout layout = (LinearLayout)findViewById(R.id.field);

        layout.post(new Runnable() {
            @Override
            public void run() {



                Intent intent = getIntent();

                score = intent.getIntExtra("score",1);
                mode = intent.getStringExtra("mode");

                playerOneName= intent.getStringExtra("player_1");
                playerTwoName = intent.getStringExtra("player_2");

                playerOneState = findViewById(R.id.player_one_state);
                playerOneState.setText(playerOneName);

                playerTwoState = findViewById(R.id.player_two_state);
                playerTwoState.setText(playerTwoName);

                playerOneVictoryCount=0;
                playerTwoVictoryCount=0;

                width = layout.getMeasuredWidth();
                height = layout.getMeasuredHeight();

                gameState = findViewById(R.id.game_state);

                FrameLayout.LayoutParams paramsState = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                paramsState.setMargins(-10,0,0,0);
                paramsState.gravity = Gravity.CENTER_VERTICAL | Gravity.LEFT;
                gameState.setLayoutParams(paramsState);


                Drawable planeImage = getResources().getDrawable(R.mipmap.plane_foreground);

                planeWidth=planeImage.getIntrinsicWidth();
                planeHeight=planeImage.getIntrinsicHeight();

                Drawable ropeImage = getResources().getDrawable(R.mipmap.rope_multiple_foreground);

                ropeWidth=ropeImage.getIntrinsicWidth()*15;
                ropeHeight=ropeImage.getIntrinsicHeight()*4;

                halfHeight=height/2;
                halfWidth=width/2;

                //marginLeft=width/3/4/2;
                marginLeft=width/3;
                marginRight=marginLeft;



                //Button rope = (Button) findViewById(R.id.rope);
                rope = (ImageView) findViewById(R.id.rope);
                final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL;

                //marginBottom = params.bottomMargin;
                //marginRight = params.rightMargin;
                //marginTop = params.topMargin;
                //marginLeft = params.leftMargin;

                marginBottomAndTop =(height-ropeHeight)/2;

                marginTop= marginBottomAndTop;
                marginBottom= marginBottomAndTop;

                //params.setMargins(marginLeft,marginTop, marginRight, marginBottom);
                params.width = ropeWidth;
                params.height = ropeHeight;
                rope.setLayoutParams(params);


                separator = (ImageView) findViewById(R.id.separator);
                FrameLayout.LayoutParams paramsSeparator = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                paramsSeparator.gravity = Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL;
                paramsSeparator.setMargins(0,halfHeight, 0, halfHeight);
                paramsSeparator.height = 300;
                paramsSeparator.width = planeWidth;
                separator.setLayoutParams(paramsSeparator);

                layout.setOnTouchListener(new OnSwipeTouchListener(Activity_pvp.this) {
                    public void onSwipeTop() {
                        //Toast.makeText(Activity_pvp.this, "top", Toast.LENGTH_SHORT).show();

                        marginTop -= marginStep;
                        marginBottom += marginStep;

                        float coeff=1.15f;
                        if (!isTablet(Activity_pvp.this))
                        {
                            coeff = 1.35f;
                        }

                        if(marginBottom*coeff>=halfHeight)
                        {
                            /*

                            Toast toast = new Toast(Activity_pvp.this);
                            toast = Toast.makeText(Activity_pvp.this,"Выиграл игрок №1",Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP,0,0);
                            toast.show();

                            */
                            playerOneVictoryCount++;
                            playerOneState.setText(playerOneName+" "+playerOneVictoryCount);

                            FrameLayout.LayoutParams paramsState = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            paramsState.setMargins(-200,0,0,0);
                            paramsState.gravity = Gravity.CENTER_VERTICAL | Gravity.LEFT;
                            gameState.setLayoutParams(paramsState);
                            gameState.setText("Продолжим");

                            if(mode.equals("До разницы в счете") && playerOneVictoryCount-playerTwoVictoryCount>=score)
                            {
                                gameState.setText("Выиграл "+playerOneName);

                                Toast toast = new Toast(Activity_pvp.this);
                                toast = Toast.makeText(Activity_pvp.this,"Выиграл(-а) "+playerOneName,Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER,0,0);
                                toast.show();

                                Intent intent = new Intent(Activity_pvp.this, Activity_win.class);
                                intent.putExtra("result", playerOneName);
                                startActivity(intent);
                            }

                            if(mode.equals("Классический") && playerOneVictoryCount>=score)
                            {
                                gameState.setText("Выиграл "+playerOneName);

                                Toast toast = new Toast(Activity_pvp.this);
                                toast = Toast.makeText(Activity_pvp.this,"Выиграл(-а) "+playerOneName,Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER,0,0);
                                toast.show();

                                Intent intent = new Intent(Activity_pvp.this, Activity_win.class);
                                intent.putExtra("result", playerOneName);
                                startActivity(intent);
                            }
                            marginBottom = marginBottomAndTop;
                            marginTop = marginBottomAndTop;

                            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                            params.gravity = Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL;
                            params.setMargins(marginLeft,marginTop, marginRight, marginBottom);

                            params.width = ropeWidth;
                            params.height = ropeHeight;
                            rope.setLayoutParams(params);
                        }
                        else
                        {
                            FrameLayout.LayoutParams paramsState = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            paramsState.setMargins(-200,0,0,0);
                            paramsState.gravity = Gravity.CENTER_VERTICAL | Gravity.LEFT;
                            gameState.setLayoutParams(paramsState);
                            gameState.setText(playerOneName+" тянет на себя");

                            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                            params.setMargins(marginLeft, marginTop, marginRight, marginBottom);
                            params.gravity = Gravity.CENTER_HORIZONTAL;
                            params.width = ropeWidth;
                            params.height = ropeHeight;
                            rope.setLayoutParams(params);
                        }
                    }
                    /*
                    public void onSwipeRight() {
                        Toast.makeText(Activity_pvp.this, "right", Toast.LENGTH_SHORT).show();
                    }
                    public void onSwipeLeft() {
                        Toast.makeText(Activity_pvp.this, "left", Toast.LENGTH_SHORT).show();
                    }
                    */
                    public void onSwipeBottom()
                    {
                        //Toast.makeText(Activity_pvp.this, "bottom", Toast.LENGTH_SHORT).show();

                        marginBottom-=marginStep;
                        marginTop+=marginStep;

                        float coeff=1.15f;
                        if (!isTablet(Activity_pvp.this))
                        {
                            coeff = 1.35f;
                        }

                        if(marginTop*coeff>=halfHeight)
                        {
                            playerTwoVictoryCount++;
                            playerTwoState.setText(playerTwoName+" "+playerTwoVictoryCount);

                            FrameLayout.LayoutParams paramsState = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            paramsState.setMargins(-200,0,0,0);
                            paramsState.gravity = Gravity.CENTER_VERTICAL | Gravity.LEFT;
                            gameState.setLayoutParams(paramsState);

                            gameState.setText("Продолжим");

                            if(mode.equals("До разницы в счете") && playerTwoVictoryCount-playerOneVictoryCount>=score)
                            {
                                gameState.setText("Выиграл(-а) "+playerTwoName);


                                Toast toast = new Toast(Activity_pvp.this);
                                toast = Toast.makeText(Activity_pvp.this,"Выиграл(-а) "+playerTwoName,Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER,0,0);
                                toast.show();

                                Intent intent = new Intent(Activity_pvp.this, Activity_win.class);
                                intent.putExtra("result", playerTwoName);
                                startActivity(intent);
                            }

                            if(mode.equals("Классический") && playerTwoVictoryCount>=score)
                            {
                                gameState.setText("Выиграл(-а) "+playerTwoName);


                                Toast toast = new Toast(Activity_pvp.this);
                                toast = Toast.makeText(Activity_pvp.this,"Выиграл(-а) "+playerTwoName,Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER,0,0);
                                toast.show();

                                Intent intent = new Intent(Activity_pvp.this, Activity_win.class);
                                intent.putExtra("result", playerTwoName);
                                startActivity(intent);
                            }

                            marginTop= marginBottomAndTop;
                            marginBottom= marginBottomAndTop;

                            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                            params.gravity = Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL;
                            params.setMargins(marginLeft,marginTop, marginRight, marginBottom);

                            params.width = ropeWidth;
                            params.height = ropeHeight;
                            rope.setLayoutParams(params);
                        }
                        else
                        {
                            FrameLayout.LayoutParams paramsState = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            paramsState.setMargins(-200,0,0,0);
                            paramsState.gravity = Gravity.CENTER_VERTICAL | Gravity.LEFT;
                            gameState.setLayoutParams(paramsState);
                            gameState.setText(playerTwoName+" тянет на себя");

                            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                            params.gravity = Gravity.CENTER_HORIZONTAL;
                            params.setMargins(marginLeft,marginTop, marginRight, marginBottom);

                            params.width = ropeWidth;
                            params.height = ropeHeight;
                            rope.setLayoutParams(params);
                        }
                    }
                });
            }
        });
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}