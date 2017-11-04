package tcc.befree.models;

/**
 * Created by gabro on 20/05/2017.
 */

public class Servico {
    private int idServico;
    private String titulo;
    private String descricao;
    private int idUsuario;
    private int idSubCategoria;
    private int idStatus;
    private float mediaAvalicao;
    private String imagemServico;
    private int idDDD;
    private boolean mostrar = true;

    public int getIdServico() {
        return idServico;
    }

    public void setIdServico(int idServico) {
        this.idServico = idServico;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdSubCategoria() {
        return idSubCategoria;
    }

    public void setIdSubCategoria(int idSubCategoria) {
        this.idSubCategoria = idSubCategoria;
    }

    public int getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }

    public String getImagemServico() {
        return imagemServico;
    }

    public void setImagemServico(String imagemServico) {
        this.imagemServico = imagemServico;
    }

    public int getIdDDD() {
        return idDDD;
    }

    public void setIdDDD(int idDDD) {
        this.idDDD = idDDD;
    }

    public boolean isMostrar() {
        return mostrar;
    }

    public void setMostrar(boolean mostrar) {
        this.mostrar = mostrar;
    }

    public float getMediaAvalicao() {
        return mediaAvalicao;
    }

    public void setMediaAvalicao(float mediaAvalicao) {
        this.mediaAvalicao = mediaAvalicao;
    }
}
