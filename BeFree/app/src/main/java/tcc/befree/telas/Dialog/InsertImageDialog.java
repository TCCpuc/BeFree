package tcc.befree.telas.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import tcc.befree.R;

/**
 * Created by guilherme.leme on 9/30/17.
 */

public class InsertImageDialog extends Dialog implements
        android.view.View.OnClickListener{

    private Activity c;

    public InsertImageDialog(Activity a, int context) {
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
        //dismiss();
    }
    public void Dismiss(){
        dismiss();
    }
}