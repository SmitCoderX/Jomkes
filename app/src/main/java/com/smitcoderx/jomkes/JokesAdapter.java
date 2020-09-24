package com.smitcoderx.jomkes;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;

public class JokesAdapter extends RecyclerView.Adapter<JokesAdapter.JokesViewHolder> {

    private ArrayList<JokesModelClass> list;
    private Context context;

    public JokesAdapter(ArrayList<JokesModelClass> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public JokesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.jokes_layout, parent, false);
        return new JokesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JokesViewHolder holder, int position) {
        JokesModelClass modelClass = list.get(position);

        String joke = modelClass.getJokesText();
        String setup = modelClass.getSetup();
        String delivery = modelClass.getDelivery();

        if (joke != null) {
            holder.JokesTextView.setText(joke);
        } else if (setup != null && delivery != null) {
            holder.JokesTextView.setText(setup + "\n" + delivery);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class JokesViewHolder extends RecyclerView.ViewHolder {

        private TextView JokesTextView;
        private Button btnShare;
        private Button btnCopy;

        public JokesViewHolder(@NonNull final View itemView) {
            super(itemView);
            JokesTextView = itemView.findViewById(R.id.hindi_jokes_textView);
            btnShare = itemView.findViewById(R.id.btn_share);
            btnCopy = itemView.findViewById(R.id.btn_copy);

            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT, JokesTextView.getText().toString() + "\n\n - See More Jokes at Jomkes. App Available on Playstore");
                    intent.setType("text/plain");
                    context.startActivity(Intent.createChooser(intent, "Send To"));
                }
            });

            btnCopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager myClickboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData myClip = ClipData.newPlainText("text", JokesTextView.getText().toString());
                    myClickboard.setPrimaryClip(myClip);
                    //Toast.makeText(v.getContext(), "Text Copied", Toast.LENGTH_SHORT).show();
                    Snackbar.make(v.getRootView(), "Text Copied.", BaseTransientBottomBar.LENGTH_SHORT).show();
                }
            });
        }
    }
}
