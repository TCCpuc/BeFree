package tcc.befree;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import tcc.befree.models.Busca;

public class AnuncioBuscaActivity extends AppCompatActivity {

    private void setText(String campo, String valor){
        int busca = getResources().getIdentifier(campo, "id", getPackageName());
        TextView elemento = (TextView) findViewById(busca);
        elemento.setText(valor);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        int id = getIntent().getIntExtra("id",0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.newactivity_anuncio);

        ApiModels conexao = new ApiModels();

        Busca bsc = new Busca();
        bsc = conexao.getBuscaByID(id);

        try {
            setText("create_busca_txtNome", bsc.titulo);
            setText("create_busca_txtDescricao", bsc.descricao);
        }catch (Exception e){
            String erro = e.getMessage();
            Toast.makeText(this,erro,Toast.LENGTH_SHORT).show();
        }
    }
}
