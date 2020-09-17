package com.smitcoderx.jomkes;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.Context.DOWNLOAD_SERVICE;

public class MemeAdapter extends RecyclerView.Adapter<MemeAdapter.MemeViewHolder> {

    Context context;
    private ArrayList<MemeModelClass> list;
    private String imageUrl;
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

    @Override
    public void onBindViewHolder(@NonNull MemeViewHolder holder, int position) {
        MemeModelClass modelClass = list.get(position);

        imageUrl = modelClass.getURL();
        String creatorName = modelClass.getCreator();
        int ups = modelClass.getUpVotes();

        holder.authorsName.setText(creatorName);
        holder.upVotes.setText("UpVotes: " + ups);
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
        TextView authorsName;
        TextView upVotes;
        FloatingActionButton button;

        public MemeViewHolder(@NonNull View itemView) {
            super(itemView);

            memeImage = itemView.findViewById(R.id.image_meme);
            authorsName = itemView.findViewById(R.id.textViewAuthor);
            upVotes = itemView.findViewById(R.id.textViewUps);
            button = itemView.findViewById(R.id.fab_download);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DownloadManager.Request request = new DownloadManager.Request(
                            Uri.parse(imageUrl));
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.
                            Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir("/Happy", "Happy.jpg");
                    DownloadManager dm = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
                    dm.enqueue(request);
                    Toast.makeText(context, "Downloading File",
                            Toast.LENGTH_LONG).show();
                }
            });


        }
    }
}
