package com.kevappsgaming.coursemanagementsqlite.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kevappsgaming.coursemanagementsqlite.R;
import com.kevappsgaming.coursemanagementsqlite.utils.CourseInformation;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder>  {

    private List<CourseInformation> coursesList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private AdapterView.OnItemLongClickListener mLongClickListener;
    private AdapterView adapterView;

    // data is passed into the constructor
    public CourseAdapter(Context context, List<CourseInformation> coursesList) {
        this.mInflater = LayoutInflater.from(context);
        this.coursesList = coursesList;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_course_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CourseInformation course = coursesList.get(position);
        holder.courseNumber.setText(course.courseNumber);
        holder.courseName.setText(course.courseName);
        holder.courseCredits.setText(course.courseCredits);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return coursesList.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView courseNumber;
        TextView courseName;
        TextView courseCredits;

        ViewHolder(View itemView) {
            super(itemView);
            courseNumber = (TextView) itemView.findViewById(R.id.courseNumber);
            courseName = (TextView) itemView.findViewById(R.id.courseName);
            courseCredits = (TextView) itemView.findViewById(R.id.courseCredits);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mClickListener != null) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View view){
            if(mLongClickListener != null){
                mLongClickListener.onItemLongClick(adapterView, view, getAdapterPosition(), getItemId());
            }
            return true;
        }

    }

    // convenience method for getting data at click position
    public String getItemCourseNumber(int id) {
        return coursesList.get(id).courseNumber;
    }

    public String getItemCourseName(int id) {
        return coursesList.get(id).courseName;
    }

    public String getItemCourseCredits(int id) {
        return coursesList.get(id).courseCredits;
    }


    public void setLongClickListener(AdapterView.OnItemLongClickListener itemLongClickListener){
        this.mLongClickListener = itemLongClickListener;
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
}
