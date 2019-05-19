package com.example.homework2.task;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TaskListContent {


    public static final List<Task> ITEMS = new ArrayList<>();

    public static final Map<String, Task> ITEM_MAP = new HashMap<>();

    private static final int COUNT = 3;

    static {
        for (int i = 1; i <= COUNT; i++) {
            addItem(createItem(i));
        }
    }

    public static void addItem(Task item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static void deleteItem(Task item) {
        ITEMS.remove(item);
        ITEM_MAP.remove(item.id);
    }

    private static TaskListContent.Task createItem(int position) {
        return new TaskListContent.Task (String.valueOf(position), "Item " + position, "dipa", "smth");
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }


    public static class Task implements Parcelable {
        public final String id;
        public final String title;
        public final String artist;
        public final String date;
        public String photoPath;

        public Task(String id, String title, String artist, String date) {
            this.id = id;
            this.title = title;
            this.photoPath = "";
            this.artist = artist;
            this.date = date;
        }

        public Task(String id, String artist, String photoPath, String title, String date) {
            this.id = id;
            this.artist = artist;
            this.photoPath = photoPath;
            this.title = title;
            this.date = date;
        }

        protected Task(Parcel in) {
            id = in.readString();
            title = in.readString();
            photoPath = in.readString();
            artist = in.readString();
            date = in.readString();
        }

        public static final Creator<Task> CREATOR = new Creator<Task>() {
            @Override
            public Task createFromParcel(Parcel in) {
                return new Task(in);
            }

            @Override
            public Task[] newArray(int size) {
                return new Task[size];
            }
        };

        @Override
        public String toString() {
            return id;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(title);
            dest.writeString(photoPath);
            dest.writeString(artist);
            dest.writeString(date);
        }

        public void setPicturePath(String mCurrentPhotoPath) {
            photoPath = mCurrentPhotoPath;
        }
    }
}
