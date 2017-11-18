package tcc.befree.telas.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

    private final ApiModels apiModels;
    private Activity c;
    private Button sendButton;
    private Button backButton;
    private Button code;
    private TextView textMessage;
    private EditText dialogMessage;
    private EditText passwordText;
    private EditText passwordText2;
    private String senha;
    private int count;
    private Usuarios usuario;
    private LoadingDialog loginDialog;
    String email = null;

    public ForgotPasswordDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        apiModels = new ApiModels();
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
        count = 0;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_btn_send:
                if(count == 0){
                    email = dialogMessage.getText().toString();
                    //VERIFICAR NO BANCO SE EMAIL EXISTE
                    if(email.length() == 0) {
                        textMessage.setText("Campo vazio, por favor digite um email");
                        dialogMessage.setText("");
                        dialogMessage.requestFocus();
                    }else if(!validaEmail(dialogMessage.getText().toString())){
                        textMessage.setText("Email invalido, por favor digite um email");
                        dialogMessage.setText("");
                        dialogMessage.requestFocus();
                    }else{
                        apiModels.getCodePassword(email);
                        textMessage.setText("Digite o codigo enviado para seu email");
                        dialogMessage.setHint("Código");
                        code.setVisibility(View.GONE);
                        dialogMessage.setText("");
                        dialogMessage.requestFocus();
                        count ++;
                    }
                }else if(count == 1){
                    //VERIFICAR NO BANCO SE CODIGO É CORRETO
                    String codigoDigitado = dialogMessage.getText().toString();
                    usuario = apiModels.validateCode(codigoDigitado);
                    if(usuario != null){
                        textMessage.setText("Por Favor digite uma senha");
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
                    senha = passwordText.getText().toString();
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
                        usuario.senha = passwordText.getText().toString();
                        startLoadingDialog();
                        threadUpdate();
                    }
                }else if (count == 3){
                    this.dismiss();
                }else
                    textMessage.setText("ERRO");
                break;
            case R.id.dialog_btn_codigo:
                textMessage.setText("Digite o codigo enviado para seu email");
                dialogMessage.setHint("Código");
                code.setVisibility(View.GONE);
                dialogMessage.setText("");
                dialogMessage.requestFocus();
                count ++;
                break;
            case R.id.dialog_btn_back:
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

    private void threadUpdate(){
        new Thread(){
            @Override
            public void run() {
                new PutApiModels().putUsuarios(usuario);
                threadUI();
            }
        }.start();
    }

    private void threadUI(){
        c.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textMessage.setText("Senha alterada com sucesso");
                sendButton.setVisibility(View.GONE);
                passwordText.setVisibility(View.GONE);
                passwordText2.setVisibility(View.GONE);
                count ++;
                stopLoadingDialog();
            }
        });
    }

    private void startLoadingDialog(){
        loginDialog = new LoadingDialog(c);
        loginDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loginDialog.show();
    }

    private void stopLoadingDialog(){
        loginDialog.dismiss();
    }
}