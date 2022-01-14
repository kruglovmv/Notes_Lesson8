package com.example.notes_lesson8.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NoteList implements Repo, Serializable {

    private ArrayList<Note> notesList = new ArrayList<>();
    private Integer counter = 0;
    private static NoteList repo;

    public static Repo getInstance() {
        if (repo == null) {
            repo = new NoteList();
        }
        return repo;
    }

    private NoteList() {

    }

    @Override
    public Integer create(Note note) {
        Integer id = counter++;
        note.setId(id);
        notesList.add(note);
        return id;
    }

    @Override
    public Note read(Integer id) {
        for (int i = 0; i < notesList.size(); i++) {
            if (notesList.get(i).getId().equals(id)) return notesList.get(i);
        }
        return null;
    }

    @Override
    public void update(Note note) {
        for (int i = 0; i < notesList.size(); i++) {
            if (notesList.get(i).getId().equals(note.getId())) {
                notesList.set(i, note);
                break;
            }
        }
    }

    @Override
    public void delete(Integer id) {
        for (int i = 0; i < notesList.size(); i++) {
            if (notesList.get(i).getId().equals(id)) {
                notesList.remove(i);
                break;
            }
        }
    }

    @Override
    public List<Note> getAll() {
        return notesList;
    }

    @Override
    public Integer getSizeRepo() {
        return notesList.size();
    }
}
