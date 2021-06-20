package com.example.movie.service;

import com.example.movie.model.movie.MovieDiscoverResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieRepository {
    @GET("3/movie/popular?api_key=caafcb0ebffcebb71e05cbb4a6ec4e95")
    Call<MovieDiscoverResponse> getMovieDiscover();
}
