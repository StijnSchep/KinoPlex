package nl.avans.kinoplex.domain;

import android.net.Uri;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** The type Movie. */
public class Movie extends DomainObject {
  /** The Reviews. */
  List<TMDbReview> reviews;

  /** The Fire reviews. */
  List<FireReview> fireReviews;

  /** The Title. */
  String title;

  /** The Id. */
  int id;

  /** The Runtime. */
  int runtime;

  /** The Poster path. */
  String posterPath;

  /** The Genres. */
  List<String> genres;

  /** The Tag. */
  String tag;

  /** The Language. */
  String language;

  /** The Overview. */
  String overview;

  /** The Release date. */
  Date releaseDate;

  /** The Adult. */
  boolean adult;

  /** The Rating. */
  Double rating;

  /**
   * @author Guus Lieben
   * Instantiates a new Movie.
   *
   * @param title the title
   * @param id the id
   * @param runtime the runtime
   * @param posterPath the poster path
   * @param adult the adult
   * @param genres the genres
   * @param tag the tag
   * @param language the language
   * @param overview the overview
   * @param releaseDate the release date
   */
  public Movie(
      String title,
      int id,
      int runtime,
      String posterPath,
      boolean adult,
      List<String> genres,
      String tag,
      String language,
      String overview,
      Date releaseDate) {
        this.title = title;
        this.id = id;
        this.runtime = runtime;
        this.posterPath = posterPath;
        this.adult = adult;
        this.genres = genres;
        this.tag = tag;
        this.language = language;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

  /**
   * Is adult boolean.
   *
   * @return the boolean
   */
  public boolean isAdult() {
        return adult;
    }

  /**
   * Gets rating.
   *
   * @return the rating
   */
  public Double getRating() {
        return rating;
    }

  /**
   * Sets rating.
   *
   * @param rating the rating
   */
  public void setRating(Double rating) {
        this.rating = rating;
    }

  /**
   * Gets title.
   *
   * @return the title
   */
  public String getTitle() {
        return title;
    }

    public String getId() {
        return String.valueOf(id);
    }

  /**
   * Gets runtime.
   *
   * @return the runtime
   */
  public int getRuntime() {
        return runtime;
    }

  /**
   * Gets fire reviews.
   *
   * @return the fire reviews
   */
  public List<FireReview> getFireReviews() {
        return fireReviews;
    }

  /**
   * Sets fire reviews.
   *
   * @param fireReviews the fire reviews
   */
  public void setFireReviews(List<FireReview> fireReviews) {
        this.fireReviews = fireReviews;
    }

  /**
   * Add app review.
   *
   * @param fireReview the fire review
   */
  public void addAppReview(FireReview fireReview) {
        this.fireReviews.add(fireReview);
    }

  /**
   * Gets formatted runtime.
   *
   * @return the formatted runtime
   */
  public String getFormattedRuntime() {
        int hours = runtime / 60;
        int minutes = runtime % 60;

        return hours + "h " + minutes + "m";
    }

  /**
   * Gets poster path.
   *
   * @return the poster path
   */
  public Uri getPosterPath() {
        return Uri.parse(posterPath);
    }

  /**
   * Gets genres.
   *
   * @return the genres
   */
  public List<String> getGenres() {
        return genres;
    }

  /**
   * Gets tag.
   *
   * @return the tag
   */
  public String getTag() {
        return tag;
    }

  /**
   * Gets language.
   *
   * @return the language
   */
  public String getLanguage() {
        return language;
    }

  /**
   * Gets overview.
   *
   * @return the overview
   */
  public String getOverview() {
        return overview;
    }

  /**
   * Gets release date.
   *
   * @return the release date
   */
  public Date getReleaseDate() {
        return releaseDate;
    }

  /**
   * Gets release year.
   *
   * @return the release year
   */
  public String getReleaseyear() {
        SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
        return formatYear.format(releaseDate);
    }

  /**
   * Add review.
   *
   * @param review the review
   */
  public void addReview(TMDbReview review) {
        this.reviews.add(review);
    }

  /**
   * Sets reviews.
   *
   * @param reviews the reviews
   */
  public void setReviews(List<TMDbReview> reviews) {
        this.reviews = reviews;
    }

  /**
   * Sets title.
   *
   * @param title the title
   */
  public void setTitle(String title) {
        this.title = title;
    }

  /**
   * Sets id.
   *
   * @param id the id
   */
  public void setId(int id) {
        this.id = id;
    }

  /**
   * Sets runtime.
   *
   * @param runtime the runtime
   */
  public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

  /**
   * Sets poster path.
   *
   * @param posterPath the poster path
   */
  public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

  /**
   * Sets genres.
   *
   * @param genres the genres
   */
  public void setGenres(List<String> genres) {
        this.genres = genres;
    }

  /**
   * Sets tag.
   *
   * @param tag the tag
   */
  public void setTag(String tag) {
        this.tag = tag;
    }

  /**
   * Sets language.
   *
   * @param language the language
   */
  public void setLanguage(String language) {
        this.language = language;
    }

  /**
   * Sets overview.
   *
   * @param overview the overview
   */
  public void setOverview(String overview) {
        this.overview = overview;
    }

  /**
   * Sets release date.
   *
   * @param releaseDate the release date
   */
  public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

  /**
   * Sets adult.
   *
   * @param adult the adult
   */
  public void setAdult(boolean adult) {
        this.adult = adult;
    }

  /**
   * Gets reviews.
   *
   * @return the reviews
   */
  public List<TMDbReview> getReviews() {
        return reviews;
    }

    @Override
    public Map<String, Object> storeToMap() {
        return new HashMap<String, Object>() {
            {
                put("id", id);
                put("title", title);
                put("adult", adult);
                put("language", language);
                put("release_date", releaseDate);
                put("runtime", runtime);
                put("overview", overview);
                put("poster", posterPath);
                put("tagline", tag);
                put("genres", genres);
                put("rating_avg", rating);
            }
        };
    }
}
