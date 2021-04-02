package com.example.multimediamanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
    private ArrayList<Media> media;

    public MyRecyclerViewAdapter(ArrayList<Media> medias)
    {
        this.media = medias;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        holder.title.setText(media.get(position).getTitle());
        holder.creationDate.setText(media.get(position).getCreationDate());
        holder.image.setImageResource(media.get(position).getImage());
        holder.fav.setText(String.valueOf(media.get(position).getFavorite()));

    }

    @Override
    public int getItemCount()
    {
        return media.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView title;
        private TextView creationDate;
        private TextView fav;
        private ImageView image;

        MyViewHolder(@NonNull View itemView)
        {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            creationDate = itemView.findViewById(R.id.date);
            image = itemView.findViewById(R.id.list_image);
            fav = itemView.findViewById(R.id.favorites);

        }
    }

    public void setFilter(List<Media> newList){
        media = new ArrayList<>();
        media.addAll(newList);
        notifyDataSetChanged();
    }

}