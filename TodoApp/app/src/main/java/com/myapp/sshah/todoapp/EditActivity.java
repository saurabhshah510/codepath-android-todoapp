package com.myapp.sshah.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {
    EditText etEditText;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        etEditText = (EditText)findViewById(R.id.editText);
        index = getIntent().getIntExtra("index", 0);
    }

    public void onEditItem(View v){
        String newItemText = etEditText.getText().toString();
        Intent data = new Intent();
        data.putExtra("updated_todo", newItemText);
        data.putExtra("index", index);
        setResult(RESULT_OK, data);
        finish();
    }
}
