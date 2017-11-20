package tcc.befree.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import tcc.befree.R;
import tcc.befree.api.PostApiModels;
import tcc.befree.models.Evento;
import tcc.befree.telas.Dialog.LoadingDialog;

/**
 * Created by guilherme.leme on 8/17/17.
 */

public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private TextView dia;
    private TextView horaInicial;
    private TextView horaFinal;
    private ImageButton horaInicialUp;
    private ImageButton horaInicialDown;
    private ImageButton horaFinalUp;
    private ImageButton horaFinalDown;
    private Button agendar;
    private Evento novoEvento;
    private PostApiModels post;
    private LoadingDialog loginDialog;
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        Intent it = this.getIntent();

        final Bundle data = it.getExtras();

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        dia = (TextView) findViewById(R.id.activity_calendario_dia);
        horaInicial = (TextView) findViewById(R.id.activity_calendario_initialTime_time);
        horaFinal = (TextView) findViewById(R.id.activity_calendario_finalTime_time);
        horaInicialUp = (ImageButton) findViewById(R.id.activity_calendario_initialTime_up);
        horaInicialDown = (ImageButton) findViewById(R.id.activity_calendario_initialTime_down);
        horaFinalUp = (ImageButton) findViewById(R.id.activity_calendario_finalTime_up);
        horaFinalDown = (ImageButton) findViewById(R.id.activity_calendario_finalTime_down);
        agendar = (Button) findViewById(R.id.activity_calendario_agendar);
        back = (ImageButton) findViewById(R.id.cabecalho_back);

        Toast.makeText(getApplicationContext(), "Selecione o dia desejado", Toast.LENGTH_LONG).show();

        post = new PostApiModels();
        calendarView.setShowWeekNumber(false);
        long selectedDate = calendarView.getDate();
        calendarView.requestFocus();
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                // display the selected date by using a toast
                Integer mesFix = month + 1;
                String labelMes = mesFix.toString();

                String labelDia = "" + dayOfMonth;

                if(dayOfMonth < 10){
                    labelDia = "0" + labelDia;
                }

                if (mesFix < 10){
                    labelMes = "0" + labelMes;
                }

                dia.setText(labelDia + "/" + labelMes + "/" + year);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        horaInicial.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String horafim[] = horaFinal.getText().toString().split(":");
                int horafinal = Integer.parseInt(horafim[0]);
                String horaini[] = horaInicial.getText().toString().split(":");
                int horainicial = Integer.parseInt(horaini[0]);
                if(horainicial >= horafinal){
                    String novahora = "";
                    if (horainicial < 9){
                        novahora = "0" + (horainicial + 1) + ":00";
                    }else if(horainicial >= 23){
                        novahora = "23:59";
                    }else {
                        novahora = (horainicial + 1) + ":00";
                    }
                    horaFinal.setText(novahora);
                }
            }
        });
        horaFinal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String horafim[] = horaFinal.getText().toString().split(":");
                int horafinal = Integer.parseInt(horafim[0]);
                String horaini[] = horaInicial.getText().toString().split(":");
                int horainicial = Integer.parseInt(horaini[0]);
                if(horafinal <= horainicial){
                    String novahora = "";
                    if (horainicial <= 10 && horainicial > 1){
                        novahora = "0" + (horafinal - 1) + ":00";
                    }else if(horainicial <= 1){
                        novahora = "00:00";
                    }else {
                        novahora = (horafinal - 1) + ":00";
                    }
                    horaInicial.setText(novahora);
                }
            }
        });
        horaInicialUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String novahora = "";
                String horavet[] = horaInicial.getText().toString().split(":");
                int hora = Integer.parseInt(horavet[0]);
                if (hora < 9){
                    novahora = "0" + (hora + 1) + ":00";
                }else if(hora >= 23){
                    novahora = "23:00";
                }else {
                    novahora = (hora + 1) + ":00";
                }
                horaInicial.setText(novahora);
            }
        });

        horaInicialDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String novahora = "";
                String horavet[] = horaInicial.getText().toString().split(":");
                int hora = Integer.parseInt(horavet[0]);
                if (hora <= 10 && hora > 1){
                    novahora = "0" + (hora - 1) + ":00";
                }else if(hora <= 1){
                    novahora = "00:00";
                }else {
                    novahora = (hora - 1) + ":00";
                }
                horaInicial.setText(novahora);
            }
        });
        horaFinalUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String novahora = "";
                String horavet[] = horaFinal.getText().toString().split(":");
                int hora = Integer.parseInt(horavet[0]);
                if (hora < 9){
                    novahora = "0" + (hora + 1) + ":00";
                }else if(hora >= 23){
                    novahora = "23:59";
                }else {
                    novahora = (hora + 1) + ":00";
                }
                horaFinal.setText(novahora);
            }
        });
        horaFinalDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String novahora = "";
                String horavet[] = horaFinal.getText().toString().split(":");
                int hora = Integer.parseInt(horavet[0]);
                if (hora <= 10 && hora > 2){
                    novahora = "0" + (hora - 1) + ":00";
                }else if(hora <= 2){
                    novahora = "01:00";
                }else {
                    novahora = (hora - 1) + ":00";
                }
                horaFinal.setText(novahora);
            }
        });
        agendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dia.getText().equals("DIA")){
                    Toast.makeText(getApplicationContext(), "Selecione o dia desejado", Toast.LENGTH_LONG).show();
                }else{
                    if(oldDate(dia.getText().toString())){
                        Toast.makeText(getApplicationContext(), "Data invalida", Toast.LENGTH_LONG).show();
                    }else {
                        String horafim[] = horaFinal.getText().toString().split(":");
                        int horafinal = Integer.parseInt(horafim[0]);
                        String horaini[] = horaInicial.getText().toString().split(":");
                        int horainicial = Integer.parseInt(horaini[0]);
                        novoEvento = new Evento();
                        novoEvento.setIdServico(data.getInt("idServico"));
                        novoEvento.setIdUsuarioContratante(data.getInt("idUsuario"));
                        novoEvento.setDtEvento(dia.getText().toString());
                        novoEvento.setHrInicio(horainicial);
                        novoEvento.setHrFinal(horafinal);

                        try {
                            startLoadingDialog();
                            threadUpdate();
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(), "Erro de Postagem", Toast.LENGTH_LONG).show();
                            stopLoadingDialog();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void threadUpdate(){
        new Thread(){
            @Override
            public void run() {
                post.postEvento(novoEvento);
                threadUI();
            }
        }.start();
    }

    private void threadUI(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                stopLoadingDialog();
                Toast.makeText(getApplicationContext(), "Evento criado com sucesso", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private void startLoadingDialog(){
        loginDialog = new LoadingDialog(CalendarActivity.this);
        loginDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loginDialog.show();
    }

    private void stopLoadingDialog(){
        loginDialog.dismiss();
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
}
