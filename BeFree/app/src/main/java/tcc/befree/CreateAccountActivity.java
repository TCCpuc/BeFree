package tcc.befree;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import tcc.befree.models.Usuarios;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        final AutoCompleteTextView edtNome = (AutoCompleteTextView) findViewById(R.id.nome_create_account);
        final AutoCompleteTextView edtCpf = (AutoCompleteTextView) findViewById(R.id.cpf_create_account);
        final AutoCompleteTextView edtEmail = (AutoCompleteTextView) findViewById(R.id.email_create_account);
        final EditText edtSenha = (EditText) findViewById(R.id.password_create_account);
        EditText edtConfirmaSenha = (EditText) findViewById(R.id.confirm_password_create_account);

        Button btnCriarAccount = (Button) findViewById(R.id.button_create_account);

        btnCriarAccount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

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
        });


    }
}
