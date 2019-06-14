package com.example.administrador.ipsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.regex.Pattern;

public class Registrar extends AppCompatActivity {

    private final String TAG = Registrar.this.getClass().getName();
    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();

RequestQueue requestQueue;
    private int PICK_IMAGE_REQUEST = 1;
    EditText nombre,apellido,dni,email,usuario,password,confirmarPass,celular;
    ProgressDialog progressDialog ;
    Button registrar;
    String selectedSexo,SelectedCarrera;
    String pnombre,pApellido,pdni,pemail,pUsuario,pPass,pRepass,pTelefono;
int valor,valor2 ;
    // String ImageName = "image_name" ;
    String USUARIO = "usuario" ;
    String EMAIL = "email" ;
    String  pass ="password";
    String  repass ="re_password";
    String nombreAlumno = "nombre" ;
    String ape="apellido";
    String carre = "carrera";
    String documento="dni";
    String genero="sexo";
    String telefono ="telefono";

    String ServerUploadPath ="http://danbijann.freeiz.com/ips/upload.php";







    Spinner spinnerDropDownCarrera, sexo;
    String []sexoAlumno={"SEXO....","Masculino","Feminino"};
    String []Carrera ={"CARRERAS....",
            "Analista Universitario en Sistemas",
            "Técnico Universitario en Gestión y Producción",
            "Técnico Universitario en Mecatrónica",
            "Técnico Universitario en Plásticos y Elastómeros",
            "Técnico Universitario en Química",
            "Técnico Universitario en Sistemas Electrónicos",

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
       //
        spinnerDropDownCarrera =(Spinner)findViewById(R.id.spinnerCarrera);
        sexo =(Spinner)findViewById(R.id.spinnerSexo);
      //  progressDialog = new ProgressDialog(Registrar.this);

        nombre = (EditText)findViewById(R.id.etNoombreAlumno);
        apellido = (EditText)findViewById(R.id.etApellidoAlumno);
        dni = (EditText)findViewById(R.id.etDNi);
        email = (EditText)findViewById(R.id.etEmail);
        usuario = (EditText)findViewById(R.id.etUsario);
        password = (EditText)findViewById(R.id.etpassword);
        confirmarPass = (EditText)findViewById(R.id.etRepass);
        celular = (EditText)findViewById(R.id.etCelularAlumno);

        registrar=(Button) findViewById(R.id.registrar);
        requestQueue= Volley.newRequestQueue(Registrar.this);



        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.
                R.layout.simple_spinner_dropdown_item ,Carrera);
        spinnerDropDownCarrera.setAdapter(adapter);


