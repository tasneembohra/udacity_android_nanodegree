package movies.popular.network.api;

import android.util.Log;

import java.util.ArrayList;

import movies.popular.network.BuildConfig;
import movies.popular.network.RetrofitClient;
import movies.popular.network.model.Movie;
import movies.popular.network.model.MovieList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesListApi {
    public interface MoviesListApiListener {
        void onSuccess(ArrayList<Movie > list, int page, int totalPages);
        void onError();
    }

    private Callback<MovieList> callback = new Callback<MovieList>() {
        @Override
        public void onResponse(Call<MovieList> call, Response<MovieList> response) {
            Log.d("MoviesListApi", "onResponse: "+response);
            MovieList movieList = response.body();
            if (movieList != null) listener.onSuccess(movieList.results, movieList.page, movieList.totalPages);
            else listener.onError();
        }

        @Override
        public void onFailure(Call<MovieList> call, Throwable t) {
            Log.d("MoviesListApi", "onFailure: ");
            listener.onError();
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    };


    private MoviesListApiListener listener;

    public MoviesListApi(MoviesListApiListener listener) {
        this.listener = listener;
    }

    public void getPopularMovies(int page) {
        Call<MovieList> list = RetrofitClient.getClient().getPopularMovies(BuildConfig.movieDBApiKey, page);
        list.enqueue(callback);
    }

    public void getTopRatedMovie(int page) {
        RetrofitClient.getClient().getTopRatedMovies(BuildConfig.movieDBApiKey, page).enqueue(callback);

    }

}
