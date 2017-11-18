package tcc.befree.telas.listaDeServicos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

import tcc.befree.R;
import tcc.befree.api.ApiModels;
import tcc.befree.models.Servico;

/**
 * Created by guilherme.leme on 5/24/17.
 */
public class ServiceFragment extends Fragment {

    private int idUsuario;
    private int id;
    private ViewGroup container;
    private LayoutInflater inflater;
    private View rootView;
    private boolean realizouBusca;
    private RecyclerView ls;
    private ServiceAdapter adapter;
    private boolean meusAnuncios;
    public ArrayList<Servico> results = new ArrayList<>();
    private ArrayList<Servico> valuesComMostrarTrue;
    private SwipeRefreshLayout mSwipeRefreshLayout;

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
        /*
        try {
            idUsuario = intent.getBundleExtra("idUsuario").getInt("idUsuario");
        }catch(Exception e){
            idUsuario = 0;
        }*/
        rootView = inflater.inflate(R.layout.fragment_slide, this.container, false);
        ls = (RecyclerView) rootView.findViewById(R.id.list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);

        getLista();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });
        return this.rootView ;
    }

    @NonNull
    public void getLista() {

        ls.setLayoutManager(new LinearLayoutManager(getContext()));
        ls.setAdapter(new loadingAdapter());
        threadUpdate();
    }

    public void setRealizouBusca(boolean realizouBusca) {
        this.realizouBusca = realizouBusca;
    }

    private class loadingAdapter extends RecyclerView.Adapter<loadingAdapter.ViewHolder>{
        private ProgressBar pr;
        public class ViewHolder extends RecyclerView.ViewHolder {

            public final View view;

            public ViewHolder(View view) {
                super(view);
                this.view = view;
                pr = (ProgressBar) view.findViewById(R.id.item_loading_progress);
            }
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            view = getActivity().getLayoutInflater().inflate(R.layout.item_loading_main, null);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 1;
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
                ls.setLayoutManager(new LinearLayoutManager(getContext()));
                ls.setAdapter(new ServiceAdapter(getContext(), valuesComMostrarTrue, meusAnuncios, id, idUsuario));
            }
        });
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    void refreshItems() {
        // Load items
        // ...
        getLista();
        // Load complete
        onItemsLoadComplete();
    }

    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...
        // Stop refresh animation
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
