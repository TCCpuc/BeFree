package tcc.befree.telas.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import tcc.befree.R;

/**
 * Created by guilherme.leme on 9/23/17.
 */

public class LoadingDialog extends Dialog implements
        android.view.View.OnClickListener{

    private Activity c;
    private ProgressBar load;

    public LoadingDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_loading);
        load = (ProgressBar) findViewById(R.id.loading_dialog_progress);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
        //dismiss();
    }

    @Override
    public void onBackPressed() {}

    public void Dismiss(){
        dismiss();
    }
}
