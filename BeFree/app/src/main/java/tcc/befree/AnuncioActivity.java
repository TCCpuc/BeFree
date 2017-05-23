package tcc.befree;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import tcc.befree.models.Servico;

public class AnuncioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        
        int id = 1 ; //Estava Gravando '1' em um inteiro?
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncio);

        ApiModels conexao = new ApiModels();

        Servico srv = new Servico();
        srv = conexao.getServicosById(id);

        ((TextView)findViewById(getResources().getIdentifier("txtNome", "id", getPackageName()))).setText(srv.idServico);
        ((TextView)findViewById(getResources().getIdentifier("txtDescricao", "id", getPackageName()))).setText(srv.descricao);
//        ((TextView)findViewById(getResources().getIdentifier("txtCidade", "id", getPackageName()))).setText(conexao.getServicosById(id).);
//        ((TextView)findViewById(getResources().getIdentifier("txtCategoria", "id", getPackageName()))).setText(conexao.getServicosById(id).);
//        ((TextView)findViewById(getResources().getIdentifier("txtSubCategoria", "id", getPackageName()))).setText(conexao.getServicosById(id).);
    }
}
