package com.example.homework2;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.homework2.task.TaskListContent;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.homework2.task.TaskListContent.ITEMS;
import static com.example.homework2.task.TaskListContent.ITEM_MAP;
import static com.example.homework2.task.TaskListContent.deleteItem;


public class MainActivity extends AppCompatActivity implements TaskFragment.OnListFragmentInteractionListener {


    private static final int ADD_BUTTON = 3;
    private static final int REQUEST_IMAGE_CAPTURE = 4;
    private static final int ADD_MUSIC_PHOTO = 5;
    public static String createdTask = "created task";
    private String mCurrentPhotoPath;

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {

        if (resultCode == RESULT_OK){

            if(requestCode == ADD_BUTTON) {
                ((TaskFragment) getSupportFragmentManager().findFragmentById(R.id.taskFragment)).notifyDataChange();
            }
            if (requestCode == REQUEST_IMAGE_CAPTURE){
                Intent intent1 = new Intent(getApplicationContext(), AddMusicActivity.class);
                startActivityForResult(intent1, ADD_MUSIC_PHOTO);
            }
            if (requestCode == ADD_MUSIC_PHOTO){
                TaskListContent.Task task = intent.getParcelableExtra(MainActivity.createdTask);
                if (task != null) {
                    TaskListContent.Task task1 = ITEM_MAP.get(task.id);
                    if (task1 != null) {
                    task1.setPicturePath(mCurrentPhotoPath);
                    ((TaskFragment) getSupportFragmentManager().findFragmentById(R.id.taskFragment)).notifyDataChange();
                    }
                }
            }
        }
    }

    public void addPhotoActivity(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File photoFile = null;

        try {
            photoFile = createImageFile();
        } catch (IOException ex) {

        }

        if (photoFile != null) {
            Uri photoUri = FileProvider.getUriForFile(this,
                    getString(R.string.myFileprovider),
                    photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private File createImageFile() throws IOException {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String counter = String.valueOf(ITEMS.size() + 1);
            String imageFileName = counter + timeStamp + "_";
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(
                    imageFileName,
                    ".jpg",
                    storageDir
            );

            mCurrentPhotoPath = image.getAbsolutePath();
            return image;
    }
}
