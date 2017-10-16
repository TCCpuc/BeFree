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

    public EditUserPassword(Activity a) {
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
        checked = false;

        usuario = new Usuarios();
        Intent it = c.getIntent();
        Bundle mainActivityIntent = it.getExtras();
        final String x [] = mainActivityIntent.getString("arrayUsuario").split("%");

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

