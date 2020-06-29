package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facedectection.R;
import com.example.model.Sticker;

import java.io.Serializable;
import java.util.ArrayList;

public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.ViewHolder> {
    ArrayList<Sticker> stickers;
    Context context;

    public StickerAdapter(ArrayList<Sticker> stickers,Context context){
        this.stickers = stickers;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtIcon.setText(stickers.get(position).getTen());
        holder.imgIcon.setImageResource(stickers.get(position).getHinh());
    }

    @Override
    public int getItemCount() {
        return stickers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtIcon;
        public ImageView imgIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIcon = itemView.findViewById(R.id.txtIcon);
            imgIcon = itemView.findViewById(R.id.imgIcon);
        }
    }
}
