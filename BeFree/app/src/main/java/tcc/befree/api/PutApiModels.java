package tcc.befree.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import tcc.befree.models.Busca;
import tcc.befree.models.Evento;
import tcc.befree.models.Servico;
import tcc.befree.models.Usuarios;
import tcc.befree.utils.Utils;

/**
 * Created by Guilherme Domingues on 10/9/2017.
 */

public class PutApiModels implements Runnable {

    private JSONArray jSonArray; //Se for retornado mais de um elemento grava num array
    private JSONObject jSonObject; //Se retornado apenas um elemento, retorna um unico objeto
    private int tipoRetornoThread; //Se o
    private boolean semaforo;
    private String urlAPI = "";


    public boolean putUsuarios(Usuarios usuario){

        Thread thread = new Thread(this);
        urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Usuarios/PutUsuario/" + usuario.idUsuario ;

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
            jSonObject.put("imagemPerfil", usuario.imagemPerfil);
            jSonObject.put("codigoSeguranca", "");

            thread.start();
            controlaThread();
            thread.interrupt();

        } catch (JSONException e) {
            e.printStackTrace();
            thread.interrupt();
        }

        return true;
    }

    public boolean putStatusEvento(Evento ev){
        //Todo Criar metodo no banco para alterar status no banco;
        ev.getSituacaoEvento();//0 ou 1 ou 2
        ev.getIdEvento();
        return true;
    }

    public boolean putNotaEvento(Evento ev){
        //Todo Criar metodo no banco para alterar nota do evento no banco;
        ev.getNotaAvalicao();//0 ou 1 ou 2
        ev.getIdEvento();
        return true;
    }

    public boolean putServico(Servico servico){
        Thread thread = new Thread(this);
        urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Servico/PutServico/" + servico.getIdServico();

        jSonObject = new JSONObject();
        try {
            jSonObject.put("idServico",servico.getIdServico());
            jSonObject.put("titulo",servico.getTitulo());
            jSonObject.put("descricao",servico.getDescricao());
            jSonObject.put("idUsuario",servico.getIdUsuario());
            jSonObject.put("idSubCategoria",servico.getIdSubCategoria());
            jSonObject.put("idStatus",servico.getIdStatus());
            jSonObject.put("imagemServico", Utils.criptografarBase64(servico.getImagemServico()));
            jSonObject.put("idDDD",servico.getIdDDD());

            thread.start();
            controlaThread();
            thread.interrupt();

        } catch (JSONException e) {
            e.printStackTrace();
            thread.interrupt();
        }

        return true;
    }

    public boolean putEvento(Evento evento){
        Thread thread = new Thread(this);
        urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Evento/PutEvento/" + evento.getIdEvento();

        jSonObject = new JSONObject();
        try {
            jSonObject.put("idServico",evento.getIdServico());
            jSonObject.put("idUsuarioContratante",evento.getIdUsuarioContratante());
            //jSonObject.put("dtEvento",evento.getDtEvento()); VERIFICAR
            jSonObject.put("hrInicio",evento.getHrInicio());
            jSonObject.put("hrFinal",evento.getHrFinal());
            jSonObject.put("notaAvaliacao",evento.getNotaAvalicao());
            jSonObject.put("avaliado",evento.isAvaliado());
            jSonObject.put("situacaoEvento",evento.getSituacaoEvento());

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

    public boolean  putBusca(Busca busca){
        Thread thread = new Thread(this);
        urlAPI = "https://befreeapi-com.umbler.net/BeFreeAPI/api/Busca/PutBusca/" + busca.idBusca;

        jSonObject = new JSONObject();
        try {
            jSonObject.put("idBusca",busca.idBusca);
            jSonObject.put("titulo",busca.titulo);
            jSonObject.put("descricao",busca.descricao);
            jSonObject.put("idUsuario",busca.idUsuario);
            jSonObject.put("idSubCategoria",busca.idSubCategoria);
            jSonObject.put("idStatus",busca.idStatus);
            jSonObject.put("imagemBusca",Utils.criptografarBase64(busca.imagemBusca));
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

            semaforo = true;
        }catch (Exception e){
            String erro = e.getMessage();
            semaforo = true;
        }
    }

}
