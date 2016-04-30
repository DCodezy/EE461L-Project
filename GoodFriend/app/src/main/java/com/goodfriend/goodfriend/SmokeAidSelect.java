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
import android.widget.RadioGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class SmokeAidSelect extends AppCompatActivity {

    private Button aidButton = null;
    private RadioGroup aidRadioGroup = null;

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

    private static Habit.UserState userState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smoke_aid_select);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        aidButton = (Button) findViewById(R.id.aidSelectButton);
        aidRadioGroup = (RadioGroup) findViewById(R.id.radioGroupAid);
        aidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences(PREFKEY, Context.MODE_PRIVATE).edit();
                if (aidRadioGroup.getCheckedRadioButtonId() == R.id.radioAided){
                    //store aided key

                    editor.putBoolean(AIDKEY, true);
                    editor.commit();

                    //user starts as normal state
                    editor.putString(STATEKEY, Habit.UserState.NORMAL.toString());
                    editor.commit();
                    userState = Habit.UserState.NORMAL;
                }
                else if (aidRadioGroup.getCheckedRadioButtonId() == R.id.radioUnaided){
                    //select unaided
                    //store aided key

                    editor.putBoolean(AIDKEY, false);
                    editor.commit();

                    //user starts as normal state
                    editor.putString(STATEKEY, Habit.UserState.NORMAL.toString());
                    editor.commit();
                    userState = Habit.UserState.NORMAL;
                }

                editor.putBoolean(INITKEY, Boolean.TRUE);

                editor.commit();
                System.out.println("nnnnnnnnn\n");
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        } );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
