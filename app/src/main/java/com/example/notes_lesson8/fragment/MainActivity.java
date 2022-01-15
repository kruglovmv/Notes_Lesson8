package com.example.notes_lesson8.fragment;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.notes_lesson8.R;
import com.example.notes_lesson8.data.Constants;
import com.example.notes_lesson8.data.Note;
import com.example.notes_lesson8.data.NoteList;
import com.example.notes_lesson8.data.Repo;

public class MainActivity extends AppCompatActivity implements NotesListFragment.Controller {
    Repo noteslist = NoteList.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fillList();
        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_list_fragment_holder, NotesListFragment.getInstance(noteslist))
                    .addToBackStack(null)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_list_fragment_holder, NotesListLandFragment.getInstance(noteslist))
                    .addToBackStack(null)
                    .commit();}
    }

    @Override
    public void listPress(Note note) {
        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_list_fragment_holder, NoteFragment.getInstance(note))
                    .addToBackStack(null)
                    .commit();
        }else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.chaild_fragment_holder, NoteFragment.getInstance(note))
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void fillList() {
        Intent intent = getIntent();
        if ((intent != null) && (noteslist.getSizeRepo() != 0)) {
            Note note = (Note) intent.getSerializableExtra(Constants.NOTE_FOR_EDIT);
            if (note != null) {
                if (note.getId() == -1) {
                    noteslist.create(note);
                } else {
                    noteslist.update(note);
                }
            }
        } else {

            noteslist.create(new Note("Понедельник", ""));
            noteslist.create(new Note("Вторник", ""));
            noteslist.create(new Note("Среда", ""));
            noteslist.create(new Note("Четверг", ""));
            noteslist.create(new Note("Пятница", ""));
            noteslist.create(new Note("Суббота", ""));
            noteslist.create(new Note("Воскресенье", ""));
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menu_add) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_list_fragment_holder, NoteFragment.getInstance(new Note()))
                    .addToBackStack(null)
                    .commit();
        }
        return super.onOptionsItemSelected(item);
    }

}

