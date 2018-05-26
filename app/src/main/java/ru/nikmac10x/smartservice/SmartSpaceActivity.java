package ru.nikmac10x.smartservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class SmartSpaceActivity extends AppCompatActivity {

    ListView raspberryLS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_space);

        raspberryLS = (ListView) findViewById(R.id.raspberryLS);

        SSAgent ssAgent = new SSAgent();
        ArrayAdapter<Raspberry> adapter = new ArrayAdapter<Raspberry>(this, android.R.layout.simple_list_item_1, ssAgent.getListOfRaspberry());

        raspberryLS.setAdapter(adapter);

    }
}
