package com.example.lcohomeworkout;

import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class Exercise extends AppCompatActivity {

    private Toolbar toolbar;

    private RelativeLayout relativeLayout02;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillisecounds, maxTimeInMilliSecounds;
    private boolean timerRunning;
    private TextView exercise_countdown_text, tv1, tv2, tv3;

    private ImageView iv;
    private MaterialProgressBar materialExerciseProgressBar;
    private ArrayList<Integer> list;

    String[] exercise_name = {"Push-Up", "Crunches", "Renegade Row", "Standing Dumbbell Curl", "Swiss Ball Decline Push-Up", "Vrksasana", "Rowing", "Incline Bench Press", "Bench Press"};

    private static final String SHARED = "WORKOUT_SHARED_DATA";
    private static final String NUMBER_OF_SETS = "SETS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        init();

        SharedPreferences mySharedPreference = getSharedPreferences(SHARED, MODE_PRIVATE);
        int setnum = mySharedPreference.getInt(NUMBER_OF_SETS, 1);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        list = bundle.getIntegerArrayList("List");
        assert list != null;
        int i = list.get(0);

        tv1.setText(exercise_name[i-1]);

        Log.d("TEST",setnum +"");
        if(setnum == 0)
            setnum =1;
        tv2.setText((5 - (list.size() / setnum)) + "/5");
        int x = setnum - countFrequencies(i);
        tv3.setText("Set : " + x + "/" + setnum);

        Log.d("BEFORE",list+"");
        list.remove(0);
        Log.d("AFTER",list+"");

        if (i == 1) {
            iv.setImageDrawable(getDrawable(R.drawable.img1));
            setTimerInSecounds(5);

        } else if (i == 2) {
            iv.setImageDrawable(getDrawable(R.drawable.img2));
            setTimerInSecounds(5);
        } else if (i == 3) {
            iv.setImageDrawable(getDrawable(R.drawable.img6));
            setTimerInSecounds(20);
        } else if (i == 4) {
            iv.setImageDrawable(getDrawable(R.drawable.img7));
            setTimerInSecounds(5);
        } else if (i == 5) {
            iv.setImageDrawable(getDrawable(R.drawable.img8));
            setTimerInSecounds(5);
        } else if (i == 6) {
            iv.setImageDrawable(getDrawable(R.drawable.img9));
            setTimerInSecounds(5);
        } else if (i == 7) {
            iv.setImageDrawable(getDrawable(R.drawable.img10));
            setTimerInSecounds(5);
        } else if (i == 8) {
            iv.setImageDrawable(getDrawable(R.drawable.img11));
            setTimerInSecounds(5);
        } else if (i == 9) {
            iv.setImageDrawable(getDrawable(R.drawable.img12));
            setTimerInSecounds(5);
        }
        startTimer();

    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);

        relativeLayout02 = findViewById(R.id.exercise_relative_layout);
        tv1 = findViewById(R.id.exercise_name_text);
        tv2 = findViewById(R.id.exercise_left_text);
        tv3 = findViewById(R.id.sets_text);
        iv = findViewById(R.id.exercise_imageview);
        exercise_countdown_text = findViewById(R.id.exercise_countdown_text);
        materialExerciseProgressBar = findViewById(R.id.progressBar_exercise);
    }


    public int countFrequencies(int number) {
        // hashmap to store the frequency of element
        Map<String, Integer> hm = new HashMap<String, Integer>();


        for (int i : list) {
            Integer j = hm.get(i);
            hm.put(Integer.toString(i), (j == null) ? 1 : j + 1);
        }

        // displaying the occurrence of elements in the arraylist
        for (Map.Entry<String, Integer> val : hm.entrySet()) {
            if (val.getKey().equals(number))
                return Integer.parseInt(val.getKey());

        }
        return 0;
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
                Bundle bundle = new Bundle();
                bundle.putInt("timerType",1);

                bundle.putIntegerArrayList("List",list);

                Intent intent = new Intent(Exercise.this, BreakTimer.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }.start();
        timerRunning = true;
    }

    private void updateProgressBar() {
        int progress = (int) (100 - (timeLeftInMillisecounds / (double) maxTimeInMilliSecounds * 100));


        materialExerciseProgressBar.setProgress(progress);

        if (timeLeftInMillisecounds < 1000)
            materialExerciseProgressBar.setProgress(100);

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


        exercise_countdown_text.setText(timeLeftText);


    }
}
