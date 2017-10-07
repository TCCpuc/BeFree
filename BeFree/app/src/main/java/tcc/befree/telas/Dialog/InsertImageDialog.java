package tcc.befree.telas.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.squareup.picasso.Picasso;
import tcc.befree.R;
import tcc.befree.models.CircleImageView;

/**
 * Created by guilherme.leme on 9/30/17.
 */

public class InsertImageDialog extends Dialog{

    private Activity c;
    private Bitmap bitmapUsuarioPerfil;
    private static final int SELECT_FILE1 = 100;
    private CircleImageView imagem;
    private Button ok;

    public InsertImageDialog(Activity a) {
        //SE CONTEXT = 1 (INSERT IMAGEM USUARIO)
        //SE CONTEXT = 2 (INSERT IMAGEM BUSCA)
        //SE CONTEXT = 3 (INSERT IMAGEM SERVICO)
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_insert_image);

        imagem = (CircleImageView) findViewById(R.id.dialog_insert_image_image);
        ok = (Button) findViewById(R.id.dialog_insert_image_ok);
        imagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(SELECT_FILE1);
                dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


    public void openGallery(int req_code) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        c.startActivityForResult(Intent.createChooser(intent,
                "Selecione uma foto "), req_code);
    }

    @Override
    public void onBackPressed() {
        dismiss();
    }
}