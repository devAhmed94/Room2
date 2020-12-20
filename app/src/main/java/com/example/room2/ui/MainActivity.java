package com.example.room2.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.room2.R;
import com.example.room2.adapter.NoteAdapter;
import com.example.room2.db.Note;
import com.example.room2.viewModel.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_REQUEST_CODE = 1;
    public static final int EDIT_REQUEST_CODE = 2;
    NoteViewModel noteViewModel;
    RecyclerView recyclerView;
    NoteAdapter noteAdapter;
    FloatingActionButton f_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycle);
        f_btn = findViewById(R.id.f_btn);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        noteAdapter = new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllData().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteAdapter.setNoteList(notes);
            }
        });
        f_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                startActivityForResult(intent, ADD_REQUEST_CODE);
            }
        });
        initItemTouchHelper();
        noteAdapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                intent.putExtra(AddEditActivity.TITLE_KEY, note.getTitle());
                intent.putExtra(AddEditActivity.DES_KEY, note.getDescription());
                intent.putExtra(AddEditActivity.NUM_KEY, note.getPriority());
                intent.putExtra(AddEditActivity.ID_KEY, note.getId());
                startActivityForResult(intent, EDIT_REQUEST_CODE);

            }
        });


    }

    private void initItemTouchHelper() {
        ItemTouchHelper.SimpleCallback simpleCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        noteViewModel.delete(noteAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                    }
                };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                String title = data.getStringExtra(AddEditActivity.TITLE_KEY);
                String des = data.getStringExtra(AddEditActivity.DES_KEY);
                int numberPicker = data.getIntExtra(AddEditActivity.NUM_KEY, 1);
                noteViewModel.insert(new Note(title, des, numberPicker));

                Toast.makeText(this, "insert done", Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK) {

            int intExtra = data.getIntExtra(AddEditActivity.ID_KEY, -1);
            if (intExtra == -1) {
                Toast.makeText(this, "some thing error", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(AddEditActivity.TITLE_KEY);
            String des = data.getStringExtra(AddEditActivity.DES_KEY);
            int numberPicker = data.getIntExtra(AddEditActivity.NUM_KEY, 1);
            Note note = new Note(title, des, numberPicker);
            note.setId(intExtra);
            noteViewModel.update(note);


        } else {
            Toast.makeText(this, "not insert", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu:
                noteViewModel.deleteAll();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}