package tcc.befree.activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import tcc.befree.api.ApiModels;

import java.util.ArrayList;

import tcc.befree.R;
import tcc.befree.models.Usuarios;

public class NewLoginActivity extends AppCompatActivity {

    //Lista Usuários
    private ArrayList<Usuarios> usuarios = null;
    private Usuarios usuario = new Usuarios();


    private TextInputEditText Email = null;
    private TextInputEditText Password = null;

    private View mProgressView;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        /*
        This activity is created and opened when SplashScreen finishes its animations.
        To ensure a smooth transition between activities, the activity creation animation
        is removed.
        RelativeLayout with EditTexts and Button is animated with a default fade in.
         */

        //mProgressView.setVisibility(View.GONE);

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
                Intent createUser = new Intent(NewLoginActivity.this,CreateAccountActivity.class);
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
