package com.abdullahalhasan.dictionary.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.abdullahalhasan.dictionary.R;

public class Definition extends AppCompatActivity {
    TextView wordTextView;
    TextView definitionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definition);
        wordTextView=(TextView) findViewById(R.id.wordTextView);
        definitionTextView=(TextView) findViewById(R.id.definitionTextView);

        Log.d("DICTIONARY", "third activity started");

        wordTextView.setText(getIntent().getStringExtra("word"));
        definitionTextView.setText(getIntent().getStringExtra("definition"));

    }

}
