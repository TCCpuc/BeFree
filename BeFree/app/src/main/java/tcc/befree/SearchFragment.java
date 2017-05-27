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

/**
 * Created by guilherme.leme on 5/24/17.
 */

public class SearchFragment extends Fragment implements SearchAdapter.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_slide, container, false);


        //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        //textView.setText("Busca");


        ArrayList<Busca> searchs = new ArrayList<>();

        ApiModels api = new ApiModels();
        searchs = api.getBuscas();

        SearchAdapter adapter = new SearchAdapter(getContext(), searchs, this);

        ListView ls = (ListView) rootView.findViewById(R.id.list);
        ls.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onClick(Busca busca) {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(getActivity(), AnuncioBuscaActivity.class);
        int id = busca.idBusca;
        intent.putExtra("id",id);
        startActivity(intent);
    }
}
