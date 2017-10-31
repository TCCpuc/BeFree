package tcc.befree.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import tcc.befree.models.Busca;
import tcc.befree.models.Chat;
import tcc.befree.models.Mensagem;
import tcc.befree.models.Servico;
import tcc.befree.models.Usuarios;

/**
 * Created by Guilherme Domingues on 5/23/2017.
 */

public class DeleteApiModels implements Runnable{

    private JSONArray jSonArray; //Se for retornado mais de um elemento grava num array
    private JSONObject jSonObject; //Se retornado apenas um elemento, retorna um unico objeto
    private int tipoRetornoThread; //Se o
    private boolean semaforo;
    private String urlAPI = "";

    //Verifica se a thread foi executada com sucesso para executar proxima
    private void controlaThread(){
        for(;;){
            if (semaforo)
                break;
        }
    }

    //Retorna Servicos pelo id
    public Servico deleteServicosById(int id){

        Servico servico = new Servico();

        try{
            urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Servico/DeleteServico/?id=" + id;

            Thread thread = new Thread(this);
            thread.start();
            controlaThread();
            thread.interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
        jSonArray = null;
        return servico;
    }

    public Busca deleteBuscaByID(int id){

        Busca busca = new Busca();
        try{
            urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Busca/DeleteBusca/?id=" + id;

            Thread thread = new Thread(this);
            thread.start();
            controlaThread();
            thread.interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
        jSonArray = null;
        return busca;
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
}
