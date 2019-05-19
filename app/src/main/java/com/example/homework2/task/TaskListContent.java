package com.example.homework2.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TaskListContent {


    public static final List<Task> ITEMS = new ArrayList<>();

    public static final Map<String, Task> ITEM_MAP = new HashMap<>();

    private static final int COUNT = 5;

    static {
        for (int i = 1; i <= COUNT; i++) {
            addItem(createItem(i));
        }
    }

    public static void addItem(Task item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static TaskListContent.Task createItem(int position) {
        return new TaskListContent.Task (String.valueOf(position), "Item " + position, makeDetails(position), "smth");
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }


    public static class Task {
        public final String id;
        public final String name;
        public final String photoPath;
        public final String title;
        public final String data;

        public Task(String id, String name, String title, String data) {
            this.id = id;
            this.name = name;
            this.photoPath = "";
            this.title = title;
            this.data = data;
        }

        public Task(String id, String name, String photoPath, String title, String data) {
            this.id = id;
            this.name = name;
            this.photoPath = photoPath;
            this.title = title;
            this.data = data;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
