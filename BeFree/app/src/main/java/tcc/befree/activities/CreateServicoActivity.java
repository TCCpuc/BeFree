package tcc.befree.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import tcc.befree.api.ApiModels;
import tcc.befree.api.PostApiModels;
import tcc.befree.R;
import tcc.befree.models.Categoria;
import tcc.befree.models.DDD;
import tcc.befree.models.Servico;
import tcc.befree.models.SubCategoria;

public class CreateServicoActivity extends AppCompatActivity {

    protected Spinner spinnerDDDs;
    protected Spinner spinnerCategorias;
    protected Spinner spinnerSubCategorias;
    protected View viewNome;
    protected EditText editNome;
    protected View viewDescricao;
    protected EditText editDescricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_servico);

        //popula o spinner do ddd
        spinnerDDDs = (Spinner) findViewById(R.id.create_service_spinnerDDD);
        ArrayAdapter arrayAdapterDDD = new ArrayAdapter(this, android.R.layout.simple_spinner_item, new ApiModels().getDDDsVetor());
        arrayAdapterDDD.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerDDDs.setAdapter(arrayAdapterDDD);

        //popula o spinner de categoria
        spinnerCategorias = (Spinner) findViewById(R.id.create_service_spinnerCategoria);
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
        Button submit = (Button) findViewById(R.id.create_service_btnSubmitService);
        submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                viewNome = findViewById(R.id.create_service_txtNome);
                editNome = (EditText) viewNome;
                viewDescricao = findViewById(R.id.create_service_txtDescricao);
                editDescricao = (EditText) viewDescricao;

                Servico novaServico = new Servico();
                String nome = editNome.getText().toString();
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
                    AlertDialog alertDialog = new AlertDialog.Builder(CreateServicoActivity.this).create();
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
                    novaServico.descricao = descricao;
                    novaServico.titulo = nome;
                    novaServico.idDDD = ((DDD)spinnerDDDs.getSelectedItem()).id;
                    novaServico.idSubCategoria = idSubCategoria;

                    new PostApiModels().postServico(novaServico);
                }
            }
        });
    }

    protected void  preencheSubCategoria(int idCategoria){

        //popula o spinner de subcategoria
        spinnerSubCategorias = (Spinner) findViewById(R.id.create_service_spinnerSubCategoria);
        ArrayAdapter arrayAdapterSubCategoria = new ArrayAdapter(this, android.R.layout.simple_spinner_item, new ApiModels().getSubCategoriasVetorByIdCategoria(idCategoria));
        arrayAdapterSubCategoria.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerSubCategorias.setAdapter(arrayAdapterSubCategoria);

    }
}
