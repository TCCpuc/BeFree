package tcc.befree.activities;

import android.content.res.AssetManager;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import tcc.befree.api.PostApiModels;
import tcc.befree.R;
import tcc.befree.models.CircleImageView;
import tcc.befree.models.Usuarios;

public class CreateAccountActivity extends AppCompatActivity {

    private TextView presentation;
    private AutoCompleteTextView edtNome;
    private AutoCompleteTextView edtCpf;
    private AutoCompleteTextView edtEmail;
    private EditText edtSenha;
    private EditText edtConfirmaSenha;
    private TextView message;
    private TextView error;
    private TextView insertPhoto;
    private CircleImageView photo;
    private ProgressBar loading;
    private Button continuar;
    private int passo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        presentation = (TextView) findViewById(R.id.create_account_presentation);
        edtNome = (AutoCompleteTextView) findViewById(R.id.create_account_username);
        edtSenha = (EditText) findViewById(R.id.create_account_password);
        edtConfirmaSenha = (EditText) findViewById(R.id.create_account_confirm_password);
        edtCpf = (AutoCompleteTextView) findViewById(R.id.create_account_CPF);
        edtEmail = (AutoCompleteTextView) findViewById(R.id.create_account_email);
        message = (TextView) findViewById(R.id.create_account_message);
        error = (TextView) findViewById(R.id.create_account_username_error);
        continuar = (Button) findViewById(R.id.create_account_continue);
        loading = (ProgressBar) findViewById(R.id.create_account_loading);
        photo = (CircleImageView) findViewById(R.id.create_account_user_photo);
        insertPhoto = (TextView) findViewById(R.id.create_account_insert_foto);
        passo = 0;

        continuar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(passo == 0){
                    presentation.setVisibility(View.GONE);
                    passo = 1;
                    edtNome.setVisibility(View.VISIBLE);
                    message.setVisibility(View.VISIBLE);
                    message.setText("\n Por favor digite um nome de usuario para validarmos em nosso banco de dados!! \n");
                }else if(passo == 1){
                    if (validaCampo(edtNome.getText().toString())){
                        passo = 2;
                        edtNome.setVisibility(View.GONE);
                        edtSenha.setVisibility(View.VISIBLE);
                        edtConfirmaSenha.setVisibility(View.VISIBLE);
                        message.setText("Otimo!!\n Agora vamos setar uma senha.");
                    }
                }else if (passo == 2){
                    if (validaCampo(edtSenha.getText().toString())){
                        if(validaCampo(edtConfirmaSenha.getText().toString())){
                            if(validaSenha(edtSenha.getText().toString(),edtConfirmaSenha.getText().toString())){
                                edtSenha.setVisibility(View.GONE);
                                edtConfirmaSenha.setVisibility(View.GONE);
                                passo = 3;
                                message.setText("\n Estamos quase lá!! \n Agora preciso que voce nos informe seu \n E-mail e CPF. \n");
                                edtCpf.setVisibility(View.VISIBLE);
                                edtEmail.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }else if (passo == 3){
                    if (validaCampo(edtCpf.getText().toString())){
                        if(validaCampo(edtEmail.getText().toString())){
                            if(validaEmail(edtEmail.getText().toString())){
                                edtCpf.setVisibility(View.GONE);
                                edtEmail.setVisibility(View.GONE);
                                message.setVisibility(View.GONE);
                                passo = 4;
                                photo.setVisibility(View.VISIBLE);
                                insertPhoto.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }else if (passo == 4){

                    PostApiModels apiPost = new PostApiModels();
                    Usuarios novoUsuario = new Usuarios();

                    novoUsuario.nomeUsuario = edtNome.getText().toString();
                    novoUsuario.cpf = edtCpf.getText().toString();
                    novoUsuario.email = edtEmail.getText().toString();
                    novoUsuario.senha = edtSenha.getText().toString();

                    //+ with -, / with _, and = with *.
                    novoUsuario.imagemPerfil = getImagem();
                    if( apiPost.postUsuarios(novoUsuario)) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Usuário criado com sucesso!", Toast.LENGTH_LONG);
                        toast.show();
                        CreateAccountActivity.super.onBackPressed();
                    }

                    passo = 0;
                    photo.setVisibility(View.GONE);
                    insertPhoto.setVisibility(View.GONE);
                    presentation.setVisibility(View.VISIBLE);
                    finish();
            }else{
                    passo = 0;
                }
            }
        });
    }

    private String getImagem() {
        byte[] bytes, buffer = new byte[8192];
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            InputStream inputStream = getResources().openRawResource(+ R.drawable.teste);

            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        bytes = output.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    protected boolean validaCampo(String var){

        boolean ret = true;

        if (TextUtils.isEmpty(var)){
            edtNome.setError("Este campo deve ser preenchido!");
            ret = false;
        }
        return ret;
    }

    protected boolean validaEmail(String email){

        boolean ret = true;

            if(!isEmailValid(email))
            {
                edtEmail.setError("E-mail inválido!");
                ret = false;
            }

        return ret;
    }

    protected boolean validaSenha(String senha, String confirmaSenha){

        boolean ret = true;

        if (!isPasswordValid(senha)) {
            edtSenha.setError("Senha deve conter no minimo 6 digítos!");
            ret = false;
        } else if (!senha.equals(confirmaSenha)) {
            edtConfirmaSenha.setError("Senha inválida!");
            ret = false;
        }
        return ret;
    }



    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 4;
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
