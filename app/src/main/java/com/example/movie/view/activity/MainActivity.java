package com.example.movie.view.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.movie.MovieCatalogueProvider;
import com.example.movie.adapter.MovieDiscoverAdapter;
import com.example.movie.view.fragment.FavoriteFragment;
import com.example.movie.view.fragment.MovieFragment;
import com.example.movie.R;
import com.example.movie.view.fragment.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.example.movie.base.BaseAppCompactActivity.KEY_TITLE;
import static com.example.movie.base.BaseAppCompactActivity.KEY_FRAGMENT;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private Fragment selectedFragment = new MovieFragment();
    MovieDiscoverAdapter movieDiscoverAdapter;

    SharedPreferences sharedPreferences;

    String title = "Home";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences("mypref", MODE_PRIVATE);
        Boolean check = sharedPreferences.getBoolean("logged", false);
        if(!check) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            bottomNavigationView = findViewById(R.id.main_bottomnav);
            bottomNavigationView.setOnNavigationItemSelectedListener(this);

            loadFragment(selectedFragment);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_bottomnav_movie:
                selectedFragment = new MovieFragment();
                loadFragment(selectedFragment);
                break;

            case R.id.menu_bottomnav_fav:
                selectedFragment = new FavoriteFragment();
                loadFragment(selectedFragment);
                break;

            case R.id.menu_bottomnav_user:
                selectedFragment = new UserFragment();
                loadFragment(selectedFragment);
                break;
        }

        return loadFragment(selectedFragment);
    }

    private boolean loadFragment(Fragment selectedFragment) {
        if (selectedFragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fragmentcontainer, selectedFragment)
                    .commit();
            return true;
        }
        return false;
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_TITLE, title);

        getSupportFragmentManager().putFragment(outState, KEY_FRAGMENT, selectedFragment);
    }

    private LoaderManager.LoaderCallbacks<Cursor> loaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {
        @NonNull
        @Override
        public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
            switch (i) {
                case 1:
                    return new CursorLoader(
                            getApplicationContext(),
                            MovieCatalogueProvider.URI_FAVORITE,
                            new String[]{
                                    "title",
                                    "poster_path",
                                    "overview",
                                    "vote_average",
                                    "category"
                            },
                            null,
                            null,
                            null
                    );

                default:
                    throw new IllegalArgumentException();
            }
        }

        @Override
        public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
            switch (loader.getId()) {
                case 1:
                    movieDiscoverAdapter.setDataCursor(cursor);
                    break;
            }
        }

        @Override
        public void onLoaderReset(@NonNull Loader<Cursor> loader) {
            switch (loader.getId()) {
                case 1:
                    movieDiscoverAdapter.setDataCursor(null);
                    break;
            }
        }
    };
}