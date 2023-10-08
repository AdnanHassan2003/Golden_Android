package com.example.golden;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {

    int[]images;
    Context context;
    String[]names;

    public RecycleViewAdapter(int[] images,String[] names, Context context) {
        this.images = images;
        this.context = context;
        this.names = names;
    }


    @NonNull
    @Override
    public RecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapter.MyViewHolder holder, int position) {
        holder.getAdapterPosition();
        holder.imageView.setImageResource(images[position]);
        holder.textView.setText(names[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (position){
                    case 0 :
                        Intent intent = new Intent(context,Exams.class);
                        context.startActivity(intent);
                        break;

                    case 1:
                        Intent myintent = new Intent(context,Finence.class);
                        context.startActivity(myintent);
                        break;

                    case 2:
                        Intent Intent = new Intent(context,attendence.class);
                        context.startActivity(Intent);
                        break;

                    case 3:
                        Intent iintent = new Intent(context,profile.class);
                        context.startActivity(iintent);
                        break;

                    case 4:
                        Intent INtent  = new Intent(context,quiz.class);
                        context.startActivity(INtent);
                }
            }

        });

    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview);
            textView = itemView.findViewById(R.id.textview);
        }
    }
}
