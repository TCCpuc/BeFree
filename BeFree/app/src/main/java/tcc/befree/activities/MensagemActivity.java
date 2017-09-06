package tcc.befree.activities;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tcc.befree.R;
import tcc.befree.api.ApiModels;
import tcc.befree.models.Mensagem;
import tcc.befree.telas.Conversa.MensagemAdapter;
import tcc.befree.models.Chat;

public class MensagemActivity extends AppCompatActivity {

    private EditText messageET;
    private ListView messagesContainer;
    private ImageButton sendBtn;
    private MensagemAdapter adapter;
    private List<Mensagem> chatHistory;
    private int idUsuarioOrigem;
    private int idChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        initControls();
    }

    private void initControls() {
        messagesContainer = (ListView) findViewById(R.id.messagesContainer);
        messageET = (EditText) findViewById(R.id.messageEdit);
        sendBtn = (ImageButton) findViewById(R.id.chatSendButton);

        RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
        loadHistory();

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

                Bundle bundle = getIntent().getBundleExtra("budleChat");
                try {
                    idUsuarioOrigem = bundle.getInt("idUsuarioOrigem");
                    newMessage.setUsuario_origem(idUsuarioOrigem);
                    newMessage.setUsuario_destino(bundle.getInt("idUsuarioDestino"));
                    idChat = bundle.getInt("idChat");
                    newMessage.setChat(idChat);

                }catch(Exception e){
                    System.err.print("deu erro no bundle chat");
                }


                messageET.setText("");

                displayMessage(newMessage);
            }
        });
    }

    public void displayMessage(Mensagem message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }

    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }

    private void loadHistory() {

        chatHistory = (new ApiModels()).getMensagensDoChat(idChat);

        adapter = new MensagemAdapter(MensagemActivity.this, new ArrayList<Mensagem>());
        messagesContainer.setAdapter(adapter);

        for (int i = 0; i < chatHistory.size(); i++) {
            Mensagem message = chatHistory.get(i);
            displayMessage(message);
        }
    }

    /*
    private void loadDummyHistory() {

        chatHistory = new ArrayList<Chat>();

        Chat msg = new Chat();
        msg.setId(1);
        msg.setMe(false);
        msg.setMessage("Hi");
        msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg);
        Chat msg1 = new Chat();
        msg1.setId(2);
        msg1.setMe(false);
        msg1.setMessage("How r u doing???");
        msg1.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg1);

        adapter = new MensagemAdapter(MensagemActivity.this, new ArrayList<Chat>());
        messagesContainer.setAdapter(adapter);

        for (int i = 0; i < chatHistory.size(); i++) {
            Chat message = chatHistory.get(i);
            displayMessage(message);
        }
    }
    */
}