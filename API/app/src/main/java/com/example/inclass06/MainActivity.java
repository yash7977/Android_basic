package com.example.inclass06;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button Select;
    TextView Category;
    TextView Title;
    TextView Date;
    ImageView NewsImage;
    TextView Description;
    ImageView previous;
    ImageView next;
    TextView indexValue;
    Integer index =0;
    ProgressBar progressBar;
    TextView Loading;

    String[] categories = {"business", "entertainment", "general", "health", "science", "sports","technology"};
    String selectedCatrgory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("News");

        Category = findViewById(R.id.Category);
        Select = findViewById(R.id.Select);
        Title = findViewById(R.id.Title);
        Date = findViewById(R.id.Date);
        NewsImage = findViewById(R.id.NewsImage);
        Description = findViewById(R.id.Description);
        previous = findViewById(R.id.previous);
        next = findViewById(R.id.next);
        indexValue = findViewById(R.id.indexValue);
        progressBar = findViewById(R.id.progressBar);
        Loading = findViewById(R.id.Loading);


        Title.setVisibility(View.INVISIBLE);
        Date.setVisibility(View.INVISIBLE);
        Description.setVisibility(View.INVISIBLE);
        indexValue.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        Loading.setVisibility(View.INVISIBLE);
        Select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setItems(categories, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        index =0;
                        selectedCatrgory = categories[which];
                        progressBar.setVisibility(View.VISIBLE);
                        Loading.setVisibility(View.VISIBLE);
                        Category.setText(selectedCatrgory);
                        String url = "https://newsapi.org/v2/top-headlines?category="+selectedCatrgory+"&apiKey=4c4b37767b4745219c14fbf1d24850e7";
                        if (isConnected()) {
                            new GetSimpleAsync().execute(url);
                        } else {
                            Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
                alertDialogBuilder.show();
            }
        });






}

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }


    private class GetSimpleAsync extends AsyncTask<String, Void, ArrayList<NewsDomain>> {
        @Override
        protected ArrayList<NewsDomain> doInBackground(String... params) {
            StringBuilder stringBuilder = new StringBuilder();
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            ArrayList<NewsDomain> newsDomains = new ArrayList<>();


            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    String json = stringBuilder.toString();
                    JSONObject root = new JSONObject(json);
                    JSONArray news = root.getJSONArray("articles");
                    for (int i = 0; i < 20; i++) {
                        JSONObject newsDomainJSON = news.getJSONObject(i);
                        NewsDomain newsDomain = new NewsDomain();
                        newsDomain.title = newsDomainJSON.getString("title");
                        newsDomain.description = newsDomainJSON.getString("description");
                        newsDomain.url = newsDomainJSON.getString("url");
                        newsDomain.urlToImage = newsDomainJSON.getString("urlToImage");
                        newsDomain.publishedAt = newsDomainJSON.getString("publishedAt");
                        newsDomains.add(newsDomain);

                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return newsDomains;
        }

        @Override
        protected void onPostExecute(final ArrayList<NewsDomain> newsDomains) {
            progressBar.setVisibility(View.INVISIBLE);
            Loading.setVisibility(View.INVISIBLE);
            for (NewsDomain news :newsDomains ) {
                System.out.println(news);
            }
            Title.setText(newsDomains.get(0).getTitle());
            Date.setText(newsDomains.get(0).getPublishedAt());
            Description.setText(newsDomains.get(0).getDescription());
            Picasso.get().load(newsDomains.get(0).getUrlToImage()).into(NewsImage);
            Title.setVisibility(View.VISIBLE);
            Date.setVisibility(View.VISIBLE);
            Description.setVisibility(View.VISIBLE);
            indexValue.setVisibility(View.VISIBLE);
            indexValue.setText((index+1)+" Out of 20");

            previous.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (index>0){
                        index --;}
                    Title.setText(newsDomains.get(index).getTitle());
                    Date.setText(newsDomains.get(index).getPublishedAt());
                    Description.setText(newsDomains.get(index).getDescription());
                    Picasso.get().load(newsDomains.get(index).getUrlToImage()).into(NewsImage);
                    indexValue.setText((index+1)+" Out of 20");


                }

            });

            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (index<newsDomains.size()-1){
                        index++;
                    }
                    Title.setText(newsDomains.get(index).getTitle());
                    Date.setText(newsDomains.get(index).getPublishedAt());
                    Description.setText(newsDomains.get(index).getDescription());
                    Picasso.get().load(newsDomains.get(index).getUrlToImage()).into(NewsImage);
                    indexValue.setText((index+1) +" Out of 20");

                }
            });
















        }
    }


}
