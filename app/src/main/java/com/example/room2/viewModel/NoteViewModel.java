package com.example.room2.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.room2.db.Note;
import com.example.room2.repositories.NoteRepository;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepository noteRepository;
    private LiveData<List<Note>> listLiveData;
    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteRepository = new NoteRepository(application);
        listLiveData = noteRepository.getAllData();
    }

    public void insert(Note note){
        noteRepository.insert(note);
    }
    public void update(Note note){
        noteRepository.update(note);
    }
    public void delete(Note note){
        noteRepository.delete(note);
    }
    public void deleteAll(){
        noteRepository.deleteAll();
    }
    public LiveData<List<Note>> getAllData(){
      return listLiveData;
    }
}
