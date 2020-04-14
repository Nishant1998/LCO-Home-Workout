package com.example.lcohomeworkout;

import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Collections;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class BreakTimer extends AppCompatActivity {

    TextView tv1, tv2, countdown_text;
    MaterialProgressBar materialProgressBar;
    Toolbar toolbar;
    private long timeLeftInMillisecounds, maxTimeInMilliSecounds;
    private boolean timerRunning;
    private CountDownTimer countDownTimer;
    ArrayList<Integer> list;
    String[] exercise_name = {"Push-Up","Crunches","Renegade Row","Standing Dumbbell Curl","Swiss Ball Decline Push-Up","Vrksasana","Rowing","Incline Bench Press","Bench Press"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_break_timer);
        init();

        Bundle bundle = getIntent().getExtras();

        int i = bundle.getInt("timerType");
        list = bundle.getIntegerArrayList("List");

        assert list != null;
        if(!list.isEmpty())
        {
            String s = "Next Exercise : " + exercise_name[list.get(0)-1] ;
            tv2.setText(s);
            if(i == 0)
            {
                tv1.setText(R.string.readyToGo);
                setTimerInSecounds(2);
                startTimer();
            }
            else if(i==1){
                tv1.setText(R.string.breaks);
                setTimerInSecounds(4);
                startTimer();
            }

        }
        else
        {
            tv1.setText("Finished !!!");
        }


    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        tv1 = findViewById(R.id.timer_text1);
        tv2 = findViewById(R.id.timer_text2);
        countdown_text = findViewById(R.id.countdown_text);
        materialProgressBar = findViewById(R.id.progress_countdown);
    }


    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillisecounds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillisecounds = millisUntilFinished;
                updateTimer();
                updateProgressBar();
            }

            @Override
            public void onFinish() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Bundle bundle = new Bundle();
                        bundle.putIntegerArrayList("List",list);
                        Intent intent = new Intent(BreakTimer.this,Exercise.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });

            }
        }.start();
        timerRunning = true;
    }

    private void updateProgressBar() {
        int progress = (int) (100 - (timeLeftInMillisecounds / (double) maxTimeInMilliSecounds * 100));



        materialProgressBar.setProgress(progress);

        if (timeLeftInMillisecounds < 1000)
            materialProgressBar.setProgress(100);

    }

    private void stopTimer() {
        countDownTimer.cancel();
        timerRunning = false;
    }

    private void setTimerInSecounds(int i) {
        timeLeftInMillisecounds = i * 1000;
        maxTimeInMilliSecounds = timeLeftInMillisecounds;
    }

    private void updateTimer() {
        int minute = (int) (timeLeftInMillisecounds / 60000);
        int seconds = (int) (timeLeftInMillisecounds % 60000 / 1000);

        String timeLeftText = "";

        if (minute < 10) timeLeftText += "0" + minute;
        else timeLeftText += minute;
        timeLeftText += ":";
        if (seconds < 10) timeLeftText += "0" + seconds;
        else timeLeftText += seconds;


        countdown_text.setText(timeLeftText);


    }

}
