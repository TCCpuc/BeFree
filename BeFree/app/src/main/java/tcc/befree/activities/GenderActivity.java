package tcc.befree.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import tcc.befree.R;
import tcc.befree.models.Agenda;

/**
 * Created by guilherme.leme on 10/23/17.
 */

public class GenderActivity extends AppCompatActivity {

    private ListView oldday;

    private ListView day;

    private ListView time;

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
        setContentView(R.layout.activity_gender);

        day = (ListView) findViewById(R.id.gender_day);
        time = (ListView) findViewById(R.id.gender_time);
        oldday = (ListView) findViewById(R.id.gender_old_day);

        oldday.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        oldday.setAdapter(new GenderActivity.OldDayAdapter());

        day.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        day.setAdapter(new GenderActivity.DayAdapter());

        time.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        time.setAdapter(new GenderActivity.TimeAdapter());
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private class DayAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            //RETORNA QUANTOS EVENTOS/DIA ENCONTROU
            return 1;
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
                tempo.setText("24/11/2017");
                titulo.setText("Tosa para Cachorro");
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
            View view = getLayoutInflater().inflate(R.layout.item_agenda, null);
            TextView titulo = (TextView) view.findViewById(R.id.item_agenda_title);
            TextView tempo = (TextView) view.findViewById(R.id.item_agenda_time);
            if(position < 9){
                tempo.setText("0" + position + ":00 - 0" + (position + 1) + ":00");
            }else if(position == 9){
                tempo.setText("0" + position + ":00 - " + (position + 1) + ":00");
            }else{
                tempo.setText(position + ":00 - " + (position + 1) + ":00");
            }
            return view;
        }
    }
}