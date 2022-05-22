package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.GetIdFavoriteNeighbour;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class MyNeighbourRecyclerViewAdapter extends RecyclerView.Adapter<MyNeighbourRecyclerViewAdapter.ViewHolder> {

    private final List<Neighbour> mNeighbours;
    private   View view;
    private GetIdFavoriteNeighbour getIdFavoriteNeighbour;
    public MyNeighbourRecyclerViewAdapter(List<Neighbour> items, GetIdFavoriteNeighbour getIdFavoriteNeighbour ) {
        mNeighbours = items;
        this. getIdFavoriteNeighbour = getIdFavoriteNeighbour;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
          view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_neighbour, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Neighbour neighbour = mNeighbours.get(position);
        holder.mNeighbourName.setText(neighbour.getName());


        Glide.with(holder.mNeighbourAvatar.getContext())
                .load(neighbour.getAvatarUrl())
                .apply(RequestOptions.circleCropTransform())
                .into((ImageView) view.findViewById(R.id.item_list_avatar));

        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm r = Realm.getDefaultInstance();
                r.beginTransaction();
                neighbour.deleteFromRealm();
                r.commitTransaction();

                getIdFavoriteNeighbour.valueFavorite("item is deleted");

                notifyItemRemoved(holder.getAbsoluteAdapterPosition());
            }
        });
       holder.mainContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle i = new Bundle();
                i.putInt("id", (int) neighbour.getId());

                ViewNeighbourFragment viewNeighbourFragment =  new ViewNeighbourFragment();
                viewNeighbourFragment.setArguments(i);

                ListNeighbourActivity mainActivity = (ListNeighbourActivity)holder.mainContent.getContext();

                mainActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.viewFragments,viewNeighbourFragment)
                        .addToBackStack(null)
                        .commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return mNeighbours.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_list_avatar)
        public ImageView mNeighbourAvatar;

        @BindView(R.id.item_list_name)
        public TextView mNeighbourName;

        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteButton;

        @BindView(R.id.main_content)
        public LinearLayout mainContent;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
