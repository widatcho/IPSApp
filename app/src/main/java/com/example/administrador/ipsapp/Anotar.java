package com.example.administrador.ipsapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;

public class Anotar extends AppCompatActivity {

    GlobalClass globalClass;
    EditText nombreCompletp ,dni,etfecha,etCarrera,mat1,mat2,mat3,mat4,mat5;
    Spinner Quatrimestre,Quatrimestre2,Quatrimestre3,spMaterias;
    String dniUser,id;
    ProgressDialog progressDialog ;
    Button anotar;
    String selectedCarrera;
    String Nombre ,Apellido,Dni, CarreraAlumno,Usuario,Contraseña;
    Date fecha;

    String []quatrimestre ={"1","2","3"};

    String getNombreAlumno,getApellidoAlumno,getDocumento,getMateria1,getMateria2,getMateria3,getMateria4,getMateria5;

    //String nombreAlumno = "nombreAlumno" ;
    String apellidoAlumno="apellidoAlumno";
    String documento="dni";
    String carre = "carrera";
    String materia1="materia1";
    String materia2="materia2";
    String materia3="materia3";
    String materia4="materia4";
    String materia5="materia5";
    String ServerUploadPath ="http://danbijann.freeiz.com/ips/anotar.php";
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anotar);

        Quatrimestre = (Spinner) findViewById(R.id.spinnerQ1);
        Quatrimestre2 = (Spinner) findViewById(R.id.spinnerQ2);
        Quatrimestre3 = (Spinner) findViewById(R.id.spinnerQ3);

        nombreCompletp = (EditText) findViewById(R.id.etNoombreAlumno);
        dni = (EditText) findViewById(R.id.etDocumento);
        etCarrera = (EditText) findViewById(R.id.etCarrera);
        etfecha = (EditText) findViewById(R.id.etFecha);
        globalClass = (GlobalClass) getApplication();


        final Calendar c = Calendar.getInstance();
        int anio = c.get(Calendar.YEAR); //obtenemos el año
        int mes = c.get(Calendar.MONTH); //obtenemos el mes
        mes = mes + 1;
        int dia = c.get(Calendar.DAY_OF_MONTH); // obtemos el día.
        etfecha.setText(dia + "/" + mes + "/" + anio);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.
                R.layout.simple_spinner_dropdown_item, quatrimestre);
        Quatrimestre.setAdapter(adapter);
  Quatrimestre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
          String position = (String) adapterView.getItemAtPosition(i);
          

      }

      @Override
      public void onNothingSelected(AdapterView<?> adapterView) {

      }
  });


        ArrayAdapter<String> adapter2= new ArrayAdapter<String>(this,android.
                R.layout.simple_spinner_dropdown_item ,quatrimestre);
        Quatrimestre2.setAdapter(adapter2);
        ArrayAdapter<String> adapter3= new ArrayAdapter<String>(this,android.
                R.layout.simple_spinner_dropdown_item ,quatrimestre);
        Quatrimestre3.setAdapter(adapter3);


        new CargoDatos().execute();

    }

    private class CargoDatos  extends AsyncTask<String,String,String>{


        @Override
        protected String doInBackground(String... strings) {
            dniUser = globalClass.getDni();

            String url = "http://danbijann.freeiz.com/ips/SelectAlumnos.php?dni=" + dniUser + "";
            HttpHandler sh = new HttpHandler();
            JSONObject jsonStr = sh.makeServiceCall(url);
            // JSONObject jsonObj = null;
            Log.e("RESS", "Response from url: " + url);
            Log.e("RESP", "Response from url: " + jsonStr);
            try {
                // jsonObj = new JSONObject(String.valueOf(jsonStr));
                JSONArray jsonArray = jsonStr.getJSONArray("resultado");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject c = jsonArray.getJSONObject(i);
                    id= c.getString("id");
                    Nombre = c.getString("nombre");
                    Apellido= c.getString("apellido");
                    dniUser= c.getString("dni");
                    CarreraAlumno= c.getString("carrera");

                    Contraseña=c.getString("password");



                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);

            // codeID=  globalClass.setId(id);
            Toast.makeText(Anotar.this,id, Toast.LENGTH_LONG).show();
            //progressDialog.dismiss();
            nombreCompletp.setText(Nombre + " "+ Apellido);
            dni.setText(dniUser);
            etCarrera.setText(CarreraAlumno);
           /* usuario.setText(pusuario);
            password.setText(Ppassword);
            confirmarPass.setText(pre_password);
            celular.setText(ptelefono);
            globalClass.setPassword(Ppassword);*/


        }
    }
}

