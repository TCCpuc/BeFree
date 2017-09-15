package tcc.befree.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import tcc.befree.api.ApiModels;
import tcc.befree.R;
import tcc.befree.models.Categoria;
import tcc.befree.models.Servico;
import tcc.befree.models.SubCategoria;

public class AnuncioServicoActivity extends AppCompatActivity {

    protected ImageView imgAnuncio;
    private FloatingActionButton contato;

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
        setContentView(R.layout.activity_anuncio);
        imgAnuncio = (ImageView) findViewById(R.id.newactivity_img_anuncio);
        contato = (FloatingActionButton) findViewById(R.id.anuncio_contato);
        contato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ApiModels conexao = new ApiModels();
        Servico srv = new Servico();
        Categoria categoria = new Categoria();
        SubCategoria subCategoria = new SubCategoria();

        try {

            srv = conexao.getServicosById(id);
            //subCategoria = conexao.getSubCategoriasByID(srv.idSubCategoria);
            //categoria = conexao.getCategoriaByID(subCategoria.idCategoria);

            //setText("newactivity_Categoria",categoria.descricao + " - " + subCategoria.descricao);
            setText("newactivity_txtNome", srv.titulo);
            setText("newactivity_txtDescricao", srv.descricao);
            Picasso.with(this).load(srv.imagemServico).into(imgAnuncio);


        }catch (Exception e){
            String erro = e.getMessage();
            Toast.makeText(this,erro,Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}
