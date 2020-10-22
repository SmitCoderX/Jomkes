package com.smitcoderx.jomkes;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import static com.smitcoderx.jomkes.MemesFragment.EXTRA_CREATOR;
import static com.smitcoderx.jomkes.MemesFragment.EXTRA_LIKES;
import static com.smitcoderx.jomkes.MemesFragment.EXTRA_TITLE;
import static com.smitcoderx.jomkes.MemesFragment.EXTRA_URL;

public class SingleMemeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SingleMemeActivity";
    private ImageView memeImageView;
    private FloatingActionButton floatingActionButton, fab2, fab3;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;
    private Boolean isFabOpen = false;
    private String imageUrl;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_meme);

        Intent intent = getIntent();
        imageUrl = intent.getStringExtra(EXTRA_URL);
        String title = intent.getStringExtra(EXTRA_TITLE);
        String creatorsName = intent.getStringExtra(EXTRA_CREATOR);
        int upVotes = intent.getIntExtra(EXTRA_LIKES, 0);

        memeImageView = findViewById(R.id.meme_ImageView);
        TextView titleName = findViewById(R.id.titleTextView);
        TextView authorName = findViewById(R.id.authorTextView);
        TextView upVotesTextView = findViewById(R.id.upVotesTextView);
        floatingActionButton = findViewById(R.id.fab_opener);
        fab2 = findViewById(R.id.fab2);
        fab3 = findViewById(R.id.fab3);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
        Picasso.get().load(imageUrl).centerInside().fit().into(memeImageView);
        titleName.setText(title);
        authorName.setText(creatorsName);
        upVotesTextView.setText("" + upVotes);

        floatingActionButton.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fab_opener:
                animateFab();
                break;
            case R.id.fab2:
                downloadImage();
                Toast.makeText(this, "Downloading Meme", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fab3:
                try {
                    shareMeme();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Something Went Wrong! Try Again Later", Toast.LENGTH_SHORT).show();
                }
        }
    }

    public void animateFab() {
        if (isFabOpen) {
            floatingActionButton.startAnimation(rotate_backward);
            fab2.startAnimation(fab_close);
            fab3.startAnimation(fab_close);
            fab2.setClickable(false);
            fab3.setClickable(false);
            isFabOpen = false;
            Log.d(TAG, "animateFab: CLOSE");
        } else {
            floatingActionButton.startAnimation(rotate_forward);
            fab2.startAnimation(fab_open);
            fab3.startAnimation(fab_open);
            fab2.setClickable(true);
            fab3.setClickable(true);
            isFabOpen = true;
            Log.d(TAG, "animateFab: OPEN ");
        }
    }

    public void shareMeme() throws IOException {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Bitmap bitmap = getBitmapFromView(memeImageView);
        try {
            File file = new File(this.getExternalCacheDir(), "meme.png");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            file.setReadable(true, false);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_TEXT, "\n\n - See More Memes at Jomkes. App Available on Playstore");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            intent.setType("image/png");
            startActivity(Intent.createChooser(intent, "Share Image via"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void downloadImage() {
        DownloadManager.Request request = new DownloadManager.Request(
                Uri.parse(imageUrl));
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.
                Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, "meme.jpg");
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        if (downloadManager != null) {
            downloadManager.enqueue(request);
        }
        Toast.makeText(SingleMemeActivity.this, "Downloading file....", Toast.LENGTH_SHORT).show();
    }

    public Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return returnedBitmap;
    }
}