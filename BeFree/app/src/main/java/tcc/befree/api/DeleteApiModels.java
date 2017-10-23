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

    public boolean postServico(Servico servico){
        Thread thread = new Thread(this);
        urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Servico/PostServico/";

        jSonObject = new JSONObject();
        try {
            jSonObject.put("idServico",servico.idServico);
            jSonObject.put("titulo",servico.titulo);
            jSonObject.put("descricao",servico.descricao);
            jSonObject.put("idUsuario",servico.idUsuario);
            jSonObject.put("idSubCategoria",servico.idSubCategoria);
            jSonObject.put("idStatus",servico.idStatus);
            //jSonObject.put("imagemServico",Utils.criptografarBase64(servico.imagemServico));
            jSonObject.put("imagemBusca",servico.imagemServico);
            jSonObject.put("idDDD",servico.idDDD);

            thread.start();
            controlaThread();
            thread.interrupt();

        } catch (JSONException e) {
            e.printStackTrace();
            thread.interrupt();
        }

        return true;
    }

    public boolean  postBusca(Busca busca){
        Thread thread = new Thread(this);
        urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Busca/PostBusca/";

        jSonObject = new JSONObject();
        try {
            jSonObject.put("idBusca",busca.idBusca);
            jSonObject.put("titulo",busca.titulo);
            jSonObject.put("descricao",busca.descricao);
            jSonObject.put("idUsuario",busca.idUsuario);
            jSonObject.put("idSubCategoria",busca.idSubCategoria);
            jSonObject.put("idStatus",busca.idStatus);
            //jSonObject.put("imagemBusca",Utils.criptografarBase64(busca.imagemBusca));
            jSonObject.put("imagemBusca",busca.imagemBusca);
            jSonObject.put("idDDD",busca.idDDD);

            thread.start();
            controlaThread();
            thread.interrupt();

        } catch (JSONException e) {
            e.printStackTrace();
            thread.interrupt();
        }

        return true;
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
