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

        //popula o spinner do ddd
        ApiModels conexao = new ApiModels();
        Spinner spinnerDDDs = (Spinner) findViewById(R.id.create_busca_spinnerDDD);
        String[] vetorDDDs = conexao.getDDDsVetor();
        ArrayAdapter<CharSequence>  arrayAdapterDDD = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, vetorDDDs);
        arrayAdapterDDD.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerDDDs.setAdapter(arrayAdapterDDD);

        //popula o spinner de subcategoria
        Spinner spinnerSubCategorias = (Spinner) findViewById(R.id.create_busca_spinnerCategoria);
        String[] vetorSubCategorias = conexao.getSubCategoriasVetor();
        ArrayAdapter<CharSequence>  arrayAdapterSubCategoria = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, vetorSubCategorias);
        arrayAdapterSubCategoria.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerSubCategorias.setAdapter(arrayAdapterSubCategoria);

        //popula o spinner de categoria
        Spinner spinnerCategorias = (Spinner) findViewById(R.id.create_busca_spinnerSubCategoria);
        String[] vetorCategorias = conexao.getCategoriasVetor();
        ArrayAdapter<CharSequence>  arrayAdapterCategoria = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, vetorCategorias);
        arrayAdapterCategoria.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerCategorias.setAdapter(arrayAdapterCategoria);
    }
}
