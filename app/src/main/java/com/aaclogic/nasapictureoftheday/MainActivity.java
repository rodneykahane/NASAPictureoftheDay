package com.aaclogic.nasapictureoftheday;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import com.aaclogic.nasapictureoftheday.UrlImageView;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    // Constants:
    private final String BASE_URL = "https://api.nasa.gov/planetary/apod";
    final int REQUEST_CODE = 123;
    private final String KEY = "N39MRyIlT7BArSOizZYxHWQFwwWHCziye3csiiTX";

    // Member Variables:
    TextView mPicDescTextView;
    ImageView mPicImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPicDescTextView = findViewById(R.id.picDescLabel);
        mPicImageView = findViewById(R.id.imageNasa);

    }//end onCreate


    @Override
    protected void onResume() {
        super.onResume();
        Log.d("NASA Picture of the day", "onResume() called");
        getNASAImage();
    }//end onResume

    private void getNASAImage() {

        String crypto = "BTC";

        RequestParams params = new RequestParams();
        params.put("api_key", KEY);

        letsDoSomeNetworking(params);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, REQUEST_CODE);
            return;
        }


    }//end getNASAImage

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("NASA Picture of the day", "onRequestPermissionsResult(): PermissionGranted!");
                getNASAImage();
            } else {
                Log.d("NASA Picture of the day", "Permission denied =(");
            }
        }
    }//end onRequestPermissionsResult


    // TODO: complete the letsDoSomeNetworking() method
    private void letsDoSomeNetworking(RequestParams params) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(BASE_URL, params, new JsonHttpResponseHandler() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // called when response HTTP status is "200 OK"
                Log.d("NASA Picture of the day", "letsDoSomeNetworking() JSON: " + response.toString());
                NASADataModel nasaData = NASADataModel.fromJSON(response);
                updateUI(nasaData);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.d("NASA Picture of the day", "Request fail! Status code: " + statusCode);
                Log.d("NASA Picture of the day", "Fail response: " + response);
                Log.e("ERROR", e.toString());
                Toast.makeText(MainActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });


    }//end letsDoSomeNetworking

    private void updateUI(NASADataModel nasaStuff) {
        Log.d("NASA Picture of the day", "inside of updateUI now");
        String picText = nasaStuff.getDesc();
        String urlText = nasaStuff.getUrl();
        mPicDescTextView.setText(picText);



        ((UrlImageView)findViewById(R.id.imageNasa)).setImageUrl(urlText); //https://stackoverflow.com/questions/14332296/how-to-set-image-from-url-using-asynctask/15797963#15797963


    }//end updateUI


}
