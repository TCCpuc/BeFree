package tcc.befree.telas.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import tcc.befree.R;
import tcc.befree.api.ApiModels;
import tcc.befree.models.Categoria;

/**
 * Created by guilherme.leme on 9/16/17.
 */

public class AdvancedSearchDialog extends Dialog implements
        android.view.View.OnClickListener{

    private Activity c;
    //private CheckBox r_categoria;
    //private CheckBox r_sub_categoria;
    //private CheckBox r_ddd;
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
        setContentView(R.layout.dialog_advanced_search);
        //r_categoria = (CheckBox) findViewById(R.id.advanced_search_dialog_categoria_radio_button);
        //r_sub_categoria = (CheckBox) findViewById(R.id.advanced_search_dialog_sub_categoria_radio_button);
        //r_ddd = (CheckBox) findViewById(R.id.advanced_search_dialog_ddd_radio_button);
        s_categoria = (Spinner) findViewById(R.id.advanced_search_dialog_categoria_spinner);
        s_sub_categoria = (Spinner) findViewById(R.id.advanced_search_dialog_sub_categoria_spinner);
        s_ddd = (Spinner) findViewById(R.id.advanced_search_dialog_ddd_spinner);
        send = (Button) findViewById(R.id.advanced_search_dialog_button_ok);
        //s_categoria.getSelectedView().setEnabled(false);
        s_categoria.setEnabled(true);
        s_categoria.setClickable(true);
        s_sub_categoria.setEnabled(true);
        s_categoria.setClickable(true);
        s_ddd.setEnabled(true);
        s_ddd.setClickable(true);
        Populate();
        send.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        //FAZ O SELECT NO BANCO DE ACORDO COM O SELECIONADO
        //c.finish();
        dismiss();
    }

    protected void preencheSubCategoria(int idCategoria){

        //popula o spinner de subcategoria
        ArrayAdapter arrayAdapterSubCategoria = new ArrayAdapter(c, android.R.layout.simple_spinner_item, new ApiModels().getSubCategoriasVetorByIdCategoriaComValorDefault(idCategoria));
        arrayAdapterSubCategoria.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        s_sub_categoria.setAdapter(arrayAdapterSubCategoria);
    }

    public void Populate(){
        //popula o spinner do ddd
        ArrayAdapter arrayAdapterDDD = new ArrayAdapter(c, android.R.layout.simple_spinner_item, new ApiModels().getDDDsVetorComValorDefault());
        arrayAdapterDDD.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        s_ddd.setAdapter(arrayAdapterDDD);


        //popula o spinner de categoria
        ArrayAdapter arrayAdapterCategoria = new ArrayAdapter(c, android.R.layout.simple_spinner_item, new ApiModels().getCategoriasVetorComValorDefault());
        arrayAdapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_categoria.setAdapter(arrayAdapterCategoria);
        s_categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                int idCategoria = ((Categoria)parent.getItemAtPosition(position)).idCategoria;
                preencheSubCategoria(idCategoria);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


//        arrayCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        arraySubCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        arrayDDD.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        s_categoria.setAdapter(arrayCategoria);
//        s_sub_categoria.setAdapter(arraySubCategoria);
//        s_ddd.setAdapter(arrayDDD);
    }

//    public void onCheckboxClicked(View view) {
//        boolean checked = ((CheckBox) view).isChecked();
//
//        // Check which checkbox was clicked
//        switch(view.getId()) {
//            case R.id.advanced_search_dialog_categoria_radio_button:
//                if (checked) {
//                    s_categoria.setEnabled(true);
//                    s_categoria.setClickable(true);
//                }
//                //s_categoria.getSelectedView().setEnabled(true);
//                // Put some meat on the sandwich
//                else {
//                    // Remove the meat
//                    // s_categoria.getSelectedView().setEnabled(false);
//                    s_categoria.setEnabled(false);
//                    s_categoria.setClickable(false);
//                    break;
//                }
//            case R.id.advanced_search_dialog_sub_categoria_radio_button:
//                if (checked)
//                {
//                    s_sub_categoria.setEnabled(true);
//                    s_sub_categoria.setClickable(true);
//                }
//                // Cheese me
//                else
//                {
//                    s_sub_categoria.setEnabled(false);
//                    s_sub_categoria.setClickable(false);
//                    break;
//                }
//            case R.id.advanced_search_dialog_ddd_radio_button:
//                if (checked)
//                {
//                    s_ddd.setEnabled(true);
//                    s_ddd.setClickable(true);
//                }
//                // Cheese me
//                else
//                {
//                    s_ddd.setEnabled(false);
//                    s_ddd.setClickable(false);
//                    break;
//                }
//        }
//    }
}
