package com.example.dv.productomaterial;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

public class Principal extends AppCompatActivity implements AdaptadorProducto.OnProductoClickListener {
    private RecyclerView listado;
    private ArrayList<Producto> productos;
    private AdaptadorProducto adapter;
    private LinearLayoutManager llm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.dv.productomaterial.R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(com.example.dv.productomaterial.R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(com.example.dv.productomaterial.R.id.fab);
        listado = (RecyclerView) findViewById(com.example.dv.productomaterial.R.id.lstOpciones);

        productos = Datos.traerProductos(getApplicationContext());
        adapter = new AdaptadorProducto(productos,this);
        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        listado.setLayoutManager(llm);
        listado.setAdapter(adapter);

    }

    public void agregar(View v){
        finish();
        Intent i = new Intent(Principal.this, AgregarProducto.class);
        startActivity(i);
    }

    @Override
    public void onProductoClick(Producto p) {
        //finish();
        Intent i = new Intent(Principal.this,DetalleProducto.class);
        Bundle b = new Bundle();
        b.putString("nombre",p.getNombre());
        b.putString("precio",p.getPrecio());
        b.putString("urlfoto",p.getUrlfoto());
        i.putExtra("datos",b);
        startActivity(i);
    }

}
