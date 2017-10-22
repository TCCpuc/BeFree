package tcc.befree.api;


import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

import tcc.befree.models.*;
import tcc.befree.utils.Utils;

/**
 * Created by Guilherme Domingues on 5/20/2017.
 */

public class ApiModels implements Runnable{


    private JSONArray jSonArray; //Se for retornado mais de um elemento grava num array
    private JSONObject jSonObject; //Se retornado apenas um elemento, retorna um unico objeto
    private int tipoRetornoThread; //Se o
    private boolean semaforo;
    private String urlAPI = "";

    /* ---------------------------------- MÉTODOS DE USUÁRIOS ----------------------------------- */
    //Retorna todos os Usuários
    final public ArrayList<Usuarios> getUsuarios(){

        ArrayList<Usuarios> arrayUsuarios = new ArrayList<Usuarios>();

        try{
            urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/GettbUsuarios";

            Thread thread = new Thread(this);
            thread.start();
            controlaThread();
            thread.interrupt();
            if (jSonArray == null)
                thread.sleep(500);
            for (int i = 0; i < jSonArray.length();i++){
                if (jSonArray == null)
                    thread.sleep(500);
                JSONObject jSonObject = jSonArray.getJSONObject(i);
                Usuarios usuario = new Usuarios();
                usuario.idUsuario = jSonObject.getInt("idUsuario");
                usuario.email = jSonObject.getString("email");
                usuario.nomeUsuario = jSonObject.getString("nomeUsuario");
                usuario.senha = jSonObject.getString("senha");
                usuario.imagemPerfil = jSonObject.getString("imagemPerfil");
                arrayUsuarios.add(usuario);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        jSonArray = null;
        return  arrayUsuarios;
    }

    final public Usuarios getUsuarioById(int id){

        Usuarios usuario = new Usuarios();

        try{
            urlAPI = ("https://befreeapi-com.umbler.net/BeFreeAPI/api/Usuarios/GetUsuario/" + id);

            Thread thread = new Thread(this);
            thread.start();
            controlaThread();
            thread.interrupt();

            if (jSonArray == null)
                thread.sleep(500);
                JSONObject jSonObject = jSonArray.getJSONObject(0);
                usuario.idUsuario = jSonObject.getInt("idUsuario");
                usuario.email = jSonObject.getString("email");
                usuario.nomeUsuario = jSonObject.getString("nomeUsuario");
                usuario.senha = jSonObject.getString("senha");
                usuario.imagemPerfil = jSonObject.getString("imagemPerfil");
                usuario.cpf = jSonObject.getString("cpf");
                usuario.idCidade = jSonObject.getInt("idCidade");
                usuario.idEstado = jSonObject.getInt("idEstado");
                usuario.bairro = jSonObject.getString("bairro");
                usuario.logradouro = jSonObject.getString("logradouro");;
                usuario.numero = jSonObject.getInt("numero");
                usuario.cep = jSonObject.getInt("cep");
                usuario.ddd = jSonObject.getInt("ddd");
        } catch (Exception e) {
            e.printStackTrace();
        }

        jSonArray = null;
        return usuario;
    }

    //Retorna usuário pelo email
    public Usuarios getUsuariosByEmail(String email){

        Usuarios usuario = new Usuarios();
        try {
            urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Usuarios/GetUsuarioByEmail/?email=" + email;

            Thread thread = new Thread(this);
            thread.start();
            controlaThread();
            thread.interrupt();
            if (jSonArray == null)
                thread.sleep(500);
            JSONObject jSonObject = jSonArray.getJSONObject(0);
            usuario.idUsuario = jSonObject.getInt("idUsuario");
            usuario.nomeUsuario = jSonObject.getString("nomeUsuario");
            usuario.cpf = jSonObject.getString("cpf");
            usuario.idCidade = jSonObject.getInt("idCidade");
            usuario.codigoSeguranca = jSonObject.getString("codigoSeguranca");
            usuario.idEstado = jSonObject.getInt("idEstado");
            if(jSonObject.getString("bairro") != "null"){
                usuario.bairro = jSonObject.getString("bairro");
            }
            if(jSonObject.getString("logradouro") != "null"){
                usuario.logradouro = jSonObject.getString("logradouro");
            }
            if(jSonObject.getString("numero") != "null"){
                usuario.numero = jSonObject.getInt("numero");
            }
            if(jSonObject.getString("cep") != "null"){
                usuario.cep = jSonObject.getInt("cep");
            }
            usuario.ativo = jSonObject.getBoolean("ativo");
            usuario.email = jSonObject.getString("email");
            usuario.senha = jSonObject.getString("senha");
            usuario.ddd = jSonObject.getInt("ddd");
            usuario.imagemPerfil = jSonObject.getString("imagemPerfil");
        }catch (Exception er){
            er.printStackTrace();
        }

        jSonArray = null;
        return  usuario;
    }

    //Gera código de recuperação de senha
    public boolean getCodePassword(String email){

        try {
            urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Usuarios/RecuperarSenha/?email=" + email;
            //urlAPI = "http://localhost:1994/api/Usuarios/RecuperarSenha/?email=" + email;

            Thread thread = new Thread(this);
            thread.start();
            controlaThread();
            thread.interrupt();
            if (jSonArray == null)
                thread.sleep(500);

            JSONObject jSonObject = jSonArray.getJSONObject(0);

        }catch (Exception er){
            er.printStackTrace();
        }

        jSonArray = null;
        return true;
    }


    /* ---------------------------------- MÉTODOS DE SERVIÇO ----------------------------------- */
    //Retorna todos os Servicos
    public ArrayList<Servico> getServicos(){

        ArrayList<Servico> arrayServicos = new ArrayList<Servico>();

        try{
            urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Servico/GettbServicoes";

            Thread thread = new Thread(this);
            thread.start();
            controlaThread();
            thread.interrupt();
            if (jSonArray == null)
                thread.sleep(500);
            for (int i = 0; i < jSonArray.length();i++){
                if (jSonArray == null)
                    thread.sleep(500);
                JSONObject jSonObject = jSonArray.getJSONObject(i);
                Servico servico = new Servico();

                servico.idServico = jSonObject.getInt("idServico");
                servico.titulo = jSonObject.getString("titulo");
                servico.descricao = jSonObject.getString("descricao");
                servico.idUsuario = jSonObject.getInt("idUsuario");
                servico.idSubCategoria = jSonObject.getInt("idSubCategoria");
                servico.idStatus = jSonObject.getInt("idStatus");
                servico.imagemServico = jSonObject.getString("imagemServico");
                servico.idDDD = jSonObject.getInt("idDDD");

                arrayServicos.add(servico);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        jSonArray = null;
        return arrayServicos;
    }

    //Retorna Servicos pelo id
    public Servico getServicosById(int id){

        Servico servico = new Servico();

        try{
            urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Servico/GetServico/" + id;

            Thread thread = new Thread(this);
            thread.start();
            controlaThread();
            thread.interrupt();
            if (jSonArray == null)
                thread.sleep(500);
            for (int i = 0; i < jSonArray.length();i++){
                if (jSonArray == null)
                    thread.sleep(500);
                JSONObject jSonObject = jSonArray.getJSONObject(i);

                servico.idServico = jSonObject.getInt("idServico");
                servico.titulo = jSonObject.getString("titulo");
                servico.descricao = jSonObject.getString("descricao");
                servico.idUsuario = jSonObject.getInt("idUsuario");
                servico.idSubCategoria = jSonObject.getInt("idSubCategoria");
                servico.idStatus = jSonObject.getInt("idStatus");
                servico.imagemServico = jSonObject.getString("imagemServico");
                servico.idDDD = jSonObject.getInt("idDDD");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        jSonArray = null;
        return servico;
    }

    public ArrayList<Servico> getServicosByUsuario(int id){

        ArrayList<Servico> arrayServicos = new ArrayList<Servico>();

        try{
            urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Servico/GetServicoByUsuario/?idUsuario=" + id;

            Thread thread = new Thread(this);
            thread.start();
            controlaThread();
            thread.interrupt();
            if (jSonArray == null)
                thread.sleep(500);
            for (int i = 0; i < jSonArray.length();i++){
                if (jSonArray == null)
                    thread.sleep(500);
                JSONObject jSonObject = jSonArray.getJSONObject(i);
                Servico servico = new Servico();

                servico.idServico = jSonObject.getInt("idServico");
                servico.titulo = jSonObject.getString("titulo");
                servico.descricao = jSonObject.getString("descricao");
                servico.idUsuario = jSonObject.getInt("idUsuario");
                servico.idSubCategoria = jSonObject.getInt("idSubCategoria");
                servico.idStatus = jSonObject.getInt("idStatus");
                servico.imagemServico = jSonObject.getString("imagemServico");
                servico.idDDD = jSonObject.getInt("idDDD");

                arrayServicos.add(servico);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        jSonArray = null;
        return arrayServicos;
    }

    /* ---------------------------------- MÉTODOS DE BUSCAS ----------------------------------- */
    //Retorna todas as Buscas
    public ArrayList<Busca> getBuscas(){

        ArrayList<Busca> arrayBuscas = new ArrayList<Busca>();

        try{
            urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Busca/GettbBuscas";

            Thread thread = new Thread(this);
            thread.start();
            controlaThread();
            thread.interrupt();
            if (jSonArray == null)
                thread.sleep(500);
            for (int i = 0; i < jSonArray.length();i++){
                if (jSonArray == null)
                    thread.sleep(500);
                JSONObject jSonObject = jSonArray.getJSONObject(i);
                Busca busca = new Busca();

                busca.idBusca = jSonObject.getInt("idBusca");
                busca.titulo = jSonObject.getString("titulo");
                busca.descricao = jSonObject.getString("descricao");
                busca.idUsuario = jSonObject.getInt("idUsuario");
                busca.idSubCategoria = jSonObject.getInt("idSubCategoria");
                busca.idStatus = jSonObject.getInt("idStatus");
                busca.imagemBusca = jSonObject.getString("imagemBusca");
                busca.idDDD = jSonObject.getInt("idDDD");
                arrayBuscas.add(busca);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        jSonArray = null;
        return arrayBuscas;
    }

    public ArrayList<Busca> getBuscaByUsuario(int id){

        ArrayList<Busca> arrayBuscas = new ArrayList<Busca>();

        try{
            urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Busca/GetBuscaByUsuario/?idUsuario=" + id;

            Thread thread = new Thread(this);
            thread.start();
            controlaThread();
            thread.interrupt();
            if (jSonArray == null)
                thread.sleep(500);
            for (int i = 0; i < jSonArray.length();i++){
                if (jSonArray == null)
                    thread.sleep(500);
                JSONObject jSonObject = jSonArray.getJSONObject(i);
                Busca busca = new Busca();

                busca.idBusca = jSonObject.getInt("idBusca");
                busca.titulo = jSonObject.getString("titulo");
                busca.descricao = jSonObject.getString("descricao");
                busca.idUsuario = jSonObject.getInt("idUsuario");
                busca.idSubCategoria = jSonObject.getInt("idSubCategoria");
                busca.idStatus = jSonObject.getInt("idStatus");
                busca.imagemBusca = jSonObject.getString("imagemBusca");
                busca.idDDD = jSonObject.getInt("idDDD");
                arrayBuscas.add(busca);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        jSonArray = null;
        return arrayBuscas;
    }

    public Busca getBuscaByID(int id){

        Busca busca = new Busca();
        try{
            urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Busca/GetBusca/" + id;

            Thread thread = new Thread(this);
            thread.start();
            controlaThread();
            thread.interrupt();
            if (jSonArray == null)
                thread.sleep(500);
            JSONObject jSonObject = jSonArray.getJSONObject(0);
            busca.idBusca = jSonObject.getInt("idBusca");
            busca.titulo = jSonObject.getString("titulo");
            busca.descricao = jSonObject.getString("descricao");
            busca.idUsuario = jSonObject.getInt("idUsuario");
            busca.idSubCategoria = jSonObject.getInt("idSubCategoria");
            busca.idStatus = jSonObject.getInt("idStatus");
            busca.imagemBusca = jSonObject.getString("imagemBusca");
            busca.idDDD = jSonObject.getInt("idDDD");

        } catch (Exception e) {
            e.printStackTrace();
        }
        jSonArray = null;
        return busca;
    }



    /* ---------------------------------- MÉTODOS DE CATEGORIAS ----------------------------------- */

    final public Categoria[] getCategoriasVetor() {

        Categoria[] vetorCategorias= null;

        try{
            urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Categoria/gettbCategorias";

            Thread thread = new Thread(this);
            thread.start();
            controlaThread();
            thread.interrupt();
            vetorCategorias= new Categoria[jSonArray.length()];
            if (jSonArray == null)
                thread.sleep(500);
            for (int i = 0; i < jSonArray.length();i++){
                if (jSonArray == null)
                    thread.sleep(500);
                JSONObject jSonObject = jSonArray.getJSONObject(i);
                Categoria categoria = new Categoria();
                categoria.idCategoria = jSonObject.getInt("idCategoria");
                categoria.descricao = jSonObject.getString("descricao");
                vetorCategorias[i] = categoria;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        jSonArray = null;
        return vetorCategorias;
    }

    final public ArrayList<Categoria> getCategoriasVetorComValorDefault() {

        ArrayList<Categoria> vetorCategorias = new ArrayList<Categoria>();

        try{
            urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Categoria/gettbCategorias";

            Thread thread = new Thread(this);
            thread.start();
            controlaThread();
            thread.interrupt();
            Categoria categoria1 = new Categoria();
            categoria1.descricao = "Todos";
            categoria1.idCategoria= 0;
            vetorCategorias.add(categoria1);
            if (jSonArray == null)
                thread.sleep(500);
            for (int i = 0; i < jSonArray.length();i++){
                if (jSonArray == null)
                    thread.sleep(500);
                JSONObject jSonObject = jSonArray.getJSONObject(i);
                Categoria categoria = new Categoria();
                categoria.idCategoria = jSonObject.getInt("idCategoria");
                categoria.descricao = jSonObject.getString("descricao");
                vetorCategorias.add(categoria);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        jSonArray = null;
        return vetorCategorias;
    }

    //Retorna todas as categorias
    final public ArrayList<Categoria> getCategorias(){

        ArrayList<Categoria> arrayCategorias= new ArrayList<Categoria>();
        try{
            urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Categoria/gettbCategorias";

            Thread thread = new Thread(this);
            thread.start();
            controlaThread();
            thread.interrupt();
            if (jSonArray == null)
                thread.sleep(500);
            for (int i = 0; i < jSonArray.length();i++){
                if (jSonArray == null)
                    thread.sleep(500);
                JSONObject jSonObject = jSonArray.getJSONObject(i);
                Categoria categoria = new Categoria();
                categoria.idCategoria = jSonObject.getInt("idCategoria");
                categoria.descricao = jSonObject.getString("descricao");
                arrayCategorias.add(categoria);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        jSonArray = null;
        return  arrayCategorias;
    }

    //Retorna Categoria por ID
    final public Categoria getCategoriaByID(int idCategoria){

        Categoria categoria = new Categoria();
        try{
            urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Categoria/GetCategoria/" + idCategoria;

            Thread thread = new Thread(this);
            thread.start();
            controlaThread();
            thread.interrupt();
            if (jSonArray == null)
                thread.sleep(500);
            JSONObject jSonObject = jSonArray.getJSONObject(0);
            categoria.idCategoria = jSonObject.getInt("idCategoria");
            categoria.descricao = jSonObject.getString("descricao");

        } catch (Exception e) {
            e.printStackTrace();
        }

        jSonArray = null;
        return categoria;
    }

    /* ---------------------------------- MÉTODOS DE SUBCATEGORIAS ----------------------------------- */


    //Retorna todas as subcategorias como vetor
    final public ArrayList<SubCategoria> getSubCategoriasVetorByIdCategoriaComValorDefault(int idCategoria) {
        ArrayList<SubCategoria> vetorSubCategorias = new ArrayList<SubCategoria>();

        try{
            SubCategoria subCategoria1 = new SubCategoria();
            subCategoria1.descricao = "Todos";
            vetorSubCategorias.add(subCategoria1);
            if(idCategoria != 0) {
                urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/SubCategoria/GetSubCategoriaByCategoria/?idCategoria=" + idCategoria;

                Thread thread = new Thread(this);
                thread.start();
                controlaThread();
                thread.interrupt();
                if (jSonArray == null)
                    thread.sleep(500);
                for (int i = 0; i < jSonArray.length(); i++) {
                    if (jSonArray == null)
                        thread.sleep(500);
                    JSONObject jSonObject = jSonArray.getJSONObject(i);
                    SubCategoria subCategoria = new SubCategoria();
                    subCategoria.idCategoria = jSonObject.getInt("idCategoria");
                    subCategoria.idSubCategoria = jSonObject.getInt("idSubCategoria");
                    subCategoria.descricao = jSonObject.getString("descricao");
                    vetorSubCategorias.add(subCategoria);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        jSonArray = null;
        return vetorSubCategorias;
    }

    //Retorna todas as subcategorias como vetor
    final public SubCategoria[] getSubCategoriasVetorByIdCategoria(int idCategoria) {

        SubCategoria[] vetorSubCategorias= null;

        try{
            urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/SubCategoria/GetSubCategoriaByCategoria/?idCategoria=" + idCategoria;

            Thread thread = new Thread(this);
            thread.start();
            controlaThread();
            thread.interrupt();
            vetorSubCategorias = new SubCategoria[jSonArray.length()];
            if (jSonArray == null)
                thread.sleep(500);
            for (int i = 0; i < jSonArray.length();i++){
                if (jSonArray == null)
                    thread.sleep(1000);
                JSONObject jSonObject = jSonArray.getJSONObject(i);
                SubCategoria subCategoria = new SubCategoria();
                subCategoria.idCategoria = jSonObject.getInt("idCategoria");
                subCategoria.idSubCategoria = jSonObject.getInt("idSubCategoria");
                subCategoria.descricao = jSonObject.getString("descricao");
                vetorSubCategorias[i] = subCategoria;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        jSonArray = null;
        return vetorSubCategorias;
    }

    //Retorna todas os DDDs como vetor
    final public SubCategoria[] getSubCategoriasVetor() {

        SubCategoria[] vetorSubCategorias= null;

        try{
            urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/SubCategoria/gettbSubCategorias";

            Thread thread = new Thread(this);
            thread.start();
            controlaThread();
            thread.interrupt();
            vetorSubCategorias = new SubCategoria[jSonArray.length()];
            if (jSonArray == null)
                thread.sleep(500);
            for (int i = 0; i < jSonArray.length();i++){
                if (jSonArray == null)
                    thread.sleep(500);
                JSONObject jSonObject = jSonArray.getJSONObject(i);
                SubCategoria subCategoria = new SubCategoria();
                subCategoria.idCategoria = jSonObject.getInt("idCategoria");
                subCategoria.idSubCategoria = jSonObject.getInt("idSubCategoria");
                subCategoria.descricao = jSonObject.getString("descricao");
                vetorSubCategorias[i] = subCategoria;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        jSonArray = null;
        return vetorSubCategorias;
    }

    //Retorna todas as subcategorias
    final public ArrayList<SubCategoria> getSubCategorias(){

        ArrayList<SubCategoria> arraySubCategorias= new ArrayList<SubCategoria>();

        try{
            urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/SubCategoria/gettbSubCategorias";

            Thread thread = new Thread(this);
            thread.start();
            controlaThread();
            thread.interrupt();
            if (jSonArray == null)
                thread.sleep(1000);
            for (int i = 0; i < jSonArray.length();i++){
                if (jSonArray == null)
                    thread.sleep(1000);
                JSONObject jSonObject = jSonArray.getJSONObject(i);
                SubCategoria subCategoria = new SubCategoria();
                subCategoria.idCategoria = jSonObject.getInt("idCategoria");
                subCategoria.idSubCategoria = jSonObject.getInt("idSubCategoria");
                subCategoria.descricao = jSonObject.getString("descricao");
                arraySubCategorias.add(subCategoria);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        jSonArray = null;
        return  arraySubCategorias;
    }

    //Retorna todas as subcategorias
    final public SubCategoria getSubCategoriasByID(int idSubCategoria){

        SubCategoria subCategoria = new SubCategoria();

        try{
            urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/SubCategoria/GetSubCategoria/" + idSubCategoria;

            Thread thread = new Thread(this);
            thread.start();
            controlaThread();
            thread.interrupt();
            if (jSonArray == null)
                thread.sleep(500);
            JSONObject jSonObject = jSonArray.getJSONObject(0);
            subCategoria.idCategoria = jSonObject.getInt("idCategoria");
            subCategoria.idSubCategoria = jSonObject.getInt("idSubCategoria");
            subCategoria.descricao = jSonObject.getString("descricao");

        } catch (Exception e) {
            e.printStackTrace();
        }

        jSonArray = null;
        return  subCategoria;
    }

    /* ---------------------------------- MÉTODOS DE DDDS ----------------------------------- */
    //Retorna todas os DDDs como vetor
    final public DDD[] getDDDsVetor() {

        DDD[] vetorDDDs = null;

        try{
            urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/DDD/gettbDDDs";

            Thread thread = new Thread(this);
            thread.start();
            controlaThread();
            thread.interrupt();
            vetorDDDs= new DDD[jSonArray.length()];
            if (jSonArray == null)
                thread.sleep(500);
            for (int i = 0; i < jSonArray.length();i++){
                if (jSonArray == null)
                    thread.sleep(500);
                JSONObject jSonObject = jSonArray.getJSONObject(i);
                DDD ddd = new DDD();
                ddd.id = jSonObject.getInt("idDDD");
                ddd.codDDD = jSonObject.getString("codDDD");
                ddd.descricao = jSonObject.getString("descricao");
                vetorDDDs[i] = ddd;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        jSonArray = null;
        return vetorDDDs;
    }

    final public ArrayList<DDD> getDDDsVetorComValorDefault() {
        ArrayList<DDD> vetorDDDs = new ArrayList<DDD>();

        try{
            urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/DDD/gettbDDDs";

            Thread thread = new Thread(this);
            thread.start();
            controlaThread();
            thread.interrupt();
            DDD ddd1 = new DDD();
            ddd1.descricao = "Todos";
            vetorDDDs.add(ddd1);
            if (jSonArray == null)
                thread.sleep(500);
            for (int i = 0; i < jSonArray.length();i++){
                if (jSonArray == null)
                    thread.sleep(500);
                JSONObject jSonObject = jSonArray.getJSONObject(i);
                DDD ddd = new DDD();
                ddd.id = jSonObject.getInt("idDDD");
                ddd.codDDD = jSonObject.getString("codDDD");
                ddd.descricao = jSonObject.getString("descricao");
                vetorDDDs.add(ddd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        jSonArray = null;
        return vetorDDDs;
    }

    //Retorna todas os DDDs
    final public ArrayList<DDD> getDDDs(){

        ArrayList<DDD> arrayDDDs= new ArrayList<DDD>();

        try{
            urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/DDD/gettbDDDs";

            Thread thread = new Thread(this);
            thread.start();
            controlaThread();
            thread.interrupt();
            if (jSonArray == null)
                thread.sleep(1000);
            for (int i = 0; i < jSonArray.length();i++){
                if (jSonArray == null)
                    thread.sleep(1000);
                JSONObject jSonObject = jSonArray.getJSONObject(i);
                DDD ddd = new DDD();
                ddd.id = jSonObject.getInt("idDDD");
                ddd.codDDD = jSonObject.getString("codDDD");
                ddd.descricao = jSonObject.getString("descricao");
                arrayDDDs.add(ddd);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        jSonArray = null;
        return  arrayDDDs;
    }

    /* ----------------------------------------------------------------------------------------- */
    //Verifica se a thread foi executada com sucesso para executar proxima
    private void controlaThread(){
        for(;;){
            if (semaforo)
                break;
        }
    }

    @Override
    public void run() {
        try {
            semaforo = false;
            HttpURLConnection urlConnection = null;
            char[] buffer = new char[1024];
            String jsonString = new String();
            StringBuilder sb = new StringBuilder();
            String line;
            URL url = new URL(urlAPI);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((line = br.readLine()) != null)
                sb.append(line + "\n");

            br.close();
            jsonString = sb.toString();

            jSonArray = new JSONArray(jsonString);
            semaforo = true;
        }catch (Exception e){
            String erro = e.getMessage();
            semaforo = true;
        }
    }

    //------------------CHAT----------------

    public boolean getChatJaExisteEntreOsUsuarios(int usuario1, int usuario2){
        boolean existe = false;
        try{
            //OK
            urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Chat/ChatExiste/?usuarioum=" + usuario1 + "&usuariodois=" + usuario2;
            //SQL = SELECT COUNT(*) AS contagem FROM CHAT WHERE (USUARIO_2 = {usuario_1} AND USUARIO_1 = {usuario_2}) OR (USUARIO_1 = {usuario_1} AND USUARIO_2 = {usuario_2})

            Thread thread = new Thread(this);
            thread.start();
            controlaThread();
            thread.interrupt();
            if (jSonArray == null)
                thread.sleep(500);
            existe = jSonArray.length() != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        jSonArray = null;
        return existe;
    }

    public List<Mensagem> getMensagensDoChat(int idDoChat){
//--------------
//      MOCK:
//
//        List<Mensagem> l = new ArrayList<Mensagem>();
//        Mensagem c = new Mensagem();
//        c.setId(1);
//        c.setChat(1);
//        //c.setMe(true);
//        c.setMensagem("oi");
//        c.setUsuario_origem(2033);
//        c.setUsuario_destino(1017);
//        l.add(c);
//        c = new Mensagem();
//        c.setId(2);
//        c.setChat(1);
//        //c.setMe(false);
//        c.setMensagem("oi tb");
//        c.setUsuario_origem(1017);
//        c.setUsuario_destino(2033);
//        l.add(c);
//        return l;
//--------------
        ArrayList<Mensagem> arrayMensagens= new ArrayList<Mensagem>();

        try{
            //OK
            urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Mensagem/GetMensagensDoChat/" + idDoChat;
            //SQL = SELECT * FROM MENSAGEM WHERE CHAT = {idDoChat} ORDER BY DATA
            Thread thread = new Thread(this);
            thread.start();
            controlaThread();
            thread.interrupt();
            if (jSonArray == null)
                thread.sleep(500);
            for (int i = 0; i < jSonArray.length();i++){
                if (jSonArray == null)
                    thread.sleep(500);
                JSONObject jSonObject = jSonArray.getJSONObject(i);
                Mensagem mensagem = new Mensagem();
                mensagem.setId(jSonObject.getInt("ID"));
                mensagem.setChat(jSonObject.getInt("CHAT"));
                mensagem.setData(jSonObject.getString("DATA"));
                mensagem.setUsuario_origem(jSonObject.getInt("USUARIO_ORIGEM"));
                mensagem.setUsuario_destino(jSonObject.getInt("USUARIO_DESTINO"));
                mensagem.setMensagem(jSonObject.getString("MENSAGEM"));
                arrayMensagens.add(mensagem);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        jSonArray = null;
        return  arrayMensagens;
    }

    public Chat getChatDosUsuarios(int usuario1, int usuario2){
        List<Chat> chats = getChatsDoUsuario(usuario1);
        for (Chat c:
                chats) {
            if (c.getUsuario_2() == usuario2 || c.getUsuario_1() == usuario2)
                return c;
        }
        return null;
    }

    public List<Chat> getChatsDoUsuario(int idDousuario){
//--------------
//      MOCK:
//
//        List<Chat> l = new ArrayList<Chat>();
//        Chat c = new Chat();
//        c.setId(1);
//        c.setUltima_mensagem(1);
//        c.setUsuario_1(2032);
//        c.setUsuario_2(2034);
//        l.add(c);
//        return l;
//--------------
        ArrayList<Chat> chats = new ArrayList<Chat>();
        try{
            //NOK - O nome está correto
            urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Chat/GetChatsInfo/?usuario=" + idDousuario;
            //SQL = SSELECT C.*, M.MENSAGEM FROM CHAT C, MENSAGEM M WHERE (C.USUARIO_1 = {idDousuario} OR C.USUARIO_2 = {IidDousuario}) AND M.ID = C.ULTIMA_MENSAGEM ORDER BY M.DATA
            Thread thread = new Thread(this);
            thread.start();
            controlaThread();
            thread.interrupt();
            if (jSonArray == null)
                thread.sleep(500);
            for (int i = 0; i < jSonArray.length();i++){
                if (jSonArray == null)
                    thread.sleep(500);
                JSONObject jSonObject = jSonArray.getJSONObject(i);
                Chat chat = new Chat();
                chat.setId(jSonObject.getInt("ID"));
                chat.setUsuario_1(jSonObject.getInt("USUARIO_1"));
                chat.setUsuario_2(jSonObject.getInt("USUARIO_2"));
                chat.setUltima_mensagem_texto(jSonObject.getString("MENSAGEM"));
                if (jSonObject.getInt("USUARIO_1") == idDousuario) {
                    chat.setImagem_outro_usuario(jSonObject.getString("imagemUsuario2"));
                    chat.setNome_outro_usuario(jSonObject.getString("nomeUsuario2"));
                } else {
                    chat.setImagem_outro_usuario(jSonObject.getString("imagemUsuario1"));
                    chat.setNome_outro_usuario(jSonObject.getString("nomeUsuario1"));
                }
                chats.add(chat);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        jSonArray = null;
        return  chats;
    }

    public boolean getUsuarioEUsuario1DoChat(int idDoChat, int idDoUsuarioAtual){

        ArrayList<Chat> arrayChats= new ArrayList<Chat>();
        int id = 0;
        try{
            //OK
            urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Chat/gettbChat/" + idDoChat;
            //SQL = SELECT USUARIO_1 FROM CHAT WHERE ID = {idDoChat}
            Thread thread = new Thread(this);
            thread.start();
            controlaThread();
            thread.interrupt();
            JSONObject jSonObject;
            if (jSonArray == null)
                thread.sleep(500);
            jSonObject = jSonArray.getJSONObject(0);
            Chat chat = new Chat();
            id = jSonObject.getInt("USUARIO_1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        jSonArray = null;
        return idDoUsuarioAtual == id;
    }

    public String getImagemMiniaturaDoUsuario(int idUsuario){
//--------------
//      MOCK:
//
//        return "https://pbs.twimg.com/profile_images/2552140292/6umzaqwv0mj922yihwpq_400x400.jpeg";
//--------------
        String imagem = "";
        try{
            //OK
            urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Usuarios/GetUsuario/" + idUsuario;
            //SQL = SELECT U.IMAGEMPERFIL FROM tbUSUARIO U WHERE U.IDusuario IN (SELECT USUARIO_2 FROM CHAT WHERE ID = {CHAT.ID})
            Thread thread = new Thread(this);
            thread.start();
            controlaThread();
            thread.interrupt();
            if (jSonArray == null)
                thread.sleep(500);
            JSONObject jSonObject = jSonArray.getJSONObject(0);
            imagem = jSonObject.getString("imagemPerfil");
        } catch (Exception e) {
            e.printStackTrace();
        }
        jSonArray = null;
        return imagem;
    }

    /* ------------------------------- UTILS -------------------------------------------------- */

    public DDD getDDDByCodigo(String nome) {
        DDD ddd = new DDD();
        ddd.id = 1;
        return ddd;
    }

    public SubCategoria getSubCategoriaByNome(String nome) {
        SubCategoria subCategoria = new SubCategoria();
        subCategoria.idCategoria = 1;
        return subCategoria;
    }

    public String getNomeMiniaturaDoChat(int i, int i1) {

        return "GET USERNAME";
    }

    public ArrayList<Busca> getBuscasExcetoDoUsuario(int idUsuario) {

        ArrayList<Busca> arrayBuscas = new ArrayList<Busca>();

        try{
            urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Busca/GettbBuscas";

            Thread thread = new Thread(this);
            thread.start();
            controlaThread();
            thread.interrupt();
            if (jSonArray == null)
                thread.sleep(500);
            for (int i = 0; i < jSonArray.length();i++){
                if (jSonArray == null)
                    thread.sleep(500);
                JSONObject jSonObject = jSonArray.getJSONObject(i);
                Busca busca = new Busca();

                if (jSonObject.getInt("idUsuario") != idUsuario) {
                    busca.idBusca = jSonObject.getInt("idBusca");
                    busca.titulo = jSonObject.getString("titulo");
                    busca.descricao = jSonObject.getString("descricao");
                    busca.idUsuario = jSonObject.getInt("idUsuario");
                    busca.idSubCategoria = jSonObject.getInt("idSubCategoria");
                    busca.idStatus = jSonObject.getInt("idStatus");
                    busca.imagemBusca = jSonObject.getString("imagemBusca");
                    busca.idDDD = jSonObject.getInt("idDDD");
                    arrayBuscas.add(busca);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        jSonArray = null;
        return arrayBuscas;
    }

    public ArrayList<Servico> getServicosExcetoDoUsuario(int idUsuario) {
        ArrayList<Servico> arrayServicos = new ArrayList<Servico>();

        try{
            urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Servico/GettbServicoes";

            Thread thread = new Thread(this);
            thread.start();
            controlaThread();
            thread.interrupt();
            if (jSonArray == null)
                thread.sleep(500);
            for (int i = 0; i < jSonArray.length();i++){
                if (jSonArray == null)
                    thread.sleep(500);
                JSONObject jSonObject = jSonArray.getJSONObject(i);
                Servico servico = new Servico();

                if (jSonObject.getInt("idUsuario") != idUsuario) {
                    servico.idServico = jSonObject.getInt("idServico");
                    servico.titulo = jSonObject.getString("titulo");
                    servico.descricao = jSonObject.getString("descricao");
                    servico.idUsuario = jSonObject.getInt("idUsuario");
                    servico.idSubCategoria = jSonObject.getInt("idSubCategoria");
                    servico.idStatus = jSonObject.getInt("idStatus");
                    servico.imagemServico = jSonObject.getString("imagemServico");
                    servico.idDDD = jSonObject.getInt("idDDD");

                    arrayServicos.add(servico);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        jSonArray = null;
        return arrayServicos;
    }

    public ArrayList<Busca> getBuscasApenasDoUsuario(int idUsuario) {

        ArrayList<Busca> arrayBuscas = new ArrayList<Busca>();

        try{
            urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Busca/GettbBuscas";

            Thread thread = new Thread(this);
            thread.start();
            controlaThread();
            thread.interrupt();
            if (jSonArray == null)
                thread.sleep(500);
            for (int i = 0; i < jSonArray.length();i++){
                if (jSonArray == null)
                    thread.sleep(500);
                JSONObject jSonObject = jSonArray.getJSONObject(i);
                Busca busca = new Busca();

                if (jSonObject.getInt("idUsuario") == idUsuario) {
                    busca.idBusca = jSonObject.getInt("idBusca");
                    busca.titulo = jSonObject.getString("titulo");
                    busca.descricao = jSonObject.getString("descricao");
                    busca.idUsuario = jSonObject.getInt("idUsuario");
                    busca.idSubCategoria = jSonObject.getInt("idSubCategoria");
                    busca.idStatus = jSonObject.getInt("idStatus");
                    busca.imagemBusca = jSonObject.getString("imagemBusca");
                    busca.idDDD = jSonObject.getInt("idDDD");
                    arrayBuscas.add(busca);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        jSonArray = null;
        return arrayBuscas;
    }

    public ArrayList<Servico> getServicosApenasDoUsuario(int idUsuario) {
        ArrayList<Servico> arrayServicos = new ArrayList<Servico>();

        try{
            urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Servico/GettbServicoes";

            Thread thread = new Thread(this);
            thread.start();
            controlaThread();
            thread.interrupt();
            if (jSonArray == null)
                thread.sleep(500);
            for (int i = 0; i < jSonArray.length();i++){
                if (jSonArray == null)
                    thread.sleep(500);
                JSONObject jSonObject = jSonArray.getJSONObject(i);
                Servico servico = new Servico();

                if (jSonObject.getInt("idUsuario") == idUsuario) {
                    servico.idServico = jSonObject.getInt("idServico");
                    servico.titulo = jSonObject.getString("titulo");
                    servico.descricao = jSonObject.getString("descricao");
                    servico.idUsuario = jSonObject.getInt("idUsuario");
                    servico.idSubCategoria = jSonObject.getInt("idSubCategoria");
                    servico.idStatus = jSonObject.getInt("idStatus");
                    servico.imagemServico = jSonObject.getString("imagemServico");
                    servico.idDDD = jSonObject.getInt("idDDD");

                    arrayServicos.add(servico);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        jSonArray = null;
        return arrayServicos;
    }

    public ArrayList<Integer> getSubCategoriasDaCategoria(int idcategoriaBuscaAvancada) {
        ArrayList<Integer> result = new ArrayList<>();
        for (SubCategoria c : getSubCategorias()) {
            if (c.idCategoria == idcategoriaBuscaAvancada)
                result.add(c.idCategoria);
        };
        return result;
    }

    public String getCodigoSegurancaByEmail(String s) {
        Usuarios usuario = getUsuariosByEmail(s);
        return usuario.codigoSeguranca;
    }
}
