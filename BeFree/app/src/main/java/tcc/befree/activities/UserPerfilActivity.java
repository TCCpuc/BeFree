package tcc.befree.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import tcc.befree.R;
import tcc.befree.models.CircleImageView;
import tcc.befree.telas.Dialog.InsertImageDialog;

public class UserPerfilActivity extends AppCompatActivity {


    private CircleImageView photo;
    private Button edit_dados;
    private Button edit_password;
    private TextView username;
    private TextView email;
    private TextView cpf;
    private TextView cidade;
    private TextView estado;
    private TextView bairro;
    private TextView logradouro;
    private TextView numero;
    private TextView cep;
    private TextView nascimento;
    private TextView ddd;
    private static final int SELECT_FILE1 = 100;
    private Bitmap bitmapUsuarioPerfil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_perfil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Minha conta");

        photo = (CircleImageView)findViewById(R.id.user_perfil_photo);
        edit_dados = (Button) findViewById(R.id.user_perfil_edit_data);
        edit_password = (Button) findViewById(R.id.user_perfil_edit_password);
        username = (TextView) findViewById(R.id.user_perfil_username);
        email = (TextView) findViewById(R.id.user_perfil_email);
        cpf = (TextView) findViewById(R.id.user_perfil_cpf);
        cidade = (TextView) findViewById(R.id.user_perfil_cidade);
        estado = (TextView) findViewById(R.id.user_perfil_estado);
        bairro = (TextView) findViewById(R.id.user_perfil_bairro);
        logradouro = (TextView) findViewById(R.id.user_perfil_logradouro);
        numero = (TextView) findViewById(R.id.user_perfil_numero);
        cep = (TextView) findViewById(R.id.user_perfil_cep);
        nascimento = (TextView) findViewById(R.id.user_perfil_nascimento);
        ddd = (TextView) findViewById(R.id.user_perfil_ddd);

        setTitle("[NOME DO USUARIO]");

        edit_dados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        edit_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        photo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                InsertImageDialog image = new InsertImageDialog(UserPerfilActivity.this, 1);
                image.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                image.show();
            }
        });


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
}
