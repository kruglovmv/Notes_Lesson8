package com.example.notes_lesson8.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import com.example.notes_lesson8.R;
import com.example.notes_lesson8.data.Note;
import com.example.notes_lesson8.fragment.MainActivity;
import com.google.android.material.button.MaterialButton;

import java.util.Calendar;

import static com.example.notes_lesson8.data.Constants.NOTE_FOR_EDIT;
import static com.example.notes_lesson8.data.Constants.NOTE_FOR_EDIT;

public class NoteFragment extends Fragment {
    Note note = new Note();
    EditText titleNote;
    EditText noteDescription;
    EditText noteData;
    AppCompatImageView dataButton;
    MaterialButton saveButton;
    MaterialButton backButton;
    private int lastSelectedYear;
    private int lastSelectedMonth;
    private int lastSelectedDayOfMonth;

    private Integer id;

    public static NoteFragment getInstance(Note note) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putSerializable(NOTE_FOR_EDIT, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        this.lastSelectedYear = c.get(Calendar.YEAR);
        this.lastSelectedMonth = c.get(Calendar.MONTH);
        this.lastSelectedDayOfMonth = c.get(Calendar.DAY_OF_MONTH);

        initNote(view);
        initButton(view);

    }

    private void initNote(View parentView) {
        Bundle args = getArguments();
        if (args.containsKey(NOTE_FOR_EDIT)) {
            note = (Note) args.getSerializable(NOTE_FOR_EDIT);

            titleNote = parentView.findViewById(R.id.title_note);
            titleNote.setText(note.getTitle());
            noteDescription = parentView.findViewById(R.id.note);
            noteDescription.setText(note.getNote());
            if (note.getId() == null) {
                id = -1;
            } else {
                id = note.getId();
            }
        }
    }


    private void initButton(View parentView) {
        saveButton = parentView.findViewById(R.id.button_save);
        backButton = parentView.findViewById(R.id.button_back);
        dataButton = parentView.findViewById(R.id.button_date);
        dataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonSelectDate();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(getContext(), MainActivity.class);
                startActivity(backIntent);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleNote = parentView.findViewById(R.id.title_note);
                noteDescription = parentView.findViewById(R.id.note);
                Note editNote = new Note(titleNote.getText().toString(), noteDescription.getText().toString(), id);
                Intent backIntent = new Intent(getContext(), MainActivity.class);
                backIntent.putExtra(NOTE_FOR_EDIT, editNote);
                startActivity(backIntent);
            }
        });

    }
    private void buttonSelectDate() {

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {

                noteData.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                lastSelectedYear = year;
                lastSelectedMonth = monthOfYear;
                lastSelectedDayOfMonth = dayOfMonth;
            }
        };

        DatePickerDialog datePickerDialog = null;

        datePickerDialog = new DatePickerDialog(getContext(),
                dateSetListener, lastSelectedYear, lastSelectedMonth, lastSelectedDayOfMonth);

        datePickerDialog.show();
    }
}

