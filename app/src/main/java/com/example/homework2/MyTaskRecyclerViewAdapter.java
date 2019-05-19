package com.example.homework2;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.homework2.TaskFragment.OnListFragmentInteractionListener;
import com.example.homework2.task.TaskListContent;

import java.util.List;


public class MyTaskRecyclerViewAdapter extends RecyclerView.Adapter<MyTaskRecyclerViewAdapter.ViewHolder> {

    private final List<TaskListContent.Task> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyTaskRecyclerViewAdapter(List<TaskListContent.Task> items, OnListFragmentInteractionListener listener) {
        this.mValues = items;
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        TaskListContent.Task task = mValues.get(position);
        holder.mItem = task;
        holder.mTextView.setText(task.title);

        final String photoPath = task.photoPath;

        Context context = holder.mView.getContext();

//        taskDrawable = context.getResources().getDrawable(1);
//
//        holder.mItemImageView.setImageDrawable(taskDrawable);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem, position);
                }
            }
        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentLongClickInteraction(position);
                    return false;
                }
                return true;
            }


        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTextView;
        public final ImageView mImageView;
        public  TaskListContent.Task mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTextView = view.findViewById(R.id.textView);
            mImageView = view.findViewById(R.id.imageView);
        }

        @Override
        public String toString() {

            return "ViewHolder{" +
                    "mView=" + mView +
                    ", textView=" + mTextView +
                    ", imageView=" + mImageView +
                    ", mItem=" + mItem +
                    '}';
        }
    }
}
