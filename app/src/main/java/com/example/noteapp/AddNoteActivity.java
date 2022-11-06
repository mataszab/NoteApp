package com.example.noteapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AddNoteActivity extends AppCompatActivity {

    EditText edAddNoteName;
    EditText edAddNoteText;
    ArrayList<Notes> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        this.edAddNoteName = findViewById(R.id.edNoteName);
        this.edAddNoteText = findViewById(R.id.edNoteDesc);

        Bundle bundle = getIntent().getExtras();
        arrayList = bundle.getParcelableArrayList("arrayList");
    }

    public void onBtnAddClick(View view) {
        String noteNameToAdd = this.edAddNoteName.getText().toString();
        String noteTextToAdd = this.edAddNoteText.getText().toString();

        if (noteNameToAdd.equals("") || noteTextToAdd.equals("")) {
            Snackbar.make(view, "Notes name or text field is empty!", Snackbar.LENGTH_SHORT).show();
        }
        else {
            arrayList.add(new Notes(noteNameToAdd, noteTextToAdd));

            SharedPreferences sharedPref = getSharedPreferences(Constants.NOTES_FILE, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            Gson gson = new Gson();
            String json = gson.toJson(arrayList);

            editor.putString(Constants.NOTE_LIST_KEY, json);
            editor.apply();
            finish();
            Log.e("onBtnAddClick", "Note successfully added!");
        }
    }
}
