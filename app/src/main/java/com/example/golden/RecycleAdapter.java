package com.example.golden;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ExamViewHolder> {

    private Context context;
    private JsonArray jsonElements;

    public RecycleAdapter(Context context, JsonArray jsonElements) {
        this.context = context;
        this.jsonElements = jsonElements;
    }

    @NonNull
    @Override
    public ExamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_recycleview,parent,false);
       ExamViewHolder examViewHolder = new ExamViewHolder(view);
       return examViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExamViewHolder holder, int i) {
        JsonObject jsonObject=jsonElements.get(i).getAsJsonObject();
        holder.subject_name.setText(jsonObject.get("subject").getAsString());
        holder.subject_result.setText(jsonObject.get("marks").getAsString());



    }



    @Override
    public int getItemCount() {
        return jsonElements.size();
    }

   public class ExamViewHolder extends RecyclerView.ViewHolder {
        TextView subject_name, subject_result;
       public ExamViewHolder(@NonNull View itemView) {
           super(itemView);
           subject_name = itemView.findViewById(R.id.subject_name);
           subject_result = itemView.findViewById(R.id.subject_result);

       }
   }
}
