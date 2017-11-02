package tcc.befree.telas.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import tcc.befree.R;
import tcc.befree.api.PutApiModels;
import tcc.befree.models.Busca;
import tcc.befree.models.CircleImageView;
import tcc.befree.models.Evento;
import tcc.befree.models.Servico;

/**
 * Created by guilherme.leme on 11/2/17.
 */

public class AnuncioDenunciaDialog  extends Dialog {

    private Activity c;
    private TextView title;
    private EditText descricao;
    private CircleImageView imagem;
    private Button accept;
    private Button back;
    private Servico servico;
    private Busca busca;

    public AnuncioDenunciaDialog(Activity a, Servico servico) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.servico = servico;
    }

    public AnuncioDenunciaDialog(Activity a, Busca busca) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.busca = busca;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_anuncio_denuncia);
        title = (TextView) findViewById(R.id.dialog_anuncio_denuncia_titulo);
        descricao = (EditText) findViewById(R.id.dialog_anuncio_denuncia_denuncia);
        imagem = (CircleImageView) findViewById(R.id.dialog_anuncio_denuncia_image);
        accept = (Button) findViewById(R.id.dialog_anuncio_denuncia_send);
        back = (Button) findViewById(R.id.dialog_anuncio_denuncia_back);

        if(busca != null){
            title.setText(busca.titulo);
            Picasso.with(c).load(busca.imagemBusca).into(imagem);
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }else{
            title.setText(servico.getTitulo());
            Picasso.with(c).load(servico.getImagemServico()).into(imagem);
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }


    }

    @Override
    public void onBackPressed() {
        dismiss();
    }
}