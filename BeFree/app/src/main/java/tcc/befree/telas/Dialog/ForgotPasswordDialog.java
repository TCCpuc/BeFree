package tcc.befree.telas.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import tcc.befree.R;

/**
 * Created by guilherme.leme on 9/13/17.
 */

public class ForgotPasswordDialog extends Dialog implements
        android.view.View.OnClickListener{

    public Activity c;
    public Dialog d;

    public ForgotPasswordDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.forgot_password_dialog);
        Button dialogButton = (Button) findViewById(R.id.dialog_btn_send);
        dialogButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_btn_send:
                c.finish();
                break;
            case R.id.dialog_btn_back:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }





   /* public void showDialog(Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView();

        TextView text = (TextView) dialog.findViewById(R.id.dialog_text);

        Button dialogButton = (Button) dialog.findViewById(R.id.dialog_btn_send);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }*/
}