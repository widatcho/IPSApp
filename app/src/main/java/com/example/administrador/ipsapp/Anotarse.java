package com.example.administrador.ipsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import cz.msebera.android.httpclient.client.cache.Resource;

/**
 * Created by Administrador on 07/02/2018.
 */

public class Anotarse extends Fragment {
    TextView TNombre,TCarera,Tdni,Tfecha;
    GlobalClass globalClass;
    EditText nombre,apellido,dni,mat1,mat2,mat3,mat4,mat5;
    Spinner carrera;
    String dniUser;
    ProgressDialog progressDialog ;
    Button anotar;
    String selectedCarrera;
    String Nombre ,Apellido,Dni, CarreraAlumno,Usuario,Contraseña;
    String []Carrera ={"CARRERAS....",
            "Analista Universitario en Sistemas",
            "Técnico Universitario en Gestión y Producción",
            "Técnico Universitario en Mecatrónica",
            "Técnico Universitario en Plásticos y Elastómeros",
            "Técnico Universitario en Química",
            "Técnico Universitario en Sistemas Electrónicos",

    };
    String getNombreAlumno,getApellidoAlumno,getDocumento,getMateria1,getMateria2,getMateria3,getMateria4,getMateria5;

    String nombreAlumno = "nombreAlumno" ;
    String apellidoAlumno="apellidoAlumno";
    String documento="dni";
    String carre = "carrera";
    String materia1="materia1";
    String materia2="materia2";
    String materia3="materia3";
    String materia4="materia4";
    String materia5="materia5";
    String ServerUploadPath ="http://danbijann.freeiz.com/ips/anotar.php";

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.anotarse, container, false);
       //dniUser = getArguments().getString("UserEmailTAG");
        globalClass= new GlobalClass();
        TNombre=(TextView) view.findViewById(R.id.txtNombreApel);
        TCarera=(TextView) view.findViewById(R.id.txtCarrera);
        Tdni=(TextView) view.findViewById(R.id.txtDni);
        Tfecha =(TextView) view.findViewById(R.id.txtFecha);



       /* q1=(Spinner) view.findViewById(R.id.Quatri1);
        q2=(Spinner) view.findViewById(R.id.Quatri2);
        q3=(Spinner) view.findViewById(R.id.Quatri3);
        q4=(Spinner) view.findViewById(R.id.Quatri4);
        q5=(Spinner) view.findViewById(R.id.Quatri5);



        m1=(Spinner) view.findViewById(R.id.spinnerMateria1);
        m2=(Spinner) view.findViewById(R.id.spinnerMateria2);
        m3=(Spinner) view.findViewById(R.id.spinnerMateria3);
        m4=(Spinner) view.findViewById(R.id.spinnerMateria4);
        m5=(Spinner) view.findViewById(R.id.spinnerMateria5);*/




        nombre = (EditText)view.findViewById(R.id.etnombre);
        apellido = (EditText)view.findViewById(R.id.etApellido);
        dni = (EditText)view.findViewById(R.id.etDni);

        carrera =(Spinner)view.findViewById(R.id.spinner);
     //   mat1 = (EditText)view.findViewById(R.id.editText);
       // mat2 = (EditText)view.findViewById(R.id.editText2);
       // mat3 = (EditText)view.findViewById(R.id.editText3);
       // mat4 = (EditText)view.findViewById(R.id.editText4);
       // mat5 = (EditText)view.findViewById(R.id.editText5);

     /*   ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, Carrera);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
       // carrera.setAdapter(adapter);
        carrera.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCarrera= (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        anotar=(Button) view.findViewById(R.id.button2);
        new CargadoDatos().execute();

        anotar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNombreAlumno = nombre.getText().toString();
                getApellidoAlumno = apellido.getText().toString();
                getDocumento = dni.getText().toString();
                //SelectedCarrera=GetCarrera.toString();
                getMateria1 = mat1.getText().toString();
                getMateria2=mat2.getText().toString();
                getMateria3 = mat3.getText().toString();
                getMateria4 = mat4.getText().toString();
                getMateria5 = mat5.getText().toString();
                Log.e("DATA",getNombreAlumno);
                Log.e("DATA",getApellidoAlumno);
                Log.e("DATA",getDocumento);
                Log.e("DATA",selectedCarrera);
                Log.e("DATA",getMateria1);     Log.e("DATA",getMateria2);     Log.e("DATA",getMateria3);



                new Anotar().execute();
            }
        });
        //fots= (Button) view.findViewById(R.id.buttonfotos);
        return  view;
    }

    private class Anotar extends AsyncTask<String,String,String> {
        protected void onPreExecute() {

            super.onPreExecute();

            progressDialog = ProgressDialog.show(getActivity(), "Cargando Datos ", "Por Favor Espere..", false, false);
        }


            @Override
            protected String doInBackground(String... strings) {

                ProcessClass imageProcessClass = new ProcessClass();
                HashMap<String,String> HashMapParams = new HashMap<String,String>();

                HashMapParams.put(nombreAlumno, getNombreAlumno);
                HashMapParams.put(apellidoAlumno, getApellidoAlumno);
                HashMapParams.put(documento, getDocumento);
                HashMapParams.put(carre, selectedCarrera);
                HashMapParams.put(materia1, getMateria1);
                HashMapParams.put(materia2, getMateria2);
                HashMapParams.put(materia3, getMateria3);
                HashMapParams.put(materia4, getMateria4);
                HashMapParams.put(materia5, getMateria5);



                String FinalData = imageProcessClass.ImageHttpRequest(ServerUploadPath, HashMapParams);

                return FinalData;
            }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);

                // Dismiss the progress dialog after done uploading.
                progressDialog.dismiss();

                // Printing uploading success message coming from server on android app.
                Toast.makeText(getActivity(),"hola",Toast.LENGTH_LONG).show();

                // Setting image as transparent after done uploading.
                // circularImageView.setImageResource(android.R.color.transparent);
            /*    nombre.getText().clear();
                email.getText().clear();
                dni.getText().clear();
                apellido.getText().clear();
                usuario.getText().clear();
                password.getText().clear();*/
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);


            }
        }

    private class CargadoDatos extends AsyncTask<String,String,String>{

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
                    //id= c.getString("id");
                    Nombre = c.getString("nombre");
                    Apellido= c.getString("apellido");
                    Dni= c.getString("dni");
                    CarreraAlumno= c.getString("carrera");


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Date fecha = new Date();

            super.onPostExecute(s);
            TNombre.setText(Nombre +Apellido );
            Tdni.setText(Dni);
            TCarera.setText(CarreraAlumno);
           // Tfecha.setText((CharSequence) fecha);
            }
    }
}
