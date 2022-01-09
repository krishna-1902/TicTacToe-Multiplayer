package com.example.ultimatetictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static String p1=null;
    public static String p2=null;
    MediaPlayer mediaPlayer=null;

    public void tapToPlay(View view)
    {
        Intent intent=new Intent(this, com.example.ultimatetictactoe.Hello.class);
        EditText e1=findViewById(R.id.player1);
        EditText e2=findViewById(R.id.player2);

        p1=e1.getText().toString();
        p2=e2.getText().toString();

        if(p1.equals("") || p2.equals(""))
        {
            Toast.makeText(this,"Please Enter Player Names",Toast.LENGTH_SHORT).show();
        }
        else if(p1.equals(p2))
        {
            Toast.makeText(this,"Please Enter Different Player Names",Toast.LENGTH_SHORT).show();
        }
        else
        {
            if (mediaPlayer != null) {
                try {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                    }
                    mediaPlayer.reset();
                    mediaPlayer.release();
                }catch (Exception ex){}
            }
            intent.putExtra(p1,p1);
            intent.putExtra(p2,p2);
            startActivity(intent);
            this.finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        mediaPlayer=MediaPlayer.create(this,R.raw.lofi);
        mediaPlayer.start();
        getSupportActionBar().hide();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mediaPlayer != null) {
            try {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                mediaPlayer.reset();
                mediaPlayer.release();
            }catch (Exception ex){}
        }
        this.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            try {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                mediaPlayer.reset();
                mediaPlayer.release();
            }catch (Exception ex){}
        }
    }
}