package tcc.befree.models;

/**
 * Created by guilherme.leme on 11/20/17.
 */

public class MensagensNaoLidas {
    private int idUsuario;
    private int numeroMensagens;

    public MensagensNaoLidas(int id, int num) {
        this.idUsuario = id;
        this.numeroMensagens = num;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getNumeroMensagens() {
        return numeroMensagens;
    }

    public void setNumeroMensagens(int numeroMensagens) {
        this.numeroMensagens = numeroMensagens;
    }
}
