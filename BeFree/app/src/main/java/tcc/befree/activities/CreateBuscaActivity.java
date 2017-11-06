package tcc.befree.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import tcc.befree.api.ApiModels;
import tcc.befree.api.PostApiModels;
import tcc.befree.R;
import tcc.befree.models.Busca;
import tcc.befree.models.Categoria;
import tcc.befree.models.CircleImageView;
import tcc.befree.models.DDD;
import tcc.befree.models.SubCategoria;
import tcc.befree.telas.Dialog.InsertImageDialog;
import tcc.befree.utils.Utils;

public class CreateBuscaActivity extends AppCompatActivity {

    private Spinner spinnerDDDs;
    private Spinner spinnerCategorias;
    protected Spinner spinnerFormaPgto;
    protected EditText valor;
    private Spinner spinnerSubCategorias;
    private ImageView photo;
    private static final int SELECT_FILE1 = 100;
    private Bitmap bitmapUsuarioPerfil;
    private int idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_busca);


        Bundle bundle = getIntent().getBundleExtra("idUsuario");
        try {
            this.idUsuario = bundle.getInt("idUsuario");
        }catch(Exception e){
            this.idUsuario = 0;
        }

        valor = (EditText) findViewById(R.id.create_busca_valor);
        spinnerFormaPgto = (Spinner) findViewById(R.id.create_busca_spinnerFormaPgto);

        //popula o spinner do ddd
        spinnerDDDs = (Spinner) findViewById(R.id.create_busca_spinnerDDD);
        photo = (ImageView) findViewById(R.id.create_busca_unounce_photo);
        ArrayAdapter arrayAdapterDDD = new ArrayAdapter(this, android.R.layout.simple_spinner_item, new ApiModels().getDDDsVetor());
        arrayAdapterDDD.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerDDDs.setAdapter(arrayAdapterDDD);

        //popula o spinner de categoria
        spinnerCategorias = (Spinner) findViewById(R.id.create_busca_spinnerCategoria);
        ArrayAdapter arrayAdapterCategoria = new ArrayAdapter(this, android.R.layout.simple_spinner_item, new ApiModels().getCategoriasVetor());
        arrayAdapterCategoria.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerCategorias.setAdapter(arrayAdapterCategoria);
        spinnerCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                int idCategoria = ((Categoria)parent.getItemAtPosition(position)).idCategoria;
                preencheSubCategoria(idCategoria);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


        //Botão Submit
        Button submit = (Button) findViewById(R.id.create_busca_BtnSubmitBusca);
        submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO POPULAR SPINNER SUBCATEGORIA SÓ COM AS CATEGORIAS CERTAS

                Busca novaBusca = new Busca();

                View viewNome = findViewById(R.id.create_busca_titulo);
                EditText editNome = (EditText) viewNome;
                String nome = editNome.getText().toString();

                View viewDescricao = findViewById(R.id.create_busca_txtDescricao);
                EditText editDescricao = (EditText) viewDescricao;
                String descricao = editDescricao.getText().toString();

                String ddd = spinnerDDDs.getSelectedItem().toString();
                String subCategoria =  spinnerSubCategorias.getSelectedItem().toString();
                int idSubCategoria = ((SubCategoria)spinnerSubCategorias.getSelectedItem()).idSubCategoria;
                String categoria = spinnerCategorias.getSelectedItem().toString();

                if(ddd ==null
                        || "".equals(subCategoria)
                        || "".equals(categoria)
                        || "".equals(nome)
                        || "".equals(descricao))
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(CreateBuscaActivity.this).create();
                    alertDialog.setTitle("Todos os campos são obrigatórios");
                    alertDialog.setMessage("Por favor, preencha todos os campos");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
                else {
                    novaBusca.setFormaPgto(spinnerFormaPgto.getSelectedItem().toString());
                    if(valor.getText().toString().equals("")){
                        novaBusca.setPreco(0);
                    }else {
                        novaBusca.setPreco(Float.parseFloat(valor.getText().toString()));
                    }
                    novaBusca.descricao = descricao;
                    novaBusca.titulo = nome;
                    novaBusca.idDDD = ((DDD)spinnerDDDs.getSelectedItem()).id;
                    novaBusca.idSubCategoria = idSubCategoria;
                    novaBusca.idUsuario = idUsuario;
                    novaBusca.imagemBusca = getImagem();
                    novaBusca.idUsuario = idUsuario;
                    try {
                        novaBusca.imagemBusca = Utils.convert(bitmapUsuarioPerfil);
                    }catch(Exception e){
                        novaBusca.imagemBusca = "";
                    }

                    new PostApiModels().postBusca(novaBusca);

                    Toast toast = Toast.makeText(getApplicationContext(), "Busca criada com sucesso!", Toast.LENGTH_LONG);
                    toast.show();
                    CreateBuscaActivity.super.onBackPressed();
                }
            }
        });
        photo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                InsertImageDialog image = new InsertImageDialog(CreateBuscaActivity.this);
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

    private String getImagem() {
        byte[] bytes, buffer = new byte[8192];
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            InputStream inputStream = getResources().openRawResource(+ R.drawable.teste);

            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        bytes = output.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    protected void preencheSubCategoria(int idCategoria){

        //popula o spinner de subcategoria
        spinnerSubCategorias = (Spinner) findViewById(R.id.create_busca_spinnerSubCategoria);
        ArrayAdapter arrayAdapterSubCategoria = new ArrayAdapter(this, android.R.layout.simple_spinner_item, new ApiModels().getSubCategoriasVetorByIdCategoria(idCategoria));
        arrayAdapterSubCategoria.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerSubCategorias.setAdapter(arrayAdapterSubCategoria);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}