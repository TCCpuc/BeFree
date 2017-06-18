package tcc.befree.models;

/**
 * Created by gabro on 20/05/2017.
 */

public class SubCategoria {
    public int idSubCategoria;
    public int idCategoria;
    public String descricao;

    @Override
    public String toString(){
        return this.descricao;
    }
}
