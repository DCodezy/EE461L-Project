package com.goodfriend.goodfriend;

import android.content.Context;
import android.content.Intent;
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
    //key for last stress input
    public static final String STRESSKEY = "stress";

    private SeekBar traitsBar = null;
    private TextView traitBar = null;
    private Button submitButton = null;

    public void returnToSender(){
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traits_input);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //create references for UI elements
        traitsBar = (SeekBar) findViewById(R.id.seek1);
        traitBar = (TextView) findViewById(R.id.traitBar);
        submitButton = (Button) findViewById(R.id.buttonInput);

        // below is commented out for future (possible) use. floating action button at bottom right (mail icon)
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                //replace with storage or transmission of key value pair
            }
        });

        //action listener for stress seek bar: updates text field below as user scrolls
        traitsBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                traitBar.setText(progressChanged+"");
            }
        });

        //once user selects this:
        //          - Calculate new user state
        //          - Store new data
        //          - Close activity and return to main activity
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int trait = -1;
                try {
                    String text = traitBar.getText().toString();
                    trait = Integer.parseInt(text);
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

                    SharedPreferences.Editor editor = getSharedPreferences(PREFKEY, Context.MODE_PRIVATE).edit();
                    editor.putString(STATEKEY, h.getState().toString());
                    editor.commit();

                    //set stress state
                    editor.putInt(STRESSKEY, trait);
                    editor.commit();
                    finish();

                } catch (NumberFormatException e) {
                    System.out.println("FAILURE");
                }

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



}
