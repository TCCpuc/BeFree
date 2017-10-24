package tcc.befree.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
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
import tcc.befree.api.PutApiModels;
import tcc.befree.models.Busca;
import tcc.befree.models.Categoria;
import tcc.befree.models.Servico;
import tcc.befree.models.SubCategoria;
import tcc.befree.telas.Dialog.InsertImageDialog;
import tcc.befree.utils.Utils;

/**
 * Created by guilherme.leme on 10/17/17.
 */

public class EditBuscaActivity extends AppCompatActivity {

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
    private int idBusca;
    private Busca busca;
    private SubCategoria subCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_busca);

        Intent it = this.getIntent();
        Bundle loginActivityIntent = it.getExtras();
        idBusca = loginActivityIntent.getInt("idBusca");
        ApiModels api = new ApiModels();
        busca = api.getBuscaByID(idBusca);
        subCategoria = new ApiModels().getSubCategoriasByID(busca.idSubCategoria);

        spinnerDDDs = (Spinner) findViewById(R.id.create_busca_spinnerDDD);
        photo = (ImageView) findViewById(R.id.create_busca_unounce_photo);
        viewNome = findViewById(R.id.create_busca_titulo);
        viewDescricao = findViewById(R.id.create_busca_txtDescricao);
        submit = (Button) findViewById(R.id.create_busca_BtnSubmitBusca);
        spinnerCategorias = (Spinner) findViewById(R.id.create_busca_spinnerCategoria);
        spinnerSubCategorias = (Spinner) findViewById(R.id.create_busca_spinnerSubCategoria);
        title = (TextView) findViewById(R.id.create_busca_title);
        editDescricao = (EditText) findViewById(R.id.create_busca_txtDescricao);


        //popula o spinner do ddd

        ArrayAdapter arrayAdapterDDD = new ArrayAdapter(this, android.R.layout.simple_spinner_item, new ApiModels().getDDDsVetor());
        arrayAdapterDDD.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDDDs.setAdapter(arrayAdapterDDD);
        if (busca.idDDD > 16)
            spinnerDDDs.setSelection(busca.idDDD - 13);
        else
            spinnerDDDs.setSelection(busca.idDDD - 12);

        //popula o spinner de categoria

        ArrayAdapter arrayAdapterCategoria = new ArrayAdapter(this, android.R.layout.simple_spinner_item, new ApiModels().getCategoriasVetor());
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

        Picasso.with(this).load(busca.imagemBusca).into(photo);
        ((EditText) viewNome).setText(busca.titulo);
        ((EditText) viewDescricao).setText(busca.descricao);
        submit.setText("Editar Busca");
        title.setText(busca.titulo);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Busca buscaAlterada = busca;
                if (spinnerDDDs.getSelectedItemPosition() > 5)
                    buscaAlterada.idDDD  = spinnerDDDs.getSelectedItemPosition() + 13;
                else
                    buscaAlterada.idDDD = spinnerDDDs.getSelectedItemPosition() + 12;
                buscaAlterada.descricao = editDescricao.getText().toString();
                buscaAlterada.titulo = ((EditText) viewNome).getText().toString();
                if(bitmapUsuarioPerfil!= null)
                    buscaAlterada.imagemBusca = Utils.convert(bitmapUsuarioPerfil);
                int idSubCategoria = ((SubCategoria)spinnerSubCategorias.getSelectedItem()).idSubCategoria;
                buscaAlterada.idSubCategoria = idSubCategoria;
                new PutApiModels().putBusca(buscaAlterada);
                Toast toast = Toast.makeText(getApplicationContext(), "Servico editado com sucesso!", Toast.LENGTH_LONG);
                toast.show();
                onBackPressed();
            }
        });


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
                    Toast toast = Toast.makeText(getApplicationContext(), "Servico criado com sucesso!", Toast.LENGTH_LONG);
                    toast.show();
                    EditServicoActivity.super.onBackPressed();
                }
            }
        });*/

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertImageDialog image = new InsertImageDialog(EditBuscaActivity.this);
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