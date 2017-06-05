package com.example.dv.productomaterial;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class DetalleProducto extends AppCompatActivity {
   private CollapsingToolbarLayout collapsingToolbarLayout;
    private Producto p;
    private String nombre,precio,urlfoto;
    private Bundle b;
    private Intent i;
    private ImageView foto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.dv.productomaterial.R.layout.activity_detalle_producto);

        Toolbar toolbar= (Toolbar)findViewById(com.example.dv.productomaterial.R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        i = getIntent();
        b=i.getBundleExtra("datos");
        nombre = b.getString("nombre");
        precio = b.getString("precio");
        urlfoto = b.getString("urlfoto");

        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(com.example.dv.productomaterial.R.id.collapsing_toolbar);
        foto = (ImageView)findViewById(com.example.dv.productomaterial.R.id.fotoProducto);

        Picasso.with(getApplicationContext()).load(urlfoto).into(foto);
        collapsingToolbarLayout.setTitle(nombre+" "+precio);

    }
}
