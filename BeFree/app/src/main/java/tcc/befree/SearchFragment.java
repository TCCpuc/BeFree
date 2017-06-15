package tcc.befree;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import tcc.befree.models.Busca;
import tcc.befree.models.Servico;

/**
 * Created by guilherme.leme on 5/24/17.
 */

public class SearchFragment extends Fragment implements SearchAdapter.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = getActivity().getIntent().getBundleExtra("bundle");
        Bundle pesquisa = getActivity().getIntent().getBundleExtra("search");
        int id;
        try {
            id = bundle.getInt("id");
        }catch(Exception e){
            id = 0;
        }

        String search;
        try {
            search = pesquisa.getString("search");
        }catch(Exception e){
            search = "";
        }

        //int id = getIntent().getIntExtra("id",0);

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
                searchs = api.getBuscas();
            }else{
                searchs = api.getBuscaByUsuario(id);
//                Busca sc = new Busca();
//                sc.descricao = "Teste";
//                sc.titulo = "Teste";
//                sc.idBusca = 1;
//                sc.idStatus = 1;
//                sc.idUsuario = 1;
//                sc.idSubCategoria = 1;
//                searchs.add(sc);
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
        Intent intent = new Intent(getActivity(), AnuncioServicoActivity.class);
        intent.putExtra("bundle", bundle);
        startActivity(intent);
    }
}
