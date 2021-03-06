package com.codepath.apps.mysimpletweets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by mbytsang on 6/30/16.
 */
public class ComposeActivity extends AppCompatActivity{

    TwitterClient client;
    User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        client = TwitterApplication.getRestClient();

    }
        //Get account info


/*
        startActivityForResult


        //Get screen name from activity that launches this
        String screenName = getIntent().getStringExtra("screen_name");
        if (savedInstanceState == null){
            //Create user timeline fragment
            UserTimelineFragment fragmentUserTimeline = UserTimelineFragment.newInstance(screenName);
            //Display user timeline fragment within activity (dynamic)
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, fragmentUserTimeline);
            ft.commit(); //changes the fragments
        }
        */

    private void populateProfileHeader(User user) {
        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvTagLine = (TextView) findViewById(R.id.tvTagName);
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvName.setText(user.getName());
        tvTagLine.setText(user.getTagline());
        Picasso.with(this).load(user.getProfileImageUrl()).into(ivProfileImage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate menu, adds items to action bar
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    //network request to status/update
    public void updateStatus(View view) {
        EditText simpleEditText = (EditText) findViewById(R.id.etStatus);
        String strValue = simpleEditText.getText().toString();

        client.composeTweet(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                //make new tweet with JSONobject
                Tweet tweet = Tweet.fromJSON(response);
                Intent i = new Intent();
                i.putExtra("Tweet", tweet);

                setResult(RESULT_OK, i);
                finish();
                //Toast.makeText(getApplicationContext(), "Tweet posted!", Toast.LENGTH_SHORT).show();

            }
        }, strValue);



    }
}
