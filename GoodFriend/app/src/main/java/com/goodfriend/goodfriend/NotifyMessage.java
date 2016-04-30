package com.goodfriend.goodfriend;

/**
 * Created by Christopher Frazier on 4/30/2016.
 */
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class NotifyMessage extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView txt=new TextView(this);

        txt.setText("Activity after click on notification");
        setContentView(txt);
    }
}