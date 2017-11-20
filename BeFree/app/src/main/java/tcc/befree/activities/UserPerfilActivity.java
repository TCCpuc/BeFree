package tcc.befree.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import tcc.befree.R;
import tcc.befree.api.ApiModels;
import tcc.befree.api.PostApiModels;
import tcc.befree.api.PutApiModels;
import tcc.befree.models.CircleImageView;
import tcc.befree.models.DDD;
import tcc.befree.models.Usuarios;
import tcc.befree.telas.Dialog.EditUserPassword;
import tcc.befree.telas.Dialog.InsertImageDialog;
import tcc.befree.telas.Dialog.LoadingDialog;
import tcc.befree.utils.Utils;

public class UserPerfilActivity extends AppCompatActivity implements View.OnClickListener {


    private CircleImageView photo;
    private TextView imageText;
    private Button edit_dados;
    private boolean edit_dados_val;
    private boolean edit_dados_editado;
    private Button edit_password;
    private EditText username;
    private ImageButton usernameButton;
    private ImageButton back;
    private EditText email;
    private ImageButton emailButton;
    private EditText cpf;
    private ImageButton cpfButton;
    private Spinner ddd;
    private ImageButton dddButton;
    private TextView titulo;
    private static final int SELECT_FILE1 = 100;
    private Bitmap bitmapUsuarioPerfil;
    private Usuarios usuario;
    private Intent it;
    private DDD[] dddArray;
    private ArrayAdapter arrayAdapterDDD;
    private LoadingDialog loginDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_perfil);

        photo = (CircleImageView)findViewById(R.id.user_perfil_photo);
        imageText = (TextView) findViewById(R.id.user_perfil_photo_text);
        edit_dados = (Button) findViewById(R.id.user_perfil_edit_data);
        edit_password = (Button) findViewById(R.id.user_perfil_edit_password);
        username = (EditText) findViewById(R.id.user_perfil_username);
        usernameButton = (ImageButton) findViewById(R.id.user_perfil_username_button);
        email = (EditText) findViewById(R.id.user_perfil_email);
        emailButton = (ImageButton) findViewById(R.id.user_perfil_email_button);
        cpf = (EditText) findViewById(R.id.user_perfil_cpf);
        cpfButton = (ImageButton) findViewById(R.id.user_perfil_cpf_button);
        ddd = (Spinner) findViewById(R.id.user_perfil_ddd);
        dddButton = (ImageButton) findViewById(R.id.user_perfil_ddd_button);
        titulo = (TextView) findViewById(R.id.user_perfil_title);
        back = (ImageButton) findViewById(R.id.cabecalho_back);

        //Recebendo Atributos do Usuario
        edit_dados_editado = false;
        usuario = new Usuarios();
        it = this.getIntent();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        startLoadingDialog();
        threadUpdate();
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
                    imageText.setVisibility(View.GONE);
                    photo.setOnClickListener(null);
                    dddButton.setVisibility(View.GONE);
                    ddd.setBackgroundColor(Color.WHITE);
                    edit_dados_val = false;
                    ddd.setEnabled(false);
                    ddd.setClickable(false);
                    ddd.setFocusableInTouchMode(false);
                    ddd.setFocusable(false);
                    ddd.setBackgroundColor(Color.WHITE);
                    username.setFocusableInTouchMode(false);
                    username.setFocusable(false);
                    username.setBackgroundColor(Color.WHITE);
                    titulo.setText(username.getText().toString().toUpperCase());
                    startLoadingDialog();
                    threadUpdateUsuario();
                }else {
                    edit_dados.setText("Confirmar");
                    usernameButton.setVisibility(View.VISIBLE);
                    emailButton.setVisibility(View.VISIBLE);
                    cpfButton.setVisibility(View.VISIBLE);
                    imageText.setVisibility(View.VISIBLE);
                    photo.setOnClickListener(this);
                    dddButton.setVisibility(View.VISIBLE);
                    edit_dados_val = true;
                    edit_dados_editado = true;
                }
                break;
            case R.id.user_perfil_photo:
                InsertImageDialog image = new InsertImageDialog(UserPerfilActivity.this);
                image.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                image.show();
                break;
            case R.id.user_perfil_username_button:
                username.setFocusableInTouchMode(true);
                username.setFocusable(true);
                imm.showSoftInput(username, InputMethodManager.SHOW_IMPLICIT);
                username.setBackgroundColor(Color.RED);
                username.requestFocus();
                break;
            case R.id.user_perfil_cpf_button:
                toast.show();
                break;
            case R.id.user_perfil_ddd_button:
                ddd.setEnabled(true);
                ddd.setClickable(true);
                ddd.setFocusableInTouchMode(true);
                ddd.setFocusable(true);
                ddd.setBackgroundColor(Color.RED);
                ddd.requestFocus();
                break;
            case R.id.user_perfil_email_button:
                toast.show();
                break;
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
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Editar")
                    .setMessage("Você deseja salvar as alterações antes de sair?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startLoadingDialog();
                                threadUpdateUsuarioBack();
                            }

                        })
                    .setNegativeButton("Não", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .show();

                //super.onBackPressed();
        }else if(edit_dados_editado){
            Intent resultInt = new Intent();
            resultInt.putExtra("Result", "Done");
            setResult(UserPerfilActivity.RESULT_OK, resultInt);
            super.onBackPressed();
            finish();
        }else{
            UserPerfilActivity.super.onBackPressed();
            finish();
        }
    }

    private void threadUpdateUsuarioBack(){
        new Thread() {
            @Override
            public void run() {
                try {
                    usuario.nomeUsuario = username.getText().toString();
                    usuario.ddd = ddd.getSelectedItemPosition() + 1;
                    if(bitmapUsuarioPerfil!= null){
                        usuario.imagemPerfil = Utils.convert(bitmapUsuarioPerfil);
                    }
                    new PutApiModels().putUsuarios(usuario);
                }
                catch (Exception E){
                    System.err.print("Erro ao atualizar usuario");
                }
                threadUIUsuarioBack();
            }
        }.start();
    }

    private void threadUIUsuarioBack() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                stopLoadingDialog();
                Intent resultInt = new Intent();
                resultInt.putExtra("Result", "Done");
                setResult(UserPerfilActivity.RESULT_OK, resultInt);
                UserPerfilActivity.super.onBackPressed();
                finish();
            }
        });
    }

    private void threadUpdateUsuario(){
        new Thread() {
            @Override
            public void run() {
                try {
                    usuario.nomeUsuario = username.getText().toString();
                    usuario.ddd = ddd.getSelectedItemPosition() + 1;
                    if(bitmapUsuarioPerfil!= null){
                        usuario.imagemPerfil = Utils.convert(bitmapUsuarioPerfil);
                    }
                    new PutApiModels().putUsuariosSemSenha(usuario);
                }
                catch (Exception E){
                    System.err.print("Erro ao atualizar usuario");
                }
                threadUIUsuario();
            }
        }.start();
    }

    private void threadUIUsuario() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                stopLoadingDialog();
            }
        });
    }

    private void threadUpdate(){
        new Thread(){
            @Override
            public void run() {
                Bundle mainActivityIntent = it.getExtras();
                String x [] = mainActivityIntent.getString("arrayUsuario").split("%");
                usuario = new ApiModels().getUsuarioById(Integer.parseInt(x[0]));
                dddArray = new ApiModels().getDDDsVetor();
                threadUI();
            }
        }.start();
    }

    private void threadUI(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Picasso.with(UserPerfilActivity.this).load(usuario.imagemPerfil).into(photo);
                edit_dados_val = false;
                username.setText(usuario.nomeUsuario);
                email.setText(usuario.email);
                cpf.setText("" + usuario.cpf);
                //popula o spinner do ddd
                arrayAdapterDDD = new ArrayAdapter(UserPerfilActivity.this, android.R.layout.simple_spinner_item, dddArray);
                arrayAdapterDDD.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
                ddd.setAdapter(arrayAdapterDDD);
                ddd.setId(usuario.ddd);
                ddd.setSelection(usuario.ddd - 1);
                ddd.setEnabled(false);
                titulo.setText((usuario.nomeUsuario).toUpperCase());
                edit_dados.setOnClickListener(UserPerfilActivity.this);
                edit_password.setOnClickListener(UserPerfilActivity.this);
                usernameButton.setOnClickListener(UserPerfilActivity.this);
                emailButton.setOnClickListener(UserPerfilActivity.this);
                cpfButton.setOnClickListener(UserPerfilActivity.this);
                dddButton.setOnClickListener(UserPerfilActivity.this);
                stopLoadingDialog();
            }
        });
    }

    private void startLoadingDialog(){
        loginDialog = new LoadingDialog(UserPerfilActivity.this);
        loginDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loginDialog.show();
    }

    private void stopLoadingDialog(){
        loginDialog.dismiss();
    }
}
