package tcc.befree.telas.listaDeBuscas;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import tcc.befree.R;
import tcc.befree.activities.AnuncioBuscaActivity;
import tcc.befree.activities.CreateBuscaActivity;
import tcc.befree.activities.CreateServicoActivity;
import tcc.befree.activities.EditBuscaActivity;
import tcc.befree.api.DeleteApiModels;
import tcc.befree.models.Busca;
import tcc.befree.models.CircleImageView;

/**
 * Created by guilherme.leme on 5/24/17.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {


    /*private LinearLayout padraoLayout;
    private LinearLayout editLayout;
    private ImageButton deleteButton;
    private ImageButton editButton;
    private RatingBar ratingBar;
    private TextView nota;
    private TextView title;
    private TextView description;
    private CircleImageView imgBusca;
    private boolean editable;*/
    private Context context;
    private boolean editable;
    private ArrayList<Busca> values;
    private int id;
    private int idUsuario;

    public SearchAdapter(Context context, ArrayList<Busca> values, boolean edit, int id, int idUsuario) {
        this.context = context;
        this.editable = edit;
        this.values = values;
        this.id = id;
        this.idUsuario = idUsuario;
        //this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_service, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        /*holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(busca);
            }
        });*/
        final Busca busca = values.get(position);


        Picasso.with(context).load(busca.imagemBusca).into(holder.imageView);
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            Intent intent = null;

            @Override
            public void onClick(View v) {
                // SUBIR UM BUNDLE COM AS INFORMAÇOES DO ANUNCIO
                intent = new Intent(context, EditBuscaActivity.class);
                intent.putExtra("idBusca", busca.idBusca);
                context.startActivity(intent);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                if (id == 0) {
                    Bundle bundle = new Bundle();
                    int id = busca.idBusca;
                    bundle.putInt("id", id);
                    bundle.putInt("idUsuario", idUsuario);
                    intent = new Intent(context, AnuncioBuscaActivity.class);
                    intent.putExtra("bundle", bundle);
                    context.startActivity(intent);
                } else {
                    intent = new Intent(context, EditBuscaActivity.class);
                    intent.putExtra("idBusca", busca.idBusca);
                    context.startActivity(intent);
                }
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete")
                        .setMessage("Você tem certeza que deseja deletar este anuncio?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new DeleteApiModels().deleteBuscaByID(busca.idBusca);
                                values.remove(busca);
                                notifyDataSetChanged();
                                Toast.makeText(context, "Busca excluida!!", Toast.LENGTH_LONG).show();
                            }

                        })
                        .setNegativeButton("Não", null)
                        .show();
            }
        });


        if (!editable) {
            holder.setVisibilityLayout(false);
        } else {
            holder.setVisibilityLayout(true);
        }

        holder.title.setText(busca.titulo);
        holder.description.setText(busca.descricao);

    }

    @Override
    public int getItemCount() {
        return this.values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout padraoLayout;
        private LinearLayout editLayout;
        private ImageButton deleteButton;
        private ImageButton editButton;
        private RatingBar ratingBar;
        private TextView nota;
        private TextView title;
        private TextView description;
        private CircleImageView imageView;

        public ViewHolder(@NonNull View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.item_service_title);
            this.description = (TextView) view.findViewById(R.id.item_service_description);
            this.nota = (TextView) view.findViewById(R.id.item_service_evaluation_note);
            this.imageView = (CircleImageView) view.findViewById(R.id.img_anuncio);
            this.deleteButton = (ImageButton) view.findViewById(R.id.item_service_delete);
            this.editButton = (ImageButton) view.findViewById(R.id.item_service_edit);
            this.padraoLayout = (LinearLayout) view.findViewById(R.id.item_service_default_layout);
            this.editLayout = (LinearLayout) view.findViewById(R.id.item_service_edit_layout);
            this.ratingBar = (RatingBar) view.findViewById(R.id.item_service_ratingBar);
        }

        public void setVisibilityLayout(boolean edit) {
            //LAYOUT PADRAO DE AVALIAÇÃO
            if (!edit) {
                padraoLayout.setVisibility(View.GONE);
                editLayout.setVisibility(View.GONE);
            } else {
                padraoLayout.setVisibility(View.GONE);
                editLayout.setVisibility(View.VISIBLE);
            }
        }
    }
}
