package com.example.refricare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class direccion extends AppCompatActivity {
Button aceptar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direccion);

        aceptar = (Button)findViewById(R.id.btnAceptar);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(direccion.this, RegistroRefrigerador.class);
                startActivity(i);

                Toast toast1 =
                        Toast.makeText(getApplicationContext(),
                                "Dirección agregada", Toast.LENGTH_SHORT);

                toast1.show();
            }
        });
    }
}
