package com.example.room2.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.loader.content.AsyncTaskLoader;

import com.example.room2.db.Note;
import com.example.room2.db.NoteDao;
import com.example.room2.db.NoteDatabase;

import java.sql.ClientInfoStatus;
import java.util.List;

public class NoteRepository {
    private  NoteDao noteDao;
    private LiveData<List<Note>> listLiveData;

    public NoteRepository(Application application) {
        NoteDatabase noteDatabase = NoteDatabase.getInstance(application);
        this.noteDao = noteDatabase.noteDao();
        this.listLiveData = noteDatabase.noteDao().getAllData();

    }

    public void insert(Note note){
        new InsertAsyncTask(noteDao).execute(note);
    }
    public void update(Note note){

        new UpdateAsyncTask(noteDao).execute(note);
    }
    public void delete(Note note){

        new DeleteAsyncTask(noteDao).execute(note);
    }
    public void deleteAll(){
        new DeleteAllAsyncTask(noteDao).execute();
    }
    public LiveData<List<Note>> getAllData(){

        return listLiveData;
    }




    private static class InsertAsyncTask extends AsyncTask<Note,Void,Void>{
        private NoteDao noteDao;

        public InsertAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }
    private static class UpdateAsyncTask extends AsyncTask<Note,Void,Void>{
        private NoteDao noteDao;

        public UpdateAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }
    private static class DeleteAsyncTask extends AsyncTask<Note,Void,Void>{
        private NoteDao noteDao;

        public DeleteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }
    private static class DeleteAllAsyncTask extends AsyncTask<Void,Void,Void>{
        private NoteDao noteDao;

        public DeleteAllAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... notes) {
            noteDao.deleteAll();
            return null;
        }
    }

}
