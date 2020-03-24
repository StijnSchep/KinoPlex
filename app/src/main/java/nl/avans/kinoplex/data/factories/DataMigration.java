package nl.avans.kinoplex.data.factories;

/** The type Data migration. */
public class DataMigration {

    private static DaoFactory factory = new FirestoreDaoFactory();
    private static DaoFactory TMDbFactory = new TMDbDaoFactory();

  /**
   * Gets app remote factory.
   *
   * @return the factory
   */
  public static DaoFactory getFactory() {
        return factory;
    }

  /**
   * Gets TMDb factory.
   *
   * @return the tm db factory
   */
  public static DaoFactory getTMDbFactory() {
        return TMDbFactory;
    }
}
