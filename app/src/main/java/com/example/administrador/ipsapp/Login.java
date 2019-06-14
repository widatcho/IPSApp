package com.example.administrador.ipsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    TextView registro;
Button login ;
GlobalClass globalClass;
    ProgressDialog progressDialog;
    Boolean CheckEditText;
    CheckBox profe, alumnos ;
    EditText etUsuario, etContra;
    String UserHolder, PasswordHolder;
    String HttpUrl = "http://danbijann.freeiz.com/ips/login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
       // Inicio();
        registro = (TextView) findViewById(R.id.registrar);
        login = (Button) findViewById(R.id.butLogin);

        etUsuario = (EditText)findViewById(R.id.etUser);
        etContra = (EditText)findViewById(R.id.etPass);

        profe = (CheckBox) findViewById(R.id.checkBoxProfe);
        alumnos=(CheckBox) findViewById(R.id.checkBox);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckEditTextIsEmptyOrNot();
               if(alumnos.isChecked()== true){


                    if (CheckEditText) {

                        UserLogin();

                    } else {
                        etUsuario.setError("Usuario requerido.");
                        etContra.setError("Contraseña requerido.");
                       // Toast.makeText(Login.this, "Porfavor Llene los campos con sus datos.", Toast.LENGTH_LONG).show();

                   }

                    //Toast.makeText(Login.this,"HOLA",Toast.LENGTH_LONG).show();

                }else{
                   Toast.makeText(Login.this,"Eres Alumno ? o Eres Profesor ?",Toast.LENGTH_LONG).show();
                }
                if(profe.isChecked()== true){
                    Intent inten = new Intent (Login.this,Profesor.class);
                    startActivity(inten);
                    //

                }
            }
        });

registro.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent inten = new Intent (Login.this,Registrar.class);
        startActivity(inten
        );
    }
});







    }



    private void UserLogin() {
//   progressDialog.setMessage("Aguarde");
       // progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                       // progressDialog.dismiss();
Log.i("RESPONSES",ServerResponse);
                        // Matching server responce message to our text.
                        if(ServerResponse.equalsIgnoreCase("Data Matched")) {


                            finish();

                            // Opening the user profile activity using intent.
                            Intent intent = new Intent(Login.this, Bienvenido.class);
                            globalClass=(GlobalClass) getApplicationContext();
                            globalClass.setDni(UserHolder);

                            // Sending User Email to another activity using intent.
                            intent.putExtra("UserEmailTAG", UserHolder);

                            startActivity(intent);
                        }
                        else {

                            // Showing Echo Response Message Coming From Server.
                            Toast.makeText(Login.this, ServerResponse, Toast.LENGTH_LONG).show();

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {



                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(Login.this, volleyError.toString(), Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("dni", UserHolder);
                params.put("password", PasswordHolder);
Log.e("Param", String.valueOf(params));
                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(Login.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }

    private void CheckEditTextIsEmptyOrNot() {

        // Getting values from EditText.
        UserHolder = etUsuario.getText().toString().trim();
        PasswordHolder = etContra.getText().toString().trim();

        // Checking whether EditText value is empty or not.
        if (TextUtils.isEmpty(UserHolder) || TextUtils.isEmpty(PasswordHolder)) {

            // If any of EditText is empty then set variable value as False.
            CheckEditText = false;

        } else {

            // If any of EditText is filled then set variable value as True.
            CheckEditText = true;
        }
    }
    }
