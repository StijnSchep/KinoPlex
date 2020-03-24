package nl.avans.kinoplex.presentation.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import nl.avans.kinoplex.R;
import nl.avans.kinoplex.business.PosterPicker;
import nl.avans.kinoplex.data.factories.DataMigration;
import nl.avans.kinoplex.domain.Constants;

/**
 * @author Stijn Schep
 * Activity that allows the user to register with their name and a password
 */
public class RegisterActivity extends Activity implements
        View.OnClickListener, TextWatcher {
    private EditText fullNameEditText;
    private EditText passwordEditText;
    private EditText repeatPasswordEditText;

    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ImageView background = findViewById(R.id.iv_login_background);
        Glide.with(this).load(PosterPicker.getRandomPosterID()).into(background);

        registerButton = findViewById(R.id.btn_register_register);
        registerButton.setOnClickListener(this);

        fullNameEditText = findViewById(R.id.et_register_fullname);
        passwordEditText = findViewById(R.id.et_register_password);
        repeatPasswordEditText = findViewById(R.id.et_register_password_confirm);
        repeatPasswordEditText.addTextChangedListener(this);

    }

    @Override
    public void onClick(View v) {
        Log.d(Constants.REGISTERACT_TAG, "User wants to register..");

        String fullname = fullNameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String repeatPassword = repeatPasswordEditText.getText().toString();

        if(!password.equals(repeatPassword)) {
            return;
        }

        Pair<String, String> credentials = new Pair<>(fullname, password);

        if(DataMigration.getFactory().getUserDao().create(credentials)) {
            finish();
        } else {
            passwordEditText.setText("");
            repeatPasswordEditText.setText("");
            passwordEditText.setBackground(getResources().getDrawable(R.drawable.login_edittext_errorcolor));
            repeatPasswordEditText.setBackground(getResources().getDrawable(R.drawable.login_edittext_errorcolor));
        }
    }

    //Listener methods to check if the confirmed password and the normal password are the same
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String password = passwordEditText.getText().toString();
        String repeatPassword = repeatPasswordEditText.getText().toString();

        if(!password.equals(repeatPassword)) {
            repeatPasswordEditText.setBackground(getResources().getDrawable(R.drawable.login_edittext_errorcolor));
        } else {
            repeatPasswordEditText.setBackground(getResources().getDrawable(R.drawable.login_edittext_color));
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
