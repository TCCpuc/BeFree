package tcc.befree.models;

import java.sql.Date;

/**
 * Created by gabro on 28/08/2017.
 */

public class Mensagem {
    public int id;
    public int chat;
    public int usuario_origem;
    public int usuario_destino;
    public Date data;
    public String mensagem;
}
