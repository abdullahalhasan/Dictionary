package com.abdullahalhasan.dictionary.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.abdullahalhasan.dictionary.Classes.DictionaryDatabaseHelper;
import com.abdullahalhasan.dictionary.Classes.DictionaryLoader;
import com.abdullahalhasan.dictionary.Classes.WordDefinition;
import com.abdullahalhasan.dictionary.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Home extends AppCompatActivity {
    TextView userTextView;
    EditText searchEditText;
    Button searchButton;
    ListView dictionaryListView;
    WordDefinition wordDefinition;
    String logTagString="DICTIONARY";
    ArrayList<WordDefinition> allWordDefinitions=new ArrayList<WordDefinition>();


    DictionaryDatabaseHelper myDictionaryDatabaseHelper;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Log.d("DICTIONARY", "second activity started");


        /*userTextView=(TextView) findViewById(R.id.personTextView);
        userTextView.setText(getIntent().getStringExtra(MainActivity.USER_NAME_STRING)+"'s Dictionary");*/

        searchEditText=(EditText) findViewById(R.id.editText);
        searchButton=(Button) findViewById(R.id.searchBT);
        dictionaryListView=(ListView) findViewById(R.id.listView);

        myDictionaryDatabaseHelper=new DictionaryDatabaseHelper(this, "Dictionary", null, 2);
        sharedPreferences=getSharedPreferences(Welcome.SHARED_NAME_STRING, MODE_PRIVATE);


        boolean initialized=sharedPreferences.getBoolean("initialized", false);

        if (initialized==false) {
            //Log.d(logTagString, "initializing for the first time");
            initializeDatabase();
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putBoolean("initialized", true);
            editor.commit();

        }else {
            Log.d(logTagString, "db already initialized");
        }

        allWordDefinitions=myDictionaryDatabaseHelper.getAllWords();

        dictionaryListView.setAdapter(new BaseAdapter() {

            @Override
            public View getView(int position, View view, ViewGroup arg2) {
                if (view==null) {
                    view=getLayoutInflater().inflate(R.layout.list_item, null);
                }
                TextView textView=(TextView) view.findViewById(R.id.listItemTextView);
                textView.setText(allWordDefinitions.get(position).getWord());

                return view;
            }

            @Override
            public long getItemId(int arg0) {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public Object getItem(int arg0) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return allWordDefinitions.size();
            }
        });

        dictionaryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position,
                                    long arg3) {
                Intent intent =new Intent(Home.this, Definition.class);
                intent.putExtra("word", allWordDefinitions.get(position).getWord());
                intent.putExtra("definition", allWordDefinitions.get(position).getDefinition());

                startActivity(intent);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String string=searchEditText.getText().toString().trim();

                wordDefinition=myDictionaryDatabaseHelper.getWordDefinition(string);

                if (wordDefinition==null) {
                    Toast.makeText(Home.this, string+" not found", Toast.LENGTH_LONG).show();
                }else {
                    Intent intent =new Intent(Home.this, Definition.class);
                    intent.putExtra("word", wordDefinition.getWord());
                    intent.putExtra("definition", wordDefinition.getDefinition());
                    startActivity(intent);
                }
                searchEditText.getText().clear();
            }
        });
    }

    private void initializeDatabase() {
        InputStream inputStream=getResources().openRawResource(R.raw.dictionary);
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
        DictionaryLoader.loadData(bufferedReader, myDictionaryDatabaseHelper);

    }
}
