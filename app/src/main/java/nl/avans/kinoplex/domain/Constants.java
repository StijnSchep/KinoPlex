package nl.avans.kinoplex.domain;

import android.content.SharedPreferences;
import android.util.SparseArray;

import nl.avans.kinoplex.business.FirestoreUtils;
import nl.avans.kinoplex.business.JsonUtils;
import nl.avans.kinoplex.business.LoginManager;
import nl.avans.kinoplex.data.dataaccessobjects.FirestoreListDao;
import nl.avans.kinoplex.data.dataaccessobjects.FirestoreMovieDao;
import nl.avans.kinoplex.data.dataaccessobjects.FirestoreReviewDao;
import nl.avans.kinoplex.data.dataaccessobjects.FirestoreUserDao;
import nl.avans.kinoplex.data.factories.FirestoreDaoFactory;
import nl.avans.kinoplex.data.factories.TMDbDaoFactory;
import nl.avans.kinoplex.presentation.activities.AddReviewActivity;
import nl.avans.kinoplex.presentation.activities.DetailActivity;
import nl.avans.kinoplex.presentation.activities.ListActivity;
import nl.avans.kinoplex.presentation.activities.LoginActivity;
import nl.avans.kinoplex.presentation.activities.MainActivity;
import nl.avans.kinoplex.presentation.activities.ManageListsActivity;
import nl.avans.kinoplex.presentation.activities.RegisterActivity;
import nl.avans.kinoplex.presentation.activities.ReviewActivity;
import nl.avans.kinoplex.presentation.activities.SearchActivity;
import nl.avans.kinoplex.presentation.adapters.MainListAdapter;
import nl.avans.kinoplex.presentation.adapters.MainMovieAdapter;
import nl.avans.kinoplex.presentation.adapters.ReviewAdapter;
import nl.avans.kinoplex.presentation.adapters.SearchAdapter;
import nl.avans.kinoplex.presentation.viewholders.ListManagerViewHolder;
import nl.avans.kinoplex.presentation.viewholders.MainListViewHolder;
import nl.avans.kinoplex.presentation.viewholders.MainMovieViewHolder;
import nl.avans.kinoplex.presentation.viewholders.ReviewViewHolder;

/** The type Constants. */
public class Constants {

    public static final String VERSION_COMMIT = "62b94e7";
    /** The constant holding genre id's and names. */
  // Domain
  public static SparseArray<String> GENRES = new SparseArray<>();

  /** The constant SharedPreferences. */
  public static SharedPreferences pref;

  /** The constant SharedPreferences editor. */
  public static SharedPreferences.Editor editor;

  /** The constant url for TMDb images. */
  // Urls
  public static final String IMAGE_URL = "https://image.tmdb.org/t/p/w500";

  /** The constant TMDb api key. */
  public static final String API_KEY = "fe324f20d33c7b7991dbbd8bdb4b7413";

  /** The constant url for TMDb movies. */
  public static final String MOVIE_API_URL = "https://api.themoviedb.org/3/movie/";

  /** The constant url for TMDb genres. */
  public static final String GENRE_API_URL = "https://api.themoviedb.org/3/genre/movie/list";

  /** The constant url for TMDb reviews. */
  public static final String REVIEW_API_URL = "https://api.themoviedb.org/3/movie/{movie_id}/reviews";

  /** The constant url for TMDb Trailer. */
  public static final String Trailer_API_URL = "https://api.themoviedb.org/3/movie/{movie_id}/videos";

  /** The constant YouTube api key. */
  public static final String YOUTUBE_API_KEY = "AIzaSyCs0EKmQ9p-AI-V8u3I6BS_VOMffDvcjdk";

  /** The constant identifier for Firestore lists. */
  public static final String COL_LISTS = "lists";

  /** The constant identifier for Firestore movies. */
  public static final String COL_MOVIES = "movies";

  /** The constant identifier for Firestore genres. */
  public static final String COL_GENRES = "genres";

  /** The constant identifier for Firestore reviews. */
  public static final String COL_REVIEWS = "reviews";

  /** The constant identifier for Firestore users. */
  public static final String COL_USERS = "users";

  /** The constant log tag for FirestoreUtils. */
  public static final String FIRESTOREUTILS_TAG = FirestoreUtils.class.getCanonicalName();

  /** The constant log tag for JsonUtils. */
  public static final String JSONUTILS_TAG = JsonUtils.class.getCanonicalName();

  /** The constant log tag for LoginManager. */
  public static final String LOGINMANGER_TAG = LoginManager.class.getCanonicalName();

  /** The constant log tag for FirestoreListDao. */
  public static final String FIRESTORELISTDAO_TAG = FirestoreListDao.class.getCanonicalName();

  /** The constant log tag for FirestoreMovieDao. */
  public static final String FIRESTOREMOVIEDAO_TAG = FirestoreMovieDao.class.getCanonicalName();

