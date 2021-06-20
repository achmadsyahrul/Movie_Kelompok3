package com.example.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movie.R;
import com.example.movie.model.movie.MovieDiscoverResultsItem;
import com.example.movie.view.activity.DetailMovieActivity;

import java.util.ArrayList;

public class MovieDiscoverAdapter extends RecyclerView.Adapter<MovieDiscoverAdapter.ViewHolder> {

    private ArrayList<MovieDiscoverResultsItem> movieDiscoverResultsItems = new ArrayList<>();
    private Context context;
    Cursor cursor;
    private static String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w185/";

    public MovieDiscoverAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<MovieDiscoverResultsItem> items){
        movieDiscoverResultsItems.clear();
        movieDiscoverResultsItems.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieDiscoverAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieDiscoverAdapter.ViewHolder holder, int position) {
        final MovieDiscoverResultsItem item = movieDiscoverResultsItems.get(position);

        Glide.with(context).load(BASE_IMAGE_URL + item
                .getPosterPath())
                .into(holder.ivThumb);
        holder.tvTitle.setText(item.getTitle());
        holder.tvRate.setText(String.valueOf(item.getVoteAverage()));

        holder.cvItem.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(context, DetailMovieActivity.class);

                MovieDiscoverResultsItem items = new MovieDiscoverResultsItem();
                items.setId(item.getId());
                items.setTitle(item.getTitle());
                items.setPosterPath(BASE_IMAGE_URL + item.getPosterPath());
                items.setOverview(item.getOverview());
                items.setVoteAverage(item.getVoteAverage());
                items.setBackdropPath(BASE_IMAGE_URL + item.getBackdropPath());


                intent.putExtra(DetailMovieActivity.DETAIL_MOVIE, items);
                context.startActivity(intent);
                Log.d("AdapterClickMovie", "MOVE INTO DETAIL ACTIVITY");
            } catch (Exception e) {
                Log.d("AdapterClickMovie", "GAGAL KLIK MOVIENYA");
                Toast.makeText(context, "Gagal Menampilkan Detail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieDiscoverResultsItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivThumb;
        TextView tvTitle,tvRate;
        CardView cvItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvItem = itemView.findViewById(R.id.itemlist_cv);
            ivThumb = itemView.findViewById(R.id.itemlist_iv_thumbnail);
            tvTitle = itemView.findViewById(R.id.itemlist_tv_title);
            tvRate = itemView.findViewById(R.id.itemlist_tv_rate);
        }
    }

    public void setDataCursor(Cursor dataCursor) {
        cursor = dataCursor;
        notifyDataSetChanged();
    }
}
