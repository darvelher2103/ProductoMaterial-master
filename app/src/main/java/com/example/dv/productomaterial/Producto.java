package com.example.dv.productomaterial;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * Created by Lab Software 1 on 01/06/2017.
 */

public class Producto {
    private String uuid;
    private String urlfoto;
    private String idfoto;
    private String codigo;
    private String nombre;
    private String precio;

    public Producto(){

    }

    public Producto(String urlfoto, String codigo, String nombre, String precio, String idfoto) {
        this.uuid= UUID.randomUUID().toString();
        this.urlfoto = urlfoto;
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.idfoto=idfoto;
    }

    public Producto(String uuid, String urlfoto, String codigo, String nombre, String precio, String idfoto) {
        this.uuid = uuid;
        this.urlfoto = urlfoto;
        this.idfoto = idfoto;
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUrlfoto() {
        return urlfoto;
    }

    public void setUrlfoto(String urlfoto) {
        this.urlfoto = urlfoto;
    }

    public String getIdfoto() {
        return idfoto;
    }

    public void setIdfoto(String idfoto) {
        this.idfoto = idfoto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public  void guardar(Context contexto){
        guardarRemoto(contexto);
    }
    public void guardarRemoto(final Context contexto){
        new AsyncTask<Void, Void, String>(){

            @Override
            protected String doInBackground(Void... params) {
                HttpURLConnection conexion = null;

                try {
                    URL url = new URL("http://34.207.114.119/guardar.php");
                    conexion =(HttpURLConnection)url.openConnection();
                    conexion.setConnectTimeout(30000);
                    conexion.setReadTimeout(30000);

                    //Configuracion de envío de datos via POST
                    conexion.setRequestMethod("POST");
                    conexion.setDoOutput(true);
                    conexion.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

                    //Crear consulta con los datos

                    StringBuilder builder = new StringBuilder();
                    builder.append("id");
                    builder.append("=");
                    builder.append(URLEncoder.encode(uuid,"UTF-8"));
                    builder.append("&");
                    builder.append("foto");
                    builder.append("=");
                    builder.append(URLEncoder.encode(getUrlfoto(),"UTF-8"));
                    builder.append("&");
                    builder.append("codigo");
                    builder.append("=");
                    builder.append(URLEncoder.encode(codigo,"UTF-8"));
                    builder.append("&");
                    builder.append("nombre");
                    builder.append("=");
                    builder.append(URLEncoder.encode(nombre,"UTF-8"));
                    builder.append("&");
                    builder.append("precio");
                    builder.append("=");
                    builder.append(URLEncoder.encode(precio,"UTF-8"));
                    builder.append("&");
                    builder.append("idfoto");
                    builder.append("=");
                    builder.append(URLEncoder.encode(idfoto,"UTF-8"));

                    String query = builder.toString();

                    conexion.setFixedLengthStreamingMode(query.getBytes("UTF-8").length);

                    OutputStream outputStream = conexion.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                    bufferedWriter.write(query);
                    bufferedWriter.flush();
                    bufferedWriter.close();

                    //Conectar
                    conexion.connect();

                    //Leer Respuesta del servidor

                    InputStream inputStream = conexion.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder datos = new StringBuilder();
                    String linea;
                    while((linea =bufferedReader.readLine())!=null){
                        datos.append(linea);
                    }

                    bufferedReader.close();
                    return datos.toString();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            return null;

            }

            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    boolean success = jsonObject.getBoolean("success");
                    if(success){
                        urlfoto = jsonObject.getString("foto");
                        guardarLocal(contexto);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }.execute();
    }


    public void guardarLocal(Context contexto){
        //declarar las variables
        SQLiteDatabase db;
        String sql;

        //Abrir la conexion de base datos en modo escritura
        ProductosSQLiteOpenHelper  aux = new ProductosSQLiteOpenHelper(contexto,"DBProductos",null);
        db = aux.getWritableDatabase();

        //insertar forma 1
        sql = "INSERT INTO Productos values('"
                +this.getUuid()+"','"
                +this.getUrlfoto()+"','"
                +this.getCodigo()+"','"
                +this.getNombre()+"','"
                +this.getPrecio()+"','"
                +this.getIdfoto()+"')";

        db.execSQL(sql);
        db.close();

    }

    public void eliminar(Context contexto){
        //declarar las variables
        SQLiteDatabase db;
        String sql;

        //Abrir la conexion de base datos en modo escritura
        ProductosSQLiteOpenHelper  aux = new ProductosSQLiteOpenHelper(contexto,"DBProductos",null);
        db = aux.getWritableDatabase();

        sql = "DELETE FROM Productos where codigo='"+this.getCodigo()+"'";
        db.execSQL(sql);
        db.close();

    }

    public void modificar(Context contexto){
        //declarar las variables
        SQLiteDatabase db;
        String sql;

        //Abrir la conexion de base datos en modo escritura
        ProductosSQLiteOpenHelper  aux = new ProductosSQLiteOpenHelper(contexto,"DBProductos",null);
        db = aux.getWritableDatabase();

        //insertar forma 1
        sql = "UPDATE Productos SET nombre='"+this.getNombre()+"', precio='"+this.getPrecio()+"' where codigo ='"+this.getCodigo()+"'";
        db.execSQL(sql);

        //cerrar conexion
        db.close();

    }



}