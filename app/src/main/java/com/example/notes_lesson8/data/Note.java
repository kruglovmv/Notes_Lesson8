package com.example.notes_lesson8.data;

import android.provider.ContactsContract;

import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;

public class Note implements Serializable {
    private String title;
    private String note;
    private Integer id;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Note(String title, String note) {
        this.title = title;
        this.note = note;
    }
    public Note(String title, String note, Integer id) {
        this.title = title;
        this.note = note;
        this.id = id;
    }
    public Note() {
        this.title = "";
        this.note = "";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}

