package tcc.befree;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AnuncioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        
        int id = '1';//TEM QUE RECEBER AQUI O ID DO ITEM SELECIONADO
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncio);

        ApiModels conexao = new ApiModels();

        ((TextView)findViewById(getResources().getIdentifier("txtNome", "id", getPackageName()))).setText(conexao.getServicosById(id).idServico);
        ((TextView)findViewById(getResources().getIdentifier("txtDescricao", "id", getPackageName()))).setText(conexao.getServicosById(id).descricao);
//        ((TextView)findViewById(getResources().getIdentifier("txtCidade", "id", getPackageName()))).setText(conexao.getServicosById(id).);
//        ((TextView)findViewById(getResources().getIdentifier("txtCategoria", "id", getPackageName()))).setText(conexao.getServicosById(id).);
//        ((TextView)findViewById(getResources().getIdentifier("txtSubCategoria", "id", getPackageName()))).setText(conexao.getServicosById(id).);
    }
}
