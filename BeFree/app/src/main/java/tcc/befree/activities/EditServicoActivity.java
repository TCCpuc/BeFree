package tcc.befree.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import tcc.befree.R;
import tcc.befree.api.ApiModels;
import tcc.befree.api.PostApiModels;
import tcc.befree.api.PutApiModels;
import tcc.befree.models.Categoria;
import tcc.befree.models.DDD;
import tcc.befree.models.Servico;
import tcc.befree.models.SubCategoria;
import tcc.befree.telas.Dialog.InsertImageDialog;
import tcc.befree.utils.Utils;

/**
 * Created by guilherme.leme on 10/16/17.
 */

public class EditServicoActivity extends AppCompatActivity {

    private int idServico;
    private Servico servico;
    private Spinner spinnerDDDs;
    private Spinner spinnerCategorias;
    private Spinner spinnerSubCategorias;
    private Spinner spinnerFormaPgto;
    private Button submit;
    private TextView title;
    private TextView photoText;
    private EditText editDescricao;
    private EditText editValor;
    private CheckBox editValorCheck;
    private ImageView photo;
    private EditText nome;
    private EditText descricao;
    private SubCategoria subCategoria;
    private Bitmap bitmapUsuarioPerfil;
    private static final int SELECT_FILE1 = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_servico);

        Intent it = this.getIntent();
        Bundle loginActivityIntent = it.getExtras();
        idServico = loginActivityIntent.getInt("idServico");
        ApiModels api = new ApiModels();
        servico = api.getServicosById(idServico);
        spinnerDDDs = (Spinner) findViewById(R.id.create_servico_spinnerDDD);
        photoText = (TextView) findViewById(R.id.create_servico_photo_text);
        spinnerCategorias = (Spinner) findViewById(R.id.create_servico_spinnerCategoria);
        spinnerSubCategorias = (Spinner) findViewById(R.id.create_servico_spinnerSubCategoria);
        spinnerFormaPgto = (Spinner) findViewById(R.id.create_servico_spinnerFormaPgto);
        submit = (Button) findViewById(R.id.create_servico_BtnSubmitServico);
        title = (TextView) findViewById(R.id.create_servico_title);
        editValor = (EditText) findViewById(R.id.create_servico_valor);
        editDescricao = (EditText) findViewById(R.id.create_servico_txtDescricao);
        photo = (ImageView) findViewById(R.id.create_servico_unounce_photo);
        nome = (EditText) findViewById(R.id.create_servico_titulo);
        descricao = (EditText) findViewById(R.id.create_servico_txtDescricao);
        editValorCheck = (CheckBox) findViewById(R.id.create_servico_valor_check);
        subCategoria = new ApiModels().getSubCategoriasByID(servico.getIdSubCategoria());

        //popula o spinner do ddd
        ArrayAdapter arrayAdapterDDD = new ArrayAdapter(this, android.R.layout.simple_spinner_item, new ApiModels().getDDDsVetor());
        arrayAdapterDDD.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDDDs.setAdapter(arrayAdapterDDD);
        spinnerDDDs.setSelection(servico.getIdDDD() - 1);
        //popula o spinner de categoria
        ArrayAdapter arrayAdapterCategoria = new ArrayAdapter(this, android.R.layout.simple_spinner_item, new ApiModels().getCategoriasVetor());
        arrayAdapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategorias.setAdapter(arrayAdapterCategoria);
        spinnerCategorias.setSelection(subCategoria.idCategoria - 1);
        //popula restante
        photoText.setText("Clique na foto para editar");
        Picasso.with(this).load(servico.getImagemServico()).into(photo);
        nome.setText(servico.getTitulo());
        descricao.setText(servico.getDescricao());
        submit.setText("Editar Servico");
        title.setText(servico.getTitulo());
        spinnerFormaPgto.setSelection(servico.getFormaPgto());
        if(servico.getPreco() == 0){
            editValor.setFocusable(false);
            editValor.setFocusableInTouchMode(false);
            editValorCheck.setChecked(true);
        }else {
            editValor.setFocusable(true);
            editValor.setFocusableInTouchMode(true);
            editValor.setText("RS:" + servico.getPreco());
            editValorCheck.setChecked(false);
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Servico servicoAlterada = servico;
                servicoAlterada.setIdDDD(spinnerDDDs.getSelectedItemPosition() + 1);
                servicoAlterada.setDescricao(editDescricao.getText().toString());
                servicoAlterada.setTitulo(nome.getText().toString());
                if(editValor.getText().equals("")){
                    servicoAlterada.setPreco(0);
                }else {
                    String preco[] = editValor.getText().toString().split(":");
                    servicoAlterada.setPreco(Float.parseFloat(preco[1]));
                }
                servicoAlterada.setFormaPgto(spinnerFormaPgto.getSelectedItemPosition());
                if(bitmapUsuarioPerfil!= null)
                    servicoAlterada.setImagemServico(Utils.convert(bitmapUsuarioPerfil));
                int idSubCategoria = ((SubCategoria)spinnerSubCategorias.getSelectedItem()).idSubCategoria;
                servicoAlterada.setIdSubCategoria(idSubCategoria);
                new PutApiModels().putServico(servicoAlterada);
                Toast toast = Toast.makeText(getApplicationContext(), "Servico editado com sucesso!", Toast.LENGTH_LONG);
                toast.show();
                onBackPressed();
            }
        });

        editValorCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(!isChecked){
                    editValor.setText("");
                    editValor.setFocusable(true);
                    editValor.setFocusableInTouchMode(true);
                }else {
                    editValor.setText("");
                    editValor.setFocusable(false);
                    editValor.setFocusableInTouchMode(false);
                }
            }
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertImageDialog image = new InsertImageDialog(EditServicoActivity.this);
                image.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                image.show();
            }
        });

        spinnerCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int idCategoria = ((Categoria) parent.getItemAtPosition(position)).idCategoria;
                preencheSubCategoria(idCategoria);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {}
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == SELECT_FILE1) {
            Uri selectedImageUri = data.getData();

            try {
                bitmapUsuarioPerfil = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                Picasso.with(this.getApplicationContext()).load(selectedImageUri).into(photo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private int getIndex(Spinner spinner, String myString)
    {
        int index = 0;
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }

    protected void preencheSubCategoria(int idCategoria) {
        ArrayAdapter arrayAdapterSubCategoria = new ArrayAdapter(this, android.R.layout.simple_spinner_item, new ApiModels().getSubCategoriasVetorByIdCategoria(idCategoria));
        arrayAdapterSubCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSubCategorias.setAdapter(arrayAdapterSubCategoria);
        int spinnerPosition = getIndex(spinnerSubCategorias, subCategoria.descricao);
        spinnerSubCategorias.setSelection(spinnerPosition);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
