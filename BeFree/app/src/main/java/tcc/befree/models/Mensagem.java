package tcc.befree.models;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;

/**
 * Created by gabro on 28/08/2017.
 */

public class Mensagem {
    private int id;
    private int chat;
    private int usuario_origem;
    private int usuario_destino;
    private Date data = new Date(new java.util.Date().getTime());
    private String mensagem;
    private boolean isMe;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChat() {
        return chat;
    }

    public void setChat(int chat) {
        this.chat = chat;
    }

    public int getUsuario_origem() {
        return usuario_origem;
    }

    public void setUsuario_origem(int usuario_origem) {
        this.usuario_origem = usuario_origem;
    }

    public int getUsuario_destino() {
        return usuario_destino;
    }

    public void setUsuario_destino(int usuario_destino) {
        this.usuario_destino = usuario_destino;
    }

    public Date getData() {
        return data;
    }

    public String formatedData() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return format.format(data);

    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public boolean isMe() {
        return isMe;
    }

    public void setMe(boolean me) {
        isMe = me;
    }
}
