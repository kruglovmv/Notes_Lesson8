package com.example.notes_lesson8.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes_lesson8.R;
import com.example.notes_lesson8.data.Constants;
import com.example.notes_lesson8.data.Note;
import com.example.notes_lesson8.data.NoteList;
import com.example.notes_lesson8.data.Repo;
import com.example.notes_lesson8.recycler.NoteHolder;
import com.example.notes_lesson8.recycler.NotesAdapter;

import java.io.Serializable;

import static com.example.notes_lesson8.data.Constants.LIST_NOTES_FOR_EDIT;

public class NotesListFragment extends Fragment implements NotesAdapter.OnNoteClickListener {
    Repo noteslist;
    RecyclerView list;
    NotesAdapter adapter;

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onPopUpMenuClick(int idItemPopUpMenu, Note note, int positionNoteInList) {
        switch (idItemPopUpMenu){
            case R.id.popup_menu_for_note_in_list_delete:{
                noteslist.delete(note.getId());
                adapter.delete(noteslist.getAll(), positionNoteInList);
                return;
            }
            case R.id.popup_menu_for_note_in_list_edit:{
                controller.listPress(note);
                return;
            }
        }
    }

    @Override
    public void onNoteClick(Note note) {
        controller.listPress(note);
    }

    interface ControllerPortretFragment {
        void listPress(Note note);
    }
    private ControllerPortretFragment controller;

    public static NotesListFragment getInstance(Repo noteslist){
        NotesListFragment fragment = new NotesListFragment();
        Bundle args = new Bundle();
        args.putSerializable(LIST_NOTES_FOR_EDIT, (Serializable) noteslist);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        if(context instanceof ControllerPortretFragment){
            this.controller = (ControllerPortretFragment)context;
        }
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        adapter = new NotesAdapter();
        Bundle args = getArguments();
        if(args.containsKey(LIST_NOTES_FOR_EDIT)){
            noteslist = (Repo) args.getSerializable(LIST_NOTES_FOR_EDIT);
        }
        adapter.setNotes(noteslist.getAll());
        adapter.setOnPopUpMenuClickListener(this);
        list = view.findViewById(R.id.fragment_notes_list_recycler);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        list.setAdapter(adapter);
        initHelper();
    }

    private void initHelper() {
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int flags = ItemTouchHelper.LEFT;
                return makeMovementFlags(0, flags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                NoteHolder holder = (NoteHolder) viewHolder;
                noteslist.delete(holder.getNote().getId());
                adapter.delete(noteslist.getAll(), viewHolder.getAdapterPosition());
            }
        });
        helper.attachToRecyclerView(list);
    }

}

