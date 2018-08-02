package com.example.anti2110.financeinfo.Detail;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.anti2110.financeinfo.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoViewAttacher;

public class FullScreenImageActivity extends AppCompatActivity {

    private ImageView fullScreenImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        fullScreenImageView = (ImageView) findViewById(R.id.fullScreenImageView);

        Intent callingActivityIntent = getIntent();
        if(callingActivityIntent != null) {
            Uri uri = callingActivityIntent.getData();
            if(uri != null && fullScreenImageView != null) {

                final PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(fullScreenImageView);
                Picasso.get().load(uri).into(fullScreenImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        photoViewAttacher.update();
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
            }
        }

    }
}
