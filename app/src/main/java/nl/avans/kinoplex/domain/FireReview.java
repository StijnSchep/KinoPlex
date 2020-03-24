package nl.avans.kinoplex.domain;

import java.util.HashMap;
import java.util.Map;

/** The type Fire review. */
public class FireReview extends DomainObject implements Review {

  /** The Id. */
  String id;

  /** The User id. */
  String userId;

  /** The review content. */
  String content;

  /** The Rating. */
  int rating;

  /** The Movie id. */
  String movieId;

  /**
   * @author Guus Lieben Instantiates a new Fire review.
   * @param id the id
   * @param userId the user id
   * @param content the content
   * @param rating the rating
   * @param movieId the movie id
   */
  public FireReview(String id, String userId, String content, int rating, String movieId) {
    this.id = id;
    this.userId = userId;
    this.content = content;
    this.rating = rating;
    this.movieId = movieId;
  }

  @Override
  public String getId() {
    return id;
  }

  /**
   * Sets id.
   *
   * @param id the id
   */
  public void setId(String id) {
    this.id = id;
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
   * Gets content.
   *
   * @return the content
   */
  public String getContent() {
    return content;
  }

  /**
   * Sets content.
   *
   * @param content the content
   */
  public void setContent(String content) {
    this.content = content;
  }

  /**
   * Gets rating.
   *
   * @return the rating
   */
  public int getRating() {
    return rating;
  }

  /**
   * Sets rating.
   *
   * @param rating the rating
   */
  public void setRating(int rating) {
    this.rating = rating;
  }

  @Override
  public Map<String, Object> storeToMap() {
    return new HashMap<String, Object>() {
      {
        put("id", id);
        put("user_id", userId);
        put("content", content);
        put("rating", rating);
        put("movie_id", movieId);
      }
    };
  }
}
