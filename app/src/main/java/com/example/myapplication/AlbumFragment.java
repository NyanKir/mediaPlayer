package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlbumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlbumFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private Integer id;
    ArrayList<Integer> integerArrayList;
    ArrayList<Media> mediaArrayList;

    JSONObject Jobject = null;


    public AlbumFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AlbumFragment newInstance(Integer param1, ArrayList<Integer> param2,ArrayList<Media> param3) {
        AlbumFragment fragment = new AlbumFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putIntegerArrayList(ARG_PARAM2, param2);
        args.putParcelableArrayList(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(ARG_PARAM1);
            integerArrayList = getArguments().getIntegerArrayList(ARG_PARAM2);
            mediaArrayList = getArguments().getParcelableArrayList(ARG_PARAM3);
        }
    }


    private ImageView imageView;
    private TextView title;
    private TextView style;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_album, container, false);
        // Inflate the layout for this fragment
        imageView = v.findViewById(R.id.Image_album);
        title = v.findViewById(R.id.Title);
        style = v.findViewById(R.id.style);
        listView= v.findViewById(R.id.listview);
        AlbumTask mt = new AlbumTask();
        String url = "https://api.deezer.com/album/" + id;
        mt.execute(url);
        ImageButton button = v.findViewById(R.id.back);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onn Media PLayer
//                Добавить потом в фоновом(возможно)
                TrackAdapter.mediaPlayer.stop();
                TrackAdapter.mediaPlayer.release();
                GridMedia gridMedia=GridMedia.newInstance(integerArrayList,mediaArrayList);
                assert getFragmentManager() != null;
                getFragmentManager().beginTransaction()
                        .replace(R.id.main_frame, gridMedia)
                        .commit();
            }
        });
        return v;
    }

    class AlbumTask extends AsyncTask<String, Integer, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            try {
                int count = strings.length;
                long totalSize = 0;
                for (int i = 0; i < count; i++) {
                    Request request = new Request.Builder()
                            .url(strings[i])
                            .get()
                            .addHeader("x-rapidapi-host", "deezerdevs-deezer.p.rapidapi.com")
                            .addHeader("x-rapidapi-key", "29ecaf52cbmsh15ca32297b7dc07p1a4ac7jsnf634b7deec73")
                            .build();
                    Response response = client.newCall(request).execute();
                    String jsonData = response.body().string();
                    Jobject = new JSONObject(jsonData);
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            try {
                JSONArray jsonArray = Jobject.getJSONObject("tracks").getJSONArray("data");
                Picasso.get().load(Jobject.getString("cover_big")).into(imageView);
                title.setText(Jobject.getString("title"));
                style.setText(Jobject.getJSONObject("genres").getJSONArray("data").getJSONObject(0).getString("name"));
                ArrayList<Track> tracks = new ArrayList<>();
                for (int i = 0; jsonArray.length() > i; i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    tracks.add(new Track(object));
                }
                TrackAdapter mAdapter = new TrackAdapter(getContext(), tracks);
                listView.setAdapter(mAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
