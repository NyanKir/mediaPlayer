package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;


public class GridMedia extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private ArrayList<Integer> arrayList;
    private ArrayList<Media> mediaList;

    public GridMedia() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static GridMedia newInstance(ArrayList<Integer> arrayList, ArrayList<Media> mediaList) {
        GridMedia fragment = new GridMedia();
        Bundle args = new Bundle();
        args.putIntegerArrayList(ARG_PARAM1, arrayList);
        args.putParcelableArrayList(ARG_PARAM2, mediaList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            arrayList = getArguments().getIntegerArrayList(ARG_PARAM1);
            mediaList = getArguments().getParcelableArrayList(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_grid_media, container, false);



//        GridView
        MediaAdapter mAdapter = new MediaAdapter(getContext(), mediaList);
        GridView gridView = v.findViewById(R.id.gridView);
        gridView.setAdapter(mAdapter);
        adjustGridView(gridView);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlbumFragment albumFragment = AlbumFragment.newInstance(arrayList.get(position), arrayList, mediaList);
                assert getFragmentManager() != null;
                getFragmentManager().beginTransaction()
                        .replace(R.id.main_frame, albumFragment)
                        .commit();
            }
        });
        // Inflate the layout for this fragment
        return v;
    }

    private void adjustGridView(GridView gridView) {
        gridView.setNumColumns(2);
        gridView.setVerticalSpacing(15);
        gridView.setHorizontalSpacing(15);
    }
}
