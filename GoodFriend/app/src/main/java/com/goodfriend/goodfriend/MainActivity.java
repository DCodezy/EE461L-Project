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
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

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

        //Following is to check if app has been launched before
        SharedPreferences session = this.getSharedPreferences(PREFKEY, Context.MODE_PRIVATE);
        //Has this app been launched before?
        boolean initialized = session.getBoolean(INITKEY, Boolean.FALSE);
        if(!initialized){

            //set initialized to true
            SharedPreferences.Editor editor = this.getSharedPreferences(PREFKEY, Context.MODE_PRIVATE).edit();
            editor.putBoolean(INITKEY, Boolean.TRUE);
            editor.commit();

            //perform initialization actions
            //store current timestamp for future use
            editor.putLong(TIMEKEY, System.currentTimeMillis());
            editor.commit();

            //store aided key
            editor.putBoolean(AIDKEY, true);
            editor.commit();

            //user starts as normal state
            editor.putString(STATEKEY, Habit.UserState.NORMAL.toString());
            editor.commit();
            userState = Habit.UserState.NORMAL;
        }


        setContentView(R.layout.activity_main);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        Toolbar mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Tuesday");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button tempButton = (Button) findViewById(R.id.tempButton);

        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TraitsInput.class);
                startActivity(intent);
            }
        } );

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @Override
    protected void onResume(){
        super.onResume();
        TextView dayCounter = (TextView) findViewById(R.id.text1);
        //get the current time and the time at initialization
        SharedPreferences session = getSharedPreferences(PREFKEY, Context.MODE_PRIVATE);
        long startTime = session.getLong(TIMEKEY, -1);
        long currentTime = System.currentTimeMillis();
        //divide by 1000 for ms->s then by 86400 for s->days
        long days = ((currentTime - startTime)/1000)/86400;
        dayCounter.setText(days+"");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
