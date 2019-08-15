package com.example.refricare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    AppCompatActivity appCompatActivity;
    Lista refrix2 = new Lista();
    Button Registrar;
    Button ad;
    List<Refrigerador> listaR;
    Bundle ObjetoEnviado = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listaR = new ArrayList<Refrigerador>();

        listaR.add(new Refrigerador("Whirpool","eee",0,0.3422323,0.343434));
        listaR.add(new Refrigerador("Mabe","eee",0,0.3422323,0.343434));
        listaR.add(new Refrigerador("Patito","eee",0,0.3422323,0.343434));



         ObjetoEnviado = getIntent().getExtras();
        Lista refrix = null;

        if(ObjetoEnviado != null){

            try{
                refrix = (Lista) ObjetoEnviado.getSerializable("Refrigerador");
                listaR = refrix.getRefril();
            }
            catch (Exception ex)
            {

            }

        }

        AdpRefri adaptador = new AdpRefri(this);
        ListView lv1 = (ListView)findViewById(R.id.listview);
        lv1.setAdapter(adaptador);
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // ItemClicked item = adapter.getItemAtPosition(i);

                Intent intent = new Intent(MainActivity.this, ejemplosdatos.class);
                //based on item add info to intent
                startActivity(intent);
            }
        });
        ad = (Button) findViewById(R.id.acercade);
        ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast1 =
                        Toast.makeText(getApplicationContext(),
                                "Versión 1.0", Toast.LENGTH_SHORT);

                toast1.show();
            }
        });
        Registrar = (Button)findViewById(R.id.registrgo);
        Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(MainActivity.this,RegistroRefrigerador.class);

                Bundle bundle = new Bundle();
                refrix2 = new Lista(listaR);
                bundle.putSerializable("RefrigeradorL",refrix2);
                intento.putExtras(bundle);
                startActivity(intento);
            }
        });

    }
    class AdpRefri extends ArrayAdapter<Refrigerador> {

        AppCompatActivity appCompatActivity;

        AdpRefri(AppCompatActivity context) {
            super(context, R.layout.refri,listaR );
            appCompatActivity = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = appCompatActivity.getLayoutInflater();
            View item = inflater.inflate(R.layout.refri, null);

            TextView textView1 = (TextView)item.findViewById(R.id.txtRefri);
            textView1.setText(listaR.get(position).getMarca());

            return(item);
        }
    }
}
