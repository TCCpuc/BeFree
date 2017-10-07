package tcc.befree.telas.listaDeBuscas;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import tcc.befree.R;
import tcc.befree.activities.CreateBuscaActivity;
import tcc.befree.activities.CreateServicoActivity;
import tcc.befree.models.Busca;
import tcc.befree.models.CircleImageView;

/**
 * Created by guilherme.leme on 5/24/17.
 */

public class SearchAdapter extends ArrayAdapter<Busca> {

    private OnClickListener onClickListener;

    private LinearLayout padraoLayout;
    private LinearLayout editLayout;
    private ImageButton deleteButton;
    private ImageButton editButton;
    private boolean editable;

    public SearchAdapter(Context context, ArrayList<Busca> values, OnClickListener onClickListener, boolean edit) {
        super(context, 0, values);
        this.editable = edit;
        this.onClickListener = onClickListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Busca busca = getItem(position);

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_service, parent, false);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(busca);
            }
        });


        TextView title = (TextView) convertView.findViewById(R.id.item_service_title);
        CircleImageView imgBusca = (CircleImageView) convertView.findViewById(R.id.img_anuncio);
        TextView description = (TextView) convertView.findViewById(R.id.item_service_description);
        title.setText(busca.titulo);
        description.setText(busca.descricao);
        Picasso.with(getContext()).load(busca.imagemBusca).into(imgBusca);
        padraoLayout = (LinearLayout) convertView.findViewById(R.id.item_service_default_layout);
        editLayout = (LinearLayout) convertView.findViewById(R.id.item_service_edit_layout);
        deleteButton = (ImageButton) convertView.findViewById(R.id.item_service_delete);
        editButton = (ImageButton) convertView.findViewById(R.id.item_service_edit);

        editButton.setOnClickListener(new View.OnClickListener() {
            Intent intent = null;
            @Override
            public void onClick(View v) {
                // SUBIR UM BUNDLE COM AS INFORMAÇOES DO ANUNCIO
                intent = new Intent(getContext(), CreateBuscaActivity.class);
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
                                //COLOCAR AQUI O METODO DELETAR ANUNCIO API
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

        return convertView;

    }

    interface OnClickListener {
        public void onClick(Busca busca);
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
