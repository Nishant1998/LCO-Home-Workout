package com.example.lcohomeworkout.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.example.lcohomeworkout.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    private CardView c1;
    private BottomNavigationView bottomNavigationView;
    private Dialog dialog;
    private Button ok, cancel;
    private NumberPicker numberPicker;
    private int numberOfSets;

    private TextView cd01_0,cd01_1;

    private static final String SHARED = "WORKOUT_SHARED_DATA";
    private static final String NUMBER_OF_SETS = "SETS";
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

        SharedPreferences mySharedPreference = getActivity().getSharedPreferences(SHARED,MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreference.edit();
        editor.putInt(NUMBER_OF_SETS,1);
        editor.apply();


        c1 = getView().findViewById(R.id.execard01);
        bottomNavigationView = getView().findViewById(R.id.bottom_navigation);

        //find textview of cards
        cd01_0 = getView().findViewById(R.id.execard01_mincount);
        cd01_1 = getView().findViewById(R.id.execard01_setscount);


        c1.setOnClickListener(this);


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

                    }
                    break;
                    case R.id.bottom_nav_sets: {
                        Log.d("TAG", "SETS SELECTED");

                        showSelectSetsDialog();


                    }
                    break;
                    case R.id.bottom_nav_random: {
                        Log.d("TAG", "RANDOM SELECTED");
                    }
                    break;
                }
                return true;
            }
        });

    }

    private void showSetsAndTime() {
        SharedPreferences mySharedPreference = getActivity().getSharedPreferences(SHARED,MODE_PRIVATE);
        int setnum = mySharedPreference.getInt(NUMBER_OF_SETS,0);


        cd01_0.setText(Integer.toString(2*setnum));
        cd01_1.setText("" + setnum);

        //TODO UPDATE TODAYS GOAL

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
                SharedPreferences mySharedPreference = getActivity().getSharedPreferences(SHARED,MODE_PRIVATE);
                SharedPreferences.Editor editor = mySharedPreference.edit();
                editor.putInt(NUMBER_OF_SETS,numberOfSets);
                editor.apply();

                showSetsAndTime();
                Log.d("LOG" ,"TIME AND SETS UPDATED");
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
            case R.id.execard01: {
//                    Intent intent = new Intent(getActivity(), Exercise.class);
//                    startActivity(intent);
            }
            break;
        }
    }
}
