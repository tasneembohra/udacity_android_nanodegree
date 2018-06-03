package movies.popular.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieList {

    @SerializedName("total_pages")
    public int totalPages;

    @SerializedName("total_results")
    public int totalResults;

    @SerializedName("page")
    public int page;

    @SerializedName("results")
    public ArrayList<Movie> results;
}
