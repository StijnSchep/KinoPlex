package nl.avans.kinoplex.domain;

import java.util.HashMap;
import java.util.Map;

/** The type Tm db review. */
public class TMDbReview extends DomainObject implements Review {
  /** The Id. */
  String id;

  /** The Author. */
  String author;

  /** The Content. */
  String content;

  /**
   * @author Guus Lieben
   * Instantiates a new Tm db review.
   *
   * @param id the id
   * @param author the author
   * @param content the content
   */
  public TMDbReview(String id, String author, String content) {
    this.id = id;
    this.author = author;
    this.content = content;
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
   * Gets author.
   *
   * @return the author
   */
  public String getAuthor() {
    return author;
  }

  /**
   * Sets author.
   *
   * @param author the author
   */
  public void setAuthor(String author) {
    this.author = author;
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

  @Override
  public Map<String, Object> storeToMap() {
    return new HashMap<String, Object>() {
      {
        put("id", id);
        put("author", author);
        put("content", content);
      }
    };
  }
}
