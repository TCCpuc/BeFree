package tcc.befree;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import tcc.befree.models.Servico;

public class CreateServicoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_servico);

        ApiModels conexao = new ApiModels();


        /*
        Spinner spinnerSubCategorias = (Spinner) findViewById(R.id.selectSubCategoria);
        String[] vetorSubCategorias = conexao.getSubCategoriasVetor();
        ArrayAdapter<CharSequence>  arrayAdapterSubCategoria = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, vetorSubCategorias);
        arrayAdapterSubCategoria.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerSubCategorias.setAdapter(arrayAdapterSubCategoria);

        Spinner spinnerDDDs = (Spinner) findViewById(R.id.selectDDD);
        String[] vetorDDDs = conexao.getDDDsVetor();
        ArrayAdapter<CharSequence>  arrayAdapterDDD = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, vetorDDDs);
        arrayAdapterDDD.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerDDDs.setAdapter(arrayAdapterDDD);



        Spinner spinnerCategorias = (Spinner) findViewById(R.id.selectCategoria);
        String[] vetorCategorias = conexao.getCategoriasVetor();
        ArrayAdapter<CharSequence>  arrayAdapterCategoria = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, vetorCategorias);
        */
        Button submit = (Button) findViewById(R.id.btnSubmitService);
        submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Servico novaServico = new Servico();
                //validar se esta tudo preenchido
                //popular
                new PostApiModels().postServico(novaServico);
            }
        });

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
