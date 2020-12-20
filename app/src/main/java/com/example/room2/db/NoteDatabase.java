package com.example.room2.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
@Database(
        entities = Note.class,
        version = 1
)
public abstract class NoteDatabase  extends RoomDatabase {
    private static NoteDatabase instance;
    public abstract NoteDao noteDao();
    public static synchronized NoteDatabase getInstance(Context context){
        if (instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return instance;
    }

   private static RoomDatabase.Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new OnCreateAsyncTask(instance).execute();
        }
    };

    private static class OnCreateAsyncTask extends AsyncTask<Void,Void,Void>{

        private NoteDao noteDao;

        public OnCreateAsyncTask(NoteDatabase db) {
            noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            noteDao.insert(new Note("title1","des1",1));
            noteDao.insert(new Note("title2","des2",2));
            noteDao.insert(new Note("title3","des3",3));
            return null;
        }
    }
}
