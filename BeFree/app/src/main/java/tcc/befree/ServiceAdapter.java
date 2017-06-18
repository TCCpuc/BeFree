package tcc.befree;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import tcc.befree.models.Servico;

/**
 * Created by guilherme.leme on 5/24/17.
 */

public class ServiceAdapter extends ArrayAdapter<Servico> {

    private OnClickListener onClickListener;

    public ServiceAdapter(Context context, ArrayList<Servico> values, OnClickListener onClickListener) {
        super(context, 0, values);
        this.onClickListener = onClickListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Servico servico = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_service, parent, false);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClick(servico);
                }
            });
        }

        TextView title = (TextView) convertView.findViewById(R.id.item_service_title);
        TextView description = (TextView) convertView.findViewById(R.id.item_service_description);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.img_anuncio);
        Picasso.with(getContext()).load(servico.imagemServico).into(imageView);

        title.setText(servico.titulo);
        description.setText(servico.descricao);

        return convertView;

    }

    interface OnClickListener {
        public void onClick(Servico servico);
    }
}



