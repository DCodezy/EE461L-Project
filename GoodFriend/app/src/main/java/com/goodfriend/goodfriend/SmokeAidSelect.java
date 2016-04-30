package com.goodfriend.goodfriend;

import android.content.Intent;
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
                if (aidRadioGroup.getCheckedRadioButtonId() == R.id.radioAided){
                    //select aided
                }
                else if (aidRadioGroup.getCheckedRadioButtonId() == R.id.radioUnaided){
                    //select unaided
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        } );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
