package tcc.befree.telas.listaDeServicos;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.text.GetChars;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import tcc.befree.R;
import tcc.befree.activities.CreateServicoActivity;
import tcc.befree.activities.EditServicoActivity;
import tcc.befree.api.DeleteApiModels;
import tcc.befree.models.CircleImageView;
import tcc.befree.models.Servico;

/**
 * Created by guilherme.leme on 5/24/17.
 */

public class ServiceAdapter extends ArrayAdapter<Servico> {

    private OnClickListener onClickListener;

    private LinearLayout padraoLayout;
    private LinearLayout editLayout;
    private ImageButton deleteButton;
    private ImageButton editButton;
    private RatingBar ratingBar;
    private TextView nota;
    private TextView title;
    private TextView description;
    private CircleImageView imageView;
    private boolean editable;

    public ServiceAdapter(Context context, ArrayList<Servico> values, OnClickListener onClickListener, boolean edit) {
        super(context, 0, values);
        this.editable = edit;
        this.onClickListener = onClickListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Servico servico = getItem(position);

        if (convertView == null) {
            try {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_service, parent, false);
            }
            catch (Exception E){
                System.err.print("erro no inflate");
            }
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(servico);
            }
        });

        title = (TextView) convertView.findViewById(R.id.item_service_title);
        description = (TextView) convertView.findViewById(R.id.item_service_description);
        nota = (TextView) convertView.findViewById(R.id.item_service_evaluation_note);
        imageView = (CircleImageView) convertView.findViewById(R.id.img_anuncio);
        deleteButton = (ImageButton) convertView.findViewById(R.id.item_service_delete);
        editButton = (ImageButton) convertView.findViewById(R.id.item_service_edit);
        padraoLayout = (LinearLayout) convertView.findViewById(R.id.item_service_default_layout);
        editLayout = (LinearLayout) convertView.findViewById(R.id.item_service_edit_layout);
        ratingBar = (RatingBar) convertView.findViewById(R.id.item_service_ratingBar);

        ratingBar.setRating(servico.getMediaAvalicao() / 2);
        nota.setText(servico.getMediaAvalicao() + "");
        Picasso.with(getContext()).load(servico.getImagemServico()).into(imageView);
        editButton.setOnClickListener(new View.OnClickListener() {
            Intent intent = null;
            @Override
            public void onClick(View v) {
                // SUBIR UM BUNDLE COM AS INFORMAÇOES DO ANUNCIO
                intent = new Intent(getContext(), EditServicoActivity.class);
                intent.putExtra("idServico", servico.getIdServico());
                getContext().startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete")
                        .setMessage("Você tem certeza que deseja deletar este anuncio?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new DeleteApiModels().deleteServicosById(servico.getIdServico());
                            }

                        })
                        .setNegativeButton("Não", null)
                        .show();
            }
        });


        if(!editable){
            setVisibilityLayout(false);
        }else {
            setVisibilityLayout(true);
        }

        title.setText(servico.getTitulo());
        description.setText(servico.getDescricao());

        return convertView;

    }

    interface OnClickListener {
        public void onClick(Servico servico);
    }

    public void setVisibilityLayout(boolean edit){
        //LAYOUT PADRAO DE AVALIAÇÃO
        if(!edit){
            padraoLayout.setVisibility(View.VISIBLE);
            editLayout.setVisibility(View.GONE);
        }else {
            padraoLayout.setVisibility(View.GONE);
            editLayout.setVisibility(View.VISIBLE);
        }
    }
}



