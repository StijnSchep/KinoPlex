package nl.avans.kinoplex.domain;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** The type Movie list. */
public class MovieList extends DomainObject {
    private List<Movie> movieList;
    private static Set<MovieList> listSet = new LinkedHashSet<>();
    private String name;
    private String dbId;
    private String userId;

  /**
   * @author Guus Lieben
   * Instantiates a new Movie list.
   *
   * @param name the name
   * @param userId the user id
   */
  public MovieList(String name, String userId) {
        this.name = name;
        this.userId = userId;
        movieList = new ArrayList<>();
    }

  /**
   * Gets db id.
   *
   * @return the db id
   */
  public String getDbId() {
        String temp = dbId;
        if (temp != null) temp = temp.toLowerCase();
        return temp;
    }

  /**
   * Sets db id.
   *
   * @param dbId the db id
   */
  public void setDbId(String dbId) {
        List<String> TMDbIds = Arrays.asList("now_playing", "popular", "top_rated", "upcoming");
        dbId = dbId.toLowerCase();
        if (TMDbIds.contains(dbId.toLowerCase()) && !(dbId.startsWith("!"))) dbId = '!' + dbId;
        this.dbId = dbId;
    }

  /**
   * Add movie.
   *
   * @param movie the movie
   */
  public void addMovie(Movie movie) {
        this.movieList.add(movie);
    }

  /**
   * Remove movie.
   *
   * @param movieId the movie id
   */
  public void removeMovie(int movieId) {}

  /**
   * Gets domain movie list.
   *
   * @return the domain movie list
   */
  public List<DomainObject> getDomainMovieList() {
        System.out.println(movieList);
        List<DomainObject> domainList = new ArrayList<>(movieList);
        return domainList;
    }

  /**
   * Gets movie list.
   *
   * @return the movie list
   */
  public List<Movie> getMovieList() {
        return movieList;
    }

  /**
   * Gets list set.
   *
   * @return the list set
   */
  public static Set<MovieList> getListSet() {
        return listSet;
    }

  /**
   * Sets movie list.
   *
   * @param movieList the movie list
   */
  public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

  /**
   * Sets list set.
   *
   * @param listSet the list set
   */
  public static void setListSet(Set<MovieList> listSet) {
        MovieList.listSet = listSet;
    }

  /**
   * Sets name.
   *
   * @param name the name
   */
  public void setName(String name) {
        this.name = name;
    }

  /**
   * Gets user id.
   *
   * @return the user id
   */
  public String getUserId() {
        return userId;
    }

  /**
   * Sets user id.
   *
   * @param userId the user id
   */
  public void setUserId(String userId) {
        this.userId = userId;
    }

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
        return name;
    }

    @Override
    public String getId() {
        return dbId;
    }

    @Override
    public Map<String, Object> storeToMap() {
        return new HashMap<String, Object>() {
            {
                ArrayList<Object> movieIds = new ArrayList<>();
                for (Movie movie : movieList) movieIds.add(movie.getId());
                put("name", name);
                put("movies", movieIds);
                put("id", dbId);
                put("user_id", userId);
            }
        };
    }
}
