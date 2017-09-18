package tcc.befree.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import tcc.befree.api.ApiModels;
import tcc.befree.R;
import tcc.befree.models.Busca;

public class AnuncioBuscaActivity extends AppCompatActivity {

    protected ImageView imgAnuncio;
    private FloatingActionButton contato;

    private void setText(String campo, String valor){
        int busca = getResources().getIdentifier(campo, "id", getPackageName());
        TextView elemento = (TextView) findViewById(busca);
        elemento.setText(valor);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle bundle = getIntent().getBundleExtra("bundle");
        int id = bundle.getInt("id");
        System.out.print("o ID eh " + id);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncio);

        contato = (FloatingActionButton) findViewById(R.id.anuncio_contato);
        contato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ApiModels conexao = new ApiModels();
        imgAnuncio = (ImageView) findViewById(R.id.newactivity_img_anuncio);
        Busca bsc = new Busca();
        try {

            bsc = conexao.getBuscaByID(id);
            //subCategoria = conexao.getSubCategoriasByID(srv.idSubCategoria);
            //categoria = conexao.getCategoriaByID(subCategoria.idCategoria);

            //setText("newactivity_Categoria",categoria.descricao + " - " + subCategoria.descricao);
            setText("newactivity_txtNome", bsc.titulo);
            setText("newactivity_txtDescricao", bsc.descricao);

            //imgAnuncio.setImageBitmap(BitmapFactory.decodeByteArray(Base64.decode(bsc.imagemBusca, Base64.DEFAULT), 0, bsc.imagemBusca.length()));

            Picasso.with(this).load(bsc.imagemBusca).into(imgAnuncio);

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
