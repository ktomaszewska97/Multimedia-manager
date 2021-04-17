package com.example.multimediamanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    public ArrayList<Media> mediaList = new ArrayList<Media>();
    MyRecyclerViewAdapter adapter;


    private Integer imageId[] = {
            R.drawable.mp3_icon,
    };

    public void prepareData() {

        String extStore = System.getenv("EXTERNAL_STORAGE");
        File directory = new File(extStore+"/Pictures/");
        File[] files = directory.listFiles();
        //Log.d("Files", "Size: "+ files.length);
        for (int i = 0; i < files.length; i++)
        {
            //Log.d("Files", "FileName:" + files[i].getName());
            Date lastModified = new Date(files[i].lastModified());
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String formattedDateString = formatter.format(lastModified);
            mediaList.add(new Media(files[i].getName(), "Picture", formattedDateString, files[i].getAbsolutePath(), false));
        }

        File directory_recordings = new File(extStore+"/Music/");
        File[] files_recordings = directory_recordings.listFiles();
        for (int i = 0; i < files_recordings.length; i++)
        {
            Date lastModified = new Date(files_recordings[i].lastModified());
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String formattedDateString = formatter.format(lastModified);
            mediaList.add(new Media(files_recordings[i].getName(), "Picture", formattedDateString,false));
        }

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

    private void filter(String text) {
        ArrayList<Media> filteredList = new ArrayList<>();

        for (Media item : mediaList) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adapter.filterList(filteredList);
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

        EditText editText = findViewById(R.id.edittext);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });


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
            Intent intent = new Intent(this, ImageCameraActivity.class);
            startActivity(intent);
        }

        if (id == R.id.recorder) {
            Intent intent = new Intent(this, VoiceRecorder.class);
            startActivity(intent);
        }
        if (id == R.id.video_recorder) {
            Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takeVideoIntent, 1);
                }
            }
        return super.onOptionsItemSelected(item);
    }

}