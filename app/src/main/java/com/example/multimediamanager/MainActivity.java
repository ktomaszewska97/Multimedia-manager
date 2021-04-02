package com.example.multimediamanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    public ArrayList<Media> mediaList = new ArrayList<Media>();
    MyRecyclerViewAdapter adapter;


    private Integer imageId[] = {
            R.drawable.mp3_icon,
    };

    public void prepareData() {
        mediaList.add(new Media("B", "MP3", "2016-03-18", imageId[0], false));
        mediaList.add(new Media("C", "MP3", "2015-03-18", imageId[0], false));
        mediaList.add(new Media("A", "MP3", "2012-03-18", imageId[0], false));
    }

    private void setUIRef()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        ItemTouchHelper itemTouchHelperFavourite = new ItemTouchHelper(simpleItemTouchCallbackFavorite);
        itemTouchHelperFavourite.attachToRecyclerView(recyclerView);

    }

    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            int position = viewHolder.getAdapterPosition();
            mediaList.remove(position);
            adapter.notifyDataSetChanged();
        }
    };

    ItemTouchHelper.SimpleCallback simpleItemTouchCallbackFavorite = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            int position = viewHolder.getAdapterPosition();
            mediaList.get(position).setFavorite(true);
            adapter.notifyDataSetChanged();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.favorites) {
            ArrayList<Media> results = new ArrayList<>();
            for (Media media : mediaList) {
                if (media.getFavorite()) {
                    results.add(media);
                }
                adapter.setFilter(results);
            }
        }

        if (id == R.id.sort_by_date) {
            mediaList.sort(new DateSorter());
            adapter.notifyDataSetChanged();
        }

        if (id == R.id.sort_by_name) {
            mediaList.sort(new TitleSorter());
            adapter.notifyDataSetChanged();
        }

        if (id == R.id.add_new) {
            //Intent about_me_intent = new Intent(context, aboutMeActivity.class);
            //startActivity(about_me_intent);
        }

        return super.onOptionsItemSelected(item);
    }


}