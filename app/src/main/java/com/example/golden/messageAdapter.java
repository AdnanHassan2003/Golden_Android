package com.example.golden;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

public class messageAdapter extends RecyclerView.Adapter<messageAdapter.MessageViewHolder> {

    private Context context;
    private JsonArray jsonElements;
    Animation translate_anim;

    public messageAdapter(Context context, JsonArray jsonElements) {
        this.context = context;
        this.jsonElements = jsonElements;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_message,parent,false);
        MessageViewHolder messageViewHolder = new MessageViewHolder(view);
        return messageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int i) {
        JsonObject jsonObject=jsonElements.get(i).getAsJsonObject();
        holder.title.setText(jsonObject.get("title").getAsString());
        holder.message.setText(jsonObject.get("message").getAsString());

    }

    @Override
    public int getItemCount() {
        return jsonElements.size();
    }
    public class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView title,message;
        LinearLayout mainLayout;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            message = itemView.findViewById(R.id.message);
            mainLayout = itemView.findViewById(R.id.main_layout);
            //animation recycleView
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }
    }
}
