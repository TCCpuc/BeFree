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
        ArrayList<Categoria> categorias = conexao.getCategorias();
        ArrayList<SubCategoria> subcategorias = conexao.getSubCategorias();


        Spinner spinner = (Spinner) findViewById(R.id.selectCategoria);
        ArrayAdapter<CharSequence> arrayAdapter;
        String[] categoriasString = categorias.toArray(new String[categorias.size()]);
        arrayAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, categoriasString);
        arrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinner.setAdapter(arrayAdapter);
    }
}
