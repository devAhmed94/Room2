package com.example.room2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.room2.R;
import com.example.room2.db.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    List<Note> noteList = new ArrayList<>();
    OnItemClickListener onItemClickListener;
    @NonNull
    @Override
    public NoteAdapter.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle,parent,false);

        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.NoteViewHolder holder, int position) {

        holder.txt_priority.setText(String.valueOf(noteList.get(position).getPriority()));
        holder.txt_title.setText(noteList.get(position).getTitle());
        holder.txt_des.setText(noteList.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }
    public void setNoteList(List<Note> noteList){
        this.noteList = noteList;
        notifyDataSetChanged();
    }
    public Note getNoteAt(int position){
        return noteList.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class  NoteViewHolder extends RecyclerView.ViewHolder{

        TextView txt_priority,txt_title,txt_des;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_priority = itemView.findViewById(R.id.priority);
            txt_title = itemView.findViewById(R.id.item_title);
            txt_des = itemView.findViewById(R.id.item_des);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener !=null){
                    onItemClickListener.onItemClick(noteList.get(getAdapterPosition()));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Note note);
    }
}
