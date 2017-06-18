package tcc.befree.models;

/**
 * Created by gabro on 20/05/2017.
 */

public class DDD {
    public int id;
    public String codDDD;
    public String descricao;

    @Override
    public String toString(){
        return codDDD + " - " + descricao;
    }
}
