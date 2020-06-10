package com.timkontrakan.githubusersearch.adapter;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.timkontrakan.githubusersearch.R;
import com.timkontrakan.githubusersearch.fragment.FollowersFragments;
import com.timkontrakan.githubusersearch.fragment.FollowingFragments;
import com.timkontrakan.githubusersearch.model.Users;

public class PageAdapter extends FragmentPagerAdapter {

    private final Context context;
    private final Users users;

    public PageAdapter(Context context, FragmentManager fm, Users users) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
        this.users = users;
    }

    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.following,
            R.string.followers
    };

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new FollowingFragments();
                Bundle bundleFollowing = new Bundle();
                bundleFollowing.putString(FollowingFragments.EXTRA_FOLLOWING, users.getLogin());
                fragment.setArguments(bundleFollowing);
                break;
            case 1:
                fragment = new FollowersFragments();
                Bundle bundleFollowers = new Bundle();
                bundleFollowers.putString(FollowersFragments.EXTRA_FOLLOWERS, users.getLogin());
                fragment.setArguments(bundleFollowers);
        }
        assert fragment != null;
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
