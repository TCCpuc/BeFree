package tcc.befree;

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

        /*Servico servico1 = new Servico();
        servico1.titulo = "Joao";
        servico1.descricao = "Preciso de um pintor";

        Servico servico2 = new Servico();
        servico2.titulo = "Pedro";
        servico2.descricao = "Preciso de um encanador";

        Servico servico3 = new Servico();
        servico3.titulo = "Tiago";
        servico3.descricao = "Preciso de um calheiro";

        Servico servico4 = new Servico();
        servico4.titulo = "Bruno";
        servico4.descricao = "Preciso de um computeiro";

        Servico servico5 = new Servico();
        servico5.titulo = "Maria";
        servico5.descricao = "Preciso de ajuda";


        searchs.add(servico1);
        searchs.add(servico2);
        searchs.add(servico3);
        searchs.add(servico4);
        searchs.add(servico5);
        */


        ServiceAdapter adapter = new ServiceAdapter(getContext(), searchs, this);

        ListView ls = (ListView) rootView.findViewById(R.id.list);
        ls.setAdapter(adapter);





        /*TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText("Serviço");


        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.Services, android.R.layout.simple_list_item_1);

        ListView ls = (ListView) rootView.findViewById(R.id.list);
        ls.setAdapter(adapter);
        */

        return rootView;
    }

    @Override
    public void onClick(Servico servico) {
        Bundle bundle = new Bundle();

        // http://www.guj.com.br/t/android-utilizar-dados-entre-varias-telas-bundle-intent/246395

        Toast.makeText(getContext(), "Oi, eu sou o " + servico.titulo, Toast.LENGTH_SHORT).show();
    }

}
