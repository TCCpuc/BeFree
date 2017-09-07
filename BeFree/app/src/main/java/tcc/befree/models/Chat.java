package tcc.befree.models;

/**
 * Created by gabro on 28/08/2017.
 */

public class Chat {
    private int id;
    private int usuario_1;
    private int usuario_2;
    private int ultima_mensagem;
    private String ultima_mensagem_texto;
    //private boolean isMe;
    //private String message;
    //private Long userId;
    //private String dateTime;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    /*
    public boolean getIsme() {
        return isMe;
    }
    public void setMe(boolean isMe) {
        this.isMe = isMe;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getDate() {
        return dateTime;
    }

    public void setDate(String dateTime) {
        this.dateTime = dateTime;
    }
    */

    public int getUsuario_1() {
        return usuario_1;
    }

    public void setUsuario_1(int usuario_1) {
        this.usuario_1 = usuario_1;
    }

    public int getUsuario_2() {
        return usuario_2;
    }

    public void setUsuario_2(int usuario_2) {
        this.usuario_2 = usuario_2;
    }

    public int getUltima_mensagem() {
        return ultima_mensagem;
    }

    public void setUltima_mensagem(int ultima_mensagem) {
        this.ultima_mensagem = ultima_mensagem;
    }

    public void setUltima_mensagem_texto(String ultima_mensagem_texto) {
        this.ultima_mensagem_texto = ultima_mensagem_texto;
    }

    public String getUltima_mensagem_texto() {
        return ultima_mensagem_texto;
    }
}
/*

public class Chat {
    public int id;
    public int usuario_1;
    public int usuario_2;
    public int ultima_mensagem;
}

*/