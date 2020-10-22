package com.smitcoderx.jomkes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MemeAdapter extends RecyclerView.Adapter<MemeAdapter.MemeViewHolder> {

    Context context;
    private ArrayList<MemeModelClass> list;
    private OnItemClickListener mListener;

    public MemeAdapter(Context context, ArrayList<MemeModelClass> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public MemeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_memes, parent, false);
        return new MemeViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MemeViewHolder holder, int position) {
        MemeModelClass modelClass = list.get(position);

        String imageUrl = modelClass.getURL();
        String creatorName = modelClass.getCreator();
        String title = modelClass.getTitle();
        int ups = modelClass.getUpVotes();

        Picasso.get().load(imageUrl).fit().centerInside().into(holder.memeImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class MemeViewHolder extends RecyclerView.ViewHolder {
        ImageView memeImage;

        public MemeViewHolder(@NonNull View itemView) {
            super(itemView);

            memeImage = itemView.findViewById(R.id.image_meme);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
