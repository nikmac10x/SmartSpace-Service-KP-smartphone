package ru.nikmac10x.smartservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SmartSpaceActivity extends AppCompatActivity {

    ListView raspberryLS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_space);

        //Получение объкта списка
        raspberryLS = (ListView) findViewById(R.id.raspberryLS);


        //Получение списка Raspberry
        ArrayList<Raspberry> listOfRaspberry = SSAgent.getListOfRaspberry();

        //Вывод списка Raspberry
        ArrayAdapter<Raspberry> adapter = new ArrayAdapter<Raspberry>(this, android.R.layout.simple_list_item_1, listOfRaspberry);
        raspberryLS.setAdapter(adapter);

    }
}