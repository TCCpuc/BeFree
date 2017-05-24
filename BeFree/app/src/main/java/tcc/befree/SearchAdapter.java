package tcc.befree;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

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

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClick(busca);
                }
            });
        }

        TextView title = (TextView) convertView.findViewById(R.id.item_service_title);
        TextView description = (TextView) convertView.findViewById(R.id.item_service_description);

        title.setText(busca.titulo);
        description.setText(busca.descricao);

        return convertView;

    }

    interface OnClickListener {
        public void onClick(Busca busca);
    }


}
