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
import tcc.befree.models.CircleImageView;
import tcc.befree.models.Evento;

/**
 * Created by guilherme.leme on 10/28/17.
 */

public class GenderServiceDialog  extends Dialog {

    private Activity c;
    private Bitmap bitmapUsuarioPerfil;
    private static final int SELECT_FILE1 = 100;
    private TextView userName;
    private TextView serviceTitle;
    private CircleImageView imagem;
    private Button accept;
    private Button refuse;
    private Button ok;
    private Evento evento;

    public GenderServiceDialog(Activity a, Evento evento) {
        //SE CONTEXT = 1 (INSERT IMAGEM USUARIO)
        //SE CONTEXT = 2 (INSERT IMAGEM BUSCA)
        //SE CONTEXT = 3 (INSERT IMAGEM SERVICO)
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.evento = evento;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_gender_service);
        userName = (TextView) findViewById(R.id.dialog_gender_service_username);
        serviceTitle = (TextView) findViewById(R.id.dialog_gender_service_title);
        imagem = (CircleImageView) findViewById(R.id.dialog_gender_service_image);
        accept = (Button) findViewById(R.id.dialog_gender_service_accept);
        refuse = (Button) findViewById(R.id.dialog_gender_service_refuse);
        ok = (Button) findViewById(R.id.dialog_gender_service_ok);
        Picasso.with(c).load(evento.getImagem()).into(imagem);
        userName.setText(evento.getNomeUsuarioContratante());
        serviceTitle.setText(evento.getTitulo());
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        dismiss();
    }
}