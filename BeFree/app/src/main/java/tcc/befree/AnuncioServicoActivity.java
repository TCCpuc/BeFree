package tcc.befree;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import tcc.befree.models.Servico;

public class AnuncioServicoActivity extends AppCompatActivity {

    private void setText(String campo, String valor){
        int Servico = getResources().getIdentifier(campo, "id", getPackageName());
        TextView elemento = (TextView) findViewById(Servico);
        elemento.setText(valor);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        Bundle bundle = getIntent().getBundleExtra("bundle");
        int id = bundle.getInt("id");

            System.out.print("o ID eh " + id);

        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newactivity_anuncio);

        ApiModels conexao = new ApiModels();

        Servico srv = new Servico();
        srv = conexao.getServicosById(id);

        try {
            setText("create_busca_txtNome", srv.titulo);
            setText("create_busca_txtDescricao", srv.descricao);

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
