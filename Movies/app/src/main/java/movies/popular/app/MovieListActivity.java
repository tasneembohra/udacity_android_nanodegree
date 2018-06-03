package movies.popular.app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import movies.popular.network.api.MoviesListApi;
import movies.popular.network.model.Movie;

/**
 * An activity representing a list of Movies. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link MovieDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 *
 * @author tasneem
 */
public class MovieListActivity extends AppCompatActivity implements MoviesListApi.MoviesListApiListener {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @Nullable
    @BindView(R.id.movie_detail_container) View mDetailContainerView;
    @BindView(R.id.movie_list) RecyclerView mRecyclerView;
    @BindView(R.id.coordinator) View mView;
    @BindView(R.id.progressBar) ProgressBar mProgressBar;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private MoviesListApi mMoviesListApi;
    private ArrayList<Movie> mMovieList = new ArrayList<>();
    private MovieListRecyclerAdapter mAdapter;
    private int mPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);
        mMoviesListApi = new MoviesListApi(this);

        setSupportActionBar(mToolbar);

        if (mDetailContainerView != null) {
            // The detail container view will be present only in the large-screen layouts (res/values-w900dp).
            // If this view is present, then the activity should be in two-pane mode.
            mTwoPane = true;
        }

        setupRecyclerView(mRecyclerView);
        getPopularMovies();
    }


    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        mAdapter = new MovieListRecyclerAdapter(this, mMovieList, mTwoPane);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    public void onSuccess(ArrayList<Movie> list, int page, int totalPages) {
        mMovieList.addAll(list);
        mAdapter.notifyDataSetChanged();
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onError() {
        Snackbar.make(mView, "Something went wrong!", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.popular :
                getPopularMovies();
                return true;
            case R.id.topRated:
                getTopRatedMovies();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getPopularMovies() {
        mToolbar.setTitle("Popular Movies");
        mMovieList.clear();
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mMoviesListApi.getPopularMovies(mPage);
    }

    private void getTopRatedMovies() {
        mToolbar.setTitle("Top Rated Movies");
        mMovieList.clear();
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mMoviesListApi.getTopRatedMovie(mPage);
    }
}
