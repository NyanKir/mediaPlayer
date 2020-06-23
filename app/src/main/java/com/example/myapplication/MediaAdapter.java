package com.example.myapplication;

import android.content.Context;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MediaAdapter  extends ArrayAdapter<Media> {

    private Context mContext;
    private List<Media> moviesList = new ArrayList<>();

    public MediaAdapter(Context applicationContext, ArrayList<Media> mediaList) {
        super(applicationContext,0,mediaList);
        mContext = applicationContext;
        moviesList = mediaList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.item,parent,false);

        Media currentMovie = moviesList.get(position);

        ImageView image = (ImageView)listItem.findViewById(R.id.imageView_poster);
        Picasso.get().load(currentMovie.getmImageDrawable()).into(image);

        TextView name = (TextView) listItem.findViewById(R.id.title_short);
        String title=currentMovie.getTitle();
        name.setText(title);


        return listItem;
    }
}
