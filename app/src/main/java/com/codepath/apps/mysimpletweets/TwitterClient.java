package com.codepath.apps.mysimpletweets;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "YwJ0xPlIEhXj9bzhNZdQPsKa1";       // Change this
	public static final String REST_CONSUMER_SECRET = "I66GUsH4naZdNgnjzYCtb3xjJTg1fX59fqdTCSfXTMGAuCVo4g"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://cpsimpletweets"; // Change this (here and in manifest)

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}



	// METHOD == ENDPOINT

    //HomeTimeLine - gets us the home timeline
    //GET statuses/home_timeline.json
    //  count = 25
    //  since_id = 1
    public void getHomeTimeline(AsyncHttpResponseHandler handler){
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        //Specify params
        RequestParams params = new RequestParams();
        params.put("count", 25);
        params.put("since_id", 1);
        //Execute the request
        getClient().get(apiUrl, params, handler);
    }




    public void getMentionsTimeline(JsonHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/mentions_timeline.json");
        //Specify params
        RequestParams params = new RequestParams();
        params.put("count", 25);
        //Execute the request
        getClient().get(apiUrl, params, handler);
    }

    public void getUserTimeline(String screenName, AsyncHttpResponseHandler handler){
        String apiUrl = getApiUrl("statuses/user_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", 25);
        params.put("screen_name", screenName);
        getClient().get(apiUrl, params, handler);
    }

    public void getUserInfo(String screenName, AsyncHttpResponseHandler handler){
        String apiUrl = getApiUrl("users/show.json");
        RequestParams params = new RequestParams();
        params.put("screen_name", screenName);
        getClient().get(apiUrl, params, handler);
    }

    public void getMyUserInfo(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("account/verify_credentials.json");
        getClient().get(apiUrl, null, handler);
    }

    /*
    //Get user info when clicked image
    public void getUserInfo( User user, AsyncHttpResponseHandler handler){
        String apiUrl = getApiUrl("users/show.json");
        RequestParams params = new RequestParams();
        params.put("screen_name", user.getScreenName());
        params.put("user_id", user.getUid());
        getClient().get(apiUrl, params, handler);

    }
    */




    //COMPOSING TWEET
    public void composeTweet(AsyncHttpResponseHandler handler, String tweet) {
	    //Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	    //   i.e getApiUrl("statuses/home_timeline.json")
        String apiUrl = getApiUrl("statuses/update.json");

        //Define the parameters to pass to the request (query or body)
        //  i.e RequestParams params = new RequestParams("foo", "bar");
        RequestParams params = new RequestParams();
        params.put("status", tweet);
        // Define the request method and make a call to the client
        //client.get(apiUrl, params, handler);
        client.post(apiUrl, params, handler);

    }



}