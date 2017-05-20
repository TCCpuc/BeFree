package tcc.befree;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AnuncioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncio);

        ((TextView)findViewById(getResources().getIdentifier("txtNome", "id", getPackageName()))).setText("a");
        ((TextView)findViewById(getResources().getIdentifier("txtDescricao", "id", getPackageName()))).setText("a");
        ((TextView)findViewById(getResources().getIdentifier("txtCidade", "id", getPackageName()))).setText("a");
        ((TextView)findViewById(getResources().getIdentifier("txtCategoria", "id", getPackageName()))).setText("a");
        ((TextView)findViewById(getResources().getIdentifier("txtSubCategoria", "id", getPackageName()))).setText("a");
    }

}
