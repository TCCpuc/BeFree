package tcc.befree.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import tcc.befree.api.ApiModels;
import tcc.befree.api.PostApiModels;
import tcc.befree.R;
import tcc.befree.models.Categoria;
import tcc.befree.models.DDD;
import tcc.befree.models.Servico;
import tcc.befree.models.SubCategoria;
import tcc.befree.telas.Dialog.InsertImageDialog;
import tcc.befree.utils.MoneyTextWatcher;
import tcc.befree.utils.Utils;

public class CreateServicoActivity extends AppCompatActivity {

    private int idUsuario;
    private Spinner spinnerDDDs;
    private Spinner spinnerCategorias;
    private Spinner spinnerSubCategorias;
    private Spinner spinnerFormaPgto;
    private Button submit;
    private TextView title;
    private EditText editDescricao;
    private EditText editValor;
    private CheckBox editValorCheck;
    private ImageView photo;
    private EditText nomeServico;
    private EditText descricaoServico;
    private Bitmap bitmapUsuarioPerfil;
    private Servico novoServico;
    private ImageButton back;
    private static final int SELECT_FILE1 = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_servico);

        Bundle bundle = getIntent().getBundleExtra("idUsuario");
        try {
            this.idUsuario = bundle.getInt("idUsuario");
        }catch(Exception e){
            this.idUsuario = 0;
        }

        spinnerDDDs = (Spinner) findViewById(R.id.create_servico_spinnerDDD);
        spinnerCategorias = (Spinner) findViewById(R.id.create_servico_spinnerCategoria);
        spinnerSubCategorias = (Spinner) findViewById(R.id.create_servico_spinnerSubCategoria);
        spinnerFormaPgto = (Spinner) findViewById(R.id.create_servico_spinnerFormaPgto);
        submit = (Button) findViewById(R.id.create_servico_BtnSubmitServico);
        title = (TextView) findViewById(R.id.create_servico_title);
        editValor = (EditText) findViewById(R.id.create_servico_valor);
        editDescricao = (EditText) findViewById(R.id.create_servico_txtDescricao);
        photo = (ImageView) findViewById(R.id.create_servico_unounce_photo);
        nomeServico = (EditText) findViewById(R.id.create_servico_titulo);
        descricaoServico = (EditText) findViewById(R.id.create_servico_txtDescricao);
        editValorCheck = (CheckBox) findViewById(R.id.create_servico_valor_check);
        back = (ImageButton) findViewById(R.id.cabecalho_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //popula o spinner do ddd
        ArrayAdapter arrayAdapterDDD = new ArrayAdapter(this, android.R.layout.simple_spinner_item, new ApiModels().getDDDsVetor());
        arrayAdapterDDD.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDDDs.setAdapter(arrayAdapterDDD);
        //popula o spinner de categoria
        ArrayAdapter arrayAdapterCategoria = new ArrayAdapter(this, android.R.layout.simple_spinner_item, new ApiModels().getCategoriasVetor());
        arrayAdapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategorias.setAdapter(arrayAdapterCategoria);
        //popula restante
        submit.setText("Criar Servico");
        title.setText("Novo Servico");

        submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO POPULAR SPINNER SUBCATEGORIA SÓ COM AS CATEGORIAS CERTAS
                novoServico = new Servico();
                String nome = nomeServico.getText().toString();
                String descricao = descricaoServico.getText().toString();
                String ddd = spinnerDDDs.getSelectedItem().toString();
                String subCategoria =  spinnerSubCategorias.getSelectedItem().toString();
                int idSubCategoria = ((SubCategoria)spinnerSubCategorias.getSelectedItem()).idSubCategoria;
                String categoria = spinnerCategorias.getSelectedItem().toString();
                if(ddd ==null
                        || "".equals(subCategoria)
                        || "".equals(categoria)
                        || "".equals(nome)
                        || "".equals(descricao))
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(CreateServicoActivity.this).create();
                    alertDialog.setTitle("Todos os campos são obrigatórios");
                    alertDialog.setMessage("Por favor, preencha todos os campos");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
                else {
                    novoServico.setFormaPgto(spinnerFormaPgto.getSelectedItemPosition());
                    try {
                        novoServico.setPreco(Integer.parseInt(editValor.getText().toString()));
                    }catch (Exception e){
                        novoServico.setPreco(0);
                    }
                    novoServico.setDescricao(descricao);
                    novoServico.setTitulo(nome);
                    novoServico.setIdDDD(((DDD)spinnerDDDs.getSelectedItem()).id);
                    novoServico.setIdSubCategoria(idSubCategoria);
                    novoServico.setIdUsuario(idUsuario);
                    novoServico.setImagemServico(getImagem());
                    novoServico.setIdUsuario(idUsuario);
                    try {
                        novoServico.setImagemServico(Utils.convert(bitmapUsuarioPerfil));
                    }catch(Exception e){
                        novoServico.setImagemServico("");
                    }
                    threadUpdate();
                    Toast toast = Toast.makeText(getApplicationContext(), "Criando o serviço...", Toast.LENGTH_LONG);
                    toast.show();
                    onBackPressed();
                }
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

        editValor.addTextChangedListener(new MoneyTextWatcher(editValor));

        photo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                InsertImageDialog image = new InsertImageDialog(CreateServicoActivity.this);
                image.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                image.show();
            }
        });

        spinnerCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                int idCategoria = ((Categoria)parent.getItemAtPosition(position)).idCategoria;
                preencheSubCategoria(idCategoria);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {}
        });
    }

    private void threadUpdate(){
        new Thread(){
            @Override
            public void run() {
                new PostApiModels().postServico(novoServico);
                threadUI();
            }
        }.start();
    }

    private void threadUI(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(getApplicationContext(), "Serviço criado com sucesso!", Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == SELECT_FILE1 ) {
            Uri selectedImageUri = data.getData();

            try {
                bitmapUsuarioPerfil = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                Picasso.with(this.getApplicationContext()).load(selectedImageUri).into(photo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getImagem() {
        byte[] bytes, buffer = new byte[8192];
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            InputStream inputStream = getResources().openRawResource(+ R.drawable.teste);

            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        bytes = output.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    protected void preencheSubCategoria(int idCategoria){

        //popula o spinner de subcategoria
        spinnerSubCategorias = (Spinner) findViewById(R.id.create_servico_spinnerSubCategoria);
        ArrayAdapter arrayAdapterSubCategoria = new ArrayAdapter(this, android.R.layout.simple_spinner_item, new ApiModels().getSubCategoriasVetorByIdCategoria(idCategoria));
        arrayAdapterSubCategoria.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerSubCategorias.setAdapter(arrayAdapterSubCategoria);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}