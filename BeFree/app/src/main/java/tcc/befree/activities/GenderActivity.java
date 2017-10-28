package tcc.befree.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import tcc.befree.R;
import tcc.befree.api.ApiModels;
import tcc.befree.models.Evento;

/**
 * Created by guilherme.leme on 10/23/17.
 */

public class GenderActivity extends AppCompatActivity {

    private ListView oldday;
    private ListView day;
    private ListView time;
    private ArrayList<Evento> gender;
    private ApiModels api;
    private int horas;
    private Evento evento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender);

        api = new ApiModels();
        gender = api.getEventosbyIdUsuario(1);// enviar id do usuario
        day = (ListView) findViewById(R.id.gender_day);
        time = (ListView) findViewById(R.id.gender_time);
        oldday = (ListView) findViewById(R.id.gender_old_day);
        horas = 0;


        oldday.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        oldday.setAdapter(new GenderActivity.OldDayAdapter());

        day.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                evento = gender.get(position);
                horas = (evento.getHrFinal() - evento.getHrInicio());
                time.setAdapter(new GenderActivity.TimeAdapter());

            }
        });

        day.setAdapter(new GenderActivity.DayAdapter());

        time.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private class DayAdapter extends BaseAdapter {
        @Override
        public int getCount() {

            //RETORNA QUANTOS EVENTOS/DIA ENCONTROU
            return gender.size();
        }

        @Override
        public Object getItem(int position) {
            return gender.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            View view = getLayoutInflater().inflate(R.layout.item_agenda, null);
            TextView titulo = (TextView) view.findViewById(R.id.item_agenda_title);
            TextView tempo = (TextView) view.findViewById(R.id.item_agenda_time);
            Evento ev = gender.get(position);
            tempo.setText(ev.getDtEvento());
            titulo.setText(ev.getTitulo());
            horas = (ev.getHrFinal() - ev.getHrInicio());
            /*
                tempo.setText("24/11/2017");
                titulo.setText("Tosa para Cachorro");

            */
            return view;
        }
    }

    private class OldDayAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            //RETORNA QUANTOS EVENTOS/DIA ENCONTROU
            return 3;
        }

        @Override
        public Object getItem(int position) {
            return gender.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.item_agenda, null);
            TextView titulo = (TextView) view.findViewById(R.id.item_agenda_title);
            TextView tempo = (TextView) view.findViewById(R.id.item_agenda_time);
            if(position==0){
                tempo.setText("19/02/2017");
                titulo.setText("Pedreiro para Argila");
            }else if(position==1){
                tempo.setText("13/08/2017");
                titulo.setText("Serviço de Buffet");
            }else {
                tempo.setText("29/09/2017");
                titulo.setText("Lavagem de Carros");
            }

            return view;
        }
    }

    private class TimeAdapter extends BaseAdapter {
        @Override
        public int getCount() {
             if(horas != 0){
                 return horas;
             }else {
                 return 0;
             }
        }

        @Override
        public Object getItem(int position) {
            return gender.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.item_agenda, null);
            TextView titulo = (TextView) view.findViewById(R.id.item_agenda_title);
            TextView tempo = (TextView) view.findViewById(R.id.item_agenda_time);
            String horario;
            if((evento.getHrInicio() + position) <= 9){
                horario = ("0" + (evento.getHrInicio() + position) + ":00");
            }else{
                horario = ((evento.getHrInicio() + position) + ":00");
            }
            if((evento.getHrInicio() + position + 1) <= 9){
                horario = (horario + " - 0" + (evento.getHrInicio() + position + 1) + ":00");
            }else {
                horario = (horario + " - " + (evento.getHrInicio() + position + 1) + ":00");
            }
            tempo.setText(horario);
            titulo.setText(evento.getTitulo());

            return view;
        }
    }
}