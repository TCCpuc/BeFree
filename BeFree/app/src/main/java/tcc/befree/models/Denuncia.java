package tcc.befree.models;

/**
 * Created by guilherme.leme on 11/3/17.
 */

public class Denuncia {
    private int idServico;
    private int idBusca;
    private int idUsuarioDenunciante;
    private int Denuncia;

    public int getIdServico() {
        return idServico;
    }

    public void setIdServico(int idServico) {
        this.idServico = idServico;
    }

    public int getIdBusca() {
        return idBusca;
    }

    public void setIdBusca(int idBusca) {
        this.idBusca = idBusca;
    }

    public int getIdUsuarioDenunciante() {
        return idUsuarioDenunciante;
    }

    public void setIdUsuarioDenunciante(int idUsuarioDenunciante) {
        this.idUsuarioDenunciante = idUsuarioDenunciante;
    }

    public int getDenuncia() {
        return Denuncia;
    }

    public void setDenuncia(int denuncia) {
        Denuncia = denuncia;
    }
}
