package tcc.befree;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import tcc.befree.models.Busca;
import tcc.befree.models.Servico;

/**
 * Created by guilherme.leme on 5/24/17.
 */
public class ServiceFragment extends Fragment implements ServiceAdapter.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = getActivity().getIntent().getBundleExtra("bundle");
        int id;
        try {
            id = bundle.getInt("id");
        }catch(Exception e){
            id = 0;
        }

        //int id = getIntent().getIntExtra("id",0);

        View rootView = inflater.inflate(R.layout.fragment_slide, container, false);

        ArrayList<Servico> searchs = new ArrayList<>();

        if(id==0){
            ApiModels api = new ApiModels();
            searchs = api.getServicos();
        }else{
            Servico sc = new Servico();
            sc.descricao = "Teste";
            sc.titulo = "Teste";
            sc.idServico = 1;
            sc.idStatus = 1;
            sc.idUsuario = 1;
            sc.idSubCategoria = 1;
            searchs.add(sc);
        }


        ServiceAdapter adapter = new ServiceAdapter(getContext(), searchs, this);

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
