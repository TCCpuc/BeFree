package tcc.befree.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
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
    public Servico getServicosById(int id){

        Servico servico = new Servico();

        try{
            urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Servico/DeleteServico/?id=" + id;

            Thread thread = new Thread(this);
            thread.start();
            controlaThread();
            thread.interrupt();
            if (jSonArray == null)
                thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        jSonArray = null;
        return servico;
    }

    public Busca getBuscaByID(int id){

        Busca busca = new Busca();
        try{
            urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Busca/DeleteBusca/?id=" + id;

            Thread thread = new Thread(this);
            thread.start();
            controlaThread();
            thread.interrupt();
            if (jSonArray == null)
                thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        jSonArray = null;
        return busca;
    }

    @Override
    public void run() {
        try {
            String type = "application/json";

            semaforo = false;
            HttpURLConnection urlConnection = null;
            StringBuilder sb = new StringBuilder();
            String line;
            URL url = new URL(urlAPI);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", type);
            urlConnection.setReadTimeout(25000 /* milliseconds */);
            urlConnection.setConnectTimeout(30000 /* milliseconds */);
            urlConnection.setDoOutput(true);
            urlConnection.connect();

            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.writeBytes(jSonObject.toString());
            wr.flush();
            wr.close();

            int HttpResult =urlConnection.getResponseCode();
            /*if(HttpResult ==HttpURLConnection.HTTP_OK){
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream(),"utf-8"));
                line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();

                System.out.println("" + sb.toString());

            }else{
                System.out.println(urlConnection.getResponseMessage());
            }*/

            semaforo = true;
        }catch (Exception e){
            String erro = e.getMessage();
            semaforo = true;
        }
    }
}
