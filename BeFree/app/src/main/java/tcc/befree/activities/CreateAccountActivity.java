package tcc.befree.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tcc.befree.api.ApiModels;
import tcc.befree.api.PostApiModels;
import tcc.befree.R;
import tcc.befree.models.CircleImageView;
import tcc.befree.models.Usuarios;
import tcc.befree.telas.Dialog.InsertImageDialog;
import tcc.befree.utils.Utils;

public class CreateAccountActivity extends AppCompatActivity {

    private static final int SELECT_FILE1 = 100;
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
    private Spinner ddd;
    private Button continuar;
    private int passo;

    // 05/09 - Guilherme Domingues
    private Bitmap bitmapUsuarioPerfil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        ddd = (Spinner) findViewById(R.id.create_account_DDD);
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
        //popula o spinner do ddd
        ArrayAdapter arrayAdapterDDD = new ArrayAdapter(this, android.R.layout.simple_spinner_item, new ApiModels().getDDDsVetor());
        arrayAdapterDDD.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        ddd.setAdapter(arrayAdapterDDD);

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
                                ddd.setVisibility(View.VISIBLE);
                                edtEmail.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }else if (passo == 3){
                    if (CPF(edtCpf.getText().toString())){
                        if(validaEmail(edtEmail.getText().toString())){
                            edtCpf.setVisibility(View.GONE);
                            edtEmail.setVisibility(View.GONE);
                            ddd.setVisibility(View.GONE);
                            message.setVisibility(View.GONE);
                            passo = 4;
                            photo.setVisibility(View.VISIBLE);
                            insertPhoto.setVisibility(View.VISIBLE);
                        }
                    }else {
                        if (TextUtils.isEmpty(edtCpf.getText().toString())){
                            edtCpf.setError("Este campo deve ser preenchido!");
                        }else {
                            edtCpf.setError("CPF INVALIDO!");
                        }
                    }
                }else if (passo == 4){

                    PostApiModels apiPost = new PostApiModels();
                    Usuarios novoUsuario = new Usuarios();

                    novoUsuario.nomeUsuario = edtNome.getText().toString();
                    novoUsuario.cpf = edtCpf.getText().toString();
                    novoUsuario.email = edtEmail.getText().toString();
                    novoUsuario.senha = edtSenha.getText().toString();
                    novoUsuario.ddd = ddd.getSelectedItemPosition() + 1;
//                    if (ddd.getSelectedItemPosition() > 5)
//                        novoUsuario.ddd = ddd.getSelectedItemPosition() + 13;
//                    else
//                        novoUsuario.ddd = ddd.getSelectedItemPosition() + 12;
                    try {
                        novoUsuario.imagemPerfil = Utils.convert(bitmapUsuarioPerfil);
                    }catch(Exception e){
                        novoUsuario.imagemPerfil = "";
                    }

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

        photo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                InsertImageDialog dialog = new InsertImageDialog(CreateAccountActivity.this);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                //openGallery(SELECT_FILE1);
            }
        });
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

        String digitado = email;
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher m = p.matcher(digitado);
        boolean matchFound = m.matches();

        if (!matchFound) {
            return false;
        }else
            return true;
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

    public void openGallery(int req_code) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select file to upload "), req_code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == SELECT_FILE1 ) {
            Uri selectedImageUri = data.getData();

            try {
                bitmapUsuarioPerfil = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                Picasso.with(this.getApplicationContext()).load(selectedImageUri).into(photo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    static public boolean CPF (String strCpf )
    {
        int     d1, d2;
        int     digito1, digito2, resto;
        int     digitoCPF;
        String  nDigResult;

        d1 = d2 = 0;
        digito1 = digito2 = resto = 0;

        for (int nCount = 1; nCount < strCpf.length() -1; nCount++)
        {
            digitoCPF = Integer.valueOf (strCpf.substring(nCount -1, nCount)).intValue();

            //multiplique a ultima casa por 2 a seguinte por 3 a seguinte por 4 e assim por diante.
            d1 = d1 + ( 11 - nCount ) * digitoCPF;

            //para o segundo digito repita o procedimento incluindo o primeiro digito calculado no passo anterior.
            d2 = d2 + ( 12 - nCount ) * digitoCPF;
        };

        //Primeiro resto da divisão por 11.
        resto = (d1 % 11);

        //Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos o resultado anterior.
        if (resto < 2)
            digito1 = 0;
        else
            digito1 = 11 - resto;

        d2 += 2 * digito1;

        //Segundo resto da divisão por 11.
        resto = (d2 % 11);

        //Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos o resultado anterior.
        if (resto < 2)
            digito2 = 0;
        else
            digito2 = 11 - resto;

        //Digito verificador do CPF que está sendo validado.
        String nDigVerific = strCpf.substring (strCpf.length()-2, strCpf.length());

        //Concatenando o primeiro resto com o segundo.
        nDigResult = String.valueOf(digito1) + String.valueOf(digito2);

        //comparar o digito verificador do cpf com o primeiro resto + o segundo resto.
        return nDigVerific.equals(nDigResult);
    }
}
