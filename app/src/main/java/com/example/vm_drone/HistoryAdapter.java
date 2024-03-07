package com.example.vm_drone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>
{

    private final Context context;
    private ArrayList<ChatHistory> chatHistory;
 public HistoryAdapter(Context context, ArrayList<ChatHistory> chatHistory)
 {
     this.context = context;
     this.chatHistory = chatHistory;
 }
    // Inflates the layout and gives a look to each of our rows
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row,parent,false);
        return new HistoryAdapter.ViewHolder(view);
    }


    // Assigns values to each of our rows back on the screen
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.text_display.setText(chatHistory.get(position).getTextHistory());
    }


    // Wants to know how many items are in the recycler view
    @Override
    public int getItemCount()
    {
        return chatHistory.size();
    }


    // Assigns all the views in the item to their specific variable
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView text_display;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            // can set the on click listener
            text_display = itemView.findViewById(R.id.textView);

        }
    }
    public void setChatHistory(ArrayList<ChatHistory> chatHistory)
    {
        this.chatHistory = chatHistory;
    }
}



