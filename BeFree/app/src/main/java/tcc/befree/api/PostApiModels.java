package tcc.befree.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import tcc.befree.models.*;
import tcc.befree.utils.Utils;

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
        urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Servico/PostServico/";

        jSonObject = new JSONObject();
        try {
            jSonObject.put("idServico",servico.getIdServico());
            jSonObject.put("titulo", Utils.workaroundReplace(servico.getTitulo()));
            jSonObject.put("descricao", Utils.workaroundReplace(servico.getDescricao()));
            jSonObject.put("idUsuario",servico.getIdUsuario());
            jSonObject.put("idSubCategoria",servico.getIdSubCategoria());
            jSonObject.put("idStatus",servico.getIdStatus());
            //jSonObject.put("imagemServico",Utils.criptografarBase64(servico.imagemServico));
            jSonObject.put("imagemServico",servico.getImagemServico());
            jSonObject.put("idDDD",servico.getIdDDD());
            jSonObject.put("preco",servico.getPreco());
            jSonObject.put("formaPagto",servico.getFormaPgto());
            thread.start();
            controlaThread();
            thread.interrupt();

        } catch (JSONException e) {
            e.printStackTrace();
            thread.interrupt();
        }

        return true;
    }

    //CRIAR CHAT E MENSAGEM--------------------------------

    public boolean postChat(Chat chat){
        Thread thread = new Thread(this);
        urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Chat/PosttbChat/";
        jSonObject = new JSONObject();
        try {
            jSonObject.put("ID", chat.getId());
            jSonObject.put("USUARIO_1", chat.getUsuario_1());
            jSonObject.put("USUARIO_2", chat.getUsuario_2());
            jSonObject.put("ULTIMA_MENSAGEM", chat.getUltima_mensagem());

            thread.start();
            controlaThread();
            thread.interrupt();

        } catch (JSONException e) {
            e.printStackTrace();
            thread.interrupt();
        }
        return true;
    }

    public boolean postMensagem(Mensagem mensagem){
        Thread thread = new Thread(this);
        //Testar
        urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Mensagem/PostMensagem/";

        jSonObject = new JSONObject();
        try {
            //jSonObject.put("id",mensagem.getId());
            jSonObject.put("CHAT",mensagem.getChat());
            jSonObject.put("MENSAGEM",Utils.workaroundReplace(mensagem.getMensagem()));
            jSonObject.put("USUARIO_ORIGEM",mensagem.getUsuario_origem());
            jSonObject.put("USUARIO_DESTINO",mensagem.getUsuario_destino());
            jSonObject.put("lida",0);

            thread.start();
            controlaThread();
            thread.interrupt();

        } catch (JSONException e) {
            e.printStackTrace();
            thread.interrupt();
        }

        return true;
    }

    //-----------------------------------------------------


    public boolean  postBusca(Busca busca){
        Thread thread = new Thread(this);
        urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Busca/PostBusca/";

        jSonObject = new JSONObject();
        try {
            jSonObject.put("idBusca",busca.idBusca);
            jSonObject.put("titulo",Utils.workaroundReplace(busca.titulo));
            jSonObject.put("descricao",Utils.workaroundReplace(busca.descricao));
            jSonObject.put("idUsuario",busca.idUsuario);
            jSonObject.put("idSubCategoria",busca.idSubCategoria);
            jSonObject.put("idStatus",busca.idStatus);
            //jSonObject.put("imagemBusca",Utils.criptografarBase64(busca.imagemBusca));
            jSonObject.put("imagemBusca",busca.imagemBusca);
            jSonObject.put("idDDD",busca.idDDD);
            jSonObject.put("preco",busca.getPreco());
            jSonObject.put("formaPagto",busca.getFormaPgto());

            thread.start();
            controlaThread();
            thread.interrupt();

        } catch (JSONException e) {
            e.printStackTrace();
            thread.interrupt();
        }
        return true;
    }

    public boolean postEvento(Evento evento){
        Thread thread = new Thread(this);
        urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Eventos/PostEvento/";

        jSonObject = new JSONObject();
        try {
            jSonObject.put("idServico",evento.getIdServico());
            jSonObject.put("idUsuarioContratante",evento.getIdUsuarioContratante());
            String trashData = evento.getDtEvento();
            String formatTrash[] = trashData.split("/");
            String data = formatTrash[2] + "-" + formatTrash[1] + "-" + formatTrash[0];
            jSonObject.put("dtEvento",data);
            jSonObject.put("hrInicio",evento.getHrInicio());
            jSonObject.put("hrFinal",evento.getHrFinal());
            jSonObject.put("avaliado",evento.isAvaliado());
            jSonObject.put("situacaoEvento",0);

            thread.start();
            controlaThread();
            thread.interrupt();

        } catch (JSONException e) {
            e.printStackTrace();
            thread.interrupt();
            return false;
        }

        return true;
    }

    public boolean postDenuncia(Denuncia denuncia){
        Thread thread = new Thread(this);
        urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Denuncia/PostDenuncia/";

        jSonObject = new JSONObject();
        try {
            jSonObject.put("idServico",denuncia.getIdServico());
            jSonObject.put("idBusca",denuncia.getIdBusca());
            jSonObject.put("causa",denuncia.getDenuncia());
            jSonObject.put("idUsuarioDenunciante",denuncia.getIdUsuarioDenunciante());
            thread.start();
            controlaThread();
            thread.interrupt();
        } catch (JSONException e) {
            e.printStackTrace();
            thread.interrupt();
            return false;
        }
        return true;
    }


    public boolean postUsuarios(Usuarios usuario){

        Thread thread = new Thread(this);
        urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Usuarios/PostUsuario/";

        jSonObject = new JSONObject();
        try {
            jSonObject.put("idUsuario",usuario.idUsuario);
            jSonObject.put("nomeUsuario",Utils.workaroundReplace(usuario.nomeUsuario));
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
            //jSonObject.put("imagemPerfil", Utils.criptografarBase64(Utils.criptografarBase64(usuario.imagemPerfil)));
            jSonObject.put("imagemPerfil", usuario.imagemPerfil);

            thread.start();
            thread.wait();
            controlaThread();
            thread.interrupt();

        } catch (JSONException e) {
            e.printStackTrace();
            thread.interrupt();
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            thread.interrupt();
            return false;
        }

        return true;
    }

    public boolean authenticateUserFacebook(Usuarios usuario){

        Thread thread = new Thread(this);
        urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Usuarios/AuthenticateUsuarioFacebook/";

        jSonObject = new JSONObject();
        try {
            jSonObject.put("idUsuario",usuario.idUsuario);
            jSonObject.put("nomeUsuario",Utils.workaroundReplace(usuario.nomeUsuario));
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
            jSonObject.put("imagemPerfil", usuario.imagemPerfil);

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
            int HttpResult = 0;

            for(;;){
                HttpResult = urlConnection.getResponseCode();
                if(HttpResult != 0){
                    break;
                }
            }

            semaforo = true;

        }catch (Exception e){
            String erro = e.getMessage();
            semaforo = true;

        }
    }
}
