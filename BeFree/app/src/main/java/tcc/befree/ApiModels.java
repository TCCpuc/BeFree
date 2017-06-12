package tcc.befree;


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
import java.util.concurrent.ExecutionException;

import tcc.befree.models.*;

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

            for (int i = 0; i < jSonArray.length();i++){
                JSONObject jSonObject = jSonArray.getJSONObject(i);
                Usuarios usuario = new Usuarios();
                usuario.idUsuario = jSonObject.getInt("idUsuario");
                usuario.email = jSonObject.getString("email");
                usuario.nomeUsuario = jSonObject.getString("nomeUsuario");
                usuario.senha = jSonObject.getString("senha");
                arrayUsuarios.add(usuario);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        jSonArray = null;
        return  arrayUsuarios;
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

            JSONObject jSonObject = jSonArray.getJSONObject(0);
            usuario.idUsuario = jSonObject.getInt("idUsuario");
            usuario.email = jSonObject.getString("email");
            usuario.nomeUsuario = jSonObject.getString("nomeUsuario");
            usuario.senha = jSonObject.getString("senha");

        }catch (Exception er){
            er.printStackTrace();
        }

        jSonArray = null;
        return  usuario;
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

            for (int i = 0; i < jSonArray.length();i++){
                JSONObject jSonObject = jSonArray.getJSONObject(i);
                Servico servico = new Servico();

                servico.idServico = jSonObject.getInt("idServico");
                servico.titulo = jSonObject.getString("titulo");
                servico.descricao = jSonObject.getString("descricao");
                servico.idUsuario = jSonObject.getInt("idUsuario");
                servico.idSubCategoria = jSonObject.getInt("idSubCategoria");
                servico.idStatus = jSonObject.getInt("idStatus");

                arrayServicos.add(servico);
            }

        } catch (JSONException e) {
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

            for (int i = 0; i < jSonArray.length();i++){
                JSONObject jSonObject = jSonArray.getJSONObject(i);

                servico.idServico = jSonObject.getInt("idServico");
                servico.titulo = jSonObject.getString("titulo");
                servico.descricao = jSonObject.getString("descricao");
                servico.idUsuario = jSonObject.getInt("idUsuario");
                servico.idSubCategoria = jSonObject.getInt("idSubCategoria");
                servico.idStatus = jSonObject.getInt("idStatus");

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        jSonArray = null;
        return servico;
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
            for (int i = 0; i < jSonArray.length();i++){
                JSONObject jSonObject = jSonArray.getJSONObject(i);
                Busca busca = new Busca();

                busca.idBusca = jSonObject.getInt("idBusca");
                busca.titulo = jSonObject.getString("titulo");
                busca.descricao = jSonObject.getString("descricao");
                busca.idUsuario = jSonObject.getInt("idUsuario");
                busca.idSubCategoria = jSonObject.getInt("idSubCategoria");
                busca.idStatus = jSonObject.getInt("idStatus");

                arrayBuscas.add(busca);
            }

        } catch (JSONException e) {
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
            JSONObject jSonObject = jSonArray.getJSONObject(0);


            busca.idBusca = jSonObject.getInt("idBusca");
            busca.titulo = jSonObject.getString("titulo");
            busca.descricao = jSonObject.getString("descricao");
            busca.idUsuario = jSonObject.getInt("idUsuario");
            busca.idSubCategoria = jSonObject.getInt("idSubCategoria");
            busca.idStatus = jSonObject.getInt("idStatus");


        } catch (JSONException e) {
            e.printStackTrace();
        }
        jSonArray = null;
        return busca;
    }

    /* ---------------------------------- MÉTODOS DE CATEGORIAS ----------------------------------- */
    final public String[] getCategoriasVetor() {

        String[] arrayCategorias= null;

        try{
            urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Categoria/gettbCategorias";

            Thread thread = new Thread(this);
            thread.start();
            controlaThread();
            thread.interrupt();
            arrayCategorias= new String[jSonArray.length()];

            for (int i = 0; i < jSonArray.length();i++){
                JSONObject jSonObject = jSonArray.getJSONObject(i);
                String descricao = jSonObject.getString("descricao");
                arrayCategorias[i] = descricao;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jSonArray = null;
        return arrayCategorias;
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

            for (int i = 0; i < jSonArray.length();i++){
                JSONObject jSonObject = jSonArray.getJSONObject(i);
                Categoria categoria = new Categoria();
                categoria.idCategoria = jSonObject.getInt("idCategoria");
                categoria.descricao = jSonObject.getString("desricao");
                arrayCategorias.add(categoria);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        jSonArray = null;
        return  arrayCategorias;
    }

    /* ---------------------------------- MÉTODOS DE SUBCATEGORIAS ----------------------------------- */

    //Retorna todas os DDDs como vetor
    final public String[] getSubCategoriasVetor() {

        String[] arraySubCategorias= null;

        try{
            urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/SubCategoria/gettbSubCategorias";

            Thread thread = new Thread(this);
            thread.start();
            controlaThread();
            thread.interrupt();
            arraySubCategorias = new String[jSonArray.length()];

            for (int i = 0; i < jSonArray.length();i++){
                JSONObject jSonObject = jSonArray.getJSONObject(i);
                String descricao = jSonObject.getString("descricao");
                arraySubCategorias[i] = descricao;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jSonArray = null;
        return arraySubCategorias;
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

            for (int i = 0; i < jSonArray.length();i++){
                JSONObject jSonObject = jSonArray.getJSONObject(i);
                SubCategoria subCategoria = new SubCategoria();
                subCategoria.idCategoria = jSonObject.getInt("idCategoria");
                subCategoria.idSubCategoria = jSonObject.getInt("idSubCategoria");
                subCategoria.descricao = jSonObject.getString("descricao");
                arraySubCategorias.add(subCategoria);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        jSonArray = null;
        return  arraySubCategorias;
    }



    /* ---------------------------------- MÉTODOS DE DDDS ----------------------------------- */
    //Retorna todas os DDDs como vetor
    final public String[] getDDDsVetor() {

        String[] arrayDDDs = null;

        try{
            urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/DDD/gettbDDDs";

            Thread thread = new Thread(this);
            thread.start();
            controlaThread();
            thread.interrupt();
            arrayDDDs= new String[jSonArray.length()];

            for (int i = 0; i < jSonArray.length();i++){
                JSONObject jSonObject = jSonArray.getJSONObject(i);
                String ddd = new String();
                ddd = jSonObject.getString("codDDD");
                arrayDDDs[i] = ddd;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jSonArray = null;
        return arrayDDDs;
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

            for (int i = 0; i < jSonArray.length();i++){
                JSONObject jSonObject = jSonArray.getJSONObject(i);
                DDD ddd = new DDD();
                ddd.id = jSonObject.getInt("idDDD");
                ddd.descricao = jSonObject.getString("codDDD");
                arrayDDDs.add(ddd);
            }

        } catch (JSONException e) {
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
}
