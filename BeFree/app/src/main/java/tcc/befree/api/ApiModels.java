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

            for (int i = 0; i < jSonArray.length();i++){
                JSONObject jSonObject = jSonArray.getJSONObject(i);
                Usuarios usuario = new Usuarios();
                usuario.idUsuario = jSonObject.getInt("idUsuario");
                usuario.email = jSonObject.getString("email");
                usuario.nomeUsuario = jSonObject.getString("nomeUsuario");
                usuario.senha = jSonObject.getString("senha");
                usuario.imagemPerfil = jSonObject.getString("imagemPerfil");
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
            usuario.imagemPerfil = jSonObject.getString("imagemPerfil");
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
                servico.imagemServico = jSonObject.getString("imagemServico");
                servico.idDDD = jSonObject.getInt("idDDD");

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
                servico.imagemServico = jSonObject.getString("imagemServico");
                servico.idDDD = jSonObject.getInt("idDDD");

            }

        } catch (JSONException e) {
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
            for (int i = 0; i < jSonArray.length();i++){
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

        } catch (JSONException e) {
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
            for (int i = 0; i < jSonArray.length();i++){
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

        } catch (JSONException e) {
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
            for (int i = 0; i < jSonArray.length();i++){
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
            busca.imagemBusca = jSonObject.getString("imagemBusca");
            busca.idDDD = jSonObject.getInt("idDDD");

        } catch (JSONException e) {
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

            for (int i = 0; i < jSonArray.length();i++){
                JSONObject jSonObject = jSonArray.getJSONObject(i);
                Categoria categoria = new Categoria();
                categoria.idCategoria = jSonObject.getInt("idCategoria");
                categoria.descricao = jSonObject.getString("descricao");
                vetorCategorias[i] = categoria;
            }
        } catch (JSONException e) {
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

            for (int i = 0; i < jSonArray.length();i++){
                JSONObject jSonObject = jSonArray.getJSONObject(i);
                Categoria categoria = new Categoria();
                categoria.idCategoria = jSonObject.getInt("idCategoria");
                categoria.descricao = jSonObject.getString("descricao");
                arrayCategorias.add(categoria);
            }

        } catch (JSONException e) {
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

            JSONObject jSonObject = jSonArray.getJSONObject(0);
            categoria.idCategoria = jSonObject.getInt("idCategoria");
            categoria.descricao = jSonObject.getString("descricao");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        jSonArray = null;
        return categoria;
    }

    /* ---------------------------------- MÉTODOS DE SUBCATEGORIAS ----------------------------------- */


    //Retorna todas os DDDs como vetor
    final public SubCategoria[] getSubCategoriasVetorByIdCategoria(int idCategoria) {

        SubCategoria[] vetorSubCategorias= null;

        try{
            urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/SubCategoria/GetSubCategoriaByCategoria/?idCategoria=" + idCategoria;

            Thread thread = new Thread(this);
            thread.start();
            controlaThread();
            thread.interrupt();
            vetorSubCategorias = new SubCategoria[jSonArray.length()];

            for (int i = 0; i < jSonArray.length();i++){
                JSONObject jSonObject = jSonArray.getJSONObject(i);
                SubCategoria subCategoria = new SubCategoria();
                subCategoria.idCategoria = jSonObject.getInt("idCategoria");
                subCategoria.idSubCategoria = jSonObject.getInt("idSubCategoria");
                subCategoria.descricao = jSonObject.getString("descricao");
                vetorSubCategorias[i] = subCategoria;
            }
        } catch (JSONException e) {
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

            for (int i = 0; i < jSonArray.length();i++){
                JSONObject jSonObject = jSonArray.getJSONObject(i);
                SubCategoria subCategoria = new SubCategoria();
                subCategoria.idCategoria = jSonObject.getInt("idCategoria");
                subCategoria.idSubCategoria = jSonObject.getInt("idSubCategoria");
                subCategoria.descricao = jSonObject.getString("descricao");
                vetorSubCategorias[i] = subCategoria;
            }
        } catch (JSONException e) {
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

    //Retorna todas as subcategorias
    final public SubCategoria getSubCategoriasByID(int idSubCategoria){

        SubCategoria subCategoria = new SubCategoria();

        try{
            urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/SubCategoria/GetSubCategoria/" + idSubCategoria;

            Thread thread = new Thread(this);
            thread.start();
            controlaThread();
            thread.interrupt();

            JSONObject jSonObject = jSonArray.getJSONObject(0);
            subCategoria.idCategoria = jSonObject.getInt("idCategoria");
            subCategoria.idSubCategoria = jSonObject.getInt("idSubCategoria");
            subCategoria.descricao = jSonObject.getString("descricao");

        } catch (JSONException e) {
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

            for (int i = 0; i < jSonArray.length();i++){
                JSONObject jSonObject = jSonArray.getJSONObject(i);
                DDD ddd = new DDD();
                ddd.id = jSonObject.getInt("idDDD");
                ddd.codDDD = jSonObject.getString("codDDD");
                ddd.descricao = jSonObject.getString("descricao");
                vetorDDDs[i] = ddd;
            }
        } catch (JSONException e) {
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

            for (int i = 0; i < jSonArray.length();i++){
                JSONObject jSonObject = jSonArray.getJSONObject(i);
                DDD ddd = new DDD();
                ddd.id = jSonObject.getInt("idDDD");
                ddd.codDDD = jSonObject.getString("codDDD");
                ddd.descricao = jSonObject.getString("descricao");
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

    //------------------CHAT----------------

    public boolean getChatJaExisteEntreOsUsuarios(int usuario1, int usuario2){
        //URL = https://befreeapi-com.umbler.net/BeFreeAPI/api/Chat/ChatExiste
        //SQL = SELECT ID FROM CHAT WHERE (USUARIO_2 = {usuario_1} AND USUARIO_1 = {usuario_2}) OR (USUARIO_1 = {usuario_1} AND USUARIO_2 = {usuario_2})
        return true;
    }

    public List<Mensagem> getMensagensDoChat(int idDoChat){
        //URL = https://befreeapi-com.umbler.net/BeFreeAPI/api/Chat/GetMensagensDoChat/
        //SQL = SELECT * FROM MENSAGEM WHERE CHAT = {idDoChat} ORDER BY DATA
        List<Mensagem> l = new ArrayList<Mensagem>();
        Mensagem c = new Mensagem();
        c.setId(1);
        c.setChat(1);
        //c.setMe(true);
        c.setMensagem("oi");
        c.setUsuario_origem(2033);
        c.setUsuario_destino(1017);
        l.add(c);
        c = new Mensagem();
        c.setId(2);
        c.setChat(1);
        //c.setMe(false);
        c.setMensagem("oi tb");
        c.setUsuario_origem(1017);
        c.setUsuario_destino(2033);
        l.add(c);
        return l;
    }

    public List<Chat> getChatsDoUsuario(int idDousuario){
        //URL = https://befreeapi-com.umbler.net/BeFreeAPI/api/Chat/GetChatsDoUsuario/
        //SQL = SELECT C.ID FROM CHAT C, MENSAGEM M WHERE (C.USUARIO_1 = {idDousuario} OR C.USUARIO_2 = {idDousuario}) AND M.ID = C.ULTIMA_MENSAGEM ORDER BY M.DATA
        List<Chat> l = new ArrayList<Chat>();
        Chat c = new Chat();
        c.setId(1);
        c.setUltima_mensagem(1);
        c.setUsuario_1(1017);
        c.setUsuario_2(2033);
        l.add(c);
        return l;
    }

    public boolean getUsuarioEUsuario1DoChat(int idDoChat, int idDoUsuarioAtual){
        //URL = https://befreeapi-com.umbler.net/BeFreeAPI/api/Chat/getUsuario1DoChat/
        //SQL = SELECT USUARIO_1 FROM CHAT WHERE ID = {idDoChat}
        return idDoUsuarioAtual == 1;
    }

    public String getImagemMiniaturaDoChat(int idDoChat, int idDoUsuarioAtual){
        if (getUsuarioEUsuario1DoChat(idDoChat, idDoUsuarioAtual)){
            //URL = https://befreeapi-com.umbler.net/BeFreeAPI/api/Chat/GetImagemMiniaturaDoChatDoUsuario2/
            //SQL = SELECT U.IMAGEMPERFIL FROM tbUSUARIO U WHERE U.IDusuario IN (SELECT USUARIO_2 FROM CHAT WHERE ID = {CHAT.ID})
        }
 	    else{
            //URL = https://befreeapi-com.umbler.net/BeFreeAPI/api/Chat/GetImagemMiniaturaDoChatDoUsuario1/
            //SQL = SELECT U.IMAGEMPERFIL FROM tbUSUARIO U WHERE U.IDusuario IN (SELECT USUARIO_1 FROM CHAT WHERE ID = {CHAT.ID})
        }
        return "https://pbs.twimg.com/profile_images/2552140292/6umzaqwv0mj922yihwpq_400x400.jpeg";
    }

    public String getTextoMiniaturaDoChat(int idDoChat){
        //URL = https://befreeapi-com.umbler.net/BeFreeAPI/api/Chat/GetTextoMiniaturaDoChat/
        //SQL = SELECT M.MENSAGEM FROM MENSAGEM M, CHAT C WHERE C.ID = {idDoChat} AND C.ULTIMA_MENSAGEM = M.ID
        return "";
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
}
