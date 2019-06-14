package com.example.administrador.ipsapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Bienvenido extends AppCompatActivity {
    WebView myWebView;
    Switch aSwitch;
    GlobalClass globalClass;
    String nombre, apellido , dni;
    TextView txtnombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenido);
        aSwitch = (Switch) findViewById(R.id.switchable);
        txtnombre=(TextView) findViewById(R.id.nombreAlumno) ;
        globalClass=(GlobalClass) getApplication();
        dni= globalClass.getDni();

        new CargarDatos().execute();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        myWebView = (WebView) findViewById(R.id.webView);
        final ProgressDialog pd = ProgressDialog.show(Bienvenido.this, "", "Cargando...", true);
        //
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setUserAgentString("Desktop");

        myWebView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                //Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                pd.show();
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                pd.dismiss();
                txtnombre.setText("Hola ! " +nombre);
                Toast.makeText(Bienvenido.this,"Bienvenid@ "+nombre,Toast.LENGTH_SHORT).show();
                String webUrl = myWebView.getUrl();

            }

        });
        myWebView.loadUrl("https://www.ips.edu.ar/");
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                metodoSwitch();

                //
                Intent intent =new Intent(Bienvenido.this,Anotar.class);

                startActivity(intent);


            }
        });

    }

    private void metodoSwitch() {
        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
              //  mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                aSwitch.setChecked(false);
            }
        }.start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_anotar:
                Intent intent =new Intent(Bienvenido.this,Anotar.class);

                startActivity(intent);
                return true;
            case R.id.action_editar:
                Intent intent2 =new Intent(Bienvenido.this,Modificacion.class);

                startActivity(intent2);
                return true;
            case R.id.action_estado:
                Intent intent3 =new Intent(Bienvenido.this,Estado.class);

                startActivity(intent3);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }
    class CargarDatos extends AsyncTask<String ,String,String> {
        Context context;
        private String TAG = Bienvenido.class.getSimpleName();
        JSONArray aJS;


        protected String doInBackground(String... strings) {


            String linkscript = "";

            // final Intent intent = getIntent();
            linkscript = "http://danbijann.freeiz.com/ips/Select.php?dni="+dni;
            final String url = linkscript;
            Log.e(TAG, "url: " + url);
            HttpHandler sh = new HttpHandler();
            JSONObject oJS = sh.makeServiceCall(url);

            Log.e(TAG, "Response  url: " + oJS);

            try {
                JSONObject jsonObj = new JSONObject(String.valueOf(oJS));
                aJS = jsonObj.getJSONArray("result");
                for (int i=0;i<aJS.length();i++){
                    JSONObject proceso= aJS.getJSONObject(i);
                    nombre =proceso.getString("nombre");
                    apellido =proceso.getString("apellido");
                    dni =proceso.getString("dni");
                    globalClass.setNombre(nombre);

                }



            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
                Log.e("TAG", nombre);
                Log.e("TAG", dni);


            }

            return null;

        }

    }


}
