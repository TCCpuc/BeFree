package tcc.befree.telas.listaDeBuscas;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import tcc.befree.R;
import tcc.befree.activities.AnuncioBuscaActivity;
import tcc.befree.activities.EditBuscaActivity;
import tcc.befree.api.ApiModels;
import tcc.befree.models.Busca;

/**
 * Created by guilherme.leme on 5/24/17.
 */
public class SearchFragment extends Fragment implements SearchAdapter.OnClickListener {

    private int idUsuario;
    private int id;
    private ViewGroup container;
    private LayoutInflater inflater;
    private View rootView;
    private boolean realizouBusca = false;
    public void setRealizouBusca(boolean realizouBusca) {
        this.realizouBusca = realizouBusca;
    }
    private SearchAdapter adapter;
    public ArrayList<Busca> results = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.container = container;
        this.inflater = inflater;
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
        getLista();
        return this.rootView ;
    }

    @NonNull
    public void getLista() {
        boolean meusAnuncios = false;
        SearchAdapter adapter;ApiModels api = new ApiModels();
        if (!this.realizouBusca) {
            if (id == 0) {
                results = api.getBuscasExcetoDoUsuario(idUsuario);
                meusAnuncios = false;
            } else {
                results = api.getBuscasApenasDoUsuario(id);
                meusAnuncios = true;
            }
        }
        this.realizouBusca = false;
        ArrayList<Busca> valuesComMostrarTrue = new ArrayList<Busca>();
        for (Busca s : results)
            if (s.mostrar)
                valuesComMostrarTrue.add(s);
        if (id != 0)
            meusAnuncios = true;
        adapter = new SearchAdapter(getContext(), valuesComMostrarTrue, this, meusAnuncios);
        this.adapter = adapter;
        this.rootView = this.inflater.inflate(R.layout.fragment_slide, this.container, false);
        ListView ls = (ListView) rootView.findViewById(R.id.list);
        ls.setAdapter(adapter);
    }

    @Override
    public void onClick(Busca busca) {
        Intent intent = null;
        if(id == 0){
            Bundle bundle = new Bundle();
            int id = busca.idBusca;
            bundle.putInt("id",id);
            bundle.putInt("idUsuario",idUsuario);
            intent = new Intent(getActivity(), AnuncioBuscaActivity.class);
            intent.putExtra("bundle", bundle);
            startActivity(intent);
        }else {
            intent = new Intent(getContext(), EditBuscaActivity.class);
            intent.putExtra("idBusca", busca.idBusca);
            getContext().startActivity(intent);
        }
    }
}
