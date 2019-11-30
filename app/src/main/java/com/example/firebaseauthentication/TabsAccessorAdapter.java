package com.example.firebaseauthentication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabsAccessorAdapter extends FragmentPagerAdapter {
    public TabsAccessorAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {


        switch (position)
        {
            case  0:
                UserReminderFragment userReminderFragment = new UserReminderFragment();
                return userReminderFragment;

            case 1:
                UserReminderFragment userReminderFragment1 = new UserReminderFragment();
                return userReminderFragment1;

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case  0:
                return "Profile";

            case 1:
               return "Reminder";



            default:
                return null;
        }
    }
}
