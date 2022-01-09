package com.example.ultimatetictactoe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Hello extends AppCompatActivity {

    private String p1;
    private String p2;
    //0=o
    //1=x
    int active_player=0;
    int game_state[]={2,2,2,2,2,2,2,2,2};
    int [][]win_pos={{0,1,2},{3,4,5},{6,7,8},
            {0,3,6},{1,4,7},{2,5,8},
            {0,4,8},{2,4,6}};
    boolean let=true;

    public void tapToPlay(View view) {
        if (game_state[0] != 2 && game_state[1] != 2 && game_state[2] != 2 && game_state[3] != 2 && game_state[4] != 2 && game_state[5] != 2 && game_state[6] != 2 && game_state[7] != 2 && game_state[8] != 2 && let) {
            Toast.makeText(this, "Match Already Tied", Toast.LENGTH_SHORT).show();
        } else {
            ImageView img = (ImageView) view;
            int tapped = Integer.parseInt(img.getTag().toString());

            if (!let) {
                if (active_player == 0) {
                    //Toast.makeText(this,"X Has Won The Game",Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(this,"O Has Won The Game",Toast.LENGTH_SHORT).show();
                }
            } else if (game_state[tapped] == 2 && let) {

                game_state[tapped] = active_player;
                img.setTranslationY(-1000);
                if (active_player == 1) {
                    MediaPlayer mediaPlayer=MediaPlayer.create(this,R.raw.playsound1);
                    mediaPlayer.start();
                    img.setImageResource(R.drawable.x);
                    active_player = 0;
                    TextView status = findViewById(R.id.status);
                    status.setText(p1+"'s Turn - Tap To Play");
                } else {
                    MediaPlayer mediaPlayer=MediaPlayer.create(this,R.raw.playsound2);
                    mediaPlayer.start();
                    img.setImageResource(R.drawable.o);
                    active_player = 1;
                    TextView status = findViewById(R.id.status);
                    status.setText(p2+"'s Turn - Tap To Play");
                }
                img.animate().translationYBy(1000f);
            }


            for (int[] win : win_pos) {

                if (game_state[win[0]] == game_state[win[1]] && game_state[win[1]] == game_state[win[2]] && game_state[win[0]] != 2)
                {
                    TextView status = findViewById(R.id.status);
                    if (game_state[win[0]] == 0) {
                        status.setText(p1+" Won");
                        ImageView v1;
                        if (win[0] == 0 || win[1] == 0 || win[2] == 0) {
                            v1 = findViewById(R.id.id0);
                            v1.setImageResource(R.drawable.o1);
                        }
                        if (win[0] == 1 || win[1] == 1 || win[2] == 1) {
                            v1 = findViewById(R.id.id1);
                            v1.setImageResource(R.drawable.o1);
                        }
                        if (win[0] == 2 || win[1] == 2 || win[2] == 2) {
                            v1 = findViewById(R.id.id2);
                            v1.setImageResource(R.drawable.o1);
                        }
                        if (win[0] == 3 || win[1] == 3 || win[2] == 3) {
                            v1 = findViewById(R.id.id3);
                            v1.setImageResource(R.drawable.o1);
                        }
                        if (win[0] == 4 || win[1] == 4 || win[2] == 4) {
                            v1 = findViewById(R.id.id4);
                            v1.setImageResource(R.drawable.o1);
                        }
                        if (win[0] == 5 || win[1] == 5 || win[2] == 5) {
                            v1 = findViewById(R.id.id5);
                            v1.setImageResource(R.drawable.o1);
                        }
                        if (win[0] == 6 || win[1] == 6 || win[2] == 6) {
                            v1 = findViewById(R.id.id6);
                            v1.setImageResource(R.drawable.o1);
                        }
                        if (win[0] == 7 || win[1] == 7 || win[2] == 7) {
                            v1 = findViewById(R.id.id7);
                            v1.setImageResource(R.drawable.o1);
                        }
                        if (win[0] == 8 || win[1] == 8 || win[2] == 8) {
                            v1 = findViewById(R.id.id8);
                            v1.setImageResource(R.drawable.o1);
                        }

                        Toast.makeText(this, p1+" has won the game", Toast.LENGTH_SHORT).show();

                        AlertDialog.Builder builder=new AlertDialog.Builder(this);
                        builder.setTitle("Congratulations "+p1);
                        builder.setIcon(R.drawable.trophy);

                        MediaPlayer mediaPlayer=MediaPlayer.create(this,R.raw.winner);
                        mediaPlayer.start();

                        new CountDownTimer(500,500){
                            @Override
                            public void onTick(long millisUntilFinished) {

                            }

                            @Override
                            public void onFinish() {
                                builder.setMessage("You Won The Game !!!").setPositiveButton("Play Again", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(getParentActivityIntent());
                                    }
                                }).show();
                            }
                        }.start();

                        let=false;
                    }
                    else {
                        status.setText(p2+" won");
                        ImageView v1;
                        if (win[0] == 0 || win[1] == 0 || win[2] == 0) {
                            v1 = findViewById(R.id.id0);
                            v1.setImageResource(R.drawable.x1);
                        }
                        if (win[0] == 1 || win[1] == 1 || win[2] == 1) {
                            v1 = findViewById(R.id.id1);
                            v1.setImageResource(R.drawable.x1);
                        }
                        if (win[0] == 2 || win[1] == 2 || win[2] == 2) {
                            v1 = findViewById(R.id.id2);
                            v1.setImageResource(R.drawable.x1);
                        }
                        if (win[0] == 3 || win[1] == 3 || win[2] == 3) {
                            v1 = findViewById(R.id.id3);
                            v1.setImageResource(R.drawable.x1);
                        }
                        if (win[0] == 4 || win[1] == 4 || win[2] == 4) {
                            v1 = findViewById(R.id.id4);
                            v1.setImageResource(R.drawable.x1);
                        }
                        if (win[0] == 5 || win[1] == 5 || win[2] == 5) {
                            v1 = findViewById(R.id.id5);
                            v1.setImageResource(R.drawable.x1);
                        }
                        if (win[0] == 6 || win[1] == 6 || win[2] == 6) {
                            v1 = findViewById(R.id.id6);
                            v1.setImageResource(R.drawable.x1);
                        }
                        if (win[0] == 7 || win[1] == 7 || win[2] == 7) {
                            v1 = findViewById(R.id.id7);
                            v1.setImageResource(R.drawable.x1);
                        }
                        if (win[0] == 8 || win[1] == 8 || win[2] == 8) {
                            v1 = findViewById(R.id.id8);
                            v1.setImageResource(R.drawable.x1);
                        }
                        Toast.makeText(this, p2+" has won the game", Toast.LENGTH_SHORT).show();

                        AlertDialog.Builder builder=new AlertDialog.Builder(this);
                        builder.setTitle("Congratulations "+p2);
                        builder.setIcon(R.drawable.trophy);
                        MediaPlayer mediaPlayer=MediaPlayer.create(this,R.raw.winner);
                        mediaPlayer.start();
                        new CountDownTimer(500,500){
                            @Override
                            public void onTick(long millisUntilFinished) {

                            }

                            @Override
                            public void onFinish() {
                                builder.setMessage("You Won The Game !!!").setPositiveButton("Play Again", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(getParentActivityIntent());
                                    }
                                }).show();
                            }
                        }.start();

                        let = false;
                    }
                }
            }

            if (game_state[0] != 2 && game_state[1] != 2 && game_state[2] != 2 && game_state[3] != 2 && game_state[4] != 2 && game_state[5] != 2 && game_state[6] != 2 && game_state[7] != 2 && game_state[8] != 2 && let)
            {
                MediaPlayer mediaPlayer=MediaPlayer.create(this,R.raw.loser);
                mediaPlayer.start();
                Log.d("let : ", "value= "+let);
                TextView status = findViewById(R.id.status);
                status.setText("Match Is Tied");

                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("Match Is Tied !!!");
                builder.setIcon(R.drawable.tied);
                builder.setMessage("Better Luck Next Time !!!").setPositiveButton("Play Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(getParentActivityIntent());
                    }
                }).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        Intent intent=getIntent();
        p1=intent.getStringExtra(MainActivity.p1);
        p2=intent.getStringExtra(MainActivity.p2);
        TextView v=findViewById(R.id.status);
        v.setText(p1+"'s Turn - Tap To Play");
    }
}