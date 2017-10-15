package tcc.befree.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import tcc.befree.R;
import tcc.befree.models.CircleImageView;
import tcc.befree.telas.Dialog.InsertImageDialog;

public class UserPerfilActivity extends AppCompatActivity implements View.OnClickListener {


    private CircleImageView photo;
    private Button edit_dados;
    private boolean edit_dados_val;
    private Button edit_password;
    private EditText username;
    private ImageButton usernameButton;
    private EditText email;
    private ImageButton emailButton;
    private EditText cpf;
    private ImageButton cpfButton;
    private EditText cidade;
    private ImageButton cidadeButton;
    private EditText estado;
    private ImageButton estadoButton;
    private EditText bairro;
    private ImageButton bairroButton;
    private EditText logradouro;
    private ImageButton logradouroButton;
    private EditText numero;
    private ImageButton numeroButton;
    private EditText cep;
    private ImageButton cepButton;
    private EditText nascimento;
    private ImageButton nascimentoButton;
    private EditText ddd;
    private ImageButton dddButton;
    private TextView titulo;
    private static final int SELECT_FILE1 = 100;
    private Bitmap bitmapUsuarioPerfil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_perfil);

        photo = (CircleImageView)findViewById(R.id.user_perfil_photo);
        edit_dados = (Button) findViewById(R.id.user_perfil_edit_data);
        edit_dados_val = false;
        edit_password = (Button) findViewById(R.id.user_perfil_edit_password);
        username = (EditText) findViewById(R.id.user_perfil_username);
        usernameButton = (ImageButton) findViewById(R.id.user_perfil_username_button);
        email = (EditText) findViewById(R.id.user_perfil_email);
        emailButton = (ImageButton) findViewById(R.id.user_perfil_email_button);
        cpf = (EditText) findViewById(R.id.user_perfil_cpf);
        cpfButton = (ImageButton) findViewById(R.id.user_perfil_cpf_button);
        cidade = (EditText) findViewById(R.id.user_perfil_cidade);
        cidadeButton = (ImageButton) findViewById(R.id.user_perfil_cidade_button);
        estado = (EditText) findViewById(R.id.user_perfil_estado);
        estadoButton = (ImageButton) findViewById(R.id.user_perfil_estado_button);
        bairro = (EditText) findViewById(R.id.user_perfil_bairro);
        bairroButton = (ImageButton) findViewById(R.id.user_perfil_bairro_button);
        logradouro = (EditText) findViewById(R.id.user_perfil_logradouro);
        logradouroButton = (ImageButton) findViewById(R.id.user_perfil_logradouro_button);
        numero = (EditText) findViewById(R.id.user_perfil_numero);
        numeroButton = (ImageButton) findViewById(R.id.user_perfil_numero_button);
        cep = (EditText) findViewById(R.id.user_perfil_cep);
        cepButton = (ImageButton) findViewById(R.id.user_perfil_cep_button);
        nascimento = (EditText) findViewById(R.id.user_perfil_nascimento);
        nascimentoButton = (ImageButton) findViewById(R.id.user_perfil_nascimento_button);
        ddd = (EditText) findViewById(R.id.user_perfil_ddd);
        dddButton = (ImageButton) findViewById(R.id.user_perfil_ddd_button);
        titulo = (TextView) findViewById(R.id.user_perfil_title);

        titulo.setText("[NOME DO USUARIO]");

        edit_dados.setOnClickListener(this);
        photo.setOnClickListener(this);
        edit_password.setOnClickListener(this);
        usernameButton.setOnClickListener(this);
        emailButton.setOnClickListener(this);
        cpfButton.setOnClickListener(this);
        cidadeButton.setOnClickListener(this);
        estadoButton.setOnClickListener(this);
        bairroButton.setOnClickListener(this);
        logradouroButton.setOnClickListener(this);
        numeroButton.setOnClickListener(this);
        cepButton.setOnClickListener(this);
        nascimentoButton.setOnClickListener(this);
        dddButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        Toast toast = Toast.makeText(this,"Este campo não pode ser alterado", Toast.LENGTH_SHORT);
        switch (v.getId()) {
            case R.id.user_perfil_edit_data:
                if(edit_dados_val) {
                    edit_dados.setText("Editar Dados");
                    usernameButton.setVisibility(View.GONE);
                    emailButton.setVisibility(View.GONE);
                    cpfButton.setVisibility(View.GONE);
                    cidadeButton.setVisibility(View.GONE);
                    estadoButton.setVisibility(View.GONE);
                    bairroButton.setVisibility(View.GONE);
                    logradouroButton.setVisibility(View.GONE);
                    numeroButton.setVisibility(View.GONE);
                    cepButton.setVisibility(View.GONE);
                    nascimentoButton.setVisibility(View.GONE);
                    dddButton.setVisibility(View.GONE);
                    edit_dados_val = false;
                }else {
                    edit_dados.setText("Confirmar");
                    usernameButton.setVisibility(View.VISIBLE);
                    emailButton.setVisibility(View.VISIBLE);
                    cpfButton.setVisibility(View.VISIBLE);
                    cidadeButton.setVisibility(View.VISIBLE);
                    estadoButton.setVisibility(View.VISIBLE);
                    bairroButton.setVisibility(View.VISIBLE);
                    logradouroButton.setVisibility(View.VISIBLE);
                    numeroButton.setVisibility(View.VISIBLE);
                    cepButton.setVisibility(View.VISIBLE);
                    nascimentoButton.setVisibility(View.VISIBLE);
                    dddButton.setVisibility(View.VISIBLE);
                    edit_dados_val = true;
                }
                break;
            case R.id.user_perfil_photo:
                InsertImageDialog image = new InsertImageDialog(UserPerfilActivity.this);
                image.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                image.show();
                break;
            case R.id.user_perfil_bairro_button:
                bairro.setFocusableInTouchMode(true);
                bairro.setFocusable(true);
                imm.showSoftInput(bairro, InputMethodManager.SHOW_IMPLICIT);
                bairro.setBackgroundColor(Color.RED);
                bairro.requestFocus();
            case R.id.user_perfil_username_button:
                toast.show();
                break;
            case R.id.user_perfil_cep_button:
                cep.setFocusableInTouchMode(true);
                cep.setFocusable(true);
                imm.showSoftInput(cep, InputMethodManager.SHOW_IMPLICIT);
                cep.setBackgroundColor(Color.RED);
                cep.requestFocus();
                break;
            case R.id.user_perfil_cidade_button:
                cidade.setFocusableInTouchMode(true);
                cidade.setFocusable(true);
                imm.showSoftInput(cidade, InputMethodManager.SHOW_IMPLICIT);
                cidade.setBackgroundColor(Color.RED);
                cidade.requestFocus();
                break;
            case R.id.user_perfil_cpf_button:
                toast.show();
                break;
            case R.id.user_perfil_ddd_button:
                ddd.setFocusableInTouchMode(true);
                ddd.setFocusable(true);
                imm.showSoftInput(ddd, InputMethodManager.SHOW_IMPLICIT);
                ddd.setBackgroundColor(Color.RED);
                ddd.requestFocus();
                break;
            case R.id.user_perfil_email_button:
                toast.show();
                break;
            case R.id.user_perfil_estado_button:
                estado.setFocusableInTouchMode(true);
                estado.setFocusable(true);
                imm.showSoftInput(estado, InputMethodManager.SHOW_IMPLICIT);
                estado.setBackgroundColor(Color.RED);
                estado.requestFocus();
                break;
            case R.id.user_perfil_logradouro_button:
                logradouro.setFocusableInTouchMode(true);
                logradouro.setFocusable(true);
                imm.showSoftInput(logradouro, InputMethodManager.SHOW_IMPLICIT);
                logradouro.setBackgroundColor(Color.RED);
                logradouro.requestFocus();
                break;
            case R.id.user_perfil_nascimento_button:
                nascimento.setFocusableInTouchMode(true);
                nascimento.setFocusable(true);
                imm.showSoftInput(nascimento, InputMethodManager.SHOW_IMPLICIT);
                nascimento.setBackgroundColor(Color.RED);
                nascimento.requestFocus();
                break;
            default:
                break;
        }
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
        if(edit_dados_val){
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Editar")
                        .setMessage("Você deseja salvar as alterações antes de sair?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //comita no banco
                                finish();
                            }

                        })
                        .setNegativeButton("Não", null)
                        .show();

                //super.onBackPressed();
            }
        }else{
            finish();
        }

    }
}
