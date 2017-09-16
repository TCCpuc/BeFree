package tcc.befree.telas.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import tcc.befree.R;

/**
 * Created by guilherme.leme on 9/16/17.
 */

public class AdvancedSearchDialog extends Dialog implements
        android.view.View.OnClickListener{

    private Activity c;
    private RadioGroup categoria;
    private RadioButton r_categoria;
    private RadioButton r_sub_categoria;
    private RadioButton r_ddd;
    private Spinner s_categoria;
    private Spinner s_sub_categoria;
    private Spinner s_ddd;
    private Button send;


    public AdvancedSearchDialog (Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.advanced_search_dialog);
        categoria = (RadioGroup) findViewById(R.id.advanced_search_dialog_categoria_group);
        //r_categoria = (RadioButton) findViewById(R.id.advanced_search_dialog_categoria_radio_button);
        r_sub_categoria = (RadioButton) findViewById(R.id.advanced_search_dialog_sub_categoria_radio_button);
        r_ddd = (RadioButton) findViewById(R.id.advanced_search_dialog_ddd_radio_button);
        s_categoria = (Spinner) findViewById(R.id.advanced_search_dialog_categoria_spinner);
        s_sub_categoria = (Spinner) findViewById(R.id.advanced_search_dialog_sub_categoria_spinner);
        s_ddd = (Spinner) findViewById(R.id.advanced_search_dialog_ddd_spinner);
        send = (Button) findViewById(R.id.advanced_search_dialog_button_ok);
        Populate();
        send.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        //FAZ O SELECT NO BANCO DE ACORDO COM O SELECIONADO
        //c.finish();
        dismiss();
    }

    public void Populate(){
        //FAZ O SELECT NO BANCO PARA POPULAR OS SPINNERS
        //CODIGO APENAS DE EXEMPLO

        ArrayAdapter<CharSequence> arrayCategoria = ArrayAdapter.createFromResource(c,R.array.Services, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> arraySubCategoria = ArrayAdapter.createFromResource(c,R.array.Bolas, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> arrayDDD = ArrayAdapter.createFromResource(c,R.array.Bolas, android.R.layout.simple_spinner_dropdown_item);
        arrayCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arraySubCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayDDD.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_categoria.setAdapter(arrayCategoria);
        s_sub_categoria.setAdapter(arraySubCategoria);
        s_ddd.setAdapter(arrayDDD);
    }
}
