package tcc.befree.models;

/**
 * Created by guilherme.leme on 10/21/17.
 */

public class Evento {
    private int idEvento;
    private int idServico;
    private int idUsuarioContratante;
    private String nomeUsuarioContratante;
    private String dtEvento;
    private int hrInicio;
    private int hrFinal;
    private float notaAvalicao;
    private boolean avaliado;
    private int situacaoEvento; //0 - PENDENTE | 1 - ACEITO - 2 - RECUSADO
    private String titulo; //SERVICO
    private String conteudo; //SERVICO
    private String imagem; //SERVICO

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public int getIdServico() {
        return idServico;
    }

    public void setIdServico(int idServico) {
        this.idServico = idServico;
    }

    public int getIdUsuarioContratante() {
        return idUsuarioContratante;
    }

    public void setIdUsuarioContratante(int idUsuarioContratante) {
        this.idUsuarioContratante = idUsuarioContratante;
    }

    public String getNomeUsuarioContratante() {
        return nomeUsuarioContratante;
    }

    public void setNomeUsuarioContratante(String nomeUsuarioContratante) {
        this.nomeUsuarioContratante = nomeUsuarioContratante;
    }

    public String getDtEvento() {
        return dtEvento;
    }

    public void setDtEvento(String dtEvento) {
        this.dtEvento = dtEvento;
    }

    public int getHrInicio() {
        return hrInicio;
    }

    public void setHrInicio(int hrInicio) {
        this.hrInicio = hrInicio;
    }

    public int getHrFinal() {
        return hrFinal;
    }

    public void setHrFinal(int hrFinal) {
        this.hrFinal = hrFinal;
    }

    public float getNotaAvalicao() {
        return notaAvalicao;
    }

    public void setNotaAvalicao(float notaAvalicao) {
        this.notaAvalicao = notaAvalicao;
    }

    public boolean isAvaliado() {
        return avaliado;
    }

    public void setAvaliado(boolean avaliado) {
        this.avaliado = avaliado;
    }

    public int getSituacaoEvento() {
        return situacaoEvento;
    }

    public void setSituacaoEvento(int situacaoEvento) {
        this.situacaoEvento = situacaoEvento;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}
