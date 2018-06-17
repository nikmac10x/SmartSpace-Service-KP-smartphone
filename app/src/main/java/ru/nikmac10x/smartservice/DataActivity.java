package ru.nikmac10x.smartservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DataActivity extends AppCompatActivity {

    private static final String TAG = "myLogs";

    TextView status;

    Button saveInfoBT;

    EditText userNameET;
    EditText userEmailET;
    EditText descriptionET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        userNameET = (EditText) findViewById(R.id.userNameET);
        userEmailET = (EditText) findViewById(R.id.userEmailET);
        descriptionET = (EditText) findViewById(R.id.descriptionET);
        saveInfoBT = (Button) findViewById(R.id.saveInfoBT);
        status = (TextView) findViewById(R.id.string_status_id);

        status.setText(Status.getCurrentStatus());
        userNameET.setText(UserData.getUserName());
        userEmailET.setText(UserData.getUserEmail());
        descriptionET.setText(UserData.getTranslationDescription());

        saveInfoBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = userNameET.getText().toString();
                String email = userEmailET.getText().toString();
                String description = descriptionET.getText().toString();

                if (name != "" && email != "" && description != "") {
                    UserData.setUserName(name);
                    UserData.setUserEmail(email);
                    UserData.setTranslationDescription(description);

                    Log.d(TAG, "user data read");

                    Status.setStatus(R.string.saved_user_data_status);
                    status.setText(Status.getCurrentStatus());
                }
            }
        });
    }

}
