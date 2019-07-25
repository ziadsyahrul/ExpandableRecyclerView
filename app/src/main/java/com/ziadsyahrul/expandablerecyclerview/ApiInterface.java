package com.ziadsyahrul.expandablerecyclerview;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("movielist.json")
    Call<List<Movie>> getAllMovies();

}
