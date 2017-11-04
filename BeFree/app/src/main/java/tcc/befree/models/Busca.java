package tcc.befree.models;

/**
 * Created by gabro on 20/05/2017.
 */

public class Busca {
    public int idBusca;
    public String titulo;
    public String descricao;
    public int idUsuario;
    public int idSubCategoria;
    public int idStatus;
    public String imagemBusca;
    public int idDDD;
    public boolean mostrar = true;
    private float mediaAvalicao;
    private float preco;
    private String formaPgto;

    public float getMediaAvalicao() {
        return mediaAvalicao;
    }

    public void setMediaAvalicao(float mediaAvalicao) {
        this.mediaAvalicao = mediaAvalicao;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public String getFormaPgto() {
        return formaPgto;
    }

    public void setFormaPgto(String formaPgto) {
        this.formaPgto = formaPgto;
    }
}