        spinnerDropDownCarrera.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,int position, long l) {
                SelectedCarrera=(String) adapterView.getItemAtPosition(position);
                if(position==0){
                    valor2=0;
                   // Toast.makeText(Registrar.this,"Por favor ingrese su sexo",Toast.LENGTH_LONG).show();
                }else if(position>0){
                    valor2=position;

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
                if (position==0){
                    valor=0;
                }else if(position>0){
                    valor=position;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


registrar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
       CheckDataEntered();
    }
});


}

    private boolean CheckDataEntered() {


        if (TextUtils.isEmpty(nombre.getText().toString().trim())) {
            nombre.setError( "Por favor Ingrese su nombre");
            return true;
        }else{
            pnombre = nombre.getText().toString().trim();

        }
        if (TextUtils.isEmpty(apellido.getText().toString().trim())) {
            apellido.setError( "Por favor Ingrese su apellido");
            return true;
        }else{
            pApellido = apellido.getText().toString().trim();

        }
        if (TextUtils.isEmpty(dni.getText().toString().trim())) {
            dni.setError( "Por favor Ingrese su documento");
            return true;
        }else{
            pdni = dni.getText().toString().trim();

        }
        if (valor==0){
            Toast.makeText(Registrar.this,"Por favor ingrese su sexo",Toast.LENGTH_LONG).show();
            return true;
        }else
        if (TextUtils.isEmpty(celular.getText().toString().trim())) {
            celular.setError( "Por favor Ingrese su numero de celular");
            return true;
        }else{
            pTelefono = celular.getText().toString().trim();
        }
        if (TextUtils.isEmpty(usuario.getText().toString().trim())) {
            usuario.setError( "Por favor Ingrese su usuario");
            return true;
        }else{
            pUsuario = usuario.getText().toString().trim();
        }
        if (TextUtils.isEmpty(password.getText().toString().trim())) {
            password.setError( "Por favor Ingrese su contraseña");
            return true;
        }else{
            pPass = password.getText().toString().trim();
        }
        if (TextUtils.isEmpty(confirmarPass.getText().toString().trim())) {
            confirmarPass.setError( "Por favor confirma su contraseña");
            return true;
        }
        if (!pPass.equals(confirmarPass.getText().toString().trim())){
            confirmarPass.setError("Su contraseña no coincide!");
            return true;
        }else{
            pRepass
                    = confirmarPass.getText().toString().trim();
        }
        if (TextUtils.isEmpty(email.getText().toString().trim())) {
            email.setError( "Por favor Ingrese su correo");
            return true;
        }else

        if(!isValidEmailId(email.getText().toString().trim())){
            email.setError("Ingrese un correo valido !");
            return true;
           // Toast.makeText(getApplicationContext(), "inValid Email Address.", Toast.LENGTH_SHORT).show();
        }
        else{
            pemail = email.getText().toString().trim();
        }
        if (valor2==0){
            Toast.makeText(Registrar.this,"Por favor ingrese su carrera !",Toast.LENGTH_LONG).show();
        }else{
           // new Signup().execute();
            
            
            new Processo().execute();
            Toast.makeText(Registrar.this,pnombre +" "+ pApellido+" "+pdni +" "+SelectedCarrera,Toast.LENGTH_LONG).show();
        }



        return false;
    }


   public class Processo extends AsyncTask <String,String,String> {
       @Override
       protected void onPreExecute() {

           super.onPreExecute();

           progressDialog = ProgressDialog.show(Registrar.this,"Cargando Datos ","Por Favor Espere..",false,false);
       }
       @Override
       protected String doInBackground(String... strings) {

         //  ProcessClass imageProcessClass = new ProcessClass();
           HashMap<String,String> HashMapParams = new HashMap<String,String>();

           hashMap.put(documento, pdni);
           hashMap.put(documento, pdni);
           hashMap.put(nombreAlumno, pnombre);
           hashMap.put(ape, pApellido);
           hashMap.put(genero, selectedSexo);
           hashMap.put(USUARIO, pUsuario);
           hashMap.put(pass, pPass);
           hashMap.put(repass, pRepass);
           hashMap.put(EMAIL , pemail);
           hashMap.put(carre , SelectedCarrera);
           hashMap.put(telefono, pTelefono);



           finalResult = httpParse.postRequest(hashMap, ServerUploadPath);
            Log.e("REGISTRARES",finalResult);
           return finalResult;


          /* HashMapParams.put(documento, pdni);
           HashMapParams.put(nombreAlumno, pnombre);
           HashMapParams.put(ape, pApellido);
           HashMapParams.put(genero, selectedSexo);
           HashMapParams.put(USUARIO, pUsuario);
           HashMapParams.put(pass, pPass);
           HashMapParams.put(repass, pRepass);
           HashMapParams.put(EMAIL, pemail);
           HashMapParams.put(carre, SelectedCarrera);
           HashMapParams.put(telefono, pTelefono);*/





          // String FinalData = imageProcessClass.ImageHttpRequest(ServerUploadPath, HashMapParams);

           //return FinalData;
       }
       @Override
       protected void onPostExecute(String string1) {

           super.onPostExecute(string1);


          // progressDialog.dismiss();

           Toast.makeText(Registrar.this,string1, Toast.LENGTH_LONG).show();
           Intent intent = new Intent(Registrar.this, Login.class);
           startActivity(intent);

            }




    }
    public void GetValueFromEditText(){

        pnombre = nombre.getText().toString().trim();
        pApellido = apellido.getText().toString().trim();
        pdni = dni.getText().toString().trim();
        pTelefono = celular.getText().toString().trim();
        pUsuario = usuario.getText().toString().trim();
        pPass = password.getText().toString().trim();
        repass = confirmarPass.getText().toString().trim();
        pemail = email.getText().toString().trim();


    }



    private boolean isValidEmailId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    }

