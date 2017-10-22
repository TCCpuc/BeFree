package tcc.befree.models;

import java.util.Date;

/**
 * Created by Guilherme Domingues on 5/16/2017.
 */

public class Usuarios {
    public int idUsuario ;
    public String nomeUsuario;
    public String cpf ;
    public int idCidade ;
    public int idEstado ;
    public String bairro ;
    public String logradouro;
    public int numero ;
    public int cep ;
    public Date dataNascimento;
    public Date dataCadastro;
    public boolean ativo;
    public String senha ;
    public String email ;
    public int ddd ;
    public String imagemPerfil;
    public String codigoSeguranca;

    @Override
    public String toString() {
        return idUsuario + "%" + nomeUsuario + "%" + cpf + "%" + idCidade + "%" + idEstado + "%" + bairro + "%" + logradouro + "%" + numero + "%" + cep + "%" + email + "%" + ddd + "%" + imagemPerfil + "%" + senha;
    }
}
