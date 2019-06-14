package com.example.administrador.ipsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Mensaje extends AppCompatActivity {
Button button;
    String dni;
    GlobalClass globalClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaje);
        globalClass= (GlobalClass) getApplication();
        button = (Button)findViewById(R.id.button3);
         dni = globalClass.getDni();
        Toast.makeText(Mensaje.this,dni,Toast.LENGTH_LONG).show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
    }
}
