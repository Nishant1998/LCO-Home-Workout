package com.example.lcohomeworkout;

import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
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
    int numOfExe;


    @Override
    public void onBackPressed() {
        stopTimer();
        Intent intent = new Intent(BreakTimer.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_break_timer);

        //Ui element initialization
        init();

        //Get data from bundle send by other activity such as 'list' of exercise and number of exercise
        Bundle bundle = getIntent().getExtras();

        int i = bundle.getInt("timerType");
        list = bundle.getIntegerArrayList("List");
        numOfExe = bundle.getInt("NumOfExe");
        assert list != null;

        //If list is empty all exercise are finished.
        if(!list.isEmpty())
        {
            String s = "Next Exercise : " + exercise_name[list.get(0)-1] ;
            tv2.setText(s);
            if(i == 0)
            {
                tv1.setText(R.string.readyToGo);
                setTimerInSecounds(10);
                startTimer();
            }
            else if(i==1){
                tv1.setText(R.string.breaks);
                setTimerInSecounds(40);
                startTimer();
            }

        }
        else
        {
            RelativeLayout r = findViewById(R.id.timer_relative_layout);
            r.setVisibility(View.GONE);
        }


    }
    //initialization
    private void init() {
        toolbar = findViewById(R.id.toolbar);
        tv1 = findViewById(R.id.timer_text1);
        tv2 = findViewById(R.id.timer_text2);
        countdown_text = findViewById(R.id.countdown_text);
        materialProgressBar = findViewById(R.id.progress_countdown);
    }

    //function to start timer
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
                        bundle.putInt("NumOfExe",numOfExe);
                        Intent intent = new Intent(BreakTimer.this,Exercise.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });

            }
        }.start();
        timerRunning = true;
    }
    //function to update progressbar
    private void updateProgressBar() {
        int progress = (int) (100 - (timeLeftInMillisecounds / (double) maxTimeInMilliSecounds * 100));



        materialProgressBar.setProgress(progress);

        if (timeLeftInMillisecounds < 1000)
            materialProgressBar.setProgress(100);

    }
    //function to stop timer
    private void stopTimer() {
        countDownTimer.cancel();
        timerRunning = false;
    }
    // Set timer for timer
    private void setTimerInSecounds(int i) {
        timeLeftInMillisecounds = i * 1000;
        maxTimeInMilliSecounds = timeLeftInMillisecounds;
    }
    //function to update timer text
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
