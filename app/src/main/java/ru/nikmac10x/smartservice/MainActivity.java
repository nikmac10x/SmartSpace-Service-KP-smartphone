package ru.nikmac10x.smartservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button joinBT;
    Button aboutMeBT;

    EditText ssipTV;
    EditText ssportTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ssipTV = (EditText) findViewById(R.id.ssipET);
        ssportTV = (EditText) findViewById(R.id.ssportET);

        joinBT = (Button) findViewById(R.id.joinBT);
        aboutMeBT = (Button) findViewById(R.id.aboutMeBT);

        joinBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SmartSpaceActivity.class);
                startActivity(intent);
            }
        });

        aboutMeBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DataActivity.class);
                startActivity(intent);
            }
        });
    }
}
