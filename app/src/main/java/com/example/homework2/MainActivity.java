package com.example.homework2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.homework2.task.TaskListContent;


public class MainActivity extends AppCompatActivity implements TaskFragment.OnListFragmentInteractionListener {


    private static final int ADD_BUTTON = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onListFragmentInteraction(TaskListContent.Task task, int position) {

    }

    @Override
    public void onListFragmentLongClickInteraction(int position) {

    }

    public void addActivityRun(View view) {
        Intent intent = new Intent(getApplicationContext(), AddMusicActivity.class);
        startActivityForResult(intent, ADD_BUTTON);
    }
}
