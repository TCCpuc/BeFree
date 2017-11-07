package tcc.befree.activities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import tcc.befree.R;
import tcc.befree.api.ApiModels;
import tcc.befree.models.CircleImageView;
import tcc.befree.models.Evento;
import tcc.befree.telas.Dialog.GenderEvaluationDialog;
import tcc.befree.telas.Dialog.GenderServiceDialog;

/**
 * Created by guilherme.leme on 10/23/17.
 */

public class GenderActivity extends AppCompatActivity {

    //private ListView oldday;
    private ListView day;
    //private ListView time;
    private ArrayList<Evento> gender;
    private ApiModels api;
    private Evento evento;
    private String beforeDate = "";
    private int idUsuario;
    private boolean notEventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender);

        Bundle intent = this.getIntent().getExtras();
        idUsuario = intent.getInt("idUsuario");

        notEventos = false;
        api = new ApiModels();

        day = (ListView) findViewById(R.id.gender_day);

        day.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                evento = gender.get(position);
                //time.setAdapter(new GenderActivity.TimeAdapter());
                GenderServiceDialog dialog = new GenderServiceDialog(GenderActivity.this, evento);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });
        setAdapter();
    }

    public void setAdapter(){
        gender = api.getEventosbyIdUsuario(idUsuario);// enviar id do usuario
        this.day.setAdapter(new GenderActivity.DayAdapter());
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private class DayAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            //RETORNA QUANTOS EVENTOS/DIA ENCONTROU
            if(gender.size() == 0){
                notEventos = true;  //SE NAO ENCONTRAR EVENTOS EMITE MENSSAGEM
                return 1;
            }else {
                return gender.size();
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

            View view;
            view = getLayoutInflater().inflate(R.layout.item_agenda, null);
            TextView titulo = (TextView) view.findViewById(R.id.item_agenda_title);
            TextView descricao = (TextView) view.findViewById(R.id.item_agenda_description);
            TextView tempo = (TextView) view.findViewById(R.id.item_agenda_time);
            TextView dia = (TextView) view.findViewById(R.id.item_agenda_day);
            CircleImageView image = (CircleImageView) view.findViewById(R.id.item_agenda_imagem);
            Button avaliar = (Button) view.findViewById(R.id.item_agenda_button);
            LinearLayout backgroundLayout = (LinearLayout) view.findViewById(R.id.item_agenda_layout_background);
            LinearLayout defaultLayout = (LinearLayout) view.findViewById(R.id.item_agenda_default_layout);
            LinearLayout dayLayout = (LinearLayout) view.findViewById(R.id.item_agenda_layout_day);
            final LinearLayout avaliarLayout = (LinearLayout) view.findViewById(R.id.item_agenda_layout_avaliar);

            if(notEventos){
                backgroundLayout.setVisibility(View.GONE);
                dia.setText("Você não tem nenhum evento agendado");
            }else {
                final Evento ev = gender.get(position);
                avaliar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //time.setAdapter(new GenderActivity.TimeAdapter());
                        GenderEvaluationDialog dialog = new GenderEvaluationDialog(GenderActivity.this, ev);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();
                        avaliarLayout.setVisibility(View.GONE);
                    }
                });

                Picasso.with(GenderActivity.this).load(ev.getImagem()).into(image);
                dia.setText(ev.getDtEvento());
                titulo.setText(ev.getTitulo());
                descricao.setText(ev.getConteudo());
                String horario;

                if(ev.isAvaliado()) {
                    tempo.setText("AVALIADO\n\n" + ev.getNotaAvaliacao());
                }else if(oldDate(ev.getDtEvento()) && (ev.getSituacaoEvento() == 0)){
                    ev.setSituacaoEvento(2);
                    tempo.setText("RECUSADO");
                    backgroundLayout.setBackgroundColor(Color.parseColor("#ffe6e6"));
                }else if(!ev.isAvaliado() && oldDate(ev.getDtEvento()) && ev.getSituacaoEvento() != 2){
                    defaultLayout.setVisibility(View.GONE);
                    avaliarLayout.setVisibility(View.VISIBLE);
                }else if(ev.getSituacaoEvento() == 2){
                    tempo.setText("RECUSADO");
                    backgroundLayout.setBackgroundColor(Color.parseColor("#ffe6e6"));
                }else if (ev.getSituacaoEvento() == 1){
                    if(ev.getHrInicio() <= 9){
                        horario = ("CONFIRMADO \n\n0" + ev.getHrInicio() + ":00");
                    }else{
                        horario = ("CONFIRMADO \n\n" + ev.getHrInicio() + ":00");
                    }
                    if(ev.getHrFinal() <= 9){
                        horario = (horario + " - 0" + ev.getHrFinal() + ":00");
                    }else {
                        horario = (horario + " - " + ev.getHrFinal() + ":00");
                    }
                    backgroundLayout.setBackgroundColor(Color.parseColor("#b3ffb3"));
                    tempo.setText(horario);
                }
                else {
                    if(ev.getHrInicio() <= 9){
                        horario = ("PENDENTE \n\n0" + ev.getHrInicio() + ":00");
                    }else{
                        horario = ("PENDENTE \n\n" + ev.getHrInicio() + ":00");
                    }
                    if(ev.getHrFinal() <= 9){
                        horario = (horario + " - 0" + ev.getHrFinal() + ":00");
                    }else {
                        horario = (horario + " - " + ev.getHrFinal() + ":00");
                    }
                    tempo.setText(horario);
                }

                if(beforeDate.equals(ev.getDtEvento())){
                    dayLayout.setVisibility(View.GONE);
                }else {
                    beforeDate = ev.getDtEvento();
                }
            }

            return view;
        }
    }

    public boolean oldDate(String data){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String today [] = dateFormat.format(date).split("/");
        String oldDay [] = data.split("/");
        int oDia = Integer.parseInt(oldDay[0]);
        int oMes = Integer.parseInt(oldDay[1]);
        int oAno = Integer.parseInt(oldDay[2]);
        int tDia = Integer.parseInt(today[0]);
        int tMes = Integer.parseInt(today[1]);
        int tAno = Integer.parseInt(today[2]);

        if(oAno < tAno){
            return true;
        }else if(oMes < tMes && oAno == tAno ){
            return true;
        }else if(oDia < tDia && oMes == tMes && oAno == tAno){
            return true;
        }else {
            return false;
        }
    }

    public Evento getEvento(){
        return this.evento;
    }
}