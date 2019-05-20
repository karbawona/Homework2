package com.example.homework2;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.homework2.task.TaskListContent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.example.homework2.task.TaskListContent.ITEMS;
import static com.example.homework2.task.TaskListContent.ITEM_MAP;
import static com.example.homework2.task.TaskListContent.deleteItem;


public class MainActivity extends AppCompatActivity implements TaskFragment.OnListFragmentInteractionListener, DeleteFragment.OnDeleteDialogInteractionListener {


    private static final int ADD_BUTTON = 3;
    private static final int REQUEST_IMAGE_CAPTURE = 4;
    private static final int ADD_MUSIC_PHOTO = 5;
    private static final String TASKS_JSON_FILE = "saving.json";
    public static String createdTask = "created task";
    private String mCurrentPhotoPath;
    private int currentItemPosition = -1;
    private TaskListContent.Task taskCurrent;
    private final String CHECKED_ID = "ckeckedId";
    private final String TASKS_SHARED_PREFS = "TasksSharedPrefs"; private final String TASKS_TEXT_FILE = "tasks.txt";
    private final String NUM_TASKS = "NumOfTasks";
    private final String TASK = "task_";
    private final String DETAIL = "desc_";
    private final String PIC = "pic_";
    private final String ID = "id_";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            taskCurrent = savedInstanceState.getParcelable(TASK);
            restoreFromJson();
        }
    }

    @Override
    protected void onDestroy() {
        savedTasksToJson();

        super.onDestroy();
    }

    @Override
    public void onListFragmentClickInteraction(TaskListContent.Task task, int position) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            displayTaskInFragment(task);
        } else {
            startDetailsActivity(task);
        }
        taskCurrent = task;
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
        Toast.makeText(this, getString(R.string.msg), Toast.LENGTH_SHORT).show();
        taskCurrent = task;
        showDeleteDialog();
    }

    public void addActivityRun(View view) {
        Intent intent = new Intent(getApplicationContext(), AddMusicActivity.class);
        startActivityForResult(intent, ADD_BUTTON);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {

        if (resultCode == RESULT_OK) {

            if (requestCode == ADD_BUTTON) {
                ((TaskFragment) getSupportFragmentManager().findFragmentById(R.id.taskFragment)).notifyDataChange();
            }
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Intent intent1 = new Intent(getApplicationContext(), AddMusicActivity.class);
                startActivityForResult(intent1, ADD_MUSIC_PHOTO);
            }
            if (requestCode == ADD_MUSIC_PHOTO) {
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

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        if (taskCurrent != null) {
            TaskListContent.deleteItem(taskCurrent);
            ((TaskFragment) getSupportFragmentManager().findFragmentById(R.id.taskFragment)).notifyDataChange();
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        View v = findViewById(R.id.addNewMusicButton);
        if (v != null) {
            Snackbar.make(v, getString(R.string.deleteCancel), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.retryMessage), new View.OnClickListener() {
                        @Override
                        public void onClick(View v1) {
                            showDeleteDialog();
                        }
                    }).show();
        }
    }

    private void showDeleteDialog() {
        DeleteFragment.newInstance().show(getSupportFragmentManager(), getString(R.string.tag));

    }

    private void savedTasksToJson() {
        Gson gson = new Gson();
        String listJson = gson.toJson(TaskListContent.ITEMS);
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(TASKS_JSON_FILE, MODE_PRIVATE);
            FileWriter writer = new FileWriter(outputStream.getFD());
            writer.write(listJson);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void restoreFromJson(){
        FileInputStream inputStream;
        int DEFAULT_BUFFER_SIZE = 10000;
        Gson gson = new Gson();
        String readJson;

        try {
            inputStream = openFileInput(TASKS_JSON_FILE);
            FileReader reader = new FileReader(inputStream.getFD());
            char[] buf = new char[DEFAULT_BUFFER_SIZE];
            int n;
            StringBuilder builder = new StringBuilder();
            while ((n = reader.read(buf)) >= 0) {
                String tmp = String.valueOf(buf);
                String substring = (n<DEFAULT_BUFFER_SIZE) ? tmp.substring(0,n) : tmp;
                builder.append(substring);
            }
            reader.close();
            readJson = builder.toString();
            Type collectionType = new TypeToken<List<TaskListContent.Task>>() {
            }.getType();
            List<TaskListContent.Task> o = gson.fromJson(readJson, collectionType);
            if (o != null) {
                TaskListContent.clearList();
                for (TaskListContent.Task task : o) {
                    TaskListContent.addItem(task);
                }
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
