package tcc.befree;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import tcc.befree.models.Busca;

public class CreateBuscaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_busca);

        //popula o spinner do ddd
        final Spinner spinnerDDDs = (Spinner) findViewById(R.id.create_busca_spinnerDDD);
        ArrayAdapter<CharSequence>  arrayAdapterDDD = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, new ApiModels().getDDDsVetor());
        arrayAdapterDDD.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerDDDs.setAdapter(arrayAdapterDDD);

        //popula o spinner de subcategoria
        final Spinner spinnerSubCategorias = (Spinner) findViewById(R.id.create_busca_spinnerCategoria);
        ArrayAdapter<CharSequence>  arrayAdapterSubCategoria = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, new ApiModels().getCategoriasVetor());
        arrayAdapterSubCategoria.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerSubCategorias.setAdapter(arrayAdapterSubCategoria);

        //popula o spinner de categoria
        final Spinner spinnerCategorias = (Spinner) findViewById(R.id.create_busca_spinnerSubCategoria);
        ArrayAdapter<CharSequence>  arrayAdapterCategoria = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, new ApiModels().getSubCategoriasVetor());
        arrayAdapterCategoria.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerCategorias.setAdapter(arrayAdapterCategoria);

        //Botão Submit
        Button submit = (Button) findViewById(R.id.create_busca_BtnSubmitBusca);
        submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
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
//                    novaServico.descricao = descricao;
//                    novaServico.titulo = nome;
//                    novaServico.ddd = ddd;
//                    novaServico.idSubCategoria = subCategoria;

                    new PostApiModels().postBusca(novaBusca);
                }
            }
        });
    }
}