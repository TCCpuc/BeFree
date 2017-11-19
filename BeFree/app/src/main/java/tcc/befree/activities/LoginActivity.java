package tcc.befree.activities;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import tcc.befree.R;
import tcc.befree.api.ApiModels;
import tcc.befree.api.PostApiModels;
import tcc.befree.models.Usuarios;
import tcc.befree.telas.Dialog.ForgotPasswordDialog;
import tcc.befree.telas.Dialog.LoadingDialog;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    //private ArrayList<Usuarios> usuarios = null;
    private Usuarios usuario = new Usuarios();

    private TextInputEditText Email = null;
    private TextInputEditText Password = null;
    private LoginButton loginButton;
    private Button forgotPassword;
    private CallbackManager callbackManager;
    private ProfileTracker profileTracker;
    private AccessTokenTracker accessTokenTracker;
    private AccessToken accessToken;
    //private View mProgressView;
    private View relativeLayout;
    private Button mEmailSignInButton;
    private SignInButton signInButton;
    private TextView txtCriaCadastro;
    //private FacebookCallback<LoginResult> callback;
    private Animation animation;
    private View focusView;
    private LoadingDialog loginDialog;
    private GoogleApiClient googleApiClient;

    public static final int SING_IN_CODE = 777;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();


        FacebookSdk.sdkInitialize(getApplicationContext());

        forgotPassword = (Button) findViewById(R.id.login_forgot_password);
        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        loginButton = (LoginButton) findViewById(R.id.login_facebook_button);
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        Email = (TextInputEditText) findViewById(R.id.username_edit_text);
        Password = (TextInputEditText) findViewById(R.id.password_edit_text);
        txtCriaCadastro = (TextView) findViewById(R.id.btnCriaCadastro);
        relativeLayout = findViewById(R.id.newLogin_container);


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,SING_IN_CODE);
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgotPasswordDialog dialog = new ForgotPasswordDialog(LoginActivity.this);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mEmailSignInButton.requestFocus();
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        callbackManager = CallbackManager.Factory.create();

        loginButton.setReadPermissions("user_friends");
        loginButton.setReadPermissions("email");
        loginButton.registerCallback(callbackManager, callback);


        Password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    mEmailSignInButton.performClick();
                    return true;
                }
                return false;
            }
        });

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            }
        };
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {

            }
        };

        accessTokenTracker.startTracking();
        profileTracker.startTracking();

        overridePendingTransition(0,0);
        animation = AnimationUtils.loadAnimation(this,android.R.anim.fade_in);
        relativeLayout.startAnimation(animation);

        txtCriaCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createUser = new Intent(LoginActivity.this,CreateAccountActivity.class);
                startActivity(createUser);
            }
        });
    }

    @Override
    public void onBackPressed() {}
    
    private void attemptLogin() {

        // Reset errors.
        Email.setError(null);
        Password.setError(null);

        // Store values at the time of the login attempt.
         String email = Email.getText().toString();
        String password = Password.getText().toString();

        boolean cancel = false;
        focusView = null;

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
            startLoadingDialog();
            attemptLoginThreadUpdate();
        }
    }

    private void attemptLoginThreadUpdate(){
        new Thread(){
            @Override
            public void run() {
                ApiModels api = new ApiModels();
                usuario = api.getUsuariosByEmail(Email.getText().toString());
                attemptLoginThreadUI();
            }
        }.start();
    }

    private void attemptLoginThreadUI(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!(validaUsuario(usuario,Email.getText().toString(),Password.getText().toString()))) {
                    stopLoadingDialog();
                }
                else
                    nextActivity(usuario);
            }
        });
    }

    public boolean validaUsuario(Usuarios usuarioValida, String email, String senha) {

        if (usuarioValida != null) {
            if (usuarioValida.email == null) {
                Email.setError("Email inválido");
                focusView = Email;
                focusView.requestFocus();
                return false;
            }
            if (usuarioValida.senha == null) {
                Password.setError("Senha inválida");
                focusView = Password;
                focusView.requestFocus();
                return false;
            }
            if (!(usuarioValida.email.toString().equals(email.toString()))) {
                Email.setError("Email inválido");
                focusView = Email;
                focusView.requestFocus();
                return false;
            }
            if (!(usuarioValida.senha.toString().equals(senha.toString()))) {
                Password.setError("Senha inválida");
                focusView = Password;
                focusView.requestFocus();
                return false;
            }
        } else {
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

    @Override
    protected void onResume() {
        super.onResume();
        //Facebook login
        Profile profile = Profile.getCurrentProfile();
        //createUsuarioFacebook(profile);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void onStop() {
        super.onStop();
        //Facebook login
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    @Override
    protected void onStart(){
        super.onStart();

        //Evento chamado na inicialização da Activity
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        super.onActivityResult(requestCode, responseCode, intent);

        if (requestCode == SING_IN_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(intent);
            handleSingInResult(result);

        } else { //Facebook login
            callbackManager.onActivityResult(requestCode, responseCode, intent);

        }
    }


    private void handleSingInResult(GoogleSignInResult result){

        if(result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
            createUsuarioGoogle(account);
        }else
            Toast.makeText(this, "Não foi possivel realizar o login.", Toast.LENGTH_SHORT).show();
    }

    private  void createUsuarioGoogle(GoogleSignInAccount account){
        Usuarios usuarioGoogle;
        ApiModels apiModels = new ApiModels();

        usuarioGoogle = apiModels.getUsuariosByEmail(account.getEmail());
        if(usuarioGoogle == null) {
            usuarioGoogle = new Usuarios();
            usuarioGoogle.email = account.getEmail();
            usuarioGoogle.nomeUsuario = account.getDisplayName();
            usuarioGoogle.imagemPerfil = (account.getPhotoUrl() == null) ? "" : account.getPhotoUrl().toString();
            usuarioGoogle.cpf = "";
            usuarioGoogle.senha = "  ";
            PostApiModels postApiModels = new PostApiModels();
            if (postApiModels.postUsuarios(usuarioGoogle)) {
                usuarioGoogle = apiModels.getUsuariosByEmail(usuarioGoogle.email);
                nextActivity(usuarioGoogle);
            }
            else
                Toast.makeText(getApplicationContext(), "Não foi possível realizar o login!", Toast.LENGTH_LONG).show();
        }
        else
            nextActivity(usuarioGoogle);

    }

    private void nextActivity(Usuarios usuario){

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("arrayUsuario", usuario.toString());
        startActivity(intent);
    }

    private void startLoadingDialog(){
        loginDialog = new LoadingDialog(LoginActivity.this);
        loginDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loginDialog.show();
    }

    private void stopLoadingDialog(){
        loginDialog.dismiss();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();
            startLoadingDialog();
            // Facebook Email address
            GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                                JSONObject object,
                                GraphResponse response) {
                            Log.v("LoginActivity Response ", response.toString());
                            try {
                                Profile profile = Profile.getCurrentProfile();
                                if (profile != null){
                                    ApiModels apiModels = new ApiModels();
                                    Usuarios usuarioFacebook = apiModels.getUsuariosByEmail(object.getString("email"));
                                    if (usuarioFacebook == null) {
                                        usuarioFacebook = new Usuarios();
                                        usuarioFacebook.nomeUsuario = object.getString("name");
                                        usuarioFacebook.cpf = "";
                                        usuarioFacebook.email = object.getString("email");
                                        usuarioFacebook.senha = " ";
                                        Uri uriImageFacebook = profile.getProfilePictureUri(100, 100);
                                        usuarioFacebook.imagemPerfil = uriImageFacebook.toString();

                                        PostApiModels postApiModels = new PostApiModels();
                                        if (postApiModels.authenticateUserFacebook(usuarioFacebook)) {
                                            usuarioFacebook = apiModels.getUsuariosByEmail(object.getString("email"));
                                            nextActivity(usuarioFacebook);
                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(), "Não foi possível realizar o login!", Toast.LENGTH_LONG).show();
                                        }
                                        LoginManager.getInstance().logOut();
                                    }
                                    else{
                                        stopLoadingDialog();
                                        nextActivity(usuarioFacebook);
                                        LoginManager.getInstance().logOut();
                                    }
                                }else {
                                    Toast.makeText(getApplicationContext(), "Não foi possivel realizar login", Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
            stopLoadingDialog();
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender, birthday");
            request.setParameters(parameters);
            request.executeAsync();


        }

        @Override
        public void onCancel() {
            LoginManager.getInstance().logOut();

        }

        @Override
        public void onError(FacebookException e) {

        }
    };
}
