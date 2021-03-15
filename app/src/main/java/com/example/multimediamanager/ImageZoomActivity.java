package com.example.multimediamanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageZoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_zoom);

        Intent zoomImageIntent = getIntent();
        int image = zoomImageIntent.getIntExtra("IMAGE_RESOURCE", 0);
        ImageView zoomedImage = (ImageView) findViewById(R.id.bigImage);
        zoomedImage.setImageResource(image);
    }
}