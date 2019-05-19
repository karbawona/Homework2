package com.example.homework2;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.homework2.task.TaskListContent;

import static com.example.homework2.task.TaskListContent.deleteItem;


public class MainActivity extends AppCompatActivity implements TaskFragment.OnListFragmentInteractionListener {


    private static final int ADD_BUTTON = 3;
    public static String createdTask = "created task";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onListFragmentClickInteraction(TaskListContent.Task task, int position) {
       if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
           displayTaskInFragment(task);
        } else {
           startDetailsActivity(task);
        }
    }

    private void displayTaskInFragment(TaskListContent.Task task) {
        DetailsMusicFragment details = ((DetailsMusicFragment) getSupportFragmentManager().findFragmentById(R.id.detailsFragment));
        if (details != null) {
            details.displayTask(task);
        }
    }

    private void startDetailsActivity(TaskListContent.Task task) {
        Intent intent = new Intent(this, DetailsMusicActivity.class);
        intent.putExtra(createdTask, task);
        startActivity(intent);
    }

    @Override
    public void onListFragmentLongClickInteraction(int position) {

    }

    @Override
    public void onDeleteButtonClickInteraction(TaskListContent.Task task) {
        deleteItem(task);
        ((TaskFragment) getSupportFragmentManager().findFragmentById(R.id.taskFragment)).notifyDataChange();
    }

    public void addActivityRun(View view) {
        Intent intent = new Intent(getApplicationContext(), AddMusicActivity.class);
        startActivityForResult(intent, ADD_BUTTON);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == RESULT_OK){

            if(requestCode == ADD_BUTTON) {
                ((TaskFragment) getSupportFragmentManager().findFragmentById(R.id.taskFragment)).notifyDataChange();
            }

        }

    }
}
