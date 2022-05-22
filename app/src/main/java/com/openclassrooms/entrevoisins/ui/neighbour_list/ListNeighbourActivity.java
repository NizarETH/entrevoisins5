package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.openclassrooms.entrevoisins.PreferencesManager;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.service.DisplayFavoriteList;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListNeighbourActivity extends AppCompatActivity implements DisplayFavoriteList {

    private static final String TAG = "ListNeighbourActivity";
    // UI Components
    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.container)
    ViewPager mViewPager;

    ListNeighbourPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_neighbour);
        PreferencesManager.getInstance().clear();

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mPagerAdapter = new ListNeighbourPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                //
            }

            @Override
            public void onPageSelected(int position) {



                      NeighbourFavorisFragment neighbourFavorisFragment = (NeighbourFavorisFragment) getSupportFragmentManager()
                             .getFragments().get(1);
                NeighbourFragment neighbourFragment = (NeighbourFragment) getSupportFragmentManager()
                          .getFragments().get(0);

                    if (position == 0) {
                       // neighbourFragment.initList();
                    } else if (position == 1) {
                      //  neighbourFavorisFragment.initList();

                    }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //
            }
        });

    }

    @OnClick(R.id.add_neighbour)
    void addNeighbour() {
        AddNeighbourActivity.navigate(this);
    }

    @Override
    public void apply() {

        NeighbourFavorisFragment neighbourFavorisFragment = (NeighbourFavorisFragment) getSupportFragmentManager().getFragments().get(1);
        neighbourFavorisFragment.initList();
        mViewPager.setCurrentItem(1);
    }
}
