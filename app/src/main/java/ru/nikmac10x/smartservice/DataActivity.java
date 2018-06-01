package ru.nikmac10x.smartservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DataActivity extends AppCompatActivity {

    Button saveInfoBT;

    EditText firstNameET;
    EditText secondNameET;
    EditText descriptionET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        firstNameET = (EditText) findViewById(R.id.firstNameET);
        secondNameET = (EditText) findViewById(R.id.secondNameET);
        descriptionET = (EditText) findViewById(R.id.descriptionET);
        saveInfoBT = (Button) findViewById(R.id.saveInfoBT);

        saveInfoBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
