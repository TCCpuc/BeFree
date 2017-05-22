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

    //Json que será retornado para prencher model
    private JSONArray jSonArray;
    private boolean semaforo;
    private String urlAPI = "";

    //Retorna todos os Usuários
    final public ArrayList<Usuarios> getUsuarios(){

        ArrayList<Usuarios> arrayUsuarios = new ArrayList<Usuarios>();

        try{
            urlAPI = "http://befree.somee.com/BeFreeAPI/api/GettbUsuarios";

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
            urlAPI = "http://befree.somee.com/BeFreeAPI/api/Usuarios/GetUsuarioByEmail/?email=" + email;

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

    //Retorna todos os Servicos
    public ArrayList<Servico> getServicos(){

        ArrayList<Servico> arrayServicos = new ArrayList<Servico>();

        try{
            urlAPI = "http://befree.somee.com/BeFreeAPI/api/Servico/GettbServicoes";

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
            urlAPI = "http://befree.somee.com/BeFreeAPI/api/Servico/GettbServicoes/"+id;

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

    //Retorna todas as Buscas
    public ArrayList<Busca> getBuscas(){

        ArrayList<Busca> arrayBuscas = new ArrayList<Busca>();

        try{
            urlAPI = "http://befree.somee.com/BeFreeAPI/api/Busca/GettbBuscas";

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
        }
    }
}
