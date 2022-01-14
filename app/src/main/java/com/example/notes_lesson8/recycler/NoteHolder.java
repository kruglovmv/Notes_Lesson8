package com.example.notes_lesson8.recycler;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes_lesson8.R;
import com.example.notes_lesson8.data.Note;

public class NoteHolder extends RecyclerView.ViewHolder {
    TextView titleNoteInList;
    TextView descriptionNoteInList;
    Note note;

    public NoteHolder(@NonNull View itemView, NotesAdapter.OnNoteClickListener listener) {
        super(itemView);
        titleNoteInList = itemView.findViewById(R.id.title_note_in_list);
        descriptionNoteInList = itemView.findViewById(R.id.description_note_in_list);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onNoteClick(note);
            }
        });
    }

    public void bind(Note note) {
        this.note = note;
        titleNoteInList.setText(note.getTitle());
        descriptionNoteInList.setText(note.getNote());
    }
}
