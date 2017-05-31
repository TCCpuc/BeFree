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

        ArrayList<DDD> ddds = conexao.getDDDs();
        Spinner spinnerDDDs = (Spinner) findViewById(R.id.selectDDD);
        String[] dddsString = ddds.toArray(new String[ddds.size()]);
        ArrayAdapter<CharSequence>  arrayAdapterDDD = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, dddsString);
        arrayAdapterDDD.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerDDDs.setAdapter(arrayAdapterDDD);

        ArrayList<SubCategoria> subCategorias = conexao.getSubCategorias();
        Spinner spinnerSubCategorias = (Spinner) findViewById(R.id.selectSubCategoria);
        String[] subCategoriasString = subCategorias.toArray(new String[subCategorias.size()]);
        ArrayAdapter<CharSequence>  arrayAdapterSubCategoria = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, subCategoriasString);
        arrayAdapterSubCategoria.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerSubCategorias.setAdapter(arrayAdapterSubCategoria);

        ArrayList<Categoria> categorias = conexao.getCategorias();
        Spinner spinnerCategorias = (Spinner) findViewById(R.id.selectCategoria);
        String[] categoriasString = categorias.toArray(new String[categorias.size()]);
        ArrayAdapter<CharSequence>  arrayAdapterCategoria = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, categoriasString);
        arrayAdapterCategoria.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerCategorias.setAdapter(arrayAdapterCategoria);
    }
}
