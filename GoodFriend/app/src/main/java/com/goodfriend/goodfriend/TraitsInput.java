package com.goodfriend.goodfriend;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;



public class TraitsInput extends AppCompatActivity {


    //key for privacy preferences
    public static final String PREFKEY = "pref";
    //key for init state
    public static final String INITKEY = "init";
    //key for initial launch time stamp
    public static final String TIMEKEY = "time";
    //key for aided/unaided
    public static final String AIDKEY = "aid";
    //key for current user state
    public static final String STATEKEY = "state";

    private SeekBar traitsBar = null;
    private TextView traitBar = null;
    private TextView traitOne = null;
    private EditText traitTextInput = null;
    private Button submitButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traits_input);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        traitsBar = (SeekBar) findViewById(R.id.seek1);
        traitBar = (TextView) findViewById(R.id.traitBar);
        submitButton = (Button) findViewById(R.id.buttonInput);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                //replace with storage or transmission of key value pair
            }
        });


        traitsBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                traitBar.setText("Stress Level: " + progressChanged);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int trait = -1;
                try {
                    trait = Integer.parseInt(traitTextInput.getText().toString());
                    if (trait > 10) {
                        throw new NumberFormatException();
                    }

                    SharedPreferences session = getSharedPreferences(PREFKEY, Context.MODE_PRIVATE);
                    long startTime = session.getLong(TIMEKEY, -1);
                    long currentTime = System.currentTimeMillis();
                    //divide by 1000 for ms->s then by 86400 for s->days
                    long days = ((currentTime - startTime) / 1000) / 86400;
                    NicotineHabit h = new NicotineHabit(session.getBoolean(AIDKEY, true));
                    h.recalculateState((int) days, trait);
                    Habit.UserState x = h.getState();

                    SharedPreferences.Editor editor = getSharedPreferences(PREFKEY, Context.MODE_PRIVATE).edit();
                    editor.putBoolean(INITKEY, Boolean.TRUE);




                } catch (NumberFormatException e) {
                }

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



}
