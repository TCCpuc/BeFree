package tcc.befree;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import tcc.befree.models.Categoria;
import tcc.befree.models.SubCategoria;
import tcc.befree.models.DDD ;
import java.util.ArrayList;
import tcc.befree.ApiModels;

import java.lang.reflect.Array;

public class CreateServicoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_servico);

        //popula o spinner do ddd
        ApiModels conexao = new ApiModels();
        Spinner spinnerDDDs = (Spinner) findViewById(R.id.create_service_spinnerDDD);
        String[] vetorDDDs = conexao.getDDDsVetor();
        ArrayAdapter<CharSequence>  arrayAdapterDDD = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, vetorDDDs);
        arrayAdapterDDD.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerDDDs.setAdapter(arrayAdapterDDD);

        //popula o spinner de subcategoria
        Spinner spinnerSubCategorias = (Spinner) findViewById(R.id.create_service_spinnerCategoria);
        //String[] vetorSubCategorias = conexao.getDDDsVetor();
        ArrayAdapter<CharSequence>  arrayAdapterSubCategoria = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, vetorDDDs);
        arrayAdapterSubCategoria.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerSubCategorias.setAdapter(arrayAdapterSubCategoria);

        //popula o spinner de categoria
        Spinner spinnerCategorias = (Spinner) findViewById(R.id.create_service_spinnerSubCategoria);
        //String[] vetorCategorias = conexao.getDDDsVetor();
        ArrayAdapter<CharSequence>  arrayAdapterCategoria = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, vetorDDDs);
        arrayAdapterCategoria.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerCategorias.setAdapter(arrayAdapterCategoria);
    }
}
