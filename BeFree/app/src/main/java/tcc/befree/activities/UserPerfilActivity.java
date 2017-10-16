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
import tcc.befree.models.Usuarios;
import tcc.befree.telas.Dialog.EditUserPassword;
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
    //private EditText cidade;
    //private ImageButton cidadeButton;
    //private EditText estado;
    //private ImageButton estadoButton;
    //private EditText bairro;
    //private ImageButton bairroButton;
    //private EditText logradouro;
    //private ImageButton logradouroButton;
    //private EditText numero;
    //private ImageButton numeroButton;
    //private EditText cep;
    //private ImageButton cepButton;
    //private EditText nascimento;
    //private ImageButton nascimentoButton;
    private EditText ddd;
    private ImageButton dddButton;
    private TextView titulo;
    private static final int SELECT_FILE1 = 100;
    private Bitmap bitmapUsuarioPerfil;
    private Usuarios usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_perfil);

        //Recebendo Atributos do Usuario
        usuario = new Usuarios();
        Intent it = this.getIntent();
        Bundle mainActivityIntent = it.getExtras();
        String x [] = mainActivityIntent.getString("arrayUsuario").split("%");
        usuario = new Usuarios();
        usuario.idUsuario = Integer.parseInt(x[0]);
        usuario.nomeUsuario = x[1];
        usuario.cpf = Integer.parseInt(x[2]);
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
        //------------------------------

        photo = (CircleImageView)findViewById(R.id.user_perfil_photo);
        Picasso.with(this).load(usuario.imagemPerfil).into(photo);
        edit_dados = (Button) findViewById(R.id.user_perfil_edit_data);
        edit_dados_val = false;
        edit_password = (Button) findViewById(R.id.user_perfil_edit_password);
        username = (EditText) findViewById(R.id.user_perfil_username);
        username.setText(usuario.nomeUsuario);
        usernameButton = (ImageButton) findViewById(R.id.user_perfil_username_button);
        email = (EditText) findViewById(R.id.user_perfil_email);
        email.setText(usuario.email);
        emailButton = (ImageButton) findViewById(R.id.user_perfil_email_button);
        cpf = (EditText) findViewById(R.id.user_perfil_cpf);
        cpf.setText("" + usuario.cpf);
        cpfButton = (ImageButton) findViewById(R.id.user_perfil_cpf_button);
        /*cidade = (EditText) findViewById(R.id.user_perfil_cidade);
        cidade.setText("" + usuario.idCidade);
        cidadeButton = (ImageButton) findViewById(R.id.user_perfil_cidade_button);
        estado = (EditText) findViewById(R.id.user_perfil_estado);
        estado.setText("" + usuario.idEstado);
        estadoButton = (ImageButton) findViewById(R.id.user_perfil_estado_button);
        bairro = (EditText) findViewById(R.id.user_perfil_bairro);
        bairro.setText(usuario.bairro);
        bairroButton = (ImageButton) findViewById(R.id.user_perfil_bairro_button);
        logradouro = (EditText) findViewById(R.id.user_perfil_logradouro);
        logradouro.setText(usuario.logradouro);
        logradouroButton = (ImageButton) findViewById(R.id.user_perfil_logradouro_button);
        numero = (EditText) findViewById(R.id.user_perfil_numero);
        numero.setText("" + usuario.numero);
        numeroButton = (ImageButton) findViewById(R.id.user_perfil_numero_button);
        cep = (EditText) findViewById(R.id.user_perfil_cep);
        cep.setText("" + usuario.cep);
        cepButton = (ImageButton) findViewById(R.id.user_perfil_cep_button);
        nascimento = (EditText) findViewById(R.id.user_perfil_nascimento);
        nascimentoButton = (ImageButton) findViewById(R.id.user_perfil_nascimento_button);*/
        ddd = (EditText) findViewById(R.id.user_perfil_ddd);
        ddd.setText("" + usuario.ddd);
        dddButton = (ImageButton) findViewById(R.id.user_perfil_ddd_button);
        titulo = (TextView) findViewById(R.id.user_perfil_title);
        titulo.setText(usuario.nomeUsuario);

        edit_dados.setOnClickListener(this);
        photo.setOnClickListener(this);
        edit_password.setOnClickListener(this);
        usernameButton.setOnClickListener(this);
        emailButton.setOnClickListener(this);
        cpfButton.setOnClickListener(this);
        /*cidadeButton.setOnClickListener(this);
        estadoButton.setOnClickListener(this);
        bairroButton.setOnClickListener(this);
        logradouroButton.setOnClickListener(this);
        numeroButton.setOnClickListener(this);
        cepButton.setOnClickListener(this);*/
        //nascimentoButton.setOnClickListener(this);
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
                    username.setBackgroundColor(Color.WHITE);
                    emailButton.setVisibility(View.GONE);
                    email.setBackgroundColor(Color.WHITE);
                    cpfButton.setVisibility(View.GONE);
                    cpf.setBackgroundColor(Color.WHITE);
                    /*cidadeButton.setVisibility(View.GONE);
                    cidade.setBackgroundColor(Color.WHITE);
                    estadoButton.setVisibility(View.GONE);
                    estado.setBackgroundColor(Color.WHITE);
                    bairroButton.setVisibility(View.GONE);
                    bairro.setBackgroundColor(Color.WHITE);
                    logradouroButton.setVisibility(View.GONE);
                    logradouro.setBackgroundColor(Color.WHITE);
                    numeroButton.setVisibility(View.GONE);
                    numero.setBackgroundColor(Color.WHITE);
                    cepButton.setVisibility(View.GONE);
                    cep.setBackgroundColor(Color.WHITE);*/
                    //nascimentoButton.setVisibility(View.GONE);
                    dddButton.setVisibility(View.GONE);
                    ddd.setBackgroundColor(Color.WHITE);
                    edit_dados_val = false;
                }else {
                    edit_dados.setText("Confirmar");
                    usernameButton.setVisibility(View.VISIBLE);
                    emailButton.setVisibility(View.VISIBLE);
                    cpfButton.setVisibility(View.VISIBLE);
                    /*cidadeButton.setVisibility(View.VISIBLE);
                    estadoButton.setVisibility(View.VISIBLE);
                    bairroButton.setVisibility(View.VISIBLE);
                    logradouroButton.setVisibility(View.VISIBLE);
                    numeroButton.setVisibility(View.VISIBLE);
                    cepButton.setVisibility(View.VISIBLE);*/
                    //nascimentoButton.setVisibility(View.VISIBLE);
                    dddButton.setVisibility(View.VISIBLE);
                    edit_dados_val = true;
                }
                break;
            case R.id.user_perfil_photo:
                InsertImageDialog image = new InsertImageDialog(UserPerfilActivity.this);
                image.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                image.show();
                break;
            /*case R.id.user_perfil_bairro_button:
                bairro.setFocusableInTouchMode(true);
                bairro.setFocusable(true);
                imm.showSoftInput(bairro, InputMethodManager.SHOW_IMPLICIT);
                bairro.setBackgroundColor(Color.RED);
                bairro.requestFocus();
                break;*/
            case R.id.user_perfil_username_button:
                username.setFocusableInTouchMode(true);
                username.setFocusable(true);
                imm.showSoftInput(username, InputMethodManager.SHOW_IMPLICIT);
                username.setBackgroundColor(Color.RED);
                username.requestFocus();
                break;
            /*ase R.id.user_perfil_cep_button:
                cep.setFocusableInTouchMode(true);
                cep.setFocusable(true);
                imm.showSoftInput(cep, InputMethodManager.SHOW_IMPLICIT);
                cep.setBackgroundColor(Color.RED);
                cep.requestFocus();
                break;*/
            /*case R.id.user_perfil_cidade_button:
                cidade.setFocusableInTouchMode(true);
                cidade.setFocusable(true);
                imm.showSoftInput(cidade, InputMethodManager.SHOW_IMPLICIT);
                cidade.setBackgroundColor(Color.RED);
                cidade.requestFocus();
                break;*/
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
            /*case R.id.user_perfil_estado_button:
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
                break;*/
            case R.id.user_perfil_edit_password:
                EditUserPassword dialog = new EditUserPassword(this);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
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
