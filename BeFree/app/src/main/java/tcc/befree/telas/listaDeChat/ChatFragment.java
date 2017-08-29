package tcc.befree.telas.listaDeChat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tcc.befree.R;

/**
 * Created by guilherme.leme on 8/29/17.
 */

public class ChatFragment extends Fragment {

    private List<String> newList;

    public ChatFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        newList = new ArrayList<>();
        for (int i = 0; i < 20; i++)
            newList.add("Item " + i);

        ListView listView = (ListView) view.findViewById(R.id.list);
        listView.setAdapter(new MyAdapter());
        return view;
    }


    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return newList.size();
        }

        @Override
        public Object getItem(int position) {
            return newList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.item_service, null);
            TextView textView = (TextView) view.findViewById(R.id.item_service_title);
            textView.setText(newList.get(position));
            return view;
        }
    }
}
