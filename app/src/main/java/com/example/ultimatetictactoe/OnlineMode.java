package com.example.ultimatetictactoe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class OnlineMode<arr> extends AppCompatActivity {

    public FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance("https://demoproject-c807b-default-rtdb.firebaseio.com/");
    public DatabaseReference ref;
    boolean is_there_winner=false;  //if someone wins then it will be set as true and game stops
    boolean is_there_draw=false;    //if match is draw then it will be set as true
    public int isMyTurn=0;
    public int isCodeMaker=0;   //1-> codemaker
    public String myCode=null;
    public boolean bothOnline=false;
    public Context context=null;
    public int buddy=0;
    public MediaPlayer mediaPlayer=null;
    public boolean winning_status=false;

    int[] arr=new int[9];

    public void winPosition()
    {
        int who_is_winner=2;

        //checking for horizontal line
        for(int i=0;i<9 && !is_there_winner;i=i+3)
        {
            if(arr[i]==1 && arr[i+1]==1 && arr[i+2]==1) //player won
            {
                for(int k=i;k<3;k++)
                {
                    String toFind = "id" + k;
                    int imageId = OnlineMode.this.getResources().getIdentifier(toFind, "id", getPackageName());
                    ImageView img=(ImageView)findViewById(imageId);
                    img.setImageResource(R.drawable.x1);
                }

                is_there_winner=true;
                who_is_winner=1;
                break;
            }

            if(arr[i]==0 && arr[i+1]==0 && arr[i+2]==0) //opponent won
            {
                for(int k=i;k<3;k++)
                {
                    String toFind = "id" + k;
                    int imageId = OnlineMode.this.getResources().getIdentifier(toFind, "id", getPackageName());
                    ImageView img=(ImageView)findViewById(imageId);
                    img.setImageResource(R.drawable.o1);
                }

                is_there_winner=true;
                who_is_winner=0;
                break;
            }
        }

        //checking for vertical line
        for(int i=0;i<3 && !is_there_winner;i++)
        {
            if(arr[i]==1 && arr[i+3]==1 && arr[i+6]==1) //player won
            {
                for(int k=i;k<9;k=k+3)
                {
                    String toFind = "id" + k;
                    int imageId = OnlineMode.this.getResources().getIdentifier(toFind, "id", getPackageName());
                    ImageView img=(ImageView)findViewById(imageId);
                    img.setImageResource(R.drawable.x1);
                }
                is_there_winner=true;
                who_is_winner=1;
                break;
            }

            if(arr[i]==0 && arr[i+3]==0 && arr[i+6]==0) //opponent won
            {
                for(int k=i;k<9;k=k+3)
                {
                    String toFind = "id" + k;
                    int imageId = OnlineMode.this.getResources().getIdentifier(toFind, "id", getPackageName());
                    ImageView img=(ImageView)findViewById(imageId);
                    img.setImageResource(R.drawable.o1);
                }
                is_there_winner=true;
                who_is_winner=0;
                break;
            }
        }

        if(!is_there_winner && arr[0]==1 && arr[4]==1 && arr[8]==1) //player won
        {
            ImageView img0=(ImageView)findViewById(R.id.id0);
            img0.setImageResource(R.drawable.x1);

            ImageView img1=(ImageView)findViewById(R.id.id4);
            img1.setImageResource(R.drawable.x1);

            ImageView img2=(ImageView)findViewById(R.id.id8);
            img2.setImageResource(R.drawable.x1);

            is_there_winner=true;
            who_is_winner=1;
        }
        if(!is_there_winner && arr[0]==0 && arr[4]==0 && arr[8]==0) //opponent won
        {
            ImageView img0=(ImageView)findViewById(R.id.id0);
            img0.setImageResource(R.drawable.o1);

            ImageView img1=(ImageView)findViewById(R.id.id4);
            img1.setImageResource(R.drawable.o1);

            ImageView img2=(ImageView)findViewById(R.id.id8);
            img2.setImageResource(R.drawable.o1);

            is_there_winner=true;
            who_is_winner=0;
        }

        if(!is_there_winner && arr[2]==1 && arr[4]==1 && arr[6]==1) //player won
        {
            ImageView img0=(ImageView)findViewById(R.id.id2);
            img0.setImageResource(R.drawable.x1);

            ImageView img1=(ImageView)findViewById(R.id.id4);
            img1.setImageResource(R.drawable.x1);

            ImageView img2=(ImageView)findViewById(R.id.id6);
            img2.setImageResource(R.drawable.x1);

            is_there_winner=true;
            who_is_winner=1;
        }
        if(!is_there_winner && arr[2]==0 && arr[4]==0 && arr[6]==0)  //opponent won)
        {
            ImageView img0=(ImageView)findViewById(R.id.id2);
            img0.setImageResource(R.drawable.o1);

            ImageView img1=(ImageView)findViewById(R.id.id4);
            img1.setImageResource(R.drawable.o1);

            ImageView img2=(ImageView)findViewById(R.id.id6);
            img2.setImageResource(R.drawable.o1);

            is_there_winner=true;
            who_is_winner=0;
        }

        if(is_there_winner)
        {
            DatabaseReference dbref=firebaseDatabase.getReference().child("datasection").child(myCode).child("winning");
            dbref.setValue(who_is_winner);

            if(who_is_winner==1)    //player won
            {
                winning_status=true;
                if(mediaPlayer!=null) {
                    if(mediaPlayer.isPlaying())
                    {
                        mediaPlayer.stop();
                    }
                    mediaPlayer.reset();
                    mediaPlayer.release();
                    mediaPlayer=null;
                }
                mediaPlayer = MediaPlayer.create(OnlineMode.this, R.raw.winner);
                mediaPlayer.start();

                TextView textView=(TextView)findViewById(R.id.status);
                textView.setText("You Won !!!");
                androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(OnlineMode.this);
                builder.setTitle("You Nailed It!!!");
                builder.setIcon(R.drawable.trophy);

                builder.setMessage("You Won The Match\nWell Played !!!").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(OnlineMode.this, createRoom.class);
                        context.startActivity(intent);
                        OnlineMode.this.finish();
                        try {
                            DatabaseReference myref = firebaseDatabase.getReference().child("datasection").child(myCode);
                            myref.removeValue();
                        }catch(Exception e){}
                    }
                }).show();
            }
            else if(who_is_winner==0) //opponent won
            {
                winning_status=false;
                if(mediaPlayer!=null) {
                    if(mediaPlayer.isPlaying())
                    {
                        mediaPlayer.stop();
                    }
                    mediaPlayer.reset();
                    mediaPlayer.release();
                    mediaPlayer=null;
                }
                mediaPlayer=MediaPlayer.create(OnlineMode.this,R.raw.loser);
                mediaPlayer.start();
                TextView textView=(TextView)findViewById(R.id.status);
                textView.setText("You Lost !!!");

                androidx.appcompat.app.AlertDialog.Builder builder=new AlertDialog.Builder(OnlineMode.this);
                builder.setTitle("You Lost !!!");
                builder.setIcon(R.drawable.lost);

                builder.setMessage("Oops.. Too Close\nBetter Luck Next Time !!!").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(OnlineMode.this,createRoom.class);
                        context.startActivity(intent);
                        OnlineMode.this.finish();
                        try {
                            DatabaseReference myref = firebaseDatabase.getReference().child("datasection").child(myCode);
                            myref.removeValue();
                        }catch(Exception e){}
                    }
                }).show();
            }
            return;
        }

        int myflag=1;
        //condition for draw
        for(int i : arr)
        {
            if(i==2)
            {
                myflag=0;
                break;
            }
        }

        if(myflag==1)
        {
            is_there_draw=true;
        }

        if(is_there_draw)
        {
            DatabaseReference dbref=firebaseDatabase.getReference().child("datasection").child(myCode).child("draw");
            dbref.setValue(1);

            if(mediaPlayer!=null) {
                if(mediaPlayer.isPlaying())
                {
                    mediaPlayer.stop();
                }
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer=null;
            }
            mediaPlayer = MediaPlayer.create(OnlineMode.this, R.raw.draw);
            mediaPlayer.start();

            TextView textView=(TextView)findViewById(R.id.status);
            textView.setText("Match Is Draw !!!");
            androidx.appcompat.app.AlertDialog.Builder builder=new AlertDialog.Builder(OnlineMode.this);
            builder.setTitle("Match Is Draw !!!");
            builder.setIcon(R.drawable.lost);

            builder.setMessage("Opponent Was Really Good....\nThat Was The Toughest Fight!!!").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent=new Intent(OnlineMode.this,createRoom.class);
                    context.startActivity(intent);
                    OnlineMode.this.finish();
                    try {
                        DatabaseReference myref = firebaseDatabase.getReference().child("datasection").child(myCode);
                        myref.removeValue();
                    }catch(Exception e){}
                }
            }).show();
        }

    }

    public void touchit(View view)
    {
        if(!bothOnline)
        {
            if(isCodeMaker==0)
            {
                bothOnline=true;
            }
            else {
                Toast.makeText(OnlineMode.this, "Wait For Opponent !!!", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alertdialog = new AlertDialog.Builder(OnlineMode.this);
                alertdialog.setTitle("Wait For The Opponent !!!");
                alertdialog.setMessage("Hope, You Shared Code With Friends !!!\nRoom Code : " + myCode);
                alertdialog.setIcon(R.drawable.trophy);
                alertdialog.setPositiveButton("Wait", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertdialog.setNegativeButton("Leave", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ref.removeValue();
                        Intent intentBack=new Intent(OnlineMode.this,createRoom.class);
                        startActivity(intentBack);
                        OnlineMode.this.finish();
                        try {
                            DatabaseReference myref = firebaseDatabase.getReference().child("datasection").child(myCode);
                            myref.removeValue();
                        }catch(Exception e){}
                    }
                });
                alertdialog.show();
            }
        }
        else {
            if (is_there_draw || is_there_winner) {
                if (is_there_winner) {
                    if(winning_status)
                    {
                        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(OnlineMode.this);
                        builder.setTitle("You Nailed It!!!");
                        builder.setIcon(R.drawable.trophy);

                        builder.setMessage("You Won The Match\nWell Played !!!").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(OnlineMode.this, createRoom.class);
                                context.startActivity(intent);
                                OnlineMode.this.finish();
                                try {
                                    DatabaseReference myref = firebaseDatabase.getReference().child("datasection").child(myCode);
                                    myref.removeValue();
                                }catch(Exception e){}
                            }
                        }).show();
                    }
                    else if(winning_status==false){
                        androidx.appcompat.app.AlertDialog.Builder builder=new AlertDialog.Builder(OnlineMode.this);
                        builder.setTitle("You Lost !!!");
                        builder.setIcon(R.drawable.lost);

                        builder.setMessage("Oops.. Too Close\nBetter Luck Next Time !!!").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent=new Intent(OnlineMode.this,createRoom.class);
                                context.startActivity(intent);
                                OnlineMode.this.finish();
                                try {
                                    DatabaseReference myref = firebaseDatabase.getReference().child("datasection").child(myCode);
                                    myref.removeValue();
                                }catch(Exception e){}
                            }
                        }).show();
                    }
                } else {
                    TextView textView=(TextView)findViewById(R.id.status);
                    textView.setText("Match Is Draw !!!");
                    androidx.appcompat.app.AlertDialog.Builder builder=new AlertDialog.Builder(OnlineMode.this);
                    builder.setTitle("Match Is Draw !!!");
                    builder.setIcon(R.drawable.lost);

                    builder.setMessage("Opponent Was Really Good....\nThat Was The Toughest Fight!!!").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent=new Intent(OnlineMode.this,createRoom.class);
                            context.startActivity(intent);
                            OnlineMode.this.finish();
                            try {
                                DatabaseReference myref = firebaseDatabase.getReference().child("datasection").child(myCode);
                                myref.removeValue();
                            }catch(Exception e){}
                        }
                    }).show();
                }
            } else {
                if (isMyTurn == 1) //if it is my turn then i will able to play
                {
                    ImageView img = (ImageView) view;
                    int tapped = Integer.parseInt(img.getTag().toString());
                    if (arr[tapped] == 2) //then it is clickable
                    {
                        DatabaseReference dbref = firebaseDatabase.getReference().child("datasection").child(myCode).child("marked");

                        arr[tapped] = 1;
                        img.setTranslationY(-1000);

                        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.playsound1);
                        mediaPlayer.start();
                        img.setImageResource(R.drawable.x);
                        TextView status = findViewById(R.id.status);
                        status.setText("Opponent's Turn !!!");

                        img.animate().translationYBy(1000f);
                        isMyTurn = 0;
                        dbref.setValue(tapped);
                        winPosition();
                    } else {
                        Toast.makeText(this, "Tap On Valid Position !!!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Opponent's Turn !!!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_mode);
        getSupportActionBar().hide();
        context=this;
        Arrays.fill(arr,2);
        Log.d("mylog","array : "+arr.toString());

//      Taking parameters from previous intent
        Intent previousIntent=getIntent();
        myCode=previousIntent.getStringExtra("myCode");
        String valueForCodeMaker =previousIntent.getStringExtra("isCodeMaker");
        isCodeMaker=Integer.parseInt(valueForCodeMaker);
        isMyTurn=isCodeMaker;
        Log.d("mylog",valueForCodeMaker);

        TextView status1 = findViewById(R.id.status);
        status1.setText("Wait For Opponent To Join....");
//        if(isMyTurn==1)
//        {
//            status1.setText("Your Turn - Tap to Play !!!");
//        }
//        else if(isMyTurn==0)
//        {
//            status1.setText("Opponent's Turn !!!");
//        }

        ref=firebaseDatabase.getReference().child("datasection").child(myCode);

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

                if(snapshot.exists()) {

                    String key = snapshot.getKey().toString();

                    if(key.equalsIgnoreCase("marked"))
                    {
                        winPosition();
                        int val=Integer.parseInt(snapshot.getValue().toString());
                        if(arr[val]==2) //set position with O
                        {
                            arr[val]=0;
                            String toFind = "id" + val;
                            int imageId = OnlineMode.this.getResources().getIdentifier(toFind, "id", getPackageName());
                            ImageView img=(ImageView)findViewById(imageId);

                            img.setTranslationY(-1000);

                            MediaPlayer mediaPlayer=MediaPlayer.create(OnlineMode.this,R.raw.playsound1);
                            mediaPlayer.start();
                            img.setImageResource(R.drawable.o);
                            TextView status = findViewById(R.id.status);
                            status.setText("Your Turn - Tap to Play !!!");

                            img.animate().translationYBy(1000f);
                            isMyTurn=1;
                        }
                    }
                    else if (key.equalsIgnoreCase("players"))
                    {
                        buddy = Integer.parseInt(snapshot.getValue().toString());
                        Toast.makeText(OnlineMode.this,"Hey, Opponent Joined The Game...",Toast.LENGTH_LONG).show();
                        Toast.makeText(OnlineMode.this,"Tap To Play",Toast.LENGTH_LONG).show();

                        if (buddy == 2) {
                            //Setting turn of players
                            bothOnline = true;
                            if (isMyTurn == 1) {   //If this is my turn then set star of opponent invisible
                                TextView textView=(TextView)findViewById(R.id.status);
                                textView.setText("Tap To Play !!!");
                            } else {
                                TextView textView=(TextView)findViewById(R.id.status);
                                textView.setText("Wait For Opponent's Move !!!");
                            }
                        }
                        else {
                            bothOnline = false;
                        }

                    }
                    else if(key.equalsIgnoreCase("winning"))
                    {
                        winPosition();
                    }
                    else if(key.equalsIgnoreCase("draw")) {
                        winPosition();
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    if (snapshot.getKey().matches("players")) {
                        if (!is_there_winner) {
                            AlertDialog.Builder alertdialog = new AlertDialog.Builder(OnlineMode.this);
                            alertdialog.setTitle("Victory !!!");
                            alertdialog.setMessage("You Won The Match,\nOpponent Left The Game!!!");
                            alertdialog.setIcon(R.drawable.trophy);
                            alertdialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(OnlineMode.this, createRoom.class);
                                    startActivity(intent);
                                    OnlineMode.this.finish();
                                }
                            });
                            alertdialog.show();
                        }
                    }
                }
            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if(is_there_draw || is_there_winner)
        {
            super.onBackPressed();
            try {
                DatabaseReference myref = firebaseDatabase.getReference().child("datasection").child(myCode);
                myref.removeValue();
            }catch(Exception e){}
        }
        else {
            AlertDialog.Builder alertdialog = new AlertDialog.Builder(OnlineMode.this);
            alertdialog.setTitle("Quite?");
            alertdialog.setMessage("Do You Want To Quite The Game?");
            alertdialog.setIcon(R.drawable.trophy);
            alertdialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(OnlineMode.this, createRoom.class);
                    startActivity(intent);
                    ref = firebaseDatabase.getReference().child("datasection").child(myCode).child("players");
                    ref.removeValue();
                    OnlineMode.this.finish();
                    try {
                        DatabaseReference myref = firebaseDatabase.getReference().child("datasection").child(myCode);
                        myref.removeValue();
                    }catch(Exception e){}
                }
            });
            alertdialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertdialog.show();
        }
    }
}