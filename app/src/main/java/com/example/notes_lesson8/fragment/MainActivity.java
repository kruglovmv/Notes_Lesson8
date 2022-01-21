package com.example.notes_lesson8.fragment;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.notes_lesson8.R;
import com.example.notes_lesson8.data.Constants;
import com.example.notes_lesson8.data.Note;
import com.example.notes_lesson8.data.NoteList;
import com.example.notes_lesson8.data.Repo;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.function.ToDoubleBiFunction;

public class MainActivity extends AppCompatActivity implements NotesListFragment.ControllerPortretFragment, NotesListLandFragment.ControllerLandFragment, NoteFragment.ControllerNoteFragment {
    Repo noteslist = NoteList.getInstance();
    NavigationBarView navigationMenu;
    FragmentManager fragmentManager;
    private NotesListFragment notesListFragment;
    private NotesListLandFragment notesListLandFragment;
    private NoteFragment noteFragment;
    public static final String LIST_FRAGMENT_PORTRAIT = "LIST_FRAGMENT_PORTRAIT";
    public static final String LIST_FRAGMENT_LAND = "LIST_FRAGMENT_LAND";
    public static final String NOTE_FRAGMENT = "NOTE_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        initBottomNavigation();
        if ((getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE)) {

            fragmentManager
                    .beginTransaction()
                    .replace(R.id.main_list_fragment_holder, NotesListFragment.getInstance(noteslist), LIST_FRAGMENT_PORTRAIT)
                    .commit();
        } else {

            fragmentManager
                    .beginTransaction()
                    .replace(R.id.main_list_fragment_holder, NotesListLandFragment.getInstance(noteslist), LIST_FRAGMENT_LAND)
                    .commit();

        }

        notesListFragment = (NotesListFragment) fragmentManager.findFragmentByTag(LIST_FRAGMENT_PORTRAIT);
        notesListLandFragment = (NotesListLandFragment) fragmentManager.findFragmentByTag(LIST_FRAGMENT_LAND);
        noteFragment = (NoteFragment) fragmentManager.findFragmentByTag(NOTE_FRAGMENT);

        if(noteFragment!=null){
            fragmentManager
                    .popBackStack(NOTE_FRAGMENT, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.executePendingTransactions();

            if ((getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE)) {
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.main_list_fragment_holder, noteFragment, NOTE_FRAGMENT)
                        .addToBackStack(NOTE_FRAGMENT)
                        .commit();
            }else {
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.main_list_fragment_holder, NotesListLandFragment.getInstance(noteslist), LIST_FRAGMENT_LAND)
                        .commit();
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.chaild_fragment_holder, noteFragment, NOTE_FRAGMENT)
                        .addToBackStack(NOTE_FRAGMENT)
                        .commit();
            }
        }

    }

    private void initBottomNavigation() {
        navigationMenu = findViewById(R.id.main_bottom_navigation_view);
        navigationMenu
                .setOnItemSelectedListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.menu_exit: {
                            finish();
                            return true;
                        }
                        case R.id.menu_search: {
                            //TODO
                            return true;
                        }
                        default:
                            return false;
                    }

                });
    }


    @Override
    public void listPress(Note note) {
        if (noteFragment == null)
            noteFragment = new NoteFragment();

        fragmentManager
                .beginTransaction()
                .replace(R.id.main_list_fragment_holder, NoteFragment.getInstance(note), NOTE_FRAGMENT)
                .addToBackStack(NOTE_FRAGMENT)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menu_add) {
            if ((getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE)) {
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.main_list_fragment_holder, NoteFragment.getInstance(new Note()), NOTE_FRAGMENT)
                        .addToBackStack(NOTE_FRAGMENT)
                        .commit();
            } else {
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.chaild_fragment_holder, NoteFragment.getInstance(new Note()), NOTE_FRAGMENT)
                        .addToBackStack(NOTE_FRAGMENT)
                        .commit();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void listPressLand(Note note) {
        if (noteFragment == null)
            noteFragment = new NoteFragment();

        fragmentManager
                .beginTransaction()
                .replace(R.id.chaild_fragment_holder, NoteFragment.getInstance(note), NOTE_FRAGMENT)
                .addToBackStack(NOTE_FRAGMENT)
                .commit();
    }

    @Override
    public void saveButtonPress(Note note) {
        if (note != null) {
            if (note.getId() == -1) {
                noteslist.create(note);
            } else {
                noteslist.update(note);
            }
        }
        fragmentManager
                .popBackStack(NOTE_FRAGMENT, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentManager.executePendingTransactions();
        if ((getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.main_list_fragment_holder, NotesListLandFragment.getInstance(noteslist), LIST_FRAGMENT_LAND)
                    .commit();
        }
    }

    @Override
    public void backButtonPress() {
        fragmentManager
                .popBackStack(NOTE_FRAGMENT, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentManager.executePendingTransactions();
    }
}



