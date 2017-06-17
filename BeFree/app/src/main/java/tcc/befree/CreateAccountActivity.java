package tcc.befree;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import tcc.befree.models.Usuarios;

public class CreateAccountActivity extends AppCompatActivity {

    AutoCompleteTextView edtNome;
    AutoCompleteTextView edtCpf;
    AutoCompleteTextView edtEmail;
    EditText edtSenha;
    EditText edtConfirmaSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        edtNome = (AutoCompleteTextView) findViewById(R.id.nome_create_account);
        edtCpf = (AutoCompleteTextView) findViewById(R.id.cpf_create_account);
        edtEmail = (AutoCompleteTextView) findViewById(R.id.email_create_account);
        edtSenha = (EditText) findViewById(R.id.password_create_account);
        edtConfirmaSenha = (EditText) findViewById(R.id.confirm_password_create_account);

        Button btnCriarAccount = (Button) findViewById(R.id.button_create_account);
        btnCriarAccount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(validaCampos(edtNome.getText().toString(),
                                 edtCpf.getText().toString(),
                                 edtEmail.getText().toString(),
                                 edtSenha.getText().toString(),
                                 edtConfirmaSenha.getText().toString())){

                    PostApiModels apiPost = new PostApiModels();
                    Usuarios novoUsuario = new Usuarios();

                    novoUsuario.nomeUsuario = edtNome.getText().toString();
                    novoUsuario.cpf = edtCpf.getText().toString();
                    novoUsuario.email = edtEmail.getText().toString();
                    novoUsuario.senha = edtSenha.getText().toString();

                    if( apiPost.postUsuarios(novoUsuario)) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Usuário criado com sucesso!", Toast.LENGTH_LONG);
                        toast.show();
                        CreateAccountActivity.super.onBackPressed();
                    }

                }
            }
        });
    }

    protected boolean validaCampos(String nomeUsuario, String cpf, String email, String senha, String confirmaSenha){

        boolean ret = true;

        if (TextUtils.isEmpty(nomeUsuario)){
            edtNome.setError("Este campo deve ser preenchido!");
            ret = false;
        }

        if (TextUtils.isEmpty(cpf)){
            edtCpf.setError("Este campo deve ser preenchido!");
            ret = false;
        }

        if (TextUtils.isEmpty(email)){
            edtEmail.setError("Este campo deve ser preenchido!");
            ret = false;
        }

        if (TextUtils.isEmpty(senha)){
            edtSenha.setError("Este campo deve ser preenchido!");
            ret = false;
        }

        if (TextUtils.isEmpty(senha)){
            edtConfirmaSenha.setError("Este campo deve ser preenchido!");
            ret = false;
        }

        if (!ret){

            if(!isEmailValid(email))
            {
                edtEmail.setError("E-mail inválido!");
                ret = false;
            }else {
                if (!isPasswordValid(senha)) {
                    edtSenha.setError("Senha deve conter no minimo 6 digítos!");
                    ret = false;
                } else if (!senha.equals(confirmaSenha)) {
                    edtConfirmaSenha.setError("Senha inválida!");
                    ret = false;
                }
            }

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
}
