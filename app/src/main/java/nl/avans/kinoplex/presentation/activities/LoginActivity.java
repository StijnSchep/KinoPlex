package nl.avans.kinoplex.presentation.activities;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.business.DialogBuilder;
import nl.avans.kinoplex.business.LoginManager;
import nl.avans.kinoplex.business.PosterPicker;
import nl.avans.kinoplex.data.dataaccessobjects.FirestoreUserDao;
import nl.avans.kinoplex.data.factories.DataMigration;
import nl.avans.kinoplex.domain.Constants;

/**
 * @author Stijn Schep
 * Activity in which the user can enter login credentials.
 */
public class LoginActivity extends Activity implements View.OnClickListener {
  private EditText usernameEditText;
  private EditText passwordEditText;

  private Button registerButton;
  private Button loginButton;

  private LinearLayout loginScreen;
  private ProgressBar progressBar;

  private CheckBox rememberMeCheckBox;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ManageListsActivity.datahasChanged = true;

    Constants.pref =
        getApplicationContext().getSharedPreferences(Constants.PREF_LOGIN, MODE_PRIVATE);
    Constants.editor = Constants.pref.edit();

    Pair<String, String> credentials = LoginManager.getLoginCredentials(this);
    if (credentials != null) {
      if (credentials.first != null && credentials.second != null) {
        Intent mainIntent = new Intent(this, MainActivity.class);
        ((FirestoreUserDao) DataMigration.getFactory().getUserDao())
            .startIntentOnSavedCredentials(credentials, this, mainIntent, this);
        return;
      }
    }

    setContentView(R.layout.activity_login);
    ImageView background = findViewById(R.id.iv_login_background);
    Glide.with(this).load(PosterPicker.getRandomPosterID()).into(background);

    usernameEditText = findViewById(R.id.et_login_username);
    passwordEditText = findViewById(R.id.et_login_password);

    registerButton = findViewById(R.id.btn_login_register);
    registerButton.setOnClickListener(this);

    loginButton = findViewById(R.id.btn_login_login);
    loginButton.setOnClickListener(this);

    loginScreen = findViewById(R.id.ll_login_screen);
    progressBar = findViewById(R.id.pb_login_loading);

    rememberMeCheckBox = findViewById(R.id.cb_remember_me);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btn_login_login:
        Log.d(Constants.LOGINACT_TAG, "User wants to log in...");
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        Pair<String, String> credentials = new Pair<>(username, password);

        if (rememberMeCheckBox.isChecked()) {
          Log.d(Constants.LOGINACT_TAG, "User wants to save password");

          LoginManager.saveLoginCredentials(this, credentials);
        }

        Intent mainIntent = new Intent(this, MainActivity.class);
        ((FirestoreUserDao) DataMigration.getFactory().getUserDao())
            .startIntentIfLoginValid(credentials, this, mainIntent, this);

        break;

      case R.id.btn_login_register:
        Log.d(Constants.LOGINACT_TAG, "User wants to register...");
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        startActivity(registerIntent);
        break;
    }
  }

  /**
   * @author Stijn Schep
   * Change layout to indicate that an error has occured.
   */
  public void showLoginError() {
    Log.d(Constants.LOGINACT_TAG, "Login failed, showing error...");

    showLoginScreen();

    passwordEditText.setText("");
    passwordEditText.setHint("");
    passwordEditText.setBackground(
        getResources().getDrawable(R.drawable.login_edittext_errorcolor));

    Toast.makeText(this, getResources().getString(R.string.invalidLogin), Toast.LENGTH_LONG).show();
  }

  /**
   * @author Stijn Schep
   * Make the ProgressBar visibible and the login screen invisible
   */
  public void showLoadingScreen() {
    Log.d(Constants.LOGINACT_TAG, "Now showing progress bar...");

    loginScreen.setVisibility(View.INVISIBLE);
    progressBar.setVisibility(View.VISIBLE);
  }

  /**
   * @author Stijn Schep
   * Make the login screen visible and the ProgressBar invisible
   */
  private void showLoginScreen() {
    Log.d(Constants.LOGINACT_TAG, "Now showing login screen...");

    progressBar.setVisibility(View.INVISIBLE);
    loginScreen.setVisibility(View.VISIBLE);
  }
}
