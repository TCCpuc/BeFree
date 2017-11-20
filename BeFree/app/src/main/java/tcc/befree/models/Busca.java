package tcc.befree.models;

import tcc.befree.utils.Utils;

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
    private int preco;
    private int formaPgto;
    private String descCategoria;
    private String descSubCategoria;

    public int getPreco() {
        return preco;
    }

    public void setPreco(int preco) {
        this.preco = preco;
    }

    public int getFormaPgto() {
        return formaPgto;
    }

    public void setFormaPgto(int formaPgto) {
        this.formaPgto = formaPgto;
    }

    public String getDescCategoria() {
        return descCategoria;
    }

    public void setDescCategoria(String descCategoria) {
        this.descCategoria = descCategoria;
    }

    public String getDescSubCategoria() {
        return descSubCategoria;
    }

    public void setDescSubCategoria(String descSubCategoria) {
        this.descSubCategoria = descSubCategoria;
    }
}
