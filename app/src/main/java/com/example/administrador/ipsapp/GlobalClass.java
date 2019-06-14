package com.example.administrador.ipsapp;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

/**
 * Created by Administrador on 28/02/2018.
 */

public class GlobalClass extends Application {
   private String nombre;
   private String dni;
    private String id;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {

        return dni;
    }

    public void setDni(String dni) {
//        Log.i("PRUEBA",dni);
        this.dni = dni;
    }

    public String getId() {
        return id;
    }

    public String setId(String id) {
        this.id = id;
        return id;
    }
}
