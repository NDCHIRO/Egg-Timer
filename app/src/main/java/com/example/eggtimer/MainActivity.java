package com.example.eggtimer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView timerTextView;
    SeekBar seekBar;
    int seconds;
    int minutes;
    Button startButton;
    MediaPlayer mediaPlayer;
    int noOfClicks=0;
    CountDownTimer countDownTimer;

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void startTimer(View view) {
        startButton = findViewById(R.id.timeerButton);
        if (noOfClicks==0)
        {
            noOfClicks=1;
            countDownTimer= new CountDownTimer(minutes * 60 * 1000 + seconds * 1000, 1000) {
                @Override
                public void onTick(long l) {
                        startButton.setText("stop");
                        seekBar.setEnabled(false);
                        if (l / 1000 < 60)
                            timerTextView.setText("0:" + l / 1000);
                        else
                            timerTextView.setText(l / (60 * 1000) + ":" + (l / 1000) % 60);

                }

                @Override
                public void onFinish() {

                    mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.sound2);
                    mediaPlayer.start();
                    resetTimer();
                }
            }.start();

        }
        else
        {
            resetTimer();
        }
    }

    public void resetTimer()
    {
        minutes=0;
        seconds=30;
        seekBar.setProgress(30);
        seekBar.setEnabled(true);
        timerTextView.setText("0:30");
        startButton.setText("start");
        noOfClicks=0;
        countDownTimer.cancel();
    }

    public void setupSeekBar()
    {
        seekBar.setMax(600);
        seekBar.setProgress(30);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seconds=30;
        minutes=0;
        seekBar = findViewById(R.id.timerSeekBar);
        timerTextView = findViewById(R.id.timerTextView);
        setupSeekBar();
        //timerTextView.setText(seekBar.getProgress());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(i<60) {
                    timerTextView.setText("0:" + i);
                    minutes=0;
                    seconds=i;
                }
                else
                {
                    timerTextView.setText(i/60+":"+i%60);
                    minutes=i/60;
                    seconds=i%60;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}