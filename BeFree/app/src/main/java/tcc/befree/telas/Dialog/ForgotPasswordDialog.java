package tcc.befree.telas.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tcc.befree.R;
import tcc.befree.api.ApiModels;
import tcc.befree.api.PutApiModels;
import tcc.befree.models.Usuarios;

/**
 * Created by guilherme.leme on 9/13/17.
 */

public class ForgotPasswordDialog extends Dialog implements
        android.view.View.OnClickListener{

    private Activity c;
    private Button sendButton;
    private Button backButton;
    private Button code;
    private TextView textMessage;
    private EditText dialogMessage;
    private EditText passwordText;
    private EditText passwordText2;
    private int count = 0;
    String email = null;

    public ForgotPasswordDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_forgot_password);
        sendButton = (Button) findViewById(R.id.dialog_btn_send);
        backButton = (Button) findViewById(R.id.dialog_btn_back);
        code = (Button) findViewById(R.id.dialog_btn_codigo);
        textMessage = (TextView) findViewById(R.id.dialog_text);
        dialogMessage = (EditText) findViewById(R.id.dialog_message);
        passwordText = (EditText) findViewById(R.id.dialog_password);
        passwordText2 = (EditText) findViewById(R.id.dialog_password2);
        sendButton.setOnClickListener(this);
        code.setOnClickListener(this);
        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_btn_send:
                if(count == 0){
                    email = dialogMessage.getText().toString();
                    //VERIFICAR NO BANCO SE EMAIL EXISTE
                    if(dialogMessage.getText().length() == 0) {
                        textMessage.setText("Campo vazio, por favor digite um email");
                        dialogMessage.setText("");
                        dialogMessage.requestFocus();
                    }else if(!validaEmail(dialogMessage.getText().toString())){
                        textMessage.setText("Email invalido, por favor digite um email");
                        dialogMessage.setText("");
                        dialogMessage.requestFocus();
                    }else{
                        new ApiModels().getCodePassword(dialogMessage.getText().toString());
                        textMessage.setText("Digite o codigo enviado para seu email");
                        code.setVisibility(View.GONE);
                        dialogMessage.setText("");
                        dialogMessage.requestFocus();
                        count ++;
                    }
                }else if(count == 1){
                    //VERIFICAR NO BANCO SE CODIGO É CORRETO
                    String codigoNoBanco = new ApiModels().getCodigoSegurancaByEmail(email);
                    String codigoDigitado = dialogMessage.getText().toString();
                    if(codigoDigitado.equals(codigoNoBanco)){
                        textMessage.setText("Digite uma nova senha");
                        dialogMessage.setText("");
                        dialogMessage.setVisibility(View.GONE);
                        passwordText.setVisibility(View.VISIBLE);
                        passwordText2.setVisibility(View.VISIBLE);
                        passwordText.requestFocus();
                        count ++;
                    }else {
                        dialogMessage.setText("");
                        textMessage.setText("Codigo invalido, digite novamente");
                        dialogMessage.requestFocus();
                    }

                }else if (count == 2){
                    String senha = passwordText.getText().toString();
                    if(senha.length() == 0){
                        passwordText.setText("");
                        passwordText2.setText("");
                        textMessage.setText("Por Favor digite uma senha");
                        passwordText.requestFocus();
                    }
                    else if(!senha.equals(passwordText2.getText().toString())){
                        passwordText.setText("");
                        passwordText2.setText("");
                        textMessage.setText("Senhas diferentes, tente novamente");
                        passwordText.requestFocus();
                    }else{
                        textMessage.setText("Senha alterada com sucesso");
                        sendButton.setVisibility(View.GONE);
                        Usuarios usuarioComSenhaAlterada = new ApiModels().getUsuariosByEmail(email);
                        usuarioComSenhaAlterada.senha = senha;
                        usuarioComSenhaAlterada.codigoSeguranca = null;
                        new PutApiModels().putUsuarios(usuarioComSenhaAlterada);
                        passwordText.setVisibility(View.GONE);
                        passwordText2.setVisibility(View.GONE);
                        count ++;
                    }
                }else if (count == 3){
                    this.dismiss();
                }else
                    textMessage.setText("ERRO");
                break;
            case R.id.dialog_btn_codigo:
                textMessage.setText("Digite o codigo enviado para seu email");
                code.setVisibility(View.GONE);
                dialogMessage.setText("");
                dialogMessage.requestFocus();
                count ++;
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
    public boolean validaEmail(Object objeto) {
        String digitado = (String) objeto;
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher m = p.matcher(digitado);
        boolean matchFound = m.matches();

        if (!matchFound) {
            return false;
        }else
            return true;
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