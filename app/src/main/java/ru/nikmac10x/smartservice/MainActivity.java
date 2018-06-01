package ru.nikmac10x.smartservice;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.projection.MediaProjectionManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "myLogs";

    Button joinBT;

    EditText ssipET;
    EditText ssportET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //получение объектов полей
        ssipET = (EditText) findViewById(R.id.ssipET);
        ssportET = (EditText) findViewById(R.id.ssportET);

        //получение объектов кнопок
        joinBT = (Button) findViewById(R.id.joinBT);

        //обработчик кнопки "Подключиться"
        joinBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Проверка ввода адреса
                String ssip = ssipET.getText().toString();
                if (ssip.matches("^[0-9]{1,3}(\\.[0-9]{1,3}){3}$")) {
                    //получение номера порта из поля ввода
                    int ssport = Integer.parseInt(ssportET.getText().toString());

                    //инициализируем класс для работы с ИП
                    Log.d(TAG, "ssInit");
                    SSAgent.ssInit(ssip, ssport);
                    //подключение к ИП
                    Log.d(TAG, "ssJoin..");
                    if (SSAgent.ssJoin()) {
                        //запуск новой activity
                        Log.d(TAG, "start activity..");
                        Intent intent = new Intent(MainActivity.this, SmartSpaceActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Не удалось подключиться", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Введите корректный ip адрес", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}
