package com.example.notes_lesson8.fragment;


import static com.example.notes_lesson8.data.Constants.LIST_FRAGMENT_LAND;
import static com.example.notes_lesson8.data.Constants.LIST_FRAGMENT_PORTRAIT;
import static com.example.notes_lesson8.data.Constants.NOTE_FRAGMENT;
import static com.example.notes_lesson8.data.Constants.SHARED_LIST;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.notes_lesson8.R;
import com.example.notes_lesson8.data.Constants;
import com.example.notes_lesson8.data.Note;
import com.example.notes_lesson8.data.NoteList;
import com.example.notes_lesson8.data.Repo;
import com.example.notes_lesson8.dialog.ExitDialogFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.function.ToDoubleBiFunction;

public class MainActivity extends AppCompatActivity implements ControllerFragment, NavigationView.OnNavigationItemSelectedListener, Toolbar.OnMenuItemClickListener {
    Repo noteslist;
    NavigationBarView navigationMenu;
    FragmentManager fragmentManager;
    private NoteFragment noteFragment;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private SharedPreferences repository;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        repository = PreferenceManager.getDefaultSharedPreferences(this);
        String list = repository.getString(SHARED_LIST, "");
        if (list.equals("")) {
            noteslist = new NoteList();

        } else {
            noteslist = new NoteList();
            noteslist = gson.fromJson(list, noteslist.getClass());
        }
        initDrawer();

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


        noteFragment = (NoteFragment) fragmentManager.findFragmentByTag(NOTE_FRAGMENT);

        if (noteFragment != null) {
            fragmentManager
                    .popBackStack(NOTE_FRAGMENT, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.executePendingTransactions();

            if ((getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE)) {
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.main_list_fragment_holder, noteFragment, NOTE_FRAGMENT)
                        .addToBackStack(NOTE_FRAGMENT)
                        .commit();
            } else {
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

    private void initDrawer() {
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initBottomNavigation() {
        navigationMenu = findViewById(R.id.main_bottom_navigation_view);
        navigationMenu
                .setOnItemSelectedListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.menu_exit: {
                            new ExitDialogFragment().show(fragmentManager, null);

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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.options_menu, menu);
//
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
//        if (item.getItemId() == R.id.menu_add) {
//            if ((getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE)) {
//                fragmentManager
//                        .beginTransaction()
//                        .replace(R.id.main_list_fragment_holder, NoteFragment.getInstance(new Note()), NOTE_FRAGMENT)
//                        .addToBackStack(NOTE_FRAGMENT)
//                        .commit();
//            } else {
//                fragmentManager
//                        .beginTransaction()
//                        .replace(R.id.chaild_fragment_holder, NoteFragment.getInstance(new Note()), NOTE_FRAGMENT)
//                        .addToBackStack(NOTE_FRAGMENT)
//                        .commit();
//            }
//        }
//        return super.onOptionsItemSelected(item);
//    }

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
            String list = gson.toJson(noteslist);
            repository
                    .edit()
                    .putString(SHARED_LIST, list)
                    .apply();
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

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        if (fragmentManager.getBackStackEntryCount() == 0) {
            new ExitDialogFragment().show(fragmentManager, null);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_about) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.main_list_fragment_holder, new AboutFragment())
                    .addToBackStack(null)
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
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
        return false;
    }
}




