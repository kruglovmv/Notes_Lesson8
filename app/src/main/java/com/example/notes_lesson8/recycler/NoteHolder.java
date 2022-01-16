package com.example.notes_lesson8.recycler;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes_lesson8.R;
import com.example.notes_lesson8.data.Note;

public class NoteHolder extends RecyclerView.ViewHolder implements PopupMenu.OnMenuItemClickListener {
    TextView titleNoteInList;
    TextView descriptionNoteInList;
    AppCompatImageView  buttonForPopUpMenu;
    androidx.appcompat.widget.PopupMenu popUpMenu;
    Note note;
    private NotesAdapter.OnPopUpMenuClickListener listener;

    public NoteHolder(@NonNull View itemView, NotesAdapter.OnPopUpMenuClickListener listener) {
        super(itemView);
        this.listener = listener;
        buttonForPopUpMenu = itemView.findViewById(R.id.activity_note_in_list_button_for_popup_menu);
        titleNoteInList = itemView.findViewById(R.id.activity_note_in_list_title_note_in_list);
        descriptionNoteInList = itemView.findViewById(R.id.activity_note_in_list_description_note_in_list);
//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                listener.onNoteClick(note);
//            }
//        });
        popUpMenu = new PopupMenu(itemView.getContext(),buttonForPopUpMenu);
        popUpMenu.inflate(R.menu.popup_menu_for_note_in_list);
        buttonForPopUpMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    popUpMenu.setForceShowIcon(true);
                }
                popUpMenu.show();
            }
        });
        popUpMenu.setOnMenuItemClickListener(this);

    }

    public void bind(Note note) {
        this.note = note;
        titleNoteInList.setText(note.getTitle());
        descriptionNoteInList.setText(note.getNote());
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch(item.getItemId()){
            case R.id.popup_menu_for_note_in_list_delete:{
                listener.onPopUpMenuClick(R.id.popup_menu_for_note_in_list_delete, note, getAdapterPosition());
                return true;
            }
            case R.id.popup_menu_for_note_in_list_edit:{
                listener.onPopUpMenuClick(R.id.popup_menu_for_note_in_list_edit, note, getAdapterPosition());
                return true;
            }
            default: return false;
        }
    }
    public Note getNote(){
        return note;
    }
}