  /** The constant log tag for FirestoreReviewDao. */
  public static final String FIRESTOREREVIEWDAO_TAG = FirestoreReviewDao.class.getCanonicalName();

  /** The constant log tag for FirestoreUserDao. */
  public static final String FIRESTOREUSERDAO_TAG = FirestoreUserDao.class.getCanonicalName();

  /** The constant log tag for the Firestore factory. */
  public static final String FIRESTOREDAOFACTORY_TAG = FirestoreDaoFactory.class.getCanonicalName();

  /** The constant log tag for the TMDb factory. */
  public static final String TMDBDAOFACTORY_TAG = TMDbDaoFactory.class.getCanonicalName();

  /** The constant log tag for domain AppReviews. */
  public static final String APPREVIEW_TAG = FireReview.class.getCanonicalName();

  /** The constant log tag for domain Movies. */
  public static final String MOVIE_TAG = Movie.class.getCanonicalName();

  /** The constant log tag for domain MovieLists. */
  public static final String MOVIELIST_TAG = MovieList.class.getCanonicalName();

  /** The constant log tag for domain TMDbReviews. */
  public static final String TMDBREVIEW_TAG = TMDbReview.class.getCanonicalName();

  /** The constant log tag for activity AddReview. */
  public static final String ADDREVIEWACT_TAG = AddReviewActivity.class.getCanonicalName();

  /** The constant log tag for activity Details. */
  public static final String DETAILACT_TAG = DetailActivity.class.getCanonicalName();

  /** The constant log tag for activity Reviews. */
  public static final String REVIEWACT_TAG = ReviewActivity.class.getCanonicalName();

  /** The constant log tag for activity Lists. */
  public static final String LISTACT_TAG = ListActivity.class.getCanonicalName();

  /** The constant log tag for activity Login. */
  public static final String LOGINACT_TAG = LoginActivity.class.getCanonicalName();

  /** The constant log tag for activity Register. */
  public static final String REGISTERACT_TAG = RegisterActivity.class.getCanonicalName();

  /** The constant log tag for activity Main. */
  public static final String MAINACT_TAG = MainActivity.class.getCanonicalName();

  /** The constant log tag for activity Search. */
  public static final String SEARCHACT_TAG = SearchActivity.class.getCanonicalName();

  /** The constant log tag for activity ManageLists. */
  public static final String MANAGELISTSACT_TAG = ManageListsActivity.class.getCanonicalName();

  /** The constant log tag for adapter Movies. */
  public static final String MOVIEADAPT_TAG = MainMovieAdapter.class.getCanonicalName();

  /** The constant log tag for adapter Parents. */
  public static final String PARENTADAPT_TAG = MainListAdapter.class.getCanonicalName();

  /** The constant log tag for adapter Reviews. */
  public static final String REVIEWADAPT_TAG = ReviewAdapter.class.getCanonicalName();

  /** The constant log tag for adapter Search. */
  public static final String SEARCHADAPT_TAG = SearchAdapter.class.getCanonicalName();

  /** The constant log tag for ViewHolder Movies. */
  public static final String MAINMOVIEVH_TAG = MainMovieViewHolder.class.getCanonicalName();

  /** The constant log tag for ViewHolder Lists. */
  public static final String MAINLISTVH_TAG = MainListViewHolder.class.getCanonicalName();

  /** The constant log tag for ViewHolder Reviews. */
  public static final String REVIEWVH_TAG = ReviewViewHolder.class.getCanonicalName();

  /** The constant log tag for ViewHolder ListsManager. */
  public static final String LISTMANAGERVH_TAG = ListManagerViewHolder.class.getCanonicalName();

  /** The constant intent extra Movie Id. */
  public static final String INTENT_EXTRA_MOVIEID = "MovieID";

  /** The constant intent extra MovieId movie Json. */
  public static final String INTENT_EXTRA_MOVIE_JSON = "movieJson";

  /** The constant intent extra login valid. */
  public static final String INTENT_EXTRA_LOGIN_VALIDATED = "lastLoginWasValid";

  /** The constant intent extra movie list json. */
  public static final String INTENT_EXTRA_MOVIE_LIST_JSON = "movieListJson";

  /** The constant shared preferences login . */
  public static final String PREF_LOGIN = "loginPreferences";

  /** The constant shared preferences username. */
  public static final String PREF_USERNAME = "username";

  /** The constant shared preferences pass hash. */
  public static final String PREF_HASHEDPASS = "usernameHash";

  /** The constant shared preferences autologin. */
  public static final String PREF_AUTOLOGIN = "autoLogin";

  /** The constant shared preferences movie list objects. */
  public static final String INTENT_EXTRA_MOVIELIST = "movieListObject";

  /** The constant movie id. */
  public static final String MOVIE_ID = "movieId";

  /** The constant movie title. */
  public static final String MOVIE_TITLE = "movieTitle";
}
