package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.PreferencesManager;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.service.DisplayFavoriteList;


public class ViewNeighbourFragment extends Fragment {


    private View v;
    private DisplayFavoriteList displayFavoriteList;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        displayFavoriteList = (DisplayFavoriteList) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.viewneighbouractivity, container,false);

        Bundle bundle = getArguments();
        int id=bundle.getInt("id",0);
        String userName=bundle.getString("username");
        String photo=bundle.getString("photo");
        String address=bundle.getString("addresse");
        String numtel=bundle.getString("phonenumber");
        String addmail=bundle.getString("addressemail");
        String aproposdemoi=bundle.getString("aboutme");
        Boolean isfavorite=bundle.getBoolean("isFavorite",false);


        TextView name = v.findViewById(R.id.nameLyt);
        TextView name1 = v.findViewById(R.id.nameLyt1);
        TextView address1 = v.findViewById(R.id.addressInput);
        TextView phone1 = v.findViewById(R.id.phoneNumberLyt);
        TextView mail = v.findViewById(R.id.avatarUrlLyt);
        TextView apropos = v.findViewById(R.id.aboutMeLyt);


        ImageView   img = v.findViewById(R.id.kb9);
        Glide.with(this).load(photo).into(img);

        name.setText(userName);
        name1.setText(userName);
        address1.setText(address);
        phone1.setText(numtel);
        mail.setText(addmail);
        apropos.setText(aproposdemoi);

        v.findViewById(R.id.ReturnButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        v.findViewById(R.id.button_etoile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PreferencesManager prefs = PreferencesManager.getInstance();

                if (isfavorite == false)
                {
                    prefs.setIntValue("id", id);
                    prefs.setStringValue("userName", userName);
                    prefs.setStringValue("photo", photo);
                    prefs.setStringValue("address", address);
                    prefs.setStringValue("numtel", numtel);
                    prefs.setStringValue("addmail", addmail);
                    prefs.setStringValue("aproposdemoi", aproposdemoi);

                    displayFavoriteList.apply();

                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        });


        return v;
    }
}