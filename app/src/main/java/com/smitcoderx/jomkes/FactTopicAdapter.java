package com.smitcoderx.jomkes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class FactTopicAdapter extends RecyclerView.Adapter<FactTopicAdapter.FactTopicViewHolder> {

    private ArrayList<FactTopicModel> mTopicModelArrayList;
    private Context mContext;
    private OnItemClickListener mListener;

    public FactTopicAdapter(Context context, ArrayList<FactTopicModel> topicModelArrayList) {
        this.mContext = context;
        this.mTopicModelArrayList = topicModelArrayList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public FactTopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_facts, parent, false);
        return new FactTopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FactTopicViewHolder holder, int position) {
        FactTopicModel currentItem = mTopicModelArrayList.get(position);

        holder.fact_img.setImageResource(currentItem.getImage());
        holder.fact_text.setText(currentItem.getName());
    }

    @Override
    public int getItemCount() {
        return mTopicModelArrayList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class FactTopicViewHolder extends RecyclerView.ViewHolder {

        ImageView fact_img;
        TextView fact_text;

        public FactTopicViewHolder(@NonNull View itemView) {
            super(itemView);

            fact_img = itemView.findViewById(R.id.fact_topic_imageView);
            fact_text = itemView.findViewById(R.id.fact_topic_textView);

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
