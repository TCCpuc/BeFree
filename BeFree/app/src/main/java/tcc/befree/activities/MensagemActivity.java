package tcc.befree.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tcc.befree.R;
import tcc.befree.api.ApiModels;
import tcc.befree.api.PostApiModels;
import tcc.befree.models.Mensagem;
import tcc.befree.models.Usuarios;
import tcc.befree.telas.Conversa.MensagemAdapter;

public class MensagemActivity extends AppCompatActivity {

    private EditText messageET;
    private ListView messagesContainer;
    private ImageButton sendBtn;
    private ImageButton back;
    private MensagemAdapter adapter;
    private ArrayList<Mensagem> chatHistory;
    private int idUsuarioOrigem;
    private int idChat;
    private int isMe;
    private int idUsuarioDestino;
    private Bundle bundle;
    private TextView userName;
    private ApiModels api;
    private Usuarios usuario;
    private ArrayList<Mensagem> msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        messagesContainer = (ListView) findViewById(R.id.messagesContainer);
        messageET = (EditText) findViewById(R.id.messageEdit);
        back = (ImageButton) findViewById(R.id.cabecalho_back);
        sendBtn = (ImageButton) findViewById(R.id.chatSendButton);
        userName = (TextView) findViewById(R.id.messages_username);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        api = new ApiModels();

        RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
        Intent intent = this.getIntent();
        bundle = intent.getBundleExtra("bundleChat");

        idUsuarioOrigem = bundle.getInt("idUsuarioOrigem");
        idChat = bundle.getInt("idChat");
        isMe = bundle.getInt("isMe");
        idUsuarioDestino = bundle.getInt("idUsuarioDestino");
        loadingAdapter();
        //loadHistory(chatHistory);

    }

    public void loadingAdapter(){
        this.messagesContainer.setAdapter(new MensagemActivity.loadingAdapter());
        threadUpdate();
    }

    private void threadUpdate(){
        new Thread(){
            @Override
            public void run() {
                usuario = api.getUsuarioById(idUsuarioDestino);
                msg = api.getMensagensDoChat(idChat, idUsuarioOrigem);
                threadUI();
            }
        }.start();
    }

    private void threadUI(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                userName.setText((usuario.nomeUsuario).toUpperCase());
                chatHistory = (msg);
                initControls();
                adapter = new MensagemAdapter(MensagemActivity.this, chatHistory, idUsuarioOrigem);
                messagesContainer.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                scroll();
                threadUpdateMessage();
            }
        });
    }

    private void threadUpdateMessage(){
        new Thread(){
            @Override
            public void run() {
                while (true){
                    try {
                        sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    List<Mensagem> newMessage = api.getMensagensDoChat(idChat, idUsuarioOrigem);
                    if(chatHistory.size() != (newMessage.size()) ){
                        for (chatHistory.size();chatHistory.size()<newMessage.size();){
                            chatHistory.add(newMessage.get(chatHistory.size()));
                            threadUIMessage();
                        }
                    }
                }
            }
        }.start();
    }

    private void threadUIMessage(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
                scroll();
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        setIntent(intent);
    }
    private void initControls() {

        messageET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    sendBtn.performClick();
                    return true;
                }
                return false;
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageET.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }

                Mensagem newMessage = new Mensagem();
                //newMessage.setId(122);//dummy
                newMessage.setMensagem(messageText);
                //newMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                //newMessage.setMe(true);

                try {
                    newMessage.setUsuario_origem(idUsuarioOrigem);
                    newMessage.setUsuario_destino(idUsuarioDestino);
                    newMessage.setMe(true);
                    //newMessage.setData(new java.sql.Date(new java.util.Date().getTime()));
                    newMessage.setChat(idChat);
                    scroll();
                    //chatHistory.add(newMessage);
                }catch(Exception e){
                    System.err.print("deu erro no bundle chat");
                }

                (new PostApiModels()).postMensagem(newMessage);
                messageET.setText("");

                displayMessage(newMessage);
            }
        });
    }

    public void displayMessage(Mensagem message) {
        adapter.add(message);

    }

    private class loadingAdapter extends BaseAdapter {

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
            view = getLayoutInflater().inflate(R.layout.item_loading, null);
            return view;
        }
    }

    @Override
    public void onBackPressed() {
        Intent resultInt = new Intent();
        resultInt.putExtra("Result", "Done");
        setResult(UserPerfilActivity.RESULT_OK, resultInt);
        MensagemActivity.super.onBackPressed();
        finish();
    }

    private void scroll()
    {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }
}