package com.example.bulletbuddy;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class ErrandListFragment<errandAdapter> extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String SHAREDPREF_KEY = "shared_preferences";
    private static final String ERRANDLIST_KEY = "errand_list";


    // TODO: Customize parameters
    private int mColumnCount = 1;
    private List<Errands> errand;
    private RecyclerView.Adapter errandAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ErrandListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ErrandListFragment newInstance(int columnCount) {
        ErrandListFragment fragment = new ErrandListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        loadErrands();
        errandAdapter = new ErrandRecyclerViewAdapter(errand);
    }


    @Override
    public void onPause() {
        super.onPause();
        saveErrands();
    }

    private void saveErrands() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHAREDPREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // TODO: Add GSON to gradle
        Gson gson = new Gson();
        String errandListString = gson.toJson(errand);
        editor.putString(ERRANDLIST_KEY, errandListString);
        editor.apply();
    }

    private void loadErrands() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHAREDPREF_KEY, Context.MODE_PRIVATE);
        String errandListString = sharedPreferences.getString(ERRANDLIST_KEY, null);
        Type type = new TypeToken<ArrayList<Errands>>(){}.getType();
        Gson gson = new Gson();
        errand = gson.fromJson(errandListString, type);
        if (errand == null){
            errand = new ArrayList<>();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_errand_list_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            //recyclerView.setAdapter(new ErrandRecyclerViewAdapter(PlaceholderContent.ITEMS));
            recyclerView.setAdapter(errandAdapter);
            RecyclerView.ItemDecoration decoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(decoration);
        }
        return view;
    }
    public void addErrand(Errands errands) {
        errand.add(errands);
        errandAdapter.notifyItemChanged(errand.size() - 1);
    }
}