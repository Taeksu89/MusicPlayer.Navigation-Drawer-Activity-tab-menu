package com.taeksukim.android.soundplayer;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ListFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    public static final String ARG_LIST_TYPE = "list-type";
    public static final String ARG_POSITION = "position";
    public static final String TYPE_SONG = "SONG";
    public static final String TYPE_ARTIST = "ARTIST";
    public static final String TYPE_ALBUM = "ALBUM";
    public static final String TYPE_GENRE = "GENRE";

    private int mColumnCount = 1;
    private String mListType = "";

    private List<?> datas;

    public ListFragment() {

    }

    public static ListFragment newInstance(int columnCount, String flag) {

        Bundle args = new Bundle();
        ListFragment fragment = new ListFragment();

        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putString(ARG_LIST_TYPE, flag);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            mListType = getArguments().getString(ARG_LIST_TYPE);

            Log.w("type", String.valueOf(mListType));

            if(TYPE_SONG.equals(mListType)) {
                datas = DataLoader.getSounds(getContext());
                Log.w("dataSize_S", String.valueOf(datas.size()));
            } else if(TYPE_ARTIST.equals(mListType)) {
                datas = DataLoader.getArtist(getContext());
                Log.w("dataSize_A", String.valueOf(datas.size()));
            }


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
//            if (mColumnCount <= 1) {
//                recyclerView.setLayoutManager(new LinearLayoutManager(context));
//            } else {
            if (mColumnCount > 1) {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            } else {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }
            Log.w("dataSize", String.valueOf(datas.size()));
            recyclerView.setAdapter(new ListAdapter(context,datas,mListType));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}