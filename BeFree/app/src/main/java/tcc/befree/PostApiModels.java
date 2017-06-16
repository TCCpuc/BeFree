package tcc.befree;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import tcc.befree.models.*;

/**
 * Created by Guilherme Domingues on 5/23/2017.
 */

public class PostApiModels implements Runnable{

    private JSONArray jSonArray; //Se for retornado mais de um elemento grava num array
    private JSONObject jSonObject; //Se retornado apenas um elemento, retorna um unico objeto
    private int tipoRetornoThread; //Se o
    private boolean semaforo;
    private String urlAPI = "";

    public boolean postServico(Servico servico){
        Thread thread = new Thread(this);
        urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Servicos/PostServico/";

        jSonObject = new JSONObject();
        try {
            jSonObject.put("idUsuario",servico.idUsuario);
            jSonObject.put("ddd",servico.ddd);
            jSonObject.put("descricao",servico.descricao);
            jSonObject.put("idStatus",servico.idStatus);
            jSonObject.put("idSubCategoria",servico.idSubCategoria);
            jSonObject.put("titulo",servico.titulo);
            jSonObject.put("idBusca",servico.idServico);

            thread.start();
            controlaThread();
            thread.interrupt();

        } catch (JSONException e) {
            e.printStackTrace();
            thread.interrupt();
        }

        return true;
    }

    public boolean postBusca(Busca busca){
        Thread thread = new Thread(this);
        urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Buscas/PostBusca/";

        jSonObject = new JSONObject();
        try {
            jSonObject.put("idUsuario",busca.idUsuario);
            jSonObject.put("ddd",busca.ddd);
            jSonObject.put("descricao",busca.descricao);
            jSonObject.put("idStatus",busca.idStatus);
            jSonObject.put("idSubCategoria",busca.idSubCategoria);
            jSonObject.put("titulo",busca.titulo);
            jSonObject.put("idBusca",busca.idBusca);

            thread.start();
            controlaThread();
            thread.interrupt();

        } catch (JSONException e) {
            e.printStackTrace();
            thread.interrupt();
        }

        return true;
    }

    public boolean postUsuarios(Usuarios usuario){

        Thread thread = new Thread(this);
        urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Usuarios/PostUsuario/";

        jSonObject = new JSONObject();
        try {
            jSonObject.put("idUsuario",usuario.idUsuario);
            jSonObject.put("nomeUsuario",usuario.nomeUsuario);
            jSonObject.put("cpf",usuario.cpf);
            jSonObject.put("idCidade",usuario.idCidade);
            jSonObject.put("idEstado",usuario.idEstado);
            jSonObject.put("bairro",usuario.bairro);
            jSonObject.put("logradouro",usuario.logradouro);
            jSonObject.put("numero",usuario.numero);
            jSonObject.put("cep",usuario.cep);
            jSonObject.put("dataNascimento",usuario.dataNascimento);
            jSonObject.put("dataCadastro",usuario.dataCadastro);
            jSonObject.put("ativo",usuario.ativo);
            jSonObject.put("senha",usuario.senha);
            jSonObject.put("email",usuario.email);
            jSonObject.put("ddd",usuario.ddd);

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
            char[] buffer = new char[1024];
            String jsonString = new String();
            StringBuilder sb = new StringBuilder();
            String line;
            URL url = new URL(urlAPI);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", type);
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
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
