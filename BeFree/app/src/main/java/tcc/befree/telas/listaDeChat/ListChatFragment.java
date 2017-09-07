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

import com.squareup.picasso.Picasso;

import java.util.List;

import tcc.befree.R;
import tcc.befree.activities.ListChatActivity;
import tcc.befree.activities.MensagemActivity;
import tcc.befree.api.ApiModels;
import tcc.befree.models.Chat;
import tcc.befree.models.CircleImageView;

/**
 * Created by guilherme.leme on 8/29/17.
 */

public class ListChatFragment extends Fragment {

    private List<Chat> chatList;

    private int idUsuario;

    ApiModels api = new ApiModels();


    public ListChatFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        Bundle bundle = getActivity().getIntent().getBundleExtra("bundle");
        try {
            idUsuario = bundle.getInt("idUsuario");
        }catch(Exception e){
            idUsuario = 0;
        }


        chatList = api.getChatsDoUsuario(idUsuario);

        ListView listView = (ListView) view.findViewById(R.id.list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundleChat = new Bundle();
                Chat chatSelecionado = chatList.get(position);

                bundleChat.putInt("idChat", chatSelecionado.getId());
                if(api.getUsuarioEUsuario1DoChat(chatSelecionado.getId(), idUsuario)) {
                    bundleChat.putInt("idUsuarioOrigem", idUsuario);
                    bundleChat.putInt("isMe", 1);
                    bundleChat.putInt("idUsuarioDestino", chatSelecionado.getUsuario_2());
                }
                else{
                    bundleChat.putInt("idUsuarioOrigem", idUsuario);
                    bundleChat.putInt("isMe", 2);
                    bundleChat.putInt("idUsuarioDestino", chatSelecionado.getUsuario_1());
                }

                Intent intent = new Intent(getActivity(), MensagemActivity.class);
                intent.putExtra("bundleChat", bundleChat);

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

            CircleImageView mImagePerfil = (CircleImageView) view.findViewById(R.id.img_anuncio);

            Picasso.with(getContext()).load(api.getImagemMiniaturaDoChat(chatList.get(position).getId(),idUsuario)).into(mImagePerfil);


            return view;
        }
    }

}
