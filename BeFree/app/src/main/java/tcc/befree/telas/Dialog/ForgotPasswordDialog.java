package tcc.befree.telas.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import tcc.befree.R;

/**
 * Created by guilherme.leme on 9/13/17.
 */

public class ForgotPasswordDialog extends Dialog implements
        android.view.View.OnClickListener{

    private Activity c;
    private Button sendButton;
    private Button backButton;
    private TextView textMessage;
    private EditText dialogMessage;
    private EditText passwordText;
    private EditText passwordText2;
    private int count = 0;

    public ForgotPasswordDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.forgot_password_dialog);
        sendButton = (Button) findViewById(R.id.dialog_btn_send);
        backButton = (Button) findViewById(R.id.dialog_btn_back);
        textMessage = (TextView) findViewById(R.id.dialog_text);
        dialogMessage = (EditText) findViewById(R.id.dialog_message);
        passwordText = (EditText) findViewById(R.id.dialog_password);
        passwordText2 = (EditText) findViewById(R.id.dialog_password2);
        sendButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_btn_send:
                if(count == 0){
                    //VERIFICAR NO BANCO SE EMAIL EXISTE
                    if(dialogMessage.getText().toString() != ""){
                        textMessage.setText("Digite o codigo enviado para seu email");
                        dialogMessage.setText("");
                        dialogMessage.setHint("QF94fg");
                        count ++;
                    }else
                        dialogMessage.setText("");
                        textMessage.setText("Email nao encontrado, digite novamente");
                }else if(count == 1){
                    //VERIFICAR NO BANCO SE CODIGO É CORRETO
                    if(dialogMessage.getText().toString() != ""){
                        textMessage.setText("Digite uma nova senha");
                        dialogMessage.setText("");
                        dialogMessage.setVisibility(View.GONE);
                        backButton.setVisibility(View.GONE);
                        passwordText.setVisibility(View.VISIBLE);
                        passwordText2.setVisibility(View.VISIBLE);
                        count ++;
                    }else
                        dialogMessage.setText("");
                        textMessage.setText("Codigo invalido, digite novamente");
                }else if (count == 2){
                    if (passwordText.getText() == passwordText2.getText()){
                        textMessage.setText("Senha alterada com sucesso");
                        passwordText.setVisibility(View.GONE);
                        passwordText2.setVisibility(View.GONE);
                        count = 0;
                    }else
                        passwordText.setText("");
                        passwordText2.setText("");
                        textMessage.setText("Senhas diferentes, tente novamente");
                }else
                    textMessage.setText("ERRO");
                break;
            case R.id.dialog_btn_back:
                c.finish();
                dismiss();
                break;
            default:
                break;
        }
        //dismiss();
    }





   /* public void showDialog(Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView();

        TextView text = (TextView) dialog.findViewById(R.id.dialog_text);

        Button dialogButton = (Button) dialog.findViewById(R.id.dialog_btn_send);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }*/
}