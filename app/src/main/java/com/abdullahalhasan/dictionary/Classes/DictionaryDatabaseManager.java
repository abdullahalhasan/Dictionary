package com.abdullahalhasan.dictionary.Classes;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Abdullah Al Hasan on 8/18/2016.
 */

public class DictionaryDatabaseManager {
    DictionaryDatabaseHelper dictionaryDatabaseHelper;

    public long insertData(WordDefinition wordDefinition) {

        SQLiteDatabase database = dictionaryDatabaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(dictionaryDatabaseHelper.WORD_COLUMN,wordDefinition.getWord());
        values.put(dictionaryDatabaseHelper.DEFINITION_COLUMN,wordDefinition.getDefinition());

        return database.insert(dictionaryDatabaseHelper.DICTIONARY_DATABASE,null,values);

    }
    public long updateData(WordDefinition wordDefinition) {

        SQLiteDatabase database = dictionaryDatabaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(dictionaryDatabaseHelper.WORD_COLUMN,wordDefinition.getWord());
        values.put(dictionaryDatabaseHelper.DEFINITION_COLUMN,wordDefinition.getDefinition());

        return database.update(dictionaryDatabaseHelper.DICTIONARY_DATABASE,values,
                dictionaryDatabaseHelper.WORD_COLUMN+" =?",new String[]{wordDefinition.getWord()});

    }
    public void deleteData(WordDefinition wordDefinition) {

        SQLiteDatabase database = dictionaryDatabaseHelper.getWritableDatabase();
        String queryString = "DELETE FROM "+dictionaryDatabaseHelper.DICTIONARY_DATABASE+" WHERE "+dictionaryDatabaseHelper.WORD_COLUMN+" = '"+wordDefinition.getWord()+"' ";
        database.execSQL(queryString);
    }

    public ArrayList<WordDefinition> getAllWords() {

        ArrayList<WordDefinition> arrayList = new ArrayList();
        SQLiteDatabase database = dictionaryDatabaseHelper.getReadableDatabase();

        String selectAllQuery = "SELECT * FROM "+dictionaryDatabaseHelper.DICTIONARY_DATABASE;
        Cursor cursor =database.rawQuery(selectAllQuery,null);

        if (cursor.moveToFirst()) {
            do {
                WordDefinition wordDefinition = new WordDefinition
                        (cursor.getString(cursor.getColumnIndex(dictionaryDatabaseHelper.WORD_COLUMN)),
                                cursor.getString(cursor.getColumnIndex(dictionaryDatabaseHelper.DEFINITION_COLUMN)));
                arrayList.add(wordDefinition);
            }while (cursor.moveToNext());
        }
        return arrayList;
    }

    public WordDefinition getWordDefinition(String word) {
        SQLiteDatabase database = dictionaryDatabaseHelper.getReadableDatabase();
        WordDefinition wordDefinition = null;
        String selectQuery = "SELECT * FORM "+dictionaryDatabaseHelper.DICTIONARY_DATABASE+" WHERE "+dictionaryDatabaseHelper.WORD_COLUMN+" = '"+word+"'";
        Cursor cursor = database.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()) {
            wordDefinition = new WordDefinition
                    (cursor.getString(cursor.getColumnIndex(dictionaryDatabaseHelper.WORD_COLUMN)),cursor.getString(cursor.getColumnIndex(dictionaryDatabaseHelper.DEFINITION_COLUMN)));
        }

        return wordDefinition;
    }

    public WordDefinition getWordDefinition(long id) {
        SQLiteDatabase database = dictionaryDatabaseHelper.getReadableDatabase();
        WordDefinition wordDefinition = null;
        String selectQuery = "SELECT * FORM "+dictionaryDatabaseHelper.DICTIONARY_DATABASE+" WHERE "+dictionaryDatabaseHelper.ITEM_ID_COLUMN+" = '"+id+"'";
        Cursor cursor = database.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()) {
            wordDefinition = new WordDefinition
                    (cursor.getString(cursor.getColumnIndex(dictionaryDatabaseHelper.WORD_COLUMN)),cursor.getString(cursor.getColumnIndex(dictionaryDatabaseHelper.DEFINITION_COLUMN)));        }

        return wordDefinition;
    }
    public void initializeDatabaseFortheFirstTime( ArrayList<WordDefinition> wordDefinitions) {
        SQLiteDatabase database = dictionaryDatabaseHelper.getWritableDatabase();
        database.execSQL("BEGIN");
        ContentValues contentValues = new ContentValues();
        for (WordDefinition wordDefinition: wordDefinitions) {
            contentValues.put(dictionaryDatabaseHelper.WORD_COLUMN,wordDefinition.getWord());
            contentValues.put(dictionaryDatabaseHelper.DEFINITION_COLUMN,wordDefinition.getDefinition());
            database.insert(dictionaryDatabaseHelper.DICTIONARY_DATABASE,null,contentValues);
        }
        database.execSQL("COMMIT");
    }
}
