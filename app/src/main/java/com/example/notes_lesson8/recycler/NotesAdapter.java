package com.example.notes_lesson8.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes_lesson8.R;
import com.example.notes_lesson8.data.Note;
import com.example.notes_lesson8.data.Repo;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class NotesAdapter extends RecyclerView.Adapter<NoteHolder> {
    private List<Note> notesList = new ArrayList<>();

    public void delete(List<Note> notes, int positionNoteInList) {
        this.notesList = notes;
        notifyItemRemoved(positionNoteInList);

    }

    public interface OnPopUpMenuClickListener {
        void onPopUpMenuClick(int idItemPopUpMenu, Note note, int positionNoteInList);
    }

    private OnPopUpMenuClickListener listener;

    public void setOnPopUpMenuClickListener(OnPopUpMenuClickListener listener) {
        this.listener = listener;
    }

    public void setNotes(List<Note> notes) {
        this.notesList = notes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_note_in_list, parent, false);
        return new NoteHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note note = notesList.get(position);
        holder.bind(note);
    }


    @Override
    public int getItemCount() {
        return notesList.size();
    }
}
