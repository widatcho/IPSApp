package com.example.administrador.ipsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Editar extends AppCompatActivity {
    String USUARIO = "usuario" ;
    String EMAIL = "email" ;
    String  pass ="contraseña";
    String nombreAlumno = "nombre" ;
    String ape="apellido";
    String carre = "carrera";
    String documento="dni";
    String genero="sexo";

    String dniUser;
    String Getnombre,GetApellido,Gedni,Getemail,GetCarrera,GetUsuario,GetPass;
    Button update;
    EditText nombre, apellido, dni, email, usuario, password;
    GlobalClass globalClass;
    String id,GetSexo;
    String Nombre ,Apellido,Dni, Email,Usuario,Contraseña;
    private ProgressDialog pDialog;
    Spinner spinnerDropDownCarrera, sexo;
    String[] sexoAlumno = {"SEXO....", "Masculino", "Feminino"};
    String[] Carrera = {"CARRERAS....",
            "Analista Universitario en Sistemas",
            "Técnico Universitario en Gestión y Producción",
            "Técnico Universitario en Mecatrónica",
            "Técnico Universitario en Plásticos y Elastómeros",
            "Técnico Universitario en Química",
            "Técnico Universitario en Sistemas Electrónicos"};
    String selectedSexo,SelectedCarrera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        update= (Button) findViewById(R.id.button) ;
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Getnombre = nombre.getText().toString();
                GetApellido = apellido.getText().toString();
                Gedni = dni.getText().toString();
                Getemail = email.getText().toString();
                GetUsuario = usuario.getText().toString();
                GetPass = password.getText().toString();
                new Update().execute();
            }
        });
        spinnerDropDownCarrera = (Spinner) findViewById(R.id.spinner);
        sexo = (Spinner) findViewById(R.id.spinner2);
        globalClass = (GlobalClass) getApplication();

        nombre = (EditText) findViewById(R.id.etnombre);
        apellido = (EditText) findViewById(R.id.etApellido);
        dni = (EditText) findViewById(R.id.etDni);
        email = (EditText) findViewById(R.id.etEmail);
        usuario = (EditText) findViewById(R.id.etPassword);
        password = (EditText) findViewById(R.id.usuario);
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.
                R.layout.simple_spinner_dropdown_item ,Carrera);
        spinnerDropDownCarrera.setAdapter(adapter);

        spinnerDropDownCarrera.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SelectedCarrera
                        = (String) adapterView.getItemAtPosition(i);
                if(i==0){
                    Toast.makeText(Editar.this,"Por favor ingrese su sexo",Toast.LENGTH_LONG).show();
                }else if(i>0){
                    Toast.makeText(Editar.this,GetSexo,Toast.LENGTH_LONG).show();


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ArrayAdapter<String> adapter2= new ArrayAdapter<String>(this,android.
                R.layout.simple_spinner_dropdown_item ,sexoAlumno);

        sexo.setAdapter(adapter2);
        sexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selectedSexo= (String) adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        new ResultadoUpdate().execute();

    }



    private class ResultadoUpdate extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Editar.this);
            pDialog.setMessage("Cargando...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {
         dniUser = globalClass.getDni();

            String url = "http://danbijann.freeiz.com/ips/Select.php?dni=" + dniUser + "";
            HttpHandler sh = new HttpHandler();
            JSONObject jsonStr = sh.makeServiceCall(url);
            // JSONObject jsonObj = null;
            Log.e("RESS", "Response from url: " + url);
            Log.e("RESP", "Response from url: " + jsonStr);
            try {
                // jsonObj = new JSONObject(String.valueOf(jsonStr));
                JSONArray jsonArray = jsonStr.getJSONArray("result");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject c = jsonArray.getJSONObject(i);
                     id= c.getString("id");
                     Nombre = c.getString("nombre");
                    Apellido= c.getString("apellido");
                    Dni= c.getString("dni");
                    Email= c.getString("email");
                    Usuario=c.getString("usuario");
                    Contraseña=c.getString("contraseña");

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
          //  Toast.makeText(Editar.this,codeID,Toast.LENGTH_LONG).show();
            pDialog.dismiss();
            nombre.setText(Nombre);
            apellido.setText(Apellido);
            dni.setText(Dni);
            email.setText(Email);
            usuario.setText(Usuario);
            password.setText(Contraseña);

        }
    }





    private class Update extends AsyncTask<String,String,String> {


        @Override
        protected String doInBackground(String... strings) {
            final Intent intent = getIntent();
          //String numeroID = intent.getStringExtra("CODE");
           String codeID= globalClass.getId();
     //    Log.i("ID",numeroID);
            String ServerUploadPath ="http://danbijann.freeiz.com/ips/update.php?id="+id+"";
            Log.e("URL",ServerUploadPath);
            ProcessClass imageProcessClass = new ProcessClass();
            HashMap<String,String> HashMapParams = new HashMap<String,String>();
            HashMapParams.put("id", id);
            HashMapParams.put("nombre", Getnombre);
            HashMapParams.put("apellido", GetApellido);
            HashMapParams.put("dni", Gedni);
            HashMapParams.put("email", Getemail);
            HashMapParams.put("carrera", SelectedCarrera);
            HashMapParams.put("sexo", selectedSexo);
            HashMapParams.put("usuario", GetUsuario);
            HashMapParams.put("contraseña", GetPass);

            Log.e("RESS", "Response from url: " + ServerUploadPath);

            String FinalData = imageProcessClass.ImageHttpRequest(ServerUploadPath, HashMapParams);

            return FinalData;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(Editar.this,s,Toast.LENGTH_LONG).show();

            // Setting image as transparent after done uploading.
            // circularImageView.setImageResource(android.R.color.transparent);
            nombre.getText().clear();
            email.getText().clear();
            dni.getText().clear();
            apellido.getText().clear();
            usuario.getText().clear();
            password.getText().clear();
            Intent intent = new Intent(Editar.this, Login.class);
            startActivity(intent);

        }
    }
}
