package tcc.befree;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import tcc.befree.models.Busca;

/**
 * Created by guilherme.leme on 5/24/17.
 */

public class SearchFragment extends Fragment implements SearchAdapter.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_slide, container, false);


        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText("Busca");


        ArrayList<Busca> searchs = new ArrayList<>();

        Busca busca1 = new Busca();
        busca1.titulo = "Joao";
        busca1.idBusca = 1;
        busca1.descricao = "Preciso de um pintor";

        Busca busca2 = new Busca();
        busca2.titulo = "Pedro";
        busca2.idBusca = 2;
        busca2.descricao = "Preciso de um encanador";

        Busca busca3 = new Busca();
        busca3.titulo = "Tiago";
        busca3.idBusca = 3;
        busca3.descricao = "Preciso de um calheiro";

        Busca busca4 = new Busca();
        busca4.titulo = "Bruno";
        busca4.idBusca = 4;
        busca4.descricao = "Preciso de um computeiro";

        Busca busca5 = new Busca();
        busca5.titulo = "Maria";
        busca5.idBusca = 5;
        busca5.descricao = "Preciso de ajuda";

        searchs.add(busca1);
        searchs.add(busca2);
        searchs.add(busca3);
        searchs.add(busca4);
        searchs.add(busca5);


        SearchAdapter adapter = new SearchAdapter(getContext(), searchs, this);

        ListView ls = (ListView) rootView.findViewById(R.id.list);
        ls.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onClick(Busca busca) {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(getActivity(), AnuncioActivity.class);
        int id = busca.idBusca;
        intent.putExtra("id",id);
        startActivity(intent);
    }
}
