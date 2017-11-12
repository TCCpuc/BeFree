package tcc.befree.telas.listaDeServicos;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import tcc.befree.R;
import tcc.befree.activities.AnuncioServicoActivity;
import tcc.befree.activities.EditServicoActivity;
import tcc.befree.activities.MainActivity;
import tcc.befree.api.DeleteApiModels;
import tcc.befree.models.CircleImageView;
import tcc.befree.models.Servico;

/**
 * Created by guilherme.leme on 5/24/17.
 */

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {

    private View.OnClickListener onClickListener;

    private Context context;
    private boolean editable;
    private ArrayList<Servico> values;
    private int id;
    private int idUsuario;

    public ServiceAdapter(Context context, ArrayList<Servico> values,  boolean edit, int id, int idUsuario) {
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
                onClickListener.onClick(servico);
            }
        });*/
        final Servico servico = values.get(position);


        holder.ratingBar.setRating(servico.getMediaAvaliacao() / 2);
        holder.nota.setText(servico.getMediaAvaliacao() + "");
        Picasso.with(context).load(servico.getImagemServico()).into(holder.imageView);
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            Intent intent = null;
            @Override
            public void onClick(View v) {
                // SUBIR UM BUNDLE COM AS INFORMAÇOES DO ANUNCIO
                intent = new Intent(context, EditServicoActivity.class);
                intent.putExtra("idServico", servico.getIdServico());
                context.startActivity(intent);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                if(id == 0){
                    Bundle bundle = new Bundle();
                    int id = servico.getIdServico();
                    bundle.putInt("id",id);
                    bundle.putInt("idUsuario",idUsuario);
                    intent = new Intent(context, AnuncioServicoActivity.class);
                    intent.putExtra("bundle", bundle);
                    context.startActivity(intent);
                }else{
                    intent = new Intent(context, EditServicoActivity.class);
                    intent.putExtra("idServico", servico.getIdServico());
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
                                new DeleteApiModels().deleteServicosById(servico.getIdServico());
                                values.remove(servico);
                                notifyDataSetChanged();
                                Toast.makeText(context, "Serviço excluido!!", Toast.LENGTH_LONG).show();
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

        holder.title.setText(servico.getTitulo());
        holder.description.setText(servico.getDescricao());

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
                padraoLayout.setVisibility(View.VISIBLE);
                editLayout.setVisibility(View.GONE);
            } else {
                padraoLayout.setVisibility(View.GONE);
                editLayout.setVisibility(View.VISIBLE);
            }
        }
    }
}



