package tcc.befree.activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

import tcc.befree.R;
import tcc.befree.api.ApiModels;
import tcc.befree.models.Usuarios;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity /*implements LoaderCallbacks<Cursor> */ {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
        "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    //Lista Usuários
    private ArrayList<Usuarios> usuarios = null;
    private Usuarios usuario = new Usuarios();


    private TextInputEditText Email = null;
    private TextInputEditText Password = null;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private ProfileTracker profileTracker;
    private AccessTokenTracker accessTokenTracker;
    private AccessToken accessToken;
    private View mProgressView;

    private GoogleApiClient googleApiClient;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {


            }
        };

        /*googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOO)*/



        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {

                Usuarios usuarioFacebook = new Usuarios();

                usuarioFacebook.nomeUsuario = currentProfile.getFirstName() + currentProfile.getLastName();
                usuarioFacebook.cpf = " ";
                usuarioFacebook.email = currentProfile.getId();
                usuarioFacebook.senha = " ";
                Uri uriImageFacebook = currentProfile.getProfilePictureUri(100, 100);
                usuarioFacebook.imagemPerfil = uriImageFacebook.toString();
            }
        };

        /*accessTokenTracker.startTracking();
        profileTracker.startTracking();*/

        loginButton = (LoginButton) findViewById(R.id.login_facebook_button);
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                //txtStatus.setText("Login Sucess \n" + loginResult.getAccessToken().getUserId() + "\n" + loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {
                //txtStatus.setText("Login Cancelled");
            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        // If the access token is available already assign it.
        //accessToken = AccessToken.getCurrentAccessToken();

        overridePendingTransition(0,0);
        View relativeLayout=findViewById(R.id.newLogin_container);
        Animation animation=AnimationUtils.loadAnimation(this,android.R.anim.fade_in);
        relativeLayout.startAnimation(animation);

        Email = (TextInputEditText) findViewById(R.id.username_edit_text);
        Password = (TextInputEditText) findViewById(R.id.password_edit_text);
        mProgressView = findViewById(R.id.circle_login_progress);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.requestFocus();
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressView.setVisibility(View.VISIBLE);
                attemptLogin();
            }
        });

        TextView txtCriaCadastro = (TextView) findViewById(R.id.btnCriaCadastro);
        txtCriaCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createUser = new Intent(LoginActivity.this,CreateAccountActivity.class);
                startActivity(createUser);
            }
        });
    }

    private void attemptLogin() {

        // Reset errors.
        Email.setError(null);
        Password.setError(null);

        // Store values at the time of the login attempt.
        String email = Email.getText().toString();
        String password = Password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            Password.setError(getString(R.string.error_invalid_password));
            focusView = Password;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            Email.setError(getString(R.string.error_field_required));
            focusView = Email;
            cancel = true;
        } else if (!isEmailValid(email)) {
            Email.setError(getString(R.string.error_invalid_email));
            focusView = Email;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);
            ApiModels api = new ApiModels();

            usuario = api.getUsuariosByEmail(email);
            if (!(validaUsuario(usuario,email,password))) {
                focusView = Email;
                focusView.requestFocus();
            }
            else {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("nomeUsuario",usuario.nomeUsuario);
                intent.putExtra("emailUsuario",usuario.email);
                intent.putExtra("imagemPerfil",usuario.imagemPerfil);

                Bundle bundle = new Bundle();
                bundle.putInt("idUsuario",usuario.idUsuario);
                intent.putExtra("idUsuario", bundle);

                startActivity(intent);
                finish();

            }
        }

    }

    public boolean validaUsuario(Usuarios usuarioValida, String email, String senha) {

        if (usuarioValida != null) {
            if (usuarioValida.email == null) {
                mProgressView.setVisibility(View.GONE);
                Email.setError("Email inválido");
                return false;
            }
            if (usuarioValida.senha == null) {
                mProgressView.setVisibility(View.GONE);
                Password.setError("Senha inválida");
                return false;
            }
            if (!(usuarioValida.email.toString().equals(email.toString()))) {
                mProgressView.setVisibility(View.GONE);
                Email.setError("Email inválido");
                return false;
            }
            if (!(usuarioValida.senha.toString().equals(senha.toString()))) {
                mProgressView.setVisibility(View.GONE);
                Password.setError("Senha inválida");
                return false;
            }
        } else {
            mProgressView.setVisibility(View.GONE);
            Email.setError("Email inválido");
            return false;
        }

        return true;
    }


    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 4;
    }
}
