package tcc.befree;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import tcc.befree.models.Servico;

public class AnuncioActivity extends AppCompatActivity {

    private void setText(String campo, String valor){
        int busca = getResources().getIdentifier(campo, "id", getPackageName());
        TextView elemento = (TextView) findViewById(busca);
        elemento.setText(valor);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        int id = 1;//TEM QUE RECEBER AQUI O ID DO ITEM SELECIONADO

        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncio);

        ApiModels conexao = new ApiModels();

        Servico srv = new Servico();
        srv = conexao.getServicosById(id);

        try {
            setText("txtNome", (new Integer(srv.idServico)).toString());
            setText("txtDescricao", (String) srv.descricao);

    //     (TextView)findViewById(getResources().getIdentifier("txtCidade", "id", getPackageName()))).setText(conexao.getServicosById(id).);
//            setText("txtCidade", (String) srv.descricao);
////        ((TextView)findViewById(getResources().getIdentifier("txtCategoria", "id", getPackageName()))).setText(conexao.getServicosById(id).);
//            setText("txtCategoria", (String) srv.descricao);
////        ((TextView)findViewById(getResources().getIdentifier("txtSubCategoria", "id", getPackageName()))).setText(conexao.getServicosById(id).);
//            setText("txtSubCategoria", (String) srv.descricao);
        }catch (Exception e){
            String erro = e.getMessage();
            Toast.makeText(this,erro,Toast.LENGTH_SHORT).show();
        }
    }
}
