package com.goodfriend.goodfriend;

import android.content.Context;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

    private Button smokeButton = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        //Following is to check if app has been launched before
        SharedPreferences session = this.getSharedPreferences(PREFKEY, Context.MODE_PRIVATE);
        //Has this app been launched before?
        boolean initialized = session.getBoolean(INITKEY, Boolean.FALSE);
        if(!initialized){
            setContentView(R.layout.habit_select);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            smokeButton = (Button) findViewById(R.id.buttonHabitSmoking);
            smokeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SharedPreferences.Editor editor = getSharedPreferences(PREFKEY, Context.MODE_PRIVATE).edit();

                    //perform initialization actions
                    //store current timestamp for future use
                    editor.putLong(TIMEKEY, System.currentTimeMillis());
                    editor.commit();

                    //user starts as normal state
                    editor.putString(STATEKEY, Habit.UserState.NORMAL.toString());
                    editor.commit();
                    userState = Habit.UserState.NORMAL;
                    
                    Intent intent = new Intent(getApplicationContext(), SmokeAidSelect.class);
                    startActivity(intent);
                }
            });
        }
        else {

            setContentView(R.layout.activity_main);
            /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);*/
            Toolbar mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle(getDayOfWeek());
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        }
        

    }
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
   // }

    public String getDayOfWeek(){
        String weekday_name = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(System.currentTimeMillis());
        return weekday_name;
    }

    @Override
    protected void onResume(){
        super.onResume();

        SharedPreferences session = getSharedPreferences(PREFKEY, Context.MODE_PRIVATE);

        boolean initialized = session.getBoolean(INITKEY, Boolean.FALSE);
        if(initialized) {
            String text = session.getString(STATEKEY, "Unknown");
            if (text.equals("NORMAL"))
                text = "Normal";
            else if (text.equals("MED_RISK"))
                text = "Some Risk";
            else if (text.equals("HIGH_RISK"))
                text = "High Risk";

            TextView box1_subtext = (TextView) findViewById(R.id.box1_subtext);
            box1_subtext.setText(text);

        System.out.println(initialized + " above method");

            System.out.println("inside method");
            TextView dayCounter = (TextView) findViewById(R.id.text1);
            //get the current time and the time at initialization

            long startTime = session.getLong(TIMEKEY, -1);
            long currentTime = System.currentTimeMillis();
            //divide by 1000 for ms->s then by 86400 for s->days
            long days = ((currentTime - startTime) / 1000) / 86400;
            dayCounter.setText(days + "");
        }
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
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.mipmap.heart)
                            .setContentTitle("My notification")
                            .setContentText("Hello World!");
            Intent resultIntent = new Intent(this, NotifyMessage.class);
            // Because clicking the notification opens a new ("special") activity, there's
            // no need to create an artificial back stack.
            PendingIntent resultPendingIntent =
                    PendingIntent.getActivity(
                            this,
                            0,
                            resultIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );

            mBuilder.setContentIntent(resultPendingIntent);

            // Sets an ID for the notification
            int mNotificationId = 001;
            // Gets an instance of the NotificationManager service
            NotificationManager mNotifyMgr =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            // Builds the notification and issues it.
            mNotifyMgr.notify(mNotificationId, mBuilder.build());

            return true;
        }
        else if (id == R.id.setStress){
            Intent intent = new Intent(getApplicationContext(), TraitsInput.class);
            startActivity(intent);
        }
        

        return super.onOptionsItemSelected(item);
    }



    public void sendNotification()
    {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.heart)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");
        Intent resultIntent = new Intent(this, TraitsInput.class);
        // Because clicking the notification opens a new ("special") activity, there's
        // no need to create an artificial back stack.
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        mBuilder.setContentIntent(resultPendingIntent);

        // Sets an ID for the notification
        int mNotificationId = 001;
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());

    }
    public void statusNotificationChecker() {
        if(true){
        sendNotification();
        }
    }
}
