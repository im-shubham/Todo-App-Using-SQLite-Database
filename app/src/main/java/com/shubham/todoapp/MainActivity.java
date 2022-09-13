package com.shubham.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.shubham.todoapp.Apdapters.RecyclerAdapter;
import com.shubham.todoapp.models.ModelClass;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements DialogCloseListener {
    ArrayList<ModelClass> arrayList;
    private DatabaseHandler db;
    RecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = new DatabaseHandler(this);
        db.openDatabase();


        arrayList = new ArrayList<>();

        recyclerAdapter = new RecyclerAdapter(db, MainActivity.this);
        recyclerView.setAdapter(recyclerAdapter);

        fab = findViewById(R.id.fab);
        arrayList = db.getAllTasks();
        Collections.reverse(arrayList);
        recyclerAdapter.setTasks(arrayList);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(recyclerAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });


    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void handleDialogClose(DialogInterface dialog) {

        arrayList = db.getAllTasks();
        Collections.reverse(arrayList);
        recyclerAdapter.setTasks(arrayList);
        recyclerAdapter.notifyDataSetChanged();

    }
}