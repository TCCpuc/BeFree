package tcc.befree.telas.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import tcc.befree.R;
import tcc.befree.activities.UserPerfilActivity;
import tcc.befree.api.ApiModels;
import tcc.befree.api.PutApiModels;
import tcc.befree.models.CircleImageView;
import tcc.befree.models.Usuarios;

/**
 * Created by guilherme.leme on 10/15/17.
 */

public class EditUserPassword extends Dialog {

    private Activity c;
    private Button send;
    private Button back;
    private TextView menssagem;
    private EditText password;
    private EditText password2;
    private boolean checked;
    private Usuarios usuario;

    public EditUserPassword(UserPerfilActivity a) {
        //SE CONTEXT = 1 (INSERT IMAGEM USUARIO)
        //SE CONTEXT = 2 (INSERT IMAGEM BUSCA)
        //SE CONTEXT = 3 (INSERT IMAGEM SERVICO)
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_edit_user_password);

        send = (Button) findViewById(R.id.dialog_edit_user_password_send);
        back = (Button) findViewById(R.id.dialog_edit_user_password_back);
        menssagem = (TextView) findViewById(R.id.dialog_edit_user_password_text);
        password = (EditText) findViewById(R.id.dialog_edit_user_password_password);
        password2 = (EditText) findViewById(R.id.dialog_edit_user_password_password2);
        checked = false;;
        Intent it = c.getIntent();
        Bundle mainActivityIntent = it.getExtras();
        final String x [] = mainActivityIntent.getString("arrayUsuario").split("%");
        usuario = new Usuarios();
        usuario.idUsuario = Integer.parseInt(x[0]);
        usuario.nomeUsuario = x[1];
        usuario.cpf = x[2];
        usuario.idCidade = Integer.parseInt(x[3]);
        usuario.idEstado = Integer.parseInt(x[4]);
        usuario.bairro = x[5];
        usuario.logradouro = x[6];
        usuario.numero = Integer.parseInt(x[7]);
        usuario.cep = Integer.parseInt(x[8]);
        usuario.email = x[9];
        usuario.ddd = Integer.parseInt(x[10]);
        usuario.imagemPerfil = x[11];
        usuario.senha = x[12];

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checked){
                    if(password.getText().toString().equals(x[12])){
                        menssagem.setText("Digite e confirme a nova senha");
                        password.setText("");
                        password2.setVisibility(View.VISIBLE);
                        checked = true;
                    }else
                        menssagem.setText("Senha incorreta");
                }else {
                    if(password.getText().toString().equals(password2.getText().toString())){
                        if(password.getText().length()<5) menssagem.setText("Senha muito curta");
                        else {
                            //ATUALIZA NO BANCO
                            usuario.senha = password.getText().toString();
                            new PutApiModels().putUsuarios(usuario);
                            Toast ntoast = Toast.makeText(c,"Senha alterada com sucesso!!", Toast.LENGTH_SHORT);
                            ntoast.show();
                            dismiss();
                        }
                    }else
                        menssagem.setText("Senha diferente");
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


    @Override
    public void onBackPressed() {
        dismiss();
    }
}

