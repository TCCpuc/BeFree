package tcc.befree.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import tcc.befree.R;
import tcc.befree.models.Agenda;
import tcc.befree.models.Chat;
import tcc.befree.models.CircleImageView;

/**
 * Created by guilherme.leme on 8/17/17.
 */

public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private List<Agenda> gender = new List<Agenda>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<Agenda> iterator() {
            return null;
        }

        @NonNull
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @NonNull
        @Override
        public <T> T[] toArray(@NonNull T[] a) {
            return null;
        }

        @Override
        public boolean add(Agenda agenda) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends Agenda> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, @NonNull Collection<? extends Agenda> c) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public Agenda get(int index) {
            return null;
        }

        @Override
        public Agenda set(int index, Agenda element) {
            return null;
        }

        @Override
        public void add(int index, Agenda element) {

        }

        @Override
        public Agenda remove(int index) {
            return null;
        }

        @Override
        public int indexOf(Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(Object o) {
            return 0;
        }

        @Override
        public ListIterator<Agenda> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<Agenda> listIterator(int index) {
            return null;
        }

        @NonNull
        @Override
        public List<Agenda> subList(int fromIndex, int toIndex) {
            return null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setShowWeekNumber(false);
        long selectedDate = calendarView.getDate();
        calendarView.requestFocus();
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // display the selected date by using a toast
                Toast.makeText(getApplicationContext(), dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
            }
        });

        popular(gender);
        ListView listView = (ListView) findViewById(R.id.calendar_listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        listView.setAdapter(new MyAdapter());
    }

    private void popular(List<Agenda> ag) {
        Agenda teste = new Agenda();
        teste.setConteudo("teste");
        teste.setHora("13:00 - 14:00");
        teste.setTitulo("Vazio");
        teste.setImagem("");
        for(int x = 0; x<24; x++){
            ag.add(teste);
        }
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 24;
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
            //Agenda agenda = gender.get(position);
            View view = getLayoutInflater().inflate(R.layout.item_agenda, null);

            //CircleImageView mImagePerfil = (CircleImageView) view.findViewById(R.id.item_agenda_image);
            TextView titulo = (TextView) view.findViewById(R.id.item_agenda_title);
            //TextView descricao = (TextView) view.findViewById(R.id.item_agenda_description);
            TextView tempo = (TextView) view.findViewById(R.id.item_agenda_time);
            if(position < 9){
                tempo.setText("0" + position + ":00 - 0" + (position + 1) + ":00");
            }else if(position == 9){
                tempo.setText("0" + position + ":00 - " + (position + 1) + ":00");
            }else{
                tempo.setText(position + ":00 - " + (position + 1) + ":00");
            }

            /*
            TextView description = (TextView) view.findViewById(R.id.item_service_description);
            description.setText(agenda.getUltima_mensagem_texto());

            TextView username = (TextView) view.findViewById(R.id.item_service_title);
            username.setText(agenda.getNome_outro_usuario());

            CircleImageView mImagePerfil = (CircleImageView) view.findViewById(R.id.item_agenda_imagem);
            Picasso.with(getContext()).load(chat.getImagem_outro_usuario()).into(mImagePerfil);
            */

            return view;
        }
    }
}
