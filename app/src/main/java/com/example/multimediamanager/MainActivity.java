package com.example.multimediamanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    public ArrayList<Media> mediaList = new ArrayList<Media>();
    MyRecyclerViewAdapter adapter;

    private Integer imageId[] = {
            R.drawable.mp3_icon,
    };

    public void prepareData() {
        mediaList.add(new Media("JAVA", "MP3", "2018-03-18", imageId[0]));
        mediaList.add(new Media("JAVA", "MP3", "2018-03-18", imageId[0]));
        mediaList.add(new Media("JAVA", "MP3", "2018-03-18", imageId[0]));
    }

    private void setUIRef()
    {
        recyclerView = findViewById(R.id.myRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter=new MyRecyclerViewAdapter(mediaList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Media clickedMedia = mediaList.get(position);
                Intent zoomImageIntent = new Intent(getApplicationContext(), ImageZoomActivity.class);
                zoomImageIntent.putExtra("IMAGE_RESOURCE", clickedMedia.getImage());
                startActivity(zoomImageIntent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        prepareData();
        setUIRef();

    }


}