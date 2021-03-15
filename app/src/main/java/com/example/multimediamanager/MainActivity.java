package com.example.multimediamanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public ArrayList<Media> arrayList = new ArrayList<Media>();

    private Integer imageId[] = {
            R.drawable.mp3_icon,
    };

    public void prepareData() {
        arrayList.add(new Media("JAVA", "MP3", "2018-03-18", imageId[0]));
        arrayList.add(new Media("JAVA", "MP3", "2018-03-18", imageId[0]));
        arrayList.add(new Media("JAVA", "MP3", "2018-03-18", imageId[0]));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prepareData();

        final ListView list = findViewById(R.id.list);

        CustomAdapter customAdapter = new CustomAdapter(this, arrayList);
        list.setAdapter(customAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("CLICKED");
                Media clickedMedia = arrayList.get(position);
                Intent zoomImageIntent = new Intent(getApplicationContext(), ImageZoomActivity.class);
                zoomImageIntent.putExtra("IMAGE_RESOURCE", clickedMedia.getImage());
                startActivity(zoomImageIntent);
            }

        });


    }
}