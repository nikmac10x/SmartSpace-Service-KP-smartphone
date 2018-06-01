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

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;

public class SmartSpaceActivity extends AppCompatActivity {

    private static final String TAG = "myLogs";

    ListView raspberryLS;

    Button translationBT;
    Button aboutMeBT;
    Button leaveBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_space);

        leaveBT = (Button) findViewById(R.id.leaveBT);
        aboutMeBT = (Button) findViewById(R.id.aboutMeBT);
        raspberryLS = (ListView) findViewById(R.id.raspberryLS);

        aboutMeBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SmartSpaceActivity.this, DataActivity.class);
                startActivity(intent);
            }
        });

        //Получение списка Raspberry
        ArrayList<Raspberry> listOfRaspberry = SSAgent.getListOfRaspberry();

        //Вывод списка Raspberry
        if (listOfRaspberry != null && !listOfRaspberry.isEmpty()) {
            ArrayAdapter<Raspberry> adapter = new ArrayAdapter<Raspberry>(this, android.R.layout.simple_list_item_1, listOfRaspberry);
            raspberryLS.setAdapter(adapter);
        }

        translationBT = (Button) findViewById(R.id.translationBT);
        translationBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date now = new Date();
                android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

                try {
                    // image naming and path  to include sd card  appending name you choose for file
                    String mPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + now + ".jpg";

                    // create bitmap screen capture
                    View v1 = getWindow().getDecorView().getRootView();
                    v1.setDrawingCacheEnabled(true);
                    Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
                    v1.setDrawingCacheEnabled(false);

                    File imageFile = new File(mPath);

                    FileOutputStream outputStream = new FileOutputStream(imageFile);
                    int quality = 100;
                    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
                    outputStream.flush();
                    outputStream.close();
                    Log.d(TAG, "record");
                } catch (Throwable e) {
                    // Several error may come out with file handling or DOM
                    e.printStackTrace();
                }
            }
        });

        raspberryLS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                translationBT.setVisibility(View.VISIBLE);
            }
        });
    }
}