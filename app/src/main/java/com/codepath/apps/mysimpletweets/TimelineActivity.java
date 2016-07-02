package com.codepath.apps.mysimpletweets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.mysimpletweets.fragments.HomeTimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.MentionsTimelineFragment;
import com.codepath.apps.mysimpletweets.models.Tweet;

/*
    [] == JSONArray (ROOT)
    {} == JSONObject
    An empty fragment, a container activity
 */


public class TimelineActivity extends AppCompatActivity {

    TweetsPagerAdapter adapterViewPager;
    private final int REQUEST_CODE = 55;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        //Get viewPager
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
        //Set viewpager adapter for pager
        //vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));
        //Find sliding tabstrip
        adapterViewPager = new TweetsPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);

        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        //Attach tabstrip to viewpager
        tabStrip.setViewPager(vpPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate menu, adds items to action bar
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle action bar item clicks here
        //Handle clicks on Home/Up button
        //As specified a parent activity in AndroidManifest.xml
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void onProfileView(MenuItem mi){
        //Launch profile view
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

    public void composeTweet(MenuItem item) {
        Intent i = new Intent(this, ComposeActivity.class);
        startActivityForResult(i, REQUEST_CODE);
    }

    //launch user profile, clicked on picture
    public void onProfileView(View view) {
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

    //Return order of fragments in view pager
    public class TweetsPagerAdapter extends SmartFragmentStatePagerAdapter{
        private String tabTitles[] = {"Home", "Mentions"};

        //Adapter gets manager insert or remove fragment from activity
        public TweetsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        //The order and creation of fragments within pager
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new HomeTimelineFragment();
            } else if (position == 1){
                return new MentionsTimelineFragment();
            } else {
                return null;
            }
        }

        //Return tab title
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        //Returns how many fragments
        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            Tweet tweet = (Tweet) data.getSerializableExtra("Tweet");
            HomeTimelineFragment fragmentHomeTweets = (HomeTimelineFragment) adapterViewPager.getRegisteredFragment(0);
            fragmentHomeTweets.appendTweet(tweet);
        }
    }

    /*
    @Override
    protected void onActivityResult(Tweet tweet) {
        HomeTimelineFragment fragmentHomeTweets = (HomeTimelineFragment) adapterViewPager.getRegisteredFragment(0);
        fragmentHomeTweets.appendTweet(tweet);

    }
    */
}
