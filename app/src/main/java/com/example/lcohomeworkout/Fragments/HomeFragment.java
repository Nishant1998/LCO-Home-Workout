package com.example.lcohomeworkout.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.lcohomeworkout.BreakTimer;
import com.example.lcohomeworkout.Exercise;
import com.example.lcohomeworkout.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    private CardView c1, c2, c3, c4, c5, c6, c7, c8, c9;
    private BottomNavigationView bottomNavigationView;
    private Dialog dialog;
    private Button ok, cancel;
    private NumberPicker numberPicker;
    private int numberOfSets;

    private Button i1, i2, i3, i4, i5, i6, i7, i8, i9;
    private Set<Integer> set;
    private TextView cd01_0, cd02_0, cd03_0, cd04_0, cd05_0, cd06_0, cd07_0, cd08_0, cd09_0;
    private TextView cd01_1, cd02_1, cd03_1, cd04_1, cd05_1, cd06_1, cd07_1, cd08_1, cd09_1;
    private TextView cd01_2, cd02_2, cd03_2, cd04_2, cd05_2, cd06_2, cd07_2, cd08_2, cd09_2;

    TextView workout, kcal, min;


    private static final String SHARED = "WORKOUT_SHARED_DATA";
    private static final String NUMBER_OF_SETS = "SETS";
    private static final String EXERICISE_LIST = "EXERCISE_LIST_RANDOM";

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences mySharedPreference = getActivity().getSharedPreferences(SHARED, MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreference.edit();
        editor.putInt(NUMBER_OF_SETS, 1);
        editor.apply();

        init();
        displayExercise(randomfive());
        updateGoals(set);


        c1.setOnClickListener(this);
        i1.setOnClickListener(this);
        i2.setOnClickListener(this);
        i3.setOnClickListener(this);
        i4.setOnClickListener(this);
        i5.setOnClickListener(this);
        i6.setOnClickListener(this);
        i7.setOnClickListener(this);
        i8.setOnClickListener(this);
        i9.setOnClickListener(this);


        //Ask for number of sets to perform
        showSelectSetsDialog();

        //show set and time of each exercise
        showSetsAndTime();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_nav_start: {
                        Log.d("TAG", "START SELECTED");

                        ArrayList<Integer> list = new ArrayList<>();

                        for (int i : set) {
                            for (int j = 0; j < getnumberOfSets(); j++)
                                list.add(i);
                        }
                        Collections.sort(list);

                        Bundle bundle = new Bundle();
                        bundle.putInt("timerType",0);

                        bundle.putIntegerArrayList("List",list);

                        Intent intent = new Intent(getActivity(), BreakTimer.class);
                        intent.putExtras(bundle);
                        startActivity(intent);

                    }
                    break;
                    case R.id.bottom_nav_sets: {
                        Log.d("TAG", "SETS SELECTED");
                        showSelectSetsDialog();
                    }
                    break;
                    case R.id.bottom_nav_random: {
                        Log.d("TAG", "RANDOM SELECTED");
                        displayExercise(randomfive());
                        updateGoals(set);
                    }
                    break;
                }
                return true;
            }
        });

    }


    private void init() {
        c1 = getView().findViewById(R.id.execard01);
        c2 = getView().findViewById(R.id.execard02);
        c3 = getView().findViewById(R.id.execard03);
        c4 = getView().findViewById(R.id.execard04);
        c5 = getView().findViewById(R.id.execard05);
        c6 = getView().findViewById(R.id.execard06);
        c7 = getView().findViewById(R.id.execard07);
        c8 = getView().findViewById(R.id.execard08);
        c9 = getView().findViewById(R.id.execard09);

        bottomNavigationView = getView().findViewById(R.id.bottom_navigation);

        //find textview of cards
        cd01_0 = getView().findViewById(R.id.execard01_mincount);
        cd01_1 = getView().findViewById(R.id.execard01_setscount);
        cd01_2 = getView().findViewById(R.id.execard01_seccount);

        cd02_0 = getView().findViewById(R.id.execard02_mincount);
        cd02_1 = getView().findViewById(R.id.execard02_setscount);
        cd02_2 = getView().findViewById(R.id.execard02_seccount);

        cd03_0 = getView().findViewById(R.id.execard03_mincount);
        cd03_1 = getView().findViewById(R.id.execard03_setscount);
        cd03_2 = getView().findViewById(R.id.execard03_seccount);

        cd04_0 = getView().findViewById(R.id.execard04_mincount);
        cd04_1 = getView().findViewById(R.id.execard04_setscount);
        cd04_2 = getView().findViewById(R.id.execard04_seccount);

        cd05_0 = getView().findViewById(R.id.execard05_mincount);
        cd05_1 = getView().findViewById(R.id.execard05_setscount);
        cd05_2 = getView().findViewById(R.id.execard05_seccount);

        cd06_0 = getView().findViewById(R.id.execard06_mincount);
        cd06_1 = getView().findViewById(R.id.execard06_setscount);
        cd06_2 = getView().findViewById(R.id.execard06_seccount);

        cd07_0 = getView().findViewById(R.id.execard07_mincount);
        cd07_1 = getView().findViewById(R.id.execard07_setscount);
        cd07_2 = getView().findViewById(R.id.execard07_seccount);

        cd08_0 = getView().findViewById(R.id.execard08_mincount);
        cd08_1 = getView().findViewById(R.id.execard08_setscount);
        cd08_2 = getView().findViewById(R.id.execard08_seccount);

        cd09_0 = getView().findViewById(R.id.execard09_mincount);
        cd09_1 = getView().findViewById(R.id.execard09_setscount);
        cd09_2 = getView().findViewById(R.id.execard09_seccount);

        //instruction
        i1 = getView().findViewById(R.id.instruction_01);
        i2 = getView().findViewById(R.id.instruction_02);
        i3 = getView().findViewById(R.id.instruction_03);
        i4 = getView().findViewById(R.id.instruction_04);
        i5 = getView().findViewById(R.id.instruction_05);
        i6 = getView().findViewById(R.id.instruction_06);
        i7 = getView().findViewById(R.id.instruction_07);
        i8 = getView().findViewById(R.id.instruction_08);
        i9 = getView().findViewById(R.id.instruction_09);

        workout = getView().findViewById(R.id.workout_goal);
        kcal = getView().findViewById(R.id.kcal_goal);
        min = getView().findViewById(R.id.min_goal);

    }

    private void displayExercise(Set<Integer> randomfive) {
        if (randomfive.contains(1)) {
            c1.setVisibility(View.VISIBLE);
        } else {
            c1.setVisibility(View.GONE);
        }
        if (randomfive.contains(2)) {
            c2.setVisibility(View.VISIBLE);
        } else {
            c2.setVisibility(View.GONE);
        }
        if (randomfive.contains(3)) {
            c3.setVisibility(View.VISIBLE);
        } else {
            c3.setVisibility(View.GONE);
        }
        if (randomfive.contains(4)) {
            c4.setVisibility(View.VISIBLE);
        } else {
            c4.setVisibility(View.GONE);
        }
        if (randomfive.contains(5)) {
            c5.setVisibility(View.VISIBLE);
        } else {
            c5.setVisibility(View.GONE);
        }
        if (randomfive.contains(6)) {
            c6.setVisibility(View.VISIBLE);
        } else {
            c6.setVisibility(View.GONE);
        }
        if (randomfive.contains(7)) {
            c7.setVisibility(View.VISIBLE);
        } else {
            c7.setVisibility(View.GONE);
        }
        if (randomfive.contains(8)) {
            c8.setVisibility(View.VISIBLE);
        } else {
            c8.setVisibility(View.GONE);
        }
        if (randomfive.contains(9)) {
            c9.setVisibility(View.VISIBLE);
        } else {
            c9.setVisibility(View.GONE);
        }


    }

    private void showSetsAndTime() {
        int setnum = getnumberOfSets();
        int i = 50 * setnum;
        cd01_0.setText(Integer.toString(i / 60));
        cd01_2.setText(Integer.toString(i % 60));
        i = 65 * setnum;
        cd02_0.setText(Integer.toString(i / 60));
        cd02_2.setText(Integer.toString(i % 60));
        i = 95 * setnum;
        cd03_0.setText(Integer.toString(i / 60));
        cd03_2.setText(Integer.toString(i % 60));
        i = 100 * setnum;
        cd04_0.setText(Integer.toString(i / 60));
        cd04_2.setText(Integer.toString(i % 60));
        i = 70 * setnum;
        cd05_0.setText(Integer.toString(i / 60));
        cd05_2.setText(Integer.toString(i % 60));
        i = 65 * setnum;
        cd06_0.setText(Integer.toString(i / 60));
        cd06_2.setText(Integer.toString(i % 60));
        i = 120 * setnum;
        cd07_0.setText(Integer.toString(i / 60));
        cd07_2.setText(Integer.toString(i % 60));
        i = 150 * setnum;
        cd08_0.setText(Integer.toString(i / 60));
        cd08_2.setText(Integer.toString(i % 60));
        i = 150 * setnum;
        cd09_0.setText(Integer.toString(i / 60));
        cd09_2.setText(Integer.toString(i % 60));


        cd01_1.setText("" + setnum);
        cd02_1.setText("" + setnum);
        cd03_1.setText("" + setnum);
        cd04_1.setText("" + setnum);
        cd05_1.setText("" + setnum);
        cd06_1.setText("" + setnum);
        cd07_1.setText("" + setnum);
        cd08_1.setText("" + setnum);
        cd09_1.setText("" + setnum);

        //TODO UPDATE TODAYS GOAL
        updateGoals(set);
    }

    private int getnumberOfSets() {
        SharedPreferences mySharedPreference = getActivity().getSharedPreferences(SHARED, MODE_PRIVATE);
        int setnum = mySharedPreference.getInt(NUMBER_OF_SETS, 1);
        if (setnum == 0) setnum = 1;
        return setnum;
    }

    private void updateGoals(Set<Integer> set) {
        workout.setText("" + set.size());
        int k = 0, sec = 0;
        if (set.contains(1)) {
            k += 0;
            sec += 50;
            Log.d("INFO", "1:" + sec);
        }
        if (set.contains(2)) {
            k += 0;
            sec += 65;
            Log.d("INFO", "2:" + sec);
        }
        if (set.contains(3)) {
            k += 0;
            sec += 95;
            Log.d("INFO", "3:" + sec);
        }
        if (set.contains(4)) {
            k += 0;
            sec += 100;
            Log.d("INFO", "4:" + sec);
        }
        if (set.contains(5)) {
            k += 0;
            sec += 70;
            Log.d("INFO", "5:" + sec);
        }
        if (set.contains(6)) {
            k += 0;
            sec += 65;
            Log.d("INFO", "6:" + sec);
        }
        if (set.contains(7)) {
            k += 0;
            sec += 120;
            Log.d("INFO", "7:" + sec);
        }
        if (set.contains(8)) {
            k += 0;
            sec += 150;
            Log.d("INFO", "8:" + sec);
        }
        if (set.contains(9)) {
            k += 0;
            sec += 150;
            Log.d("INFO", "9:" + sec);
        }

        int setnum = getnumberOfSets();
        Log.d("INFO", "min:" + (sec / 60) * setnum);
        kcal.setText(Integer.toString(k * setnum));
        min.setText(Integer.toString((sec * setnum) / 60));

    }


    private void showSelectSetsDialog() {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_custom_dialog_sets);
        numberPicker = dialog.findViewById(R.id.numberpicker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(25);

        ok = dialog.findViewById(R.id.button_sets_ok);
        cancel = dialog.findViewById(R.id.button_sets_cancel);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences mySharedPreference = getActivity().getSharedPreferences(SHARED, MODE_PRIVATE);
                SharedPreferences.Editor editor = mySharedPreference.edit();
                editor.putInt(NUMBER_OF_SETS, numberOfSets);
                editor.apply();

                showSetsAndTime();
                Log.d("LOG", "TIME AND SETS UPDATED");
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                numberOfSets = numberPicker.getValue();
                Log.d("PICKER VALUE", numberOfSets + "");
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.instruction_01:
            case R.id.instruction_02:
            case R.id.instruction_03:
            case R.id.instruction_04:
            case R.id.instruction_05:
            case R.id.instruction_06:
            case R.id.instruction_07:
            case R.id.instruction_08:
            case R.id.instruction_09: {
                display_instruction();
            }
            break;
        }
    }

    public void display_instruction() {

        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_custom_dialog_instruction);

        ok = dialog.findViewById(R.id.button_instruction_ok);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();

    }

    public Set<Integer> randomfive() {
        Random randNum = new Random();
        set = new LinkedHashSet<Integer>();
        while (set.size() < 5) {
            set.add(randNum.nextInt(9) + 1);
        }


        return set;
    }
}
