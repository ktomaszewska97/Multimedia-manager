package com.example.multimediamanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import com.github.chrisbanes.photoview.PhotoView;

public class ImageZoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_zoom);

        Intent zoomImageIntent = getIntent();
        String image = zoomImageIntent.getStringExtra("IMAGE_RESOURCE");

        PhotoView photoView = (PhotoView)
                findViewById(R.id.photo_view);

        Bitmap bm = BitmapFactory.decodeFile(image);
        photoView.setImageBitmap(bm);

        //photoView.setImageResource(image);

        //ImageView zoomedImage = (ImageView) findViewById(R.id.bigImage);
        //zoomedImage.setImageResource(image);


    }
}