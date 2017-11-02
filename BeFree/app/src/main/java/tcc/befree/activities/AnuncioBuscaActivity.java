package tcc.befree.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import tcc.befree.api.PostApiModels;
import tcc.befree.models.Busca;
import tcc.befree.models.Categoria;
import tcc.befree.models.Chat;
import tcc.befree.models.SubCategoria;
import tcc.befree.telas.Dialog.AnuncioDenunciaDialog;

public class AnuncioBuscaActivity extends AppCompatActivity {

    private Bundle bundle;
    private ApiModels conexao;
    private Categoria categoria;
    private SubCategoria subCategoria;
    protected ImageView imgAnuncio;
    private TextView titulo;
    private TextView descricao;
    private TextView preco;
    private TextView formaPgto;
    private TextView categoriaESub;
    private FloatingActionButton contato;
    private FloatingActionButton agenda;
    private FloatingActionButton denuncia;
    private Busca srv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bundle = getIntent().getBundleExtra("bundle");
        final int id = bundle.getInt("id");
        final int idUsuarioAtual = bundle.getInt("idUsuario");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncio);
        imgAnuncio = (ImageView) findViewById(R.id.activity_anuncio_img_anuncio);
        agenda = (FloatingActionButton) findViewById(R.id.anuncio_gender);
        contato = (FloatingActionButton) findViewById(R.id.anuncio_contato);
        denuncia = (FloatingActionButton) findViewById(R.id.anuncio_denunciar);
        titulo = (TextView) findViewById(R.id.activity_anuncio_txtNome);
        descricao = (TextView) findViewById(R.id.activity_anuncio_txtDescricao);
        preco = (TextView) findViewById(R.id.activity_anuncio_preco);
        formaPgto = (TextView) findViewById(R.id.activity_anuncio_forma_pagamento);
        categoriaESub = (TextView) findViewById(R.id.activity_anuncio_categoria);

        conexao = new ApiModels();
        srv = new Busca();
        categoria = new Categoria();
        subCategoria = new SubCategoria();

        try {
            srv = conexao.getBuscaByID(id);
            Picasso.with(this).load(srv.imagemBusca).into(imgAnuncio);
            titulo.setText(srv.titulo);
            descricao.setText(srv.descricao);
            //preco.setText(srv.preco);
            //formaPgto.setText(srv.formaPgto);
            //categoriaESub.setText(srv.);
        }catch (Exception e){
            String erro = "Problema de conexao";
            Toast.makeText(this,erro,Toast.LENGTH_SHORT).show();
        }

        agenda.setVisibility(View.GONE);

        contato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiModels api = new ApiModels();
                int idAnunciante = api.getBuscaByID(id).idUsuario;
                Chat chat = new Chat();
                if (api.getChatJaExisteEntreOsUsuarios(idAnunciante,idUsuarioAtual)){
                    chat = api.getChatDosUsuarios(idAnunciante,idUsuarioAtual);
                }
                else{
                    PostApiModels postApi = new PostApiModels();
                    chat.setUsuario_1(idUsuarioAtual);
                    chat.setUsuario_2(idAnunciante);
                    do {
                        postApi.postChat(chat);
                        chat = api.getChatDosUsuarios(idUsuarioAtual, idAnunciante);
                    }
                    while (chat == null);
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
                Intent intent = new Intent(AnuncioBuscaActivity.this,MensagemActivity.class);
                intent.putExtra("bundleChat", bundleChat);
                startActivity(intent);
            }
        });
        denuncia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnuncioDenunciaDialog dialog = new AnuncioDenunciaDialog(AnuncioBuscaActivity.this, srv);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}
