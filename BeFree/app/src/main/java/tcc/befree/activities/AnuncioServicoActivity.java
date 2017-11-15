package tcc.befree.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import tcc.befree.api.PostApiModels;
import tcc.befree.models.Categoria;
import tcc.befree.models.Chat;
import tcc.befree.models.Servico;
import tcc.befree.models.SubCategoria;
import tcc.befree.telas.Dialog.AnuncioDenunciaDialog;
import tcc.befree.telas.Dialog.LoadingDialog;

public class AnuncioServicoActivity extends AppCompatActivity {
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
    private Servico srv;
    private LoadingDialog loginDialog;
    private int idServico;
    private int idUsuarioAtual;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        bundle = getIntent().getBundleExtra("bundle");
        idServico = bundle.getInt("id");
        idUsuarioAtual = bundle.getInt("idUsuario");
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
        srv = new Servico();
        categoria = new Categoria();
        subCategoria = new SubCategoria();

        startLoadingDialog();
        threadUpdate();

        agenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnuncioServicoActivity.this,CalendarActivity.class);
                intent.putExtra("idUsuario", idUsuarioAtual);
                intent.putExtra("idServico", idServico);
                startActivity(intent);
            }
        });

        contato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoadingDialog();
                threadContatoUpdate();
            }
        });
        denuncia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnuncioDenunciaDialog dialog = new AnuncioDenunciaDialog(AnuncioServicoActivity.this, srv, idUsuarioAtual);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        finish();
    }

    private void threadContatoUpdate(){
        new Thread(){
            @Override
            public void run() {
                int idAnunciante = conexao.getServicosById(idServico).getIdUsuario();
                Chat chat = new Chat();
                if (conexao.getChatJaExisteEntreOsUsuarios(idAnunciante,idUsuarioAtual)){
                    chat = conexao.getChatDosUsuarios(idAnunciante,idUsuarioAtual);
                }
                else{
                    PostApiModels postApi = new PostApiModels();
                    chat.setUsuario_1(idUsuarioAtual);
                    chat.setUsuario_2(idAnunciante);
                    do {
                        postApi.postChat(chat);
                        chat = conexao.getChatDosUsuarios(idUsuarioAtual, idAnunciante);
                    }
                    while (chat == null);
                }
                Bundle bundleChat = new Bundle();
                bundleChat.putInt("idChat", chat.getId());
                if(conexao.getUsuarioEUsuario1DoChat(chat.getId(), idUsuarioAtual)) {
                    bundleChat.putInt("idUsuarioOrigem", idUsuarioAtual);
                    bundleChat.putInt("isMe", 1);
                    bundleChat.putInt("idUsuarioDestino", chat.getUsuario_2());
                }
                else{
                    bundleChat.putInt("idUsuarioOrigem", idUsuarioAtual);
                    bundleChat.putInt("isMe", 2);
                    bundleChat.putInt("idUsuarioDestino", chat.getUsuario_1());
                }
                intent = new Intent(AnuncioServicoActivity.this,MensagemActivity.class);
                intent.putExtra("bundleChat", bundleChat);
                threadContatoUI();
            }
        }.start();
    }

    private void threadContatoUI(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                startActivity(intent);
                stopLoadingDialog();
            }
        });
    }

    private void threadUpdate(){
        new Thread(){
            @Override
            public void run() {
                srv = conexao.getServicosById(idServico);
                threadUI();
            }
        }.start();
    }

    private void threadUI(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Picasso.with(AnuncioServicoActivity.this).load(srv.getImagemServico()).into(imgAnuncio);
                    titulo.setText(srv.getTitulo());
                    categoriaESub.setText(srv.getDescCategoria() + " > " + srv.getDescSubCategoria());
                    descricao.setText(srv.getDescricao());
                    preco.setText(srv.getPreco() + "");
                    int pgto = srv.getFormaPgto();
                    switch (pgto){
                        case 0: formaPgto.setText("A Negociar");
                            break;
                        case 1: formaPgto.setText("A Vista");
                            break;
                        case 2: formaPgto.setText("2x");
                            break;
                        case 3: formaPgto.setText("3x");
                            break;
                        case 4: formaPgto.setText("4x");
                            break;
                        case 5: formaPgto.setText("6x");
                            break;
                        case 6: formaPgto.setText("12x");
                            break;
                        case 7: formaPgto.setText("24x");
                            break;
                        default: formaPgto.setText("A Negociar");
                            break;
                    }
                }catch (Exception e){
                    String erro = "Problema de conexao";
                    Toast.makeText(AnuncioServicoActivity.this,erro,Toast.LENGTH_SHORT).show();
                }
                stopLoadingDialog();
            }
        });
    }

    private void startLoadingDialog(){
        loginDialog = new LoadingDialog(AnuncioServicoActivity.this);
        loginDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loginDialog.show();
    }

    private void stopLoadingDialog(){
        loginDialog.dismiss();
    }
}
