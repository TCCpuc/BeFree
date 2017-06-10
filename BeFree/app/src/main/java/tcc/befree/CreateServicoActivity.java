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
        Spinner spinnerDDDs = (Spinner) findViewById(R.id.create_service_spinnerDDD);
        ArrayAdapter<CharSequence>  arrayAdapterDDD = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, new ApiModels().getDDDsVetor());
        arrayAdapterDDD.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerDDDs.setAdapter(arrayAdapterDDD);

        //popula o spinner de subcategoria
        Spinner spinnerSubCategorias = (Spinner) findViewById(R.id.create_service_spinnerCategoria);
        ArrayAdapter<CharSequence>  arrayAdapterSubCategoria = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, new ApiModels().getCategoriasVetor());
        arrayAdapterSubCategoria.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerSubCategorias.setAdapter(arrayAdapterSubCategoria);

        //popula o spinner de categoria
        Spinner spinnerCategorias = (Spinner) findViewById(R.id.create_service_spinnerSubCategoria);
        ArrayAdapter<CharSequence>  arrayAdapterCategoria = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, new ApiModels().getSubCategoriasVetor());
        arrayAdapterCategoria.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerCategorias.setAdapter(arrayAdapterCategoria);
    }
}
