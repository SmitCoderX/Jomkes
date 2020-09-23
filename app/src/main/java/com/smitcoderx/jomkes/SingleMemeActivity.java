package com.smitcoderx.jomkes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import static com.smitcoderx.jomkes.MemesFragment.EXTRA_CREATOR;
import static com.smitcoderx.jomkes.MemesFragment.EXTRA_LIKES;
import static com.smitcoderx.jomkes.MemesFragment.EXTRA_TITLE;
import static com.smitcoderx.jomkes.MemesFragment.EXTRA_URL;

public class SingleMemeActivity extends AppCompatActivity {

    private ImageView memeImageView;
    private TextView titleName;
    private TextView authorName;
    private TextView upVotesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_meme);

        setTitle("Meme");

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_URL);
        String title = intent.getStringExtra(EXTRA_TITLE);
        String creatorsName = intent.getStringExtra(EXTRA_CREATOR);
        int upVotes = intent.getIntExtra(EXTRA_LIKES, 0);

        memeImageView = findViewById(R.id.meme_ImageView);
        titleName = findViewById(R.id.titleTextView);
        authorName = findViewById(R.id.authorTextView);
        upVotesTextView = findViewById(R.id.upVotesTextView);

        Picasso.get().load(imageUrl).fit().into(memeImageView);
        titleName.setText(title);
        authorName.setText(creatorsName);
        upVotesTextView.setText("" + upVotes);

        /*     button.setOnClickListener(new View.OnClickListener() {
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

        */
    }
}