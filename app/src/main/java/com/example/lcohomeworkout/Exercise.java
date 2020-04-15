package com.example.lcohomeworkout;

import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lcohomeworkout.Fragments.HomeFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class Exercise extends AppCompatActivity {

    private Toolbar toolbar;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillisecounds, maxTimeInMilliSecounds;
    private boolean timerRunning;
    private TextView exercise_countdown_text, tv1, tv2, tv3;

    MediaPlayer player;
    int[] audio = {R.raw.audio1,R.raw.audio2,R.raw.audio3,R.raw.audio4,R.raw.audio5};

    private ImageView iv;
    private MaterialProgressBar materialExerciseProgressBar;
    private ArrayList<Integer> list;
    private int numOfExe;
    String[] exercise_name = {"Push-Up", "Crunches", "Renegade Row", "Standing Dumbbell Curl", "Swiss Ball Decline Push-Up", "Vrksasana", "Rowing", "Incline Bench Press", "Bench Press"};

    //for shared preference
    private static final String SHARED = "WORKOUT_SHARED_DATA";
    private static final String NUMBER_OF_SETS = "SETS";

    @Override
    public void onBackPressed() {
        stopTimer();
        player.release();
        Intent intent = new Intent(Exercise.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        //Ui element initialization
        init();

        //Get number of sets to perform from SharedPreferences
        SharedPreferences mySharedPreference = getSharedPreferences(SHARED, MODE_PRIVATE);
        int setnum = mySharedPreference.getInt(NUMBER_OF_SETS, 1);

        //Get data from bundle send by other activity such as 'list' of exercise and number of exercise
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        list = bundle.getIntegerArrayList("List");
        numOfExe = bundle.getInt("NumOfExe");
        assert list != null;
        int i = list.get(0);

        //Display exercise name
        tv1.setText(exercise_name[i-1]);


        //Display current exercise and current set Number
        if(setnum == 0)
            setnum =1;
        tv2.setText("Exercise : " + ((int) ((numOfExe) - (list.size() / (double)setnum))+1) + "/" + numOfExe);
        int x = (((numOfExe*setnum)-list.size())%2)+1;
        tv3.setText("Set : " + x + "/" + setnum);

        //remove current exercise from list consider it as complete
        list.remove(0);

        //Set exercise image and time
        {
            if (i == 1) {
                iv.setImageDrawable(getDrawable(R.drawable.img1));
                setTimerInSecounds(50);
            } else if (i == 2) {
                iv.setImageDrawable(getDrawable(R.drawable.img2));
                setTimerInSecounds(65);
            } else if (i == 3) {
                iv.setImageDrawable(getDrawable(R.drawable.img6));
                setTimerInSecounds(95);
            } else if (i == 4) {
                iv.setImageDrawable(getDrawable(R.drawable.img7));
                setTimerInSecounds(100);
            } else if (i == 5) {
                iv.setImageDrawable(getDrawable(R.drawable.img8));
                setTimerInSecounds(70);
            } else if (i == 6) {
                iv.setImageDrawable(getDrawable(R.drawable.img9));
                setTimerInSecounds(65);
            } else if (i == 7) {
                iv.setImageDrawable(getDrawable(R.drawable.img10));
                setTimerInSecounds(120);
            } else if (i == 8) {
                iv.setImageDrawable(getDrawable(R.drawable.img11));
                setTimerInSecounds(150);
            } else if (i == 9) {
                iv.setImageDrawable(getDrawable(R.drawable.img12));
                setTimerInSecounds(150);
            }
        }

        //start timer from exercise
        startTimer();

    }

    //initialization
    private void init() {
        toolbar = findViewById(R.id.toolbar);


        tv1 = findViewById(R.id.exercise_name_text);
        tv2 = findViewById(R.id.exercise_left_text);
        tv3 = findViewById(R.id.sets_text);
        iv = findViewById(R.id.exercise_imageview);
        exercise_countdown_text = findViewById(R.id.exercise_countdown_text);
        materialExerciseProgressBar = findViewById(R.id.progressBar_exercise);

        Random random = new Random();
        player = MediaPlayer.create(this,audio[random.nextInt(5)]);
    }

    //function to start timer
    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillisecounds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //start music
                player.start();
                timeLeftInMillisecounds = millisUntilFinished;
                updateTimer();
                updateProgressBar();
            }

            @Override
            public void onFinish() {
                player.release();
                Bundle bundle = new Bundle();
                bundle.putInt("timerType",1);

                bundle.putIntegerArrayList("List",list);
                bundle.putInt("NumOfExe",numOfExe);
                Intent intent = new Intent(Exercise.this, BreakTimer.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }.start();
        timerRunning = true;
    }

    //function to update progressbar
    private void updateProgressBar() {
        int progress = (int) (100 - (timeLeftInMillisecounds / (double) maxTimeInMilliSecounds * 100));


        materialExerciseProgressBar.setProgress(progress);

        if (timeLeftInMillisecounds < 1000)
            materialExerciseProgressBar.setProgress(100);

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
        // Find remaining min and sec
        int minute = (int) (timeLeftInMillisecounds / 60000);
        int seconds = (int) (timeLeftInMillisecounds % 60000 / 1000);


        String timeLeftText = "";
        if (minute < 10) timeLeftText += "0" + minute;
        else timeLeftText += minute;
        timeLeftText += ":";
        if (seconds < 10) timeLeftText += "0" + seconds;
        else timeLeftText += seconds;


        exercise_countdown_text.setText(timeLeftText);


    }
}
