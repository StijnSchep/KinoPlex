package nl.avans.kinoplex.domain;

import java.util.Map;

/** The type Domain object. */
public abstract class DomainObject {

  /**
   * @author Guus Lieben
   * Gets the id for the object.
   *
   * @return the id of the object
   */
  public abstract String getId();

  /**
   * @author Guus Lieben
   * Convert the object to a HashMap, for Firestore
   *
   * @return the generated map
   */
  public abstract Map<String, Object> storeToMap();
}
