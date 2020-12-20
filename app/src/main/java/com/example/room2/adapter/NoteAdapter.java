package com.example.room2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.room2.R;
import com.example.room2.db.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends ListAdapter<Note,NoteAdapter.NoteViewHolder> {
    OnItemClickListener onItemClickListener;

    public NoteAdapter() {
        super(NoteAdapter.itemCallback);
    }
   private static DiffUtil.ItemCallback<Note> itemCallback = new DiffUtil.ItemCallback<Note>() {

        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId()==newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public NoteAdapter.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle,parent,false);

        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.NoteViewHolder holder, int position) {

        holder.txt_priority.setText(String.valueOf(getItem(position).getPriority()));
        holder.txt_title.setText(getItem(position).getTitle());
        holder.txt_des.setText(getItem(position).getDescription());

    }


    public Note getNoteAt(int position){
        return getItem(position);
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
                    onItemClickListener.onItemClick(getItem(getAdapterPosition()));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Note note);
    }
}
