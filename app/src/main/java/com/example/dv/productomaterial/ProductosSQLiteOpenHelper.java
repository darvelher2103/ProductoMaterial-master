package com.example.dv.productomaterial;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lab Software 1 on 01/06/2017.
 */
public class ProductosSQLiteOpenHelper extends SQLiteOpenHelper {
    private String sql = "CREATE TABLE Productos(uuid text, urlfoto text, codigo text, nombre text, precio text, idfoto text)";
    private static int version=1;
    public ProductosSQLiteOpenHelper(Context contexto, String name, SQLiteDatabase.CursorFactory factory){

        super(contexto, name, factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Productos");
        db.execSQL(sql);
    }
}
