package tcc.befree.telas.listaDeBuscas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import tcc.befree.R;
import tcc.befree.models.Busca;

/**
 * Created by guilherme.leme on 5/24/17.
 */

public class SearchAdapter extends ArrayAdapter<Busca> {

    private OnClickListener onClickListener;

    public SearchAdapter(Context context, ArrayList<Busca> values, OnClickListener onClickListener) {
        super(context, 0, values);
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
        ImageView imgBusca = (ImageView) convertView.findViewById(R.id.img_anuncio);
        TextView description = (TextView) convertView.findViewById(R.id.item_service_description);

        title.setText(busca.titulo);
        description.setText(busca.descricao);
        Picasso.with(getContext()).load(busca.imagemBusca).into(imgBusca);

        return convertView;

    }

    interface OnClickListener {
        public void onClick(Busca busca);
    }


}
