package tcc.befree;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import tcc.befree.models.Busca;
import tcc.befree.models.Categoria;
import tcc.befree.models.SubCategoria;

public class CreateBuscaActivity extends AppCompatActivity {

    private Spinner spinnerDDDs;
    private Spinner spinnerCategorias;
    private Spinner spinnerSubCategorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_busca);

        //popula o spinner do ddd
        spinnerDDDs = (Spinner) findViewById(R.id.create_busca_spinnerDDD);
        ArrayAdapter arrayAdapterDDD = new ArrayAdapter(this, android.R.layout.simple_spinner_item, new ApiModels().getDDDsVetor());
        arrayAdapterDDD.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerDDDs.setAdapter(arrayAdapterDDD);

        //popula o spinner de categoria
        spinnerCategorias = (Spinner) findViewById(R.id.create_busca_spinnerCategoria);
        ArrayAdapter arrayAdapterCategoria = new ArrayAdapter(this, android.R.layout.simple_spinner_item, new ApiModels().getCategoriasVetor());
        arrayAdapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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

                View viewNome = findViewById(R.id.create_busca_txtNome);
                EditText editNome = (EditText) viewNome;
                String nome = editNome.getText().toString();

                View viewDescricao = findViewById(R.id.create_busca_txtDescricao);
                EditText editDescricao = (EditText) viewDescricao;
                String descricao = editDescricao.getText().toString();

                String ddd = spinnerDDDs.getSelectedItem().toString();

                String subCategoria = spinnerSubCategorias.getSelectedItem().toString();

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
                    novaBusca.descricao = descricao;
                    novaBusca.titulo = nome;
                    novaBusca.idDDD = new ApiModels().getDDDByCodigo(ddd).id;
                    novaBusca.idSubCategoria = new ApiModels().getSubCategoriaByNome(subCategoria).idSubCategoria;

                    new PostApiModels().postBusca(novaBusca);

                    Toast toast = Toast.makeText(getApplicationContext(), "Busca criada com sucesso!", Toast.LENGTH_LONG);
                    toast.show();
                    CreateBuscaActivity.super.onBackPressed();
                }
            }
        });
    }

    protected void preencheSubCategoria(int idCategoria){

        //popula o spinner de subcategoria
        spinnerSubCategorias = (Spinner) findViewById(R.id.create_busca_spinnerSubCategoria);
        ArrayAdapter arrayAdapterSubCategoria = new ArrayAdapter(this, android.R.layout.simple_spinner_item, new ApiModels().getSubCategoriasVetorByIdCategoria(idCategoria));
        arrayAdapterSubCategoria.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerSubCategorias.setAdapter(arrayAdapterSubCategoria);
    }

}