package com.example.room2.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.room2.R;

public class AddEditActivity extends AppCompatActivity {


    public static final String TITLE_KEY = "title";
    public static final String DES_KEY = "des";
    public static final String NUM_KEY = "number";
    public static final String ID_KEY = "id";

    EditText et_title, et_des;
    NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);



        et_title = findViewById(R.id.add_et_title);
        et_des = findViewById(R.id.add_et_des);
        numberPicker = findViewById(R.id.numberPicker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);

        if (getIntent().hasExtra(ID_KEY)) {
            setTitle("Edit Note");
            et_title.setText(getIntent().getStringExtra(TITLE_KEY).toString());
            et_des.setText(getIntent().getStringExtra(DES_KEY));
            numberPicker.setValue(getIntent().getIntExtra(NUM_KEY,1));

        } else {
            setTitle("Add Note");
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void saveNote() {
        String title = et_title.getText().toString();
        String des = et_des.getText().toString();
        int valueNumberPicker = numberPicker.getValue();

        if (title.trim().isEmpty() || des.trim().isEmpty()) {
            Toast.makeText(this, " plz complete your note", Toast.LENGTH_SHORT).show();
        } else {


            Intent intent = new Intent(AddEditActivity.this, MainActivity.class);
            intent.putExtra(TITLE_KEY, title);
            intent.putExtra(DES_KEY, des);
            intent.putExtra(NUM_KEY, valueNumberPicker);

            int id = getIntent().getIntExtra(ID_KEY, -1);
            if(id!=-1){
                intent.putExtra(ID_KEY,id);
            }
            setResult(RESULT_OK, intent);
            finish();
        }

    }
}