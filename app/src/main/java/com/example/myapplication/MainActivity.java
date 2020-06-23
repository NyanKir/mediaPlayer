package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    GridView gridView;

    ArrayList<Integer> IDList;
    ArrayList<Media> mediaList;


    private OkHttpClient client;
    private Request request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//       Запрос API
        client = new OkHttpClient();

        request = new Request.Builder()
                .url("https://api.deezer.com/search?q=50cent")
                .get()
                .addHeader("x-rapidapi-host", "deezerdevs-deezer.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "29ecaf52cbmsh15ca32297b7dc07p1a4ac7jsnf634b7deec73")
                .build();
        MyTask mt = new MyTask();
        mt.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search,menu);
        MenuItem menuItem=menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                request = new Request.Builder()
                        .url("https://api.deezer.com/search?q="+query)
                        .get()
                        .addHeader("x-rapidapi-host", "deezerdevs-deezer.p.rapidapi.com")
                        .addHeader("x-rapidapi-key", "29ecaf52cbmsh15ca32297b7dc07p1a4ac7jsnf634b7deec73")
                        .build();
                MyTask mt = new MyTask();
                mt.execute();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_frame, someFragment)
                .commit();
    }



    //    I get data from Api
    public class MyTask extends AsyncTask<Void, Void, Void> {

        JSONArray myMediaJarray;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Toast.makeText(getApplicationContext(), "Loading...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                Response response = client.newCall(request).execute();

                String jsonData = response.body().string();
                JSONObject Jobject = new JSONObject(jsonData);
                myMediaJarray = Jobject.getJSONArray("data");

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            try {
                mediaList = new ArrayList<>();
                IDList = new ArrayList<>();
                JSONObject object = null;
                for (int i = 0; i < myMediaJarray.length(); i++) {
                    object = myMediaJarray.getJSONObject(i);
                    int j=IDList.indexOf(object.getJSONObject("album").getInt("id"));
                    if(j == -1){
                        IDList.add(object.getJSONObject("album").getInt("id"));
                        mediaList.add(new Media(object));
                    }
                }

                GridMedia gridMedia=GridMedia.newInstance(IDList,mediaList);
                replaceFragment(gridMedia);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


}
