package tcc.befree.telas.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import tcc.befree.R;
import tcc.befree.activities.GenderActivity;
import tcc.befree.api.PutApiModels;
import tcc.befree.models.CircleImageView;
import tcc.befree.models.Evento;

/**
 * Created by guilherme.leme on 10/28/17.
 */

public class GenderServiceDialog  extends Dialog {

    private GenderActivity c;
    private TextView userName;
    private TextView serviceTitle;
    private TextView serviceDescription;
    private CircleImageView imagem;
    private Button accept;
    private Button refuse;
    private Button ok;
    private Evento evento;
    private Boolean isUsuario;

    public GenderServiceDialog(GenderActivity a, Evento evento, Boolean isUsuario) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.evento = evento;
        this.isUsuario = isUsuario;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_gender_service);
        userName = (TextView) findViewById(R.id.dialog_gender_service_username);
        serviceTitle = (TextView) findViewById(R.id.dialog_gender_service_title);
        serviceDescription = (TextView) findViewById(R.id.dialog_gender_service_descricao);
        imagem = (CircleImageView) findViewById(R.id.dialog_gender_service_image);
        accept = (Button) findViewById(R.id.dialog_gender_service_accept);
        refuse = (Button) findViewById(R.id.dialog_gender_service_refuse);
        ok = (Button) findViewById(R.id.dialog_gender_service_ok);
        Picasso.with(c).load(evento.getImagem()).into(imagem);
        userName.setText(evento.getNomeUsuarioContratante());
        serviceTitle.setText(evento.getTitulo());
        serviceDescription.setText(evento.getConteudo());
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PutApiModels api = new PutApiModels();
                evento.setSituacaoEvento(1);
                api.putEvento(evento);
                c.setAdapter();
                dismiss();
            }
        });
        refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PutApiModels api = new PutApiModels();
                evento.setSituacaoEvento(2);
                api.putEvento(evento);
                c.setAdapter();
                dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        if(evento.getSituacaoEvento() != 0){
            accept.setVisibility(View.GONE);
            refuse.setVisibility(View.GONE);
            ok.setVisibility(View.VISIBLE);
        }else {
            if (isUsuario){
                accept.setVisibility(View.GONE);
                refuse.setVisibility(View.GONE);
                ok.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onBackPressed() {
        c.setAdapter();
        dismiss();
    }
}