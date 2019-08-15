package com.example.refricare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.se.omapi.Session;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RegistroRefrigerador extends AppCompatActivity {
    EditText txtMarca;
    EditText txtModelo;
    RadioButton rbtnauto;
    RadioButton rbtnSemi;
    Button Registrar;
    EditText ubicacion;
    Button ubimanual;
    Refrigerador refrigerador;
    int tipo;
    Lista refrix2;
    List<Refrigerador> RefriList = new ArrayList<Refrigerador>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_registro_refrigerador);
        txtMarca = (EditText) findViewById(R.id.txtMArca);
        txtModelo = (EditText) findViewById(R.id.txtModelo);
        rbtnauto = (RadioButton) findViewById(R.id.rbtnauto);
        rbtnSemi = (RadioButton) findViewById(R.id.rbtnSemi);
        Registrar = (Button) findViewById(R.id.btnRegistrar);
        ubicacion = (EditText) findViewById(R.id.ubicaciontxt);
        ubimanual = (Button) findViewById(R.id.ubimanual);

        Bundle ObjetoEnviado = getIntent().getExtras();
        Lista refrix = null;

        if(ObjetoEnviado != null){
            refrix = (Lista) ObjetoEnviado.getSerializable("RefrigeradorL");
            RefriList = refrix.getRefril();
        }


        ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Lista l = new Lista(RefriList);
                l.setRefril(RefriList);
                Intent ot = new Intent(RegistroRefrigerador.this,MapsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Refrigeradorm",l);
                ot.putExtras(bundle);
                startActivity(ot);
            }
        });
        ubimanual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent um = new Intent(RegistroRefrigerador.this, direccion.class);
                startActivity(um);
            }
        });
        Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    if(rbtnauto.isSelected()){
                        tipo = 0;
                    }else if(rbtnSemi.isSelected()){
                        tipo = 1;
                    }else{
                        Toast toast = Toast.makeText(getApplicationContext(),"Favor de seleccionar un tipo",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    refrigerador = new Refrigerador(txtMarca.getText().toString(),txtModelo.getText().toString(),tipo,0.45434434,0.343434);
                    RefriList.add(refrigerador);
                    Toast toast = Toast.makeText(getApplicationContext(),"Refirgerador"+txtMarca.getText().toString()+" registrado",Toast.LENGTH_SHORT);
                    toast.show();


                    Lista l = new Lista(RefriList);
                    Intent ot = new Intent(RegistroRefrigerador.this,MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Refrigerador",l);
                    ot.putExtras(bundle);
                    startActivity(ot);
                 /*   Refrigerador refrix = refrigerador;
                    Intent it = new Intent(RegistroRefrigerador.this,MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Refrigerador",refrix);

                    it.putExtras(bundle);
                    startActivity(it);
*/

                }catch(Exception ex){
                    Toast toast = Toast.makeText(getApplicationContext(),"Refrigerador no registrado favor de revisar los campos",Toast.LENGTH_SHORT);
                    toast.show();
                }


            }
        });


    }

}
