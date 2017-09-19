package tcc.befree.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import tcc.befree.api.ApiModels;
import tcc.befree.R;
import tcc.befree.api.PostApiModels;
import tcc.befree.models.Categoria;
import tcc.befree.models.Chat;
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
        
        Bundle bundle = getIntent().getBundleExtra("idUsuario");
        bundle = getIntent().getBundleExtra("bundle");
        final int id = bundle.getInt("id");
        final int idUsuarioAtual = bundle.getInt("idUsuario");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncio);
        imgAnuncio = (ImageView) findViewById(R.id.newactivity_img_anuncio);
        contato = (FloatingActionButton) findViewById(R.id.anuncio_contato);
        contato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiModels api = new ApiModels();
                int idAnunciante = api.getServicosById(id).idUsuario;
                Chat chat = new Chat();
                if (api.getChatJaExisteEntreOsUsuarios(idAnunciante,idUsuarioAtual)){
                    chat = api.getChatDosUsuarios(idAnunciante,idUsuarioAtual);
                }
                else{
                    PostApiModels postApi = new PostApiModels();
                    chat.setUsuario_1(idUsuarioAtual);
                    chat.setUsuario_2(idAnunciante);
                    postApi.postChat(chat);
                }
                Bundle bundleChat = new Bundle();
                bundleChat.putInt("idChat", chat.getId());
                if(api.getUsuarioEUsuario1DoChat(chat.getId(), idUsuarioAtual)) {
                    bundleChat.putInt("idUsuarioOrigem", idUsuarioAtual);
                    bundleChat.putInt("isMe", 1);
                    bundleChat.putInt("idUsuarioDestino", chat.getUsuario_2());
                }
                else{
                    bundleChat.putInt("idUsuarioOrigem", idUsuarioAtual);
                    bundleChat.putInt("isMe", 2);
                    bundleChat.putInt("idUsuarioDestino", chat.getUsuario_1());
                }
                Intent intent = new Intent(AnuncioServicoActivity.this,MensagemActivity.class);
                intent.putExtra("bundleChat", bundleChat);
                startActivity(intent);
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
