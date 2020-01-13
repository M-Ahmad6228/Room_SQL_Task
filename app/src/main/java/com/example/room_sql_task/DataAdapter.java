package com.example.room_sql_task;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {

    private List<Employee> mDataset;

    public DataAdapter(List<Employee> myDataSet) {
        mDataset = myDataSet;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.listview, parent, false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if(!mDataset.get(position).getImageSource().equals(""))
            holder.imageView.setImageURI(Uri.parse(mDataset.get(position).getImageSource()));
        holder.txt.setText("Name: " + mDataset.get(position).getFullName());
        holder.txt1.setText("Email: " + mDataset.get(position).getEmailAddress());
        holder.txt2.setText("Age: " + mDataset.get(position).getAge());
        holder.txt3.setText("Gender: " + mDataset.get(position).getGender());
        holder.txt4.setText("Password: " + mDataset.get(position).getPassword());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView txt;
        TextView txt1;
        TextView txt2;
        TextView txt3;
        TextView txt4;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            txt = itemView.findViewById(R.id.tempName);
            txt1 = itemView.findViewById(R.id.tempEmail);
            txt2 = itemView.findViewById(R.id.tempAge);
            txt3 = itemView.findViewById(R.id.tempGender);
            txt4 = itemView.findViewById(R.id.tempPassword);
        }
    }
}
