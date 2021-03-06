package com.myapp.sshah.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvItems;
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    EditText etEditText;
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView)findViewById(R.id.lvItems);
        etEditText = (EditText)findViewById(R.id.etNewItem);
        populateArrayItems();
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }


    private void setupListViewListener(){
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                launchEditView(position);
            }
        });

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
    }

    private void launchEditView(int index){
        Intent i  = new Intent(MainActivity.this, EditActivity.class);
        i.putExtra("index", index);
        startActivityForResult(i, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String newText = data.getExtras().getString("updated_todo");
            int index = data.getExtras().getInt("index", 0);
            // Toast the name to display temporarily on screen
            items.set(index, newText);
            itemsAdapter.notifyDataSetChanged();
            writeItems();
        }
    }

    private void readItems(){
        File fileDir = getFilesDir();
        File file = new File(fileDir, "todo.text");
        try{
            items = new ArrayList<String>(FileUtils.readLines(file));
        }catch (IOException ex){
            items = new ArrayList<String>();
        }
    }

    private void writeItems(){
        File fileDir = getFilesDir();
        File file = new File(fileDir, "todo.text");
        try{
            FileUtils.writeLines(file, items);
        }catch (IOException ex){

        }
    }

    public void populateArrayItems(){
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
    }

    public void onAddItem(View v){
        String newItemText = etEditText.getText().toString();
        itemsAdapter.add(newItemText);
        etEditText.setText("");
        writeItems();
    }
}
