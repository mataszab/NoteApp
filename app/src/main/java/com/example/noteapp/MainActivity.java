package com.example.noteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    ListView lvNotes;
    ArrayList<Notes> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvNotes = findViewById(R.id.lvNotes);
        SharedPreferences sharedPref = this.getSharedPreferences(Constants.NOTES_FILE, this.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPref.getString(Constants.NOTE_LIST_KEY, null);

        Type type = new TypeToken<ArrayList<Notes>>() {}.getType();
        arrayList = gson.fromJson(json, type);

        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }

        NotesAdapter noteAdapter = new NotesAdapter(this, R.layout.list_row, arrayList);
        lvNotes.setAdapter(noteAdapter);
        Log.e("App", "NoteApp successfully started!");
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sharedPref = this.getSharedPreferences(Constants.NOTES_FILE, this.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPref.getString(Constants.NOTE_LIST_KEY, null);

        Type type = new TypeToken<ArrayList<Notes>>() {}.getType();
        arrayList = gson.fromJson(json,type);

        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }

        NotesAdapter noteAdapter = new NotesAdapter(this, R.layout.list_row, arrayList);
        lvNotes.setAdapter(noteAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.notes_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_note:
                Intent i1 = new Intent(this, AddNoteActivity.class);

                Bundle bundle1 = new Bundle();
                bundle1.putParcelableArrayList("arrayList", arrayList);
                i1.putExtras(bundle1);

                startActivity(i1);
                return true;
            case R.id.delete_note:
                Intent i2 = new Intent(this, DeleteNoteActivity.class);

                Bundle bundle2 = new Bundle();
                bundle2.putParcelableArrayList("arrayList", arrayList);
                i2.putExtras(bundle2);

                startActivity(i2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}