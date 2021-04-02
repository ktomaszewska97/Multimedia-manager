package com.example.multimediamanager;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

public class DateSorter implements Comparator<Media> {
    @Override
    public int compare(Media o1, Media o2) {
        DateFormat f = new SimpleDateFormat("yyyy-mm-dd");
        try {
            return f.parse(o1.getCreationDate()).compareTo(f.parse(o2.getCreationDate()));
        }
        catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }
}