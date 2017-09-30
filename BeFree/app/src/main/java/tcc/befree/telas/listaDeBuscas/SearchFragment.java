package tcc.befree.telas.listaDeBuscas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import tcc.befree.R;
import tcc.befree.activities.AnuncioBuscaActivity;
import tcc.befree.api.ApiModels;
import tcc.befree.models.Busca;

/**
 * Created by guilherme.leme on 5/24/17.
 */

public class SearchFragment extends Fragment implements SearchAdapter.OnClickListener {

    int idUsuario;
    int id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Intent intent = getActivity().getIntent();
        Bundle pesquisa = intent.getBundleExtra("search");

        try {
            id = intent.getBundleExtra("bundle").getInt("id");
        }catch(Exception e){
            id = 0;
        }
        try {
            idUsuario = intent.getBundleExtra("idUsuario").getInt("idUsuario");
        }catch(Exception e){
            idUsuario = 0;
        }

        String search;
        try {
            search = pesquisa.getString("search");
        }catch(Exception e){
            search = "";
        }

        View rootView = inflater.inflate(R.layout.fragment_slide, container, false);
        ArrayList<Busca> searchs = new ArrayList<>();
        SearchAdapter adapter;
        ApiModels api = new ApiModels();

        if(!search.equals("")){
            ArrayList<Busca> resultado = new ArrayList<>();
            searchs = api.getBuscas();

            for(int i = 0; i<searchs.size();i++){
                if(searchs.get(i).titulo.toLowerCase().contains(search)){
                    resultado.add(searchs.get(i));
                }
            }
            adapter = new SearchAdapter(getContext(), resultado, this);

        }else{
            if(id==0){
                searchs = api.getBuscasExcetoDoUsuario(idUsuario);
            }else{
                searchs = api.getBuscasApenasDoUsuario(id);
            }
            adapter = new SearchAdapter(getContext(), searchs, this);
        }


        ListView ls = (ListView) rootView.findViewById(R.id.list);
        ls.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onClick(Busca busca) {
        Bundle bundle = new Bundle();
        int id = busca.idBusca;
        bundle.putInt("id",id);
        bundle.putInt("idUsuario",idUsuario);
        Intent intent = new Intent(getActivity(), AnuncioBuscaActivity.class);
        intent.putExtra("bundle", bundle);
        startActivity(intent);
    }
}
