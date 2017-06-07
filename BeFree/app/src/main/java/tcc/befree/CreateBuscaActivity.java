package tcc.befree;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import tcc.befree.models.Categoria;
import tcc.befree.models.DDD;
import tcc.befree.models.SubCategoria;

import static tcc.befree.R.layout.activity_create_busca;

public class CreateBuscaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_create_busca);

        ApiModels conexao = new ApiModels();

        Spinner spinnerDDDs = (Spinner) findViewById(R.id.create_busca_spinnerDDD);
        ArrayAdapter<CharSequence>  arrayAdapterDDD = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, conexao.getDDDsVetor());
        arrayAdapterDDD.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerDDDs.setAdapter(arrayAdapterDDD);

        Spinner spinnerSubCategorias = (Spinner) findViewById(R.id.create_busca_spinnerSubCategoria);
        ArrayAdapter<CharSequence>  arrayAdapterSubCategoria = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, conexao.getSubCategoriasVetor());
        arrayAdapterSubCategoria.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerSubCategorias.setAdapter(arrayAdapterSubCategoria);

        Spinner spinnerCategorias = (Spinner) findViewById(R.id.create_busca_spinnerCategoria);
        ArrayAdapter<CharSequence>  arrayAdapterCategoria = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, conexao.getCategoriasVetor());
        arrayAdapterCategoria.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerCategorias.setAdapter(arrayAdapterCategoria);
    }
}
