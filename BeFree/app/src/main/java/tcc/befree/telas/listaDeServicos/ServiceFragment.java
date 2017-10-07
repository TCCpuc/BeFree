package tcc.befree.telas.listaDeServicos;

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
import tcc.befree.activities.AnuncioServicoActivity;
import tcc.befree.api.ApiModels;
import tcc.befree.models.Servico;

/**
 * Created by guilherme.leme on 5/24/17.
 */
public class ServiceFragment extends Fragment implements ServiceAdapter.OnClickListener {

    int idUsuario;
    int id;
    private ViewGroup container;
    private LayoutInflater inflater;
    private View rootView;
    private boolean realizouBusca = false;
    public void setRealizouBusca(boolean realizouBusca) {
        this.realizouBusca = realizouBusca;
    }
    private ServiceAdapter adapter;
    public ArrayList<Servico> results = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.container = container;
        this.inflater = inflater;
        Intent intent = getActivity().getIntent();
        Bundle pesquisa = intent.getBundleExtra("service");
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
        ServiceAdapter adapter;ApiModels api = new ApiModels();
        if (!this.realizouBusca) {
            if (id == 0) {
                results = api.getServicosExcetoDoUsuario(idUsuario);
                meusAnuncios = false;
            } else {
                results = api.getServicosApenasDoUsuario(id);
                meusAnuncios = true;
            }
        }
        this.realizouBusca = false;
        ArrayList<Servico> valuesComMostrarTrue = new ArrayList<Servico>();
        for (Servico s : results)
            if (s.mostrar)
                valuesComMostrarTrue.add(s);
        if (id != 0)
            meusAnuncios = true;
        adapter = new ServiceAdapter(getContext(), valuesComMostrarTrue, this, meusAnuncios);
        this.adapter = adapter;
        this.rootView = this.inflater.inflate(R.layout.fragment_slide, this.container, false);
        ListView ls = (ListView) rootView.findViewById(R.id.list);
        ls.setAdapter(adapter);
    }

    @Override
    public void onClick(Servico servico) {
        Bundle bundle = new Bundle();
        int id = servico.idServico;
        bundle.putInt("id",id);
        bundle.putInt("idUsuario",idUsuario);
        Intent intent = new Intent(getActivity(), AnuncioServicoActivity.class);
        intent.putExtra("bundle", bundle);
        startActivity(intent);
    }
}
