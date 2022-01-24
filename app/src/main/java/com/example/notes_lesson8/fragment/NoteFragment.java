package com.example.notes_lesson8.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.notes_lesson8.R;
import com.example.notes_lesson8.data.Note;
import com.example.notes_lesson8.fragment.MainActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

import static com.example.notes_lesson8.data.Constants.NOTE_FOR_EDIT;
import static com.example.notes_lesson8.data.Constants.NOTE_FOR_EDIT;

public class NoteFragment extends Fragment {
    Note note = new Note();
    EditText titleNote;
    EditText noteDescription;
    TextView noteData;
    Spinner spinner;
    AppCompatImageView dataButton;
    MaterialButton saveButton;
    MaterialButton backButton;
    private int lastSelectedYear;
    private int lastSelectedMonth;
    private int lastSelectedDayOfMonth;
    ArrayAdapter adapterForSpinner;

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
        final FragmentActivity fragmentActivity = getActivity();
        if (fragmentActivity != null) {
            fragmentActivity.findViewById(R.id.main_bottom_navigation_view).setVisibility(View.GONE);
        }
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

    private void initSpinner(View parentView, int importance) {
        int index = (importance) % parentView.getResources().getStringArray(R.array.importance).length;
        spinner = parentView.findViewById(R.id.spinner);
        this.adapterForSpinner = ArrayAdapter.createFromResource(getContext(), R.array.importance,
                android.R.layout.simple_spinner_item);
        adapterForSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterForSpinner);
        if (index >= 0) {
            spinner.setSelection(index);
        }
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
            initSpinner(parentView, note.getImportance());
            initData(parentView, note.getData());
        }
    }

    private void initData(View parentView, String data) {
        dataButton = parentView.findViewById(R.id.button_date);
        noteData = parentView.findViewById(R.id.date_note);
        noteData.setText(normalisationToData(data));
        dataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonSelectDate();
            }
        });

    }

    private String normalisationToData(String data) {

        if ((data == null) || (data == "")) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH) + 1;
            int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
            return dayToString(dayOfMonth) + " " + monthToString(month) + " " + yearToString(year);
        } else {
            return data;
        }

    }

    private String normalisationToData(int[] data) {

        if ((data == null) || (data[0] == 0)) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH) + 1;
            int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
            return dayToString(dayOfMonth) + " " + monthToString(month) + " " + yearToString(year);
        } else {
            return dayToString(data[0]) + " " + monthToString(data[1] + 1) + " " + yearToString(data[2]);
        }

    }

    private String yearToString(int year) {
        return Integer.toString(year) + " year";
    }

    private String dayToString(int day) {
        if (day < 10) {
            return "0" + Integer.toString(day);
        }
        return Integer.toString(day);
    }

    private String monthToString(int month) {
        switch (month) {
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
            default:
                return "";
        }
    }

    private void initButton(View parentView) {
        saveButton = parentView.findViewById(R.id.button_save);
        backButton = parentView.findViewById(R.id.button_back);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ControllerFragment) getContext()).backButtonPress();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = adapterForSpinner.getPosition(spinner.getSelectedItem());
                Note editNote = new Note(titleNote.getText().toString(), noteDescription.getText().toString(), id, position, noteData.getText().toString());
                ((ControllerFragment) getContext()).saveButtonPress(editNote);
            }
        });

    }

    private void buttonSelectDate() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                int[] array = {dayOfMonth, monthOfYear, year};
                noteData.setText(normalisationToData(array));

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

