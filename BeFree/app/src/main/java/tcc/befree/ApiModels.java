package tcc.befree;


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

import tcc.befree.models.*;

/**
 * Created by Guilherme Domingues on 5/20/2017.
 */

public class ApiModels {

    //Json que será retornado para prencher model
    private JSONArray jSonArray;

    public ArrayList<Usuarios> getUsuarios(){

        ArrayList<Usuarios> arrayUsuarios = new ArrayList<Usuarios>();

        try{
            getJson("http://befree.somee.com/BeFreeAPI/api/Usuarios");

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

        return  arrayUsuarios;
    }

    private void getJson(final String urlString) {
        // Toda chamada externa necessita rodar em background, então utilizamos thread
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    HttpURLConnection urlConnection = null;
                    char[] buffer = new char[1024];
                    String jsonString = new String();
                    StringBuilder sb = new StringBuilder();
                    String line;
                    URL url = new URL(urlString);

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

                }catch (Exception e){

                }
            }
        }).start();
    }

    public JSONObject postJSONObjectFromURL(){

        return null;
    }


}
