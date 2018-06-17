package ru.nikmac10x.smartservice;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;

public class SmartSpaceActivity extends AppCompatActivity {

    private static final String TAG = "myLogs";

    TextView status;

    ListView raspberryLS;

    Button translationBT;
    Button aboutMeBT;

    private int RASPBERRY_POS = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_space);

        aboutMeBT = (Button) findViewById(R.id.aboutMeBT);
        raspberryLS = (ListView) findViewById(R.id.raspberryLS);
        status = (TextView) findViewById(R.id.string_status_id);

        status.setText(Status.getCurrentStatus());



        aboutMeBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SmartSpaceActivity.this, DataActivity.class);
                Status.setStatus(R.string.input_user_data_status);
                startActivity(intent);
            }
        });




        //Получение списка Raspberry
        final ArrayList<Raspberry> listOfRaspberry = SSAgent.getListOfRaspberry();

        //Вывод списка Raspberry
        if (listOfRaspberry != null && !listOfRaspberry.isEmpty()) {
            ArrayAdapter<Raspberry> adapter = new ArrayAdapter<Raspberry>(this, android.R.layout.simple_list_item_checked, listOfRaspberry);
            raspberryLS.setAdapter(adapter);
        }






        translationBT = (Button) findViewById(R.id.translationBT);
        translationBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "start translation");
                SSAgent.startTranslation(listOfRaspberry.get(RASPBERRY_POS), v);
            }
        });

        raspberryLS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                RASPBERRY_POS = position;
                translationBT.setVisibility(View.VISIBLE);
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        SSAgent.ssLeave();
    }
}