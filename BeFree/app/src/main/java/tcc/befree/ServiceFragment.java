package tcc.befree;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

        View rootView = inflater.inflate(R.layout.fragment_slide, container, false);

        ArrayList<Servico> searchs = new ArrayList<>();


        ApiModels api = new ApiModels();
        searchs = api.getServicos();

        ServiceAdapter adapter = new ServiceAdapter(getContext(), searchs, this);

        ListView ls = (ListView) rootView.findViewById(R.id.list);
        ls.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onClick(Servico servico) {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(getActivity(), AnuncioServicoActivity.class);
        int id = servico.idServico;
        intent.putExtra("id",id);
        startActivity(intent);
    }

}
