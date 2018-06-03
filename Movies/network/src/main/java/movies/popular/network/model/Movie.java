package movies.popular.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Movie implements Serializable {

    @SerializedName("release_date")
    public String releaseDate;

    @SerializedName("overview")
    public String overview;

    @SerializedName("adult")
    public boolean adult;

    @SerializedName("backdrop_path")
    public String backdropPath;

    @SerializedName("genre_ids")
    public List<Integer> genreIds;

    @SerializedName("original_title")
    public String originalTitle;

    @SerializedName("original_language")
    public String originalLanguage;

    @SerializedName("poster_path")
    public String posterPath;

    @SerializedName("popularity")
    public float popularity;

    @SerializedName("title")
    public String title;

    @SerializedName("vote_average")
    public float voteAverage;

    @SerializedName("video")
    public boolean video;

    @SerializedName("id")
    public int id;

    @SerializedName("vote_count")
    public int voteCount;

    @Override
    public String toString() {
        return "Title: "+title + " - id: "+id;
    }
}
