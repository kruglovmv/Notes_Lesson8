package com.example.notes_lesson8.fragment;

import com.example.notes_lesson8.data.Note;

public interface ControllerFragment {
    void saveButtonPress(Note note);

    void backButtonPress();

    void listPressLand(Note note);

    void listPress(Note note);
}
