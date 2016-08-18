package com.abdullahalhasan.dictionary.Classes;

import java.util.ArrayList;

/**
 * Created by Abdullah Al Hasan on 8/18/2016.
 */
public class WordDefinition {

    private String word;
    private String definition;

    public WordDefinition(String word, String definition) {
        this.word = word;
        this.definition = definition;
    }

    public WordDefinition(String word, ArrayList<String> alldefinition) {
        this.word = word;
        StringBuilder stringBuilder = new StringBuilder();
        for (String string: alldefinition) {
            stringBuilder.append(string);

        }
        this.definition = stringBuilder.toString();

    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
}
