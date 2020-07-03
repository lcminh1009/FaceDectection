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
        holder.setData(stickers.get(position),position);
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
        public void setData(final Sticker sticker,final int position){
            txtIcon.setText(sticker.getTen());
            imgIcon.setImageResource(sticker.getHinh());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onStickerSelect != null){
                        onStickerSelect.onSelect(sticker);
                    }
                }
            });
        }
    }
    public interface OnStickerSelect{
        void onSelect(Sticker sticker);
    }
    OnStickerSelect onStickerSelect;
    public void setOnStickerSelect(OnStickerSelect onStickerSelect){
        this.onStickerSelect = onStickerSelect;
    }
}
