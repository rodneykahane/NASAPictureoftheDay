package com.aaclogic.nasapictureoftheday;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class NASADataModel {

    //member variables
    private String mUrl;
    private String mDesc;
    private String mTitle;

    //PriceDataModel from a JSON
    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static NASADataModel fromJSON(JSONObject jsonObject){

        try {

            NASADataModel nasaData = new NASADataModel();


            nasaData.mUrl = jsonObject.getString("url");
            Log.d("NASA Picture of the day","NASADataModel - the pic url is "+nasaData.mUrl);

            nasaData.mDesc = jsonObject.getString("explanation");
            Log.d("NASA Picture of the day","NASADataModel - the pic desc is "+nasaData.mDesc);

            nasaData.mTitle = jsonObject.getString("title");
            Log.d("NASA Picture of the day","NASADataModel - the pic title is "+nasaData.mTitle);

            return nasaData;
        } catch(JSONException e){
            e.printStackTrace();
            return null;
        }//end try/catch

    }//end BitcoinDataModel

    public String getUrl() {
        return mUrl;
    }

    public String getDesc() {
        return mDesc;
    }

    public String getTitle(){
        return mTitle;
    }
}
