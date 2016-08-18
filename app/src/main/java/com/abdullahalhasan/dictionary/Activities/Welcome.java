package com.abdullahalhasan.dictionary.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.abdullahalhasan.dictionary.R;

public class Welcome extends AppCompatActivity {


    final static String SHARED_NAME_STRING="sharedp";
    final static String USER_NAME_STRING="user";

    Button button;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        button=(Button) findViewById(R.id.enterButton);

        Log.d("DICTIONARY", "main activity started");


        sharedPreferences=getSharedPreferences(SHARED_NAME_STRING, MODE_PRIVATE);
        String userNameString=sharedPreferences.getString(USER_NAME_STRING, "");

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(), Home.class);
//                intent.putExtra("user", string);

                SharedPreferences.Editor editor=sharedPreferences.edit();
  //              editor.putString(USER_NAME_STRING, string);
                editor.commit();
                startActivity(intent);
                finish();

            }
        });

    }

}
