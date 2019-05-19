package com.example.homework2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.homework2.task.TaskListContent;


public class DetailsMusicFragment extends Fragment {

    private TaskListContent.Task task1;

    public DetailsMusicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_details_music, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);
        Intent intent = getActivity().getIntent();
        TaskListContent.Task task = intent.getParcelableExtra(MainActivity.createdTask);
        if(task != null) displayTask(task);
    }


    public void displayTask(TaskListContent.Task  task) {
        FragmentActivity activity = getActivity();

        (activity.findViewById(R.id.detailsFragment)).setVisibility(View.VISIBLE);

        TextView musicTitle = activity.findViewById(R.id.titleDetail);
        TextView artistMusic = activity.findViewById(R.id.artistDetail);
        TextView dataMusic = activity.findViewById(R.id.dateDetail);
        final ImageView img = activity.findViewById(R.id.imageView2);

        musicTitle.setText(task.title);
        artistMusic.setText(task.artist);
        dataMusic.setText(task.date);
        if (task.photoPath != null && !task.photoPath.isEmpty()) {
            if (task.photoPath.contains("drawable")) {
                Drawable taskDrawable;
                switch (task.photoPath) {
//                    case "drawable 1":
//                        taskDrawable = activity.getResources().getDrawable(R.drawable.circle_drawable_red);
//                        break;
//                    case "drawable 2":
//                        taskDrawable = activity.getResources().getDrawable(R.drawable.circle_drawable_red);
//                        break;
//                    case "drawable 3":
//                        taskDrawable = activity.getResources().getDrawable(R.drawable.circle_drawable_red);
//                        break;
//                    default:
//                        taskDrawable = activity.getResources().getDrawable(R.drawable.circle_drawable_red);
                }
//                attractionInfoImage.setImageDrawable(taskDrawable);
            } else {
                Handler handler = new Handler();

                img.setVisibility(View.VISIBLE);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        img.setVisibility(View.VISIBLE);

                        Bitmap cameraImage = PicUtils.decodePic(task1.photoPath,
                                img.getWidth(),
                                img.getHeight());

                        img.setImageBitmap(cameraImage);
                    }
                }, 200);

            }
        } else {
        }
        task1 = task;
    }
}
