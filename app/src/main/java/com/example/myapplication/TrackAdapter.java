package com.example.myapplication;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TrackAdapter extends ArrayAdapter<Track> {
    private Context mContext;
    private List<Track> tracksList = new ArrayList<>();
    public static MediaPlayer mediaPlayer;
    private ImageButton nowButton=null;
    private String nowURL = null;

    public TrackAdapter(Context applicationContext, ArrayList<Track> tracks) {
        super(applicationContext, 0, tracks);
        mContext = applicationContext;
        tracksList = tracks;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_tracks, parent, false);

        final Track currentTrack = tracksList.get(position);


        TextView name = (TextView) listItem.findViewById(R.id.nameTrack);
        TextView time = (TextView) listItem.findViewById(R.id.TimeTrack);
        final ImageButton button = (ImageButton) listItem.findViewById(R.id.play);

        Integer seconds = currentTrack.getTime();
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60);
        long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) * 60);
        String Ssecond = Integer.toString((int) second);
        if (second < 10) {
            Ssecond = "0" + second;
        }
        name.setText(currentTrack.getName());
        time.setText(minute + ":" + Ssecond);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = currentTrack.getPreview();

                if (nowURL == null) {
                    nowURL = url;
                    try {
                        mediaPlayer.setDataSource(url);
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(url);
                System.out.println(nowURL);
                if (nowURL.equals(url)){
                    if(nowButton == null){
                        nowButton=button;
                    }
                    if(mediaPlayer.isPlaying()){
                        button.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                        mediaPlayer.pause();
                    }else{
                        button.setImageResource(R.drawable.ic_pause_black_24dp);
                        mediaPlayer.start();
                    }
                }else{
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    nowButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    nowButton=button;
                    mediaPlayer=null;
                    mediaPlayer=new MediaPlayer();
                    nowURL=url;
                    button.setImageResource(R.drawable.ic_pause_black_24dp);
                    try {
                        mediaPlayer.setDataSource(url);
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mediaPlayer.start();
                }
            }
        });

        return listItem;
    }
}
