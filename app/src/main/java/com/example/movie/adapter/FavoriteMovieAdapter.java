package com.example.movie.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.movie.database.MovieTable;
import com.example.movie.model.movie.MovieDiscoverResultsItem;
import com.example.movie.view.activity.DetailMovieActivity;

import java.util.ArrayList;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.ViewHolder> {

    private ArrayList<MovieTable> movieItems = new ArrayList<>();
    private Context context;

    private static String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w185/";

    public FavoriteMovieAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<MovieTable> items) {
        movieItems.clear();
        movieItems.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteMovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.favorite_item_list, viewGroup, false);
        return new FavoriteMovieAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteMovieAdapter.ViewHolder viewHolder, int i) {
        final MovieTable item = movieItems.get(i);

        Glide.with(context).load(item.getPosterPath()).into(viewHolder.ivPoster);
        viewHolder.tvTitle.setText(item.getTitle());
        viewHolder.tvRating.setText(String.valueOf(item.getVoteAverage()));

        viewHolder.cvFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        TextView tvTitle;
        TextView tvRating;
        CardView cvFilm;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPoster = itemView.findViewById(R.id.fav_itemlist_iv_thumbnail);
            tvTitle = itemView.findViewById(R.id.fav_itemlist_tv_title);
            tvRating = itemView.findViewById(R.id.fav_itemlist_tv_rate);
            cvFilm = itemView.findViewById(R.id.fav_itemlist_cv);
        }
    }
}
