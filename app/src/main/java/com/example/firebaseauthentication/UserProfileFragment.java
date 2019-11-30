package com.example.firebaseauthentication;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileFragment extends Fragment {

    private ViewPager myviewPager;
    private TabLayout myTablayout;
    private TabsAccessorAdapter mytabsAccessorAdapter;


    public UserProfileFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        myviewPager = view.findViewById(R.id.main_tabs_pager);
        mytabsAccessorAdapter = new TabsAccessorAdapter(getActivity().getSupportFragmentManager());
        myviewPager.setAdapter(mytabsAccessorAdapter);

        myTablayout = view.findViewById(R.id.main_tabs);
        myTablayout.setupWithViewPager(myviewPager);






       return view;
    }

}
