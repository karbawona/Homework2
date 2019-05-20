package com.example.homework2;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.homework2.task.TaskListContent;

import java.util.Random;

public class AddMusicActivity extends AppCompatActivity {

    TaskListContent.Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_music);
    }

    public void AddNewMusic(View view) {

        EditText taskTitleEdit = findViewById(R.id.title);
        EditText taskArtistEdit = findViewById(R.id.artist);
        EditText taskDateEdit = findViewById(R.id.releaseDate);
        String taskTitle = taskTitleEdit.getText().toString();
        String taskArtist = taskArtistEdit.getText().toString();
        String taskDate = taskDateEdit.getText().toString();

        String avatar1 = "android.resource://" +  getPackageName() + "/" + R.drawable.pobrane;
        String avatar2 =  "android.resource://" +  getPackageName() + "/" + R.drawable.pobrane2;
        String avatar3 =  "android.resource://" +  getPackageName() + "/" + R.drawable.pobrane3;
        String avatarRandom = "android.resource://" +  getPackageName() + "/" + R.drawable.pobrane;
        Random rand = new Random();
        int number = rand.nextInt(3);
        String url= Uri.parse(avatarRandom).toString();;

        switch (number) {
            case 0:
                avatarRandom = avatar1;
                url = Uri.parse(avatarRandom).toString();

                break;

            case 1:
                avatarRandom = avatar2;
                url = Uri.parse(avatarRandom).toString();

                break;

            case 2:
                avatarRandom = avatar3;
                url = Uri.parse(avatarRandom).toString();

                break;
        }


        if(taskTitle.isEmpty() && taskArtist.isEmpty() && taskDate.isEmpty()) {
            task = new TaskListContent.Task("task" + TaskListContent.ITEMS.size() + 1, getString(R.string.default_title),url, getString(R.string.default_artist), getString(R.string.default_date));
            TaskListContent.addItem(task);
        } else {
            if(taskTitle.isEmpty()) taskTitle = getString(R.string.default_title);
            if(taskArtist.isEmpty()) taskArtist = getString(R.string.default_artist);
            if(taskDate.isEmpty()) taskDate = getString(R.string.default_date);
            task = new TaskListContent.Task("task" + TaskListContent.ITEMS.size() + 1, taskArtist, url,taskTitle, taskDate);
            TaskListContent.addItem(task);
        }


     //   ((TaskFragment) getSupportFragmentManager().findFragmentById(R.id.taskFragment)).notifyDataChange();

        taskTitleEdit.setText("");
        taskArtistEdit.setText("");
        taskDateEdit.setText("");

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);

        Intent intent = new Intent();
        intent.putExtra(MainActivity.createdTask, task);
        setResult(RESULT_OK, intent);
        finish();
    }


}
