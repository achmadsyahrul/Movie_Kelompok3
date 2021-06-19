package com.example.movie.view.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movie.adapter.MovieDiscoverAdapter;
import com.example.movie.model.movie.MovieDiscoverResponse;
import com.example.movie.model.movie.MovieDiscoverResultsItem;
import com.example.movie.service.ApiMain;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieViewModel extends ViewModel {
    private ApiMain apiMain;

    private MutableLiveData<ArrayList<MovieDiscoverResultsItem>> listDiscoverMovie = new MutableLiveData<>();

    public void setMovieDiscover(){
        if (this.apiMain == null){
            apiMain = new ApiMain();
        }

        apiMain.getApiMovie().getMovieDiscover().enqueue(new Callback<MovieDiscoverResponse>() {
            @Override
            public void onResponse(Call<MovieDiscoverResponse> call, Response<MovieDiscoverResponse> response) {
                MovieDiscoverResponse responseDiscover = response.body();
                if (responseDiscover != null && responseDiscover.getResults() != null){
                    ArrayList<MovieDiscoverResultsItem> movieDiscoverItems = responseDiscover.getResults();
                    listDiscoverMovie.postValue(movieDiscoverItems);

                }
            }

            @Override
            public void onFailure(Call<MovieDiscoverResponse> call, Throwable t) {

            }
        });
    }

    public LiveData<ArrayList<MovieDiscoverResultsItem>> getMoviesDiscover(){
        return listDiscoverMovie;
    }

}
