package com.example.administrador.ipsapp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Pattern;

public class Modificacion extends AppCompatActivity {
    EditText nombre,apellido,dni,email,usuario,password,confirmarPass,celular;
    Spinner spinnerDropDownCarrera;
    Spinner sexo;
    String selectedSexo,SelectedCarrera,finalResult,ERROR;
    String pNombre,pApellido,Pdni,Pemail,pUsuario,Ppass,Prepass,pTelefono;
    String dniUser,id,pnombre,Papellido,pusuario,Ppassword,pre_password,pemail,ptelefono;
    ProgressDialog progressDialog ,progress;
    GlobalClass globalClass;
    int valor,valor2 ;
    Button modificar;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();

    String []sexoAlumno={"SEXO....","Masculino","Feminino"};
    String []Carrera ={"CARRERAS....",
            "Analista Universitario en Sistemas",
            "Técnico Universitario en Gestión y Producción",
            "Técnico Universitario en Mecatrónica",
            "Técnico Universitario en Plásticos y Elastómeros",
            "Técnico Universitario en Química",
            "Técnico Universitario en Sistemas Electrónicos",

    };

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificacion);
        globalClass=(GlobalClass)getApplication();
        modificar=(Button) findViewById(R.id.modificacion);

        spinnerDropDownCarrera =(Spinner)findViewById(R.id.spinnerCarrera);
        sexo =(Spinner)findViewById(R.id.spinnerSexo);


        nombre = (EditText)findViewById(R.id.etNoombreAlumno);
        apellido = (EditText)findViewById(R.id.etApellidoAlumno);
        dni = (EditText)findViewById(R.id.etDNi);
        email = (EditText)findViewById(R.id.etEmail);
        usuario = (EditText)findViewById(R.id.etUsario);
        password = (EditText)findViewById(R.id.etpassword);
        confirmarPass = (EditText)findViewById(R.id.etRepass);
        celular = (EditText)findViewById(R.id.etCelularAlumno);

        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.
                R.layout.simple_spinner_dropdown_item ,Carrera);
        spinnerDropDownCarrera.setAdapter(adapter);


        spinnerDropDownCarrera.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
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

        new  CargoDatos().execute();

            modificar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CheckDataEntered();
                }
            });
    }
    private class CargoDatos  extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(Modificacion.this,"Cargando Datos ","Por Favor Espere..",false,false);
        }

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
                    pnombre = c.getString("nombre");
                    Papellido= c.getString("apellido");
                    dniUser= c.getString("dni");
                    pemail= c.getString("email");
                    pusuario=c.getString("usuario");
                    Ppassword=c.getString("password");
                    pre_password=c.getString("re_password");
                    ptelefono =c.getString("telefono");

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
            Toast.makeText(Modificacion.this,id, Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            nombre.setText(pnombre);
            apellido.setText(Papellido);
            dni.setText(dniUser);
            email.setText(pemail);
            usuario.setText(pusuario);
            password.setText(Ppassword);
            confirmarPass.setText(pre_password);
            celular.setText(ptelefono);
            globalClass.setPassword(Ppassword);


        }
    }

    private boolean CheckDataEntered() {
        if (TextUtils.isEmpty(nombre.getText().toString().trim())) {
            nombre.setError( "Por favor Ingrese su nombre");
            return true;
        }else{
            pNombre = nombre.getText().toString().trim();

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
            Pdni = dni.getText().toString().trim();

        }
        if (valor==0){
            Toast.makeText(Modificacion.this,"Por favor ingrese su sexo",Toast.LENGTH_LONG).show();
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
            Ppass = password.getText().toString().trim();
        }
        if (TextUtils.isEmpty(confirmarPass.getText().toString().trim())) {
            confirmarPass.setError( "Por favor confirma su contraseña");
            return true;
        }
        if (!Ppass.equals(confirmarPass.getText().toString().trim())){
            confirmarPass.setError("Su contraseña no coincide!");
            return true;
        }else{
            Prepass= confirmarPass.getText().toString().trim();
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
            Pemail = email.getText().toString().trim();
        }
        if (valor2==0){
            Toast.makeText(Modificacion.this,"Por favor ingrese su carrera !",Toast.LENGTH_LONG).show();
        }else{
            // new Signup().execute();


            new UpdateProcesso().execute();
          //  Toast.makeText(Modificacion.this,pnombre +" "+ pApellido+" "+pdni +" "+SelectedCarrera,Toast.LENGTH_LONG).show();
        }



        return false;
    }



    private boolean isValidEmailId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    private class UpdateProcesso extends AsyncTask <String,String,String>{

        String ServerUploadPath ="http://danbijann.freeiz.com/ips/modificarDatos.php?id="+id;
        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            progressDialog = ProgressDialog.show(Modificacion.this,"Modificando sus Datos ","Por Favor Espere..",false,false);
        }
        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> HashMapParams = new HashMap<String,String>();

            hashMap.put("id", id);
            hashMap.put(documento, Pdni);
            hashMap.put(nombreAlumno, pNombre);
            hashMap.put(ape, pApellido);
            hashMap.put(genero, selectedSexo);
            hashMap.put(USUARIO, pUsuario);
            hashMap.put(pass, Ppass);
            hashMap.put(repass, Prepass);
            hashMap.put(EMAIL , Pemail);
            hashMap.put(carre , SelectedCarrera);
            hashMap.put(telefono, pTelefono);



          String  finalResult = httpParse.postRequest(hashMap, ServerUploadPath);
            Log.e("REGISTRARES",finalResult);
            return finalResult;
        }
        protected void onPostExecute(String string1) {

            super.onPostExecute(string1);


             progressDialog.dismiss();

            Toast.makeText(Modificacion.this,string1, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Modificacion.this, Bienvenido.class);
          //  startActivity(intent);

        }

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        /** Create an option menu from res/menu/items.xml */
        getMenuInflater().inflate(R.menu.items, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.darBaja:

                CajadeDialogo();



            default: return super.onOptionsItemSelected(item);
        }
    }

    private void CajadeDialogo() {
        final Dialog dialog2 = new Dialog(Modificacion.this);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setCancelable(false);
        dialog2.setContentView(R.layout.delete_popup);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));

        Button buttonConfirmar , cancelar;
        final EditText Conpass=(EditText) dialog2.findViewById(R.id.etpasswordDelete);
        buttonConfirmar = (Button) dialog2.findViewById(R.id.bt_delete);
        cancelar = (Button) dialog2.findViewById(R.id.butCancelar);


        buttonConfirmar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String pass = Conpass.getText().toString().trim();
                String contra=globalClass.getPassword();
                if (pass.equals(contra)){
                    new  DeleteCuenta().execute();
                    dialog2.dismiss();
                }else{
                    Toast.makeText(Modificacion.this,"Se ha ocurrido un error!", Toast.LENGTH_LONG).show();
                }

                //dialog2.dismiss();
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
            }
        });
        dialog2.show();

    }

    class DeleteCuenta extends AsyncTask<String,String,String> {
  String   HttpUrlDeleteRecord =  "http://danbijann.freeiz.com/ips/DeleteAlumnos.php?id="+id;

        @Override
        protected void onPreExecute() {
            progress = new ProgressDialog(Modificacion.this);
            progress.setMessage("Loading...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.show();
            super.onPreExecute();
        }

        @Override
     protected String doInBackground(String... strings) {
         Log.e("URL",HttpUrlDeleteRecord);
         HttpHandler sh = new HttpHandler();
         JSONObject jsonStr = sh.makeServiceCall(HttpUrlDeleteRecord);
         try {
             ERROR=jsonStr.getString("resultado");
         } catch (JSONException e) {
             e.printStackTrace();
         }


         return null;

     }
     @Override
     protected void onPostExecute(String ResponseMsg) {
         if (progress.isShowing())
             progress.dismiss();
         super.onPostExecute(ResponseMsg);
         Toast.makeText(Modificacion.this, ERROR, Toast.LENGTH_LONG).show();
         Intent intent =new Intent(Modificacion.this,Login.class);
         startActivity(intent);

      /*   Log.e("REGIST",httpResponseMsg);
         Log.e("REGISTE",ERROR);*/


       /* if (httpResponseMsg.equals(ERROR)){
            Intent intent =new Intent(Modificacion.this,Login.class);
            startActivity(intent);
            Toast.makeText(Modificacion.this, httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

        }
           // Intent intent =new Intent(Modificacion.this,Login.class);
           // startActivity(intent);
         Toast.makeText(Modificacion.this, "ERROR", Toast.LENGTH_LONG).show();*/

        // finish();

     }
 }
}
