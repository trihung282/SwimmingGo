package com.example.swimtracker.coach.library_manage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.swimtracker.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ButterflyStyleVideo extends AppCompatActivity {

    //public  static String API_KEY = "AIzaSyA032cWNiBq9d0m5-mg0PZ63Z6asWKGHtY";
    String ID_FREEPLAYLIST = "PLo9kSKqKYi5GKEJn7zZsDX3_q36lTE11G";
    String urlGetJson = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId="+ ID_FREEPLAYLIST +"&key="+ ManageKeyVideo.API_KEY +"&maxResults=50";

    ListView lvVideo;
    ArrayList<VideoYoutube> arrayVideo;
    VideoYoutubeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butterfly_style_video);

        lvVideo = (ListView) findViewById(R.id.listviewVideo);
        arrayVideo = new ArrayList<>();
        adapter = new VideoYoutubeAdapter(this, R.layout.row_video_youtube, arrayVideo);
        lvVideo.setAdapter(adapter);

        getJsonYouTube(urlGetJson);

        lvVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ButterflyStyleVideo.this, PlayVideo.class);
                intent.putExtra("idVideoYouTube", arrayVideo.get(position).getVideoID());
                startActivity(intent);
            }
        });



    }

    private void getJsonYouTube(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonItems = response.getJSONArray("items");

                            String title = "";
                            String urlThumbnail = "";
                            String idVideo = "";

                            for (int i = 0 ; i < jsonItems.length() ; i++){

                                JSONObject jsonItem = jsonItems.getJSONObject(i);
                                JSONObject jsonSnippet = jsonItem.getJSONObject("snippet");
                                title = jsonSnippet.getString("title");

                                JSONObject jsonThumbnail = jsonSnippet.getJSONObject("thumbnails");
                                JSONObject jsonMedium = jsonThumbnail.getJSONObject("medium");
                                urlThumbnail = jsonMedium.getString("url");

                                JSONObject jsonResourceID = jsonSnippet.getJSONObject("resourceId");
                                idVideo = jsonResourceID.getString("videoId");

                                arrayVideo.add(new VideoYoutube(title, urlThumbnail, idVideo));

                                //Toast.makeText(FreeStyleVideo.this, urlThumbnail, Toast.LENGTH_SHORT).show();
                            }

                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ButterflyStyleVideo.this, "Lỗi", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

}
