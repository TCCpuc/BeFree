package tcc.befree.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import tcc.befree.R;
import tcc.befree.api.ApiModels;
import tcc.befree.api.PostApiModels;
import tcc.befree.api.PutApiModels;
import tcc.befree.models.Categoria;
import tcc.befree.models.DDD;
import tcc.befree.models.Servico;
import tcc.befree.models.SubCategoria;
import tcc.befree.telas.Dialog.InsertImageDialog;
import tcc.befree.utils.Utils;

/**
 * Created by guilherme.leme on 10/16/17.
 */

public class EditServicoActivity extends AppCompatActivity {

    protected Spinner spinnerDDDs;
    protected Spinner spinnerCategorias;
    protected Spinner spinnerSubCategorias;
    private Button submit;
    private TextView title;
    protected View viewNome;
    protected EditText editNome;
    protected View viewDescricao;
    protected EditText editDescricao;
    private ImageView photo;
    private static final int SELECT_FILE1 = 100;
    private Bitmap bitmapUsuarioPerfil;
    private int idServico;
    private Servico servico;
    private SubCategoria subCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_servico);

        Intent it = this.getIntent();
        Bundle loginActivityIntent = it.getExtras();
        idServico = loginActivityIntent.getInt("idServico");
        ApiModels api = new ApiModels();
        servico = api.getServicosById(idServico);
        subCategoria = new ApiModels().getSubCategoriasByID(servico.getIdSubCategoria());

        spinnerDDDs = (Spinner) findViewById(R.id.create_servico_spinnerDDD);
        photo = (ImageView) findViewById(R.id.create_servico_unounce_photo);
        viewNome = findViewById(R.id.create_servico_titulo);
        viewDescricao = findViewById(R.id.create_servico_txtDescricao);
        submit = (Button) findViewById(R.id.create_servico_BtnSubmitServico);
        spinnerCategorias = (Spinner) findViewById(R.id.create_servico_spinnerCategoria);
        spinnerSubCategorias = (Spinner) findViewById(R.id.create_servico_spinnerSubCategoria);
        title = (TextView) findViewById(R.id.create_servico_title);
        editDescricao = (EditText) findViewById(R.id.create_servico_txtDescricao);

        //popula o spinner do ddd
        ArrayAdapter arrayAdapterDDD = new ArrayAdapter(this, android.R.layout.simple_spinner_item, new ApiModels().getDDDsVetor());
        arrayAdapterDDD.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDDDs.setAdapter(arrayAdapterDDD);
        spinnerDDDs.setId(servico.getIdDDD());
        spinnerDDDs.setSelection(servico.getIdDDD() - 1);
//        if (servico.idDDD > 16)
//            spinnerDDDs.setSelection(servico.idDDD - 13);
//        else
//            spinnerDDDs.setSelection(servico.idDDD - 12);

        //popula o spinner de categoria
        Categoria[] categorias = new ApiModels().getCategoriasVetor();
        ArrayAdapter arrayAdapterCategoria = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categorias);
        arrayAdapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategorias.setAdapter(arrayAdapterCategoria);
//        Categoria categoria = null;
//        for (Categoria c : categorias) {
//            if (c.idCategoria == subCategoria.idCategoria) {
//                categoria = c;
//                break;
//            }
//        }
//        if (!categoria.descricao.equals(null)) {
//            int spinnerPosition = arrayAdapterCategoria.getPosition(categoria.descricao);
            spinnerCategorias.setSelection(subCategoria.idCategoria - 1);
//        }



        //popula restante

        Picasso.with(this).load(servico.getImagemServico()).into(photo);
        ((EditText) viewNome).setText(servico.getTitulo());
        ((EditText) viewDescricao).setText(servico.getDescricao());
        submit.setText("Editar Anuncio");
        title.setText(servico.getTitulo());

        spinnerCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                int idCategoria = ((Categoria) parent.getItemAtPosition(position)).idCategoria;
                preencheSubCategoria(idCategoria);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });





        //Botão Submit

        /*submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Servico novaServico = new Servico();
                String nome = editNome.getText().toString();
                String descricao = editDescricao.getText().toString();

                String ddd = spinnerDDDs.getSelectedItem().toString();
                String subCategoria = spinnerSubCategorias.getSelectedItem().toString();
                int idSubCategoria = ((SubCategoria) spinnerSubCategorias.getSelectedItem()).idSubCategoria;
                String categoria = spinnerCategorias.getSelectedItem().toString();

                if (ddd == null
                        || "".equals(subCategoria)
                        || "".equals(categoria)
                        || "".equals(nome)
                        || "".equals(descricao)) {
                    AlertDialog alertDialog = new AlertDialog.Builder(EditServicoActivity.this).create();
                    alertDialog.setTitle("Todos os campos são obrigatórios");
                    alertDialog.setMessage("Por favor, preencha todos os campos");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                } else {
                    novaServico.descricao = descricao;
                    novaServico.titulo = nome;
                    novaServico.idDDD = ((DDD) spinnerDDDs.getSelectedItem()).id;
                    novaServico.idSubCategoria = idSubCategoria;
                    novaServico.idUsuario = idUsuario;
                    try {
                        novaServico.imagemServico = Utils.convert(bitmapUsuarioPerfil);
                    } catch (Exception e) {
                        novaServico.imagemServico = "";
                    }
                    new PostApiModels().postServico(novaServico);

                }
            }
        });*/

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Servico servicoAlterado = servico;
                servicoAlterado.setIdDDD(spinnerDDDs.getSelectedItemPosition() + 1);
//                if (spinnerDDDs.getSelectedItemPosition() > 5)
//                    servicoAlterado.idDDD  = spinnerDDDs.getSelectedItemPosition() + 13;
//                else
//                    servicoAlterado.idDDD = spinnerDDDs.getSelectedItemPosition() + 12;
                servicoAlterado.setDescricao(editDescricao.getText().toString());
                servicoAlterado.setTitulo(((EditText) viewNome).getText().toString());
                if(bitmapUsuarioPerfil!= null)
                    servicoAlterado.setImagemServico(Utils.convert(bitmapUsuarioPerfil));
                int idSubCategoria = ((SubCategoria)spinnerSubCategorias.getSelectedItem()).idSubCategoria;
                servicoAlterado.setIdSubCategoria(idSubCategoria);
                new PutApiModels().putServico(servicoAlterado);
                Toast toast = Toast.makeText(getApplicationContext(), "Servico editado com sucesso!", Toast.LENGTH_LONG);
                toast.show();
                onBackPressed();
            }
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertImageDialog image = new InsertImageDialog(EditServicoActivity.this);
                image.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                image.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == SELECT_FILE1) {
            Uri selectedImageUri = data.getData();

            try {
                bitmapUsuarioPerfil = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                Picasso.with(this.getApplicationContext()).load(selectedImageUri).into(photo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //private method of your class
    private int getIndex(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }

    protected void preencheSubCategoria(int idCategoria) {
        //popula o spinner de subcategoria - não está populando certo
        ArrayAdapter arrayAdapterSubCategoria = new ArrayAdapter(this, android.R.layout.simple_spinner_item, new ApiModels().getSubCategoriasVetorByIdCategoria(idCategoria));
        arrayAdapterSubCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSubCategorias.setAdapter(arrayAdapterSubCategoria);
//        if (!subCategoria.descricao.equals(null)) {
            int spinnerPosition = getIndex(spinnerSubCategorias, subCategoria.descricao);
            spinnerSubCategorias.setSelection(spinnerPosition);
//        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}