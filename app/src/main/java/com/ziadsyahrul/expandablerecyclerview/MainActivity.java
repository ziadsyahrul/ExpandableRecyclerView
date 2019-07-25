package com.ziadsyahrul.expandablerecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mindorks.placeholderview.ExpandablePlaceHolderView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Map<String, List<Movie>> categoryMap;
    private List<Movie> movieList;

    private ExpandablePlaceHolderView expandablePlaceHolderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieList = new ArrayList<>();
        categoryMap = new HashMap<>();
        expandablePlaceHolderView = (ExpandablePlaceHolderView) findViewById(R.id.expandablePlaceHolder);

        loadData();

        expandablePlaceHolderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Clicked", v.getId()).show();
            }
        });

    }

    private void loadData() {

        ApiInterface apiInterface = ApiClient.getInstance().create(ApiInterface.class);
        apiInterface.getAllMovies().enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                movieList = response.body();
                getHeaderAndChild(movieList);
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {

            }
        });

    }

    private void getHeaderAndChild(List<Movie> movieList) {
        for (Movie movie : movieList) {
            List<Movie> movieList1 = categoryMap.get(movie.getCategory());
            if (movieList1 == null) {
                movieList1 = new ArrayList<>();
            }

            movieList1.add(movie);
            categoryMap.put(movie.getCategory(), movieList1);
        }

        Log.d("MAP", categoryMap.toString());
        Iterator it = categoryMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Log.d("Key", pair.getKey().toString());
            expandablePlaceHolderView.addView(new HeaderView(this, pair.getKey().toString()));
            List<Movie> movieList1 = (List<Movie>)pair.getValue();

            for (Movie movie : movieList1) {
                expandablePlaceHolderView.addView(new ChildView(this, movie));
            }

            it.remove();
        }
    }
}

