package tcc.befree.telas.listaDeServicos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import tcc.befree.R;
import tcc.befree.activities.AnuncioServicoActivity;
import tcc.befree.activities.EditServicoActivity;
import tcc.befree.api.ApiModels;
import tcc.befree.models.Servico;

/**
 * Created by guilherme.leme on 5/24/17.
 */
public class ServiceFragment extends Fragment implements ServiceAdapter.OnClickListener {

    private int idUsuario;
    private int id;
    private ViewGroup container;
    private LayoutInflater inflater;
    private View rootView;
    private boolean realizouBusca;
    private ListView ls;
    private ServiceAdapter adapter;
    private boolean meusAnuncios;
    public ArrayList<Servico> results = new ArrayList<>();
    private ArrayList<Servico> valuesComMostrarTrue;

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
        rootView = inflater.inflate(R.layout.fragment_slide, this.container, false);
        ls = (ListView) rootView.findViewById(R.id.list);
        ls.setAdapter(new loadingAdapter());
        threadUpdate();
    }

    @Override
    public void onClick(Servico servico) {
        Intent intent = null;
        if(id == 0){
            Bundle bundle = new Bundle();
            int id = servico.getIdServico();
            bundle.putInt("id",id);
            bundle.putInt("idUsuario",idUsuario);
            intent = new Intent(getActivity(), AnuncioServicoActivity.class);
            intent.putExtra("bundle", bundle);
            startActivity(intent);
        }else{
            intent = new Intent(getContext(), EditServicoActivity.class);
            intent.putExtra("idServico", servico.getIdServico());
            getContext().startActivity(intent);
        }

    }

    public void setRealizouBusca(boolean realizouBusca) {
        this.realizouBusca = realizouBusca;
    }

    private class loadingAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            view = getActivity().getLayoutInflater().inflate(R.layout.item_loading, null);
            return view;
        }
    }

    private void threadUpdate(){
        new Thread(){
            @Override
            public void run() {
                meusAnuncios = false;
                ApiModels api = new ApiModels();
                if (!realizouBusca) {
                    if (id == 0) {
                        results = api.getServicosExcetoDoUsuario(idUsuario);
                        meusAnuncios = false;
                    } else {
                        results = api.getServicosApenasDoUsuario(id);
                        meusAnuncios = true;
                    }
                }
                realizouBusca = false;
                valuesComMostrarTrue = new ArrayList<Servico>();
                for (Servico s : results)
                    if (s.isMostrar())
                        valuesComMostrarTrue.add(s);
                if (id != 0)
                    meusAnuncios = true;
                threadUI();
            }
        }.start();
    }

    private void threadUI(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter = new ServiceAdapter(getContext(), valuesComMostrarTrue, ServiceFragment.this, meusAnuncios);
                ListView ls = (ListView) rootView.findViewById(R.id.list);
                ls.setAdapter(adapter);
            }
        });
    }
}
