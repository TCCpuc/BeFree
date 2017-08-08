package tcc.befree.telas.listaDeServicos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import tcc.befree.R;
import tcc.befree.activities.AnuncioServicoActivity;
import tcc.befree.api.ApiModels;
import tcc.befree.models.Servico;

/**
 * Created by guilherme.leme on 5/24/17.
 */
public class ServiceFragment extends Fragment implements ServiceAdapter.OnClickListener {

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

        ArrayList<Servico> searchs = new ArrayList<>();

        ServiceAdapter adapter;

        ApiModels api = new ApiModels();

        if(!search.equals("")){
            ArrayList<Servico> resultado = new ArrayList<>();

            searchs = api.getServicos();

            for(int i = 0; i<searchs.size();i++){
                if(searchs.get(i).titulo.toLowerCase().contains(search)){
                    resultado.add(searchs.get(i));
                }
            }
            adapter = new ServiceAdapter(getContext(), resultado, this);

        }else{
            if(id==0){
                searchs = api.getServicos();
            }else{
                searchs = api.getServicosByUsuario(id);

            }
            adapter = new ServiceAdapter(getContext(), searchs, this);
        }



        ListView ls = (ListView) rootView.findViewById(R.id.list);
        ls.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onClick(Servico servico) {
        Bundle bundle = new Bundle();
        int id = servico.idServico;
        bundle.putInt("id",id);
        Intent intent = new Intent(getActivity(), AnuncioServicoActivity.class);
        intent.putExtra("bundle", bundle);
        startActivity(intent);
    }

}
