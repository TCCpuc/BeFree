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

        ApiModels conexao = new ApiModels();

        Spinner spinnerSubCategorias = (Spinner) findViewById(R.id.selectSubCategoria);
        String[] vetorSubCategorias = conexao.getSubCategoriasVetor();
        ArrayAdapter<CharSequence>  arrayAdapterSubCategoria = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, vetorSubCategorias);
        arrayAdapterSubCategoria.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerSubCategorias.setAdapter(arrayAdapterSubCategoria);

        /*Spinner spinnerDDDs = (Spinner) findViewById(R.id.selectDDD);
        String[] vetorDDDs = conexao.getDDDsVetor();
        ArrayAdapter<CharSequence>  arrayAdapterDDD = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, vetorDDDs);
        arrayAdapterDDD.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerDDDs.setAdapter(arrayAdapterDDD);
*/
        /*Spinner spinnerCategorias = (Spinner) findViewById(R.id.selectCategoria);
        String[] vetorCategorias = conexao.getCategoriasVetor();
        ArrayAdapter<CharSequence>  arrayAdapterCategoria = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, vetorCategorias);
        arrayAdapterCategoria.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerCategorias.setAdapter(arrayAdapterCategoria);
        */

    }
}
