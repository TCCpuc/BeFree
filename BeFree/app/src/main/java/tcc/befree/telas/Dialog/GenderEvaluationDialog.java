package tcc.befree.telas.Dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import tcc.befree.R;
import tcc.befree.activities.GenderActivity;
import tcc.befree.api.PutApiModels;
import tcc.befree.models.CircleImageView;
import tcc.befree.models.Evento;

/**
 * Created by guilherme.leme on 10/31/17.
 */

public class GenderEvaluationDialog   extends Dialog {

    private GenderActivity c;
    private TextView userName;
    private TextView serviceTitle;
    private CircleImageView imagem;
    private TextView nota;
    private RatingBar notaStar;
    private Button accept;
    private Button refuse;
    private ImageButton up;
    private ImageButton down;
    private Evento evento;

    public GenderEvaluationDialog(GenderActivity a, Evento evento) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.evento = evento;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_gender_evaluation);
        userName = (TextView) findViewById(R.id.dialog_gender_evaluation_username);
        serviceTitle = (TextView) findViewById(R.id.dialog_gender_evaluation_title);
        imagem = (CircleImageView) findViewById(R.id.dialog_gender_evaluation_image);
        accept = (Button) findViewById(R.id.dialog_gender_evaluation_accept);
        refuse = (Button) findViewById(R.id.dialog_gender_evaluation_back);
        nota = (TextView) findViewById(R.id.dialog_gender_evaluation_text);
        notaStar = (RatingBar) findViewById(R.id.dialog_gender_evaluation_ratingBar);
        up = (ImageButton) findViewById(R.id.dialog_gender_evaluation_up);
        down = (ImageButton) findViewById(R.id.dialog_gender_evaluation_down);

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nota.getText().toString().equals("")){
                    nota.setText((0.5) + "");
                }else if(Float.parseFloat(nota.getText().toString()) >= 10 ){
                    nota.setText((10.0) + "");
                }else if(Float.parseFloat(nota.getText().toString()) > 9.5){
                    nota.setText((10.0) + "");
                }else {
                    nota.setText((Double.parseDouble(nota.getText().toString()) + 0.5) + "");
                }
                notaStar.setRating(Float.parseFloat(nota.getText().toString())/2);
            }
        });

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nota.getText().toString().equals("")){
                    nota.setText((0.0) + "");

                }else if(Float.parseFloat(nota.getText().toString()) <= 0 ){
                    nota.setText((0.0) + "");
                }else if(Float.parseFloat(nota.getText().toString()) < 0.5){
                    nota.setText((0.0) + "");
                }else {
                    nota.setText((Double.parseDouble(nota.getText().toString()) - 0.5) + "");
                }
                notaStar.setRating(Float.parseFloat(nota.getText().toString())/2);
            }
        });
        
        notaStar.setRating((float) 3.5);
        notaStar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                nota.setText((rating * 2) + "");
            }
        });
        nota.setHint((notaStar.getRating() * 2) + "");
        Picasso.with(c).load(evento.getImagem()).into(imagem);
        userName.setText(evento.getNomeUsuarioContratante());
        serviceTitle.setText(evento.getTitulo());
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    PutApiModels api = new PutApiModels();
                    evento.setNotaAvaliacao(Float.parseFloat(nota.getText().toString()));
                    evento.setAvaliado(true);
                    api.putEvento(evento);
                    c.setAdapter();
                    Toast.makeText(c.getApplicationContext(), evento.getDtEvento(), Toast.LENGTH_LONG).show();
                    dismiss();
                }catch (Exception e){
                    Toast.makeText(c.getApplicationContext(), "Escolha uma nota", Toast.LENGTH_LONG).show();
                }

            }
        });
        refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.setAdapter();
                dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        c.setAdapter();
        dismiss();
    }

}
