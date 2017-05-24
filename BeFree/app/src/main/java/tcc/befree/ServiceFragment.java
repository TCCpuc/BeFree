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

/**
 * Created by guilherme.leme on 5/24/17.
 */
public class ServiceFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_slide, container, false);


        //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        //textView.setText("Serviço");


        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.Services, android.R.layout.simple_list_item_1);

        ListView ls = (ListView) rootView.findViewById(R.id.list);
        ls.setAdapter(adapter);

        return rootView;
    }
}
