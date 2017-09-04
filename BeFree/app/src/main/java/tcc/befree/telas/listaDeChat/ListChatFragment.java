package tcc.befree.telas.listaDeChat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import tcc.befree.R;
import tcc.befree.activities.ChatActivity;
import tcc.befree.api.ApiModels;
import tcc.befree.models.Chat;

/**
 * Created by guilherme.leme on 8/29/17.
 */

public class ListChatFragment extends Fragment {

    private List<Chat> chatList;

    private int idUsuario;

    public ListChatFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        Bundle bundle = getActivity().getIntent().getBundleExtra("idUsuario");
        try {
            idUsuario = bundle.getInt("idUsuario");
        }catch(Exception e){
            idUsuario = 0;
        }

        ApiModels api = new ApiModels();


        chatList = api.getChatsDoUsuario(idUsuario);

        ListView listView = (ListView) view.findViewById(R.id.list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getContext(), ChatActivity.class);
                //based on item add info to intent
                //MANDA O BUNDLE COM O ID DO USUARIO2, E O ID DO CHAT
                startActivity(intent);
            }
        });
        listView.setAdapter(new MyAdapter());
        return view;
    }


    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            if(chatList == null){
                return 1;
            }
            return chatList.size();
        }

        @Override
        public Object getItem(int position) {
            return chatList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.item_service, null);
            TextView textView = (TextView) view.findViewById(R.id.item_service_title);
            textView.setText("GET NOME DO USUARIO");
            return view;
        }
    }

}
