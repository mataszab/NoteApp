package com.example.noteapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeleteNoteActivity extends AppCompatActivity {

    ArrayList<Notes> arrayList;
    ArrayList<String> newNoteList;
    Spinner spinner;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_note);

        spinner = findViewById(R.id.spDel);
        bundle = getIntent().getExtras();
        arrayList = bundle.getParcelableArrayList("arrayList");

        SharedPreferences sharedPref = this.getSharedPreferences(Constants.NOTES_FILE, this.MODE_PRIVATE);
        String json = sharedPref.getString(Constants.NOTE_LIST_KEY, null);

        String shortJson;         //Stores a single filtered name from json string
        String longJson = json;   //Stores what's left without a filtered name
        int count = 0;
        newNoteList = new ArrayList<>();

        //Defining repeating elements in json string
        //json = [{"Desc":"First message","Name":"First"},{"Desc":"Second message","Name":"Second "},{"Desc":"Third message","Name":"Third"}]
        String beforeName = "\",\"Name\":\"";   //Text before notes name
        String afterName = "\"},{\"Desc\":\"";  //Text after notes name
        String afterNameLast = "\"}]";          //Text after notes name (last in string)

        //Finding how many notes are created
        Pattern pattern = Pattern.compile(beforeName);
        Matcher matcher = pattern.matcher(json);
        while (matcher.find()) count++;

        //Filtering NOTE NAMES from json to newNoteList
        while (longJson.contains(beforeName)) {
            if (count == 1) {
                shortJson = longJson.substring(longJson.indexOf(beforeName) + beforeName.length(), longJson.indexOf(afterNameLast));
                longJson = afterNameLast;
                newNoteList.add(shortJson);
            }
            else {
                shortJson = longJson.substring(longJson.indexOf(beforeName) + beforeName.length(), longJson.indexOf(afterName));
                longJson = longJson.substring(longJson.indexOf(afterName) + afterName.length());
                newNoteList.add(shortJson);
                count--;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, newNoteList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        if (spinner != null && spinner.getSelectedItem() != null) {
            //Do nothing..
        }
        else {
            spinner.setEnabled(false);
        }
    }

    public void onBtnDeleteClick(View view) {
        if (spinner != null && spinner.getSelectedItem() != null) {
            String spinnerSelectedItem = spinner.getSelectedItem().toString();
            int indexToDelete = newNoteList.indexOf(spinnerSelectedItem);
            arrayList.remove(indexToDelete);

            SharedPreferences sharedPref = getSharedPreferences(Constants.NOTES_FILE, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            Gson gson = new Gson();
            String json = gson.toJson(arrayList);

            editor.putString(Constants.NOTE_LIST_KEY, json);
            editor.apply();
            finish();
            Log.e("onBtnDeleteClick", "Note successfully deleted!");
        }
        else {
            Snackbar.make(view, "There are no notes to delete!", Snackbar.LENGTH_SHORT).show();
        }
    }
}
