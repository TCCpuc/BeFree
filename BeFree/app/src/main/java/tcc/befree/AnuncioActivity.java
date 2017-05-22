package tcc.befree;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AnuncioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncio);

        ((TextView)findViewById(getResources().getIdentifier("txtNome", "id", getPackageName()))).setText("");
        ((TextView)findViewById(getResources().getIdentifier("txtDescricao", "id", getPackageName()))).setText("");
        ((TextView)findViewById(getResources().getIdentifier("txtCidade", "id", getPackageName()))).setText("");
        ((TextView)findViewById(getResources().getIdentifier("txtCategoria", "id", getPackageName()))).setText("");
        ((TextView)findViewById(getResources().getIdentifier("txtSubCategoria", "id", getPackageName()))).setText("");
    }

}
