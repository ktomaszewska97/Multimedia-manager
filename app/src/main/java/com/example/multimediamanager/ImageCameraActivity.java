package com.example.multimediamanager;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;

public class ImageCameraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_camera);

        //Bitmap photo = (Bitmap) data.getExtras().get("data");
        ImageView imageView = (ImageView) findViewById(R.id.camera_result);
        //imageView.setImageBitmap(photo);

    }
}