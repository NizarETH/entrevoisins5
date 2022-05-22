package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArraySet;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.entrevoisins.PreferencesManager;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.GetIdNeighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;


public class NeighbourFavorisFragment extends Fragment implements GetIdNeighbour   {

    private NeighbourApiService mApiService;
 
    private RecyclerView mRecyclerView;
    private List<Neighbour> favoriteNeighbours ;


    /**
     * Create and return a new instance
     * @return @{@link NeighbourFavorisFragment}
     */
    public static NeighbourFavorisFragment newInstance() {
        NeighbourFavorisFragment fragment = new NeighbourFavorisFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getNeighbourApiService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_neighbour_list_favori, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        return view;
    }

    /**
     * Init the List of neighbours
     */
    public void initList() {

        favoriteNeighbours = new ArrayList<>();

        List<Neighbour> mNeighbours = mApiService.getNeighbours();
        for (int i = 0; i < mNeighbours.size(); i++) {
            if(mNeighbours.get(i).getFavorite())
                favoriteNeighbours.add(mNeighbours.get(i));
            
        }
 
        PreferencesManager prefs = PreferencesManager.getInstance();
        Neighbour neighbour = new Neighbour(prefs.getIntValue("id"),
                prefs.getStringValue("userName"),
                prefs.getStringValue("photo"),
                prefs.getStringValue("address"),
                prefs.getStringValue("numtel"),
                prefs.getStringValue("addmail"),
                prefs.getStringValue("aproposdemoi"),
                true
                );
        if(prefs.getStringValue("userName") != null && !prefs.getStringValue("userName").isEmpty())
        {
            favoriteNeighbours.add(neighbour);
          prefs.clear();
        }

        mRecyclerView.setAdapter(new MyFavoriteNeighbourRecyclerViewAdapter(favoriteNeighbours,   this));
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * Fired if the user clicks on a delete button
     * @param event
     */
    @Subscribe
    public void onDeleteNeighbour(DeleteNeighbourEvent event) {
        mApiService.deleteNeighbour(event.neighbour);
        initList();
    }


    @Override
    public void value(String name) {
        for (int i = 0; i < mApiService.getNeighbours().size(); i++) {
            if(mApiService.getNeighbours().get(i).getName().equalsIgnoreCase(name))
               mApiService.deleteNeighbour(mApiService.getNeighbours().get(i));
        }

        NeighbourFragment neighbourFragment = (NeighbourFragment) getActivity(). getSupportFragmentManager()
                .getFragments().get(0);

        neighbourFragment.initList();
    }
}
