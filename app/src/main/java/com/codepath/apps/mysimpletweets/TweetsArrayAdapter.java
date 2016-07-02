package com.codepath.apps.mysimpletweets;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by mbytsang on 6/27/16.
 */

//Takes tweets objects and turn them into views displayed in list
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {

    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, 0, tweets);
    }

    //Override and set up custom template
    //ViewHolder pattern, to every array adapter for optimization

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the tweet
        final Tweet tweet = getItem(position);

        //find or inflate the template
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }
        //find subviews to fill in data template

        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        //ivProfileImage.setTag(tweet.getUser().getScreenName());
        //ivProfileImage.setTag( ,tweet.getUser()); //

        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        TextView tvTimeStamp = (TextView) convertView.findViewById(R.id.tvTimeStamp);



        //populate data into subviews
        tvUserName.setText(tweet.getUser().getScreenName());
        tvBody.setText(tweet.getBody());
        tvTimeStamp.setText(tweet.getRelativeTimeAgo());
        ivProfileImage.setImageResource(android.R.color.transparent); //clear old image for recycle view
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);

        ivProfileImage.setTag(tweet.getUser().getScreenName());
        ivProfileImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //String user = ivProfileImage.getTag().toString();
                //User account = (User) ivProfileImage.getTag();
                Intent i = new Intent(getContext(), ProfileActivity.class);
                i.putExtra("screen_name", (String) v.getTag());
                //i.putExtra("user", String.valueOf(account));
                getContext().startActivity(i);

            }
        });



        //return view to be inserted into list
        return convertView;
    }


}
