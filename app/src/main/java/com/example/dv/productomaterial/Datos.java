package com.example.dv.productomaterial;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Lab Software 1 on 01/06/2017.
 */


public class Datos {
    public static ArrayList<Producto> traerProductos(Context contexto){
        ArrayList<Producto> productos = new ArrayList<>();

        //Declarar variables
        SQLiteDatabase db;
        String sql, uuid,urlfoto, codigo, nombre, precio, idfoto;
        Producto p;

        //Abrir conexión de lectura
        ProductosSQLiteOpenHelper aux = new ProductosSQLiteOpenHelper(contexto,"DBProductos",null);
        db = aux.getReadableDatabase();

        //Cursor
        sql ="select * from Productos";
        Cursor c = db.rawQuery(sql,null);

        //Recorrido del cursor
        if(c.moveToFirst()){
            do{
                uuid = c.getString(0);
                urlfoto=c.getString(1);
                codigo = c.getString(2);
                nombre =c.getString(3);
                precio = c.getString(4);
                idfoto = c.getColumnName(5);

                p = new Producto(uuid,urlfoto,codigo,nombre,precio,idfoto);
                productos.add(p);

            }while (c.moveToNext());
        }
        db.close();
        return productos;

    }


    /*public static Persona buscarPersona(Context contexto, String ced){


        //Declarar variables
        SQLiteDatabase db;
        String sql, uuid,urlfoto, cedula, nombre, apellido,idfoto;
        Persona p=null;
        //Abrir conexión de lectura
        PersonasSQLiteOpenHelper aux = new PersonasSQLiteOpenHelper(contexto,"DBPersonas",null);
        db = aux.getReadableDatabase();

        //Cursor
        sql ="select * from Personas where cedula ='"+ced+"'";
        Cursor c = db.rawQuery(sql,null);

        //Recorrido del cursor
        if(c.moveToFirst()){

            uuid = c.getString(0);
            urlfoto=c.getString(1);
            cedula = c.getString(2);
            nombre =c.getString(3);
            apellido = c.getString(4);
            idfoto = c.getColumnName(5);

            p = new Persona(uuid,urlfoto,cedula,nombre,apellido,idfoto);
        }

        db.close();
        return p;
    }*/

}
