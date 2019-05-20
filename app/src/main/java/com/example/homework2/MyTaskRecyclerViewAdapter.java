package com.example.homework2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.homework2.TaskFragment.OnListFragmentInteractionListener;
import com.example.homework2.task.TaskListContent;

import java.util.List;

import static com.example.homework2.PicUtils.calculateInSampleSize;


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
        final TaskListContent.Task task = mValues.get(position);
        holder.mItem = task;
        holder.mTextView.setText(task.title);
        final String photoPath = task.photoPath;

        Context context = holder.mView.getContext(); //?

        if (photoPath != null && !photoPath.isEmpty()) {

            if (photoPath.contains("android.resource")) {
                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = calculateInSampleSize(options,
                        100,
                        100);

                options.inJustDecodeBounds = false;
                Bitmap bitmap = BitmapFactory.decodeResource(holder.mView.getContext().getResources(),
                        Integer.parseInt(task.photoPath.substring(task.photoPath.lastIndexOf("/") + 1)),
                        options);

                holder.mImageView.setImageBitmap(bitmap);
            }

            } else {
        Bitmap camera = PicUtils.decodePic(task.photoPath, 100, 100);
        holder.mImageView.setImageBitmap(camera);

        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentClickInteraction(holder.mItem, position);
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

        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDeleteButtonClickInteraction(task);
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
        public final Button buttonDelete;
        public  TaskListContent.Task mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTextView = view.findViewById(R.id.titleTextView);
            mImageView = view.findViewById(R.id.imageView);
            buttonDelete = view.findViewById(R.id.buttonDelete);
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
