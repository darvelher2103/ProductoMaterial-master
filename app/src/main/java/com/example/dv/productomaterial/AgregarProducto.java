package com.example.dv.productomaterial;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;

public class AgregarProducto extends AppCompatActivity {
    private EditText cajaCodigo;
    private EditText cajaNombre;
    private EditText cajaPrecio;
    private boolean guardado;
    private TextInputLayout icajaCodigo;
    private TextInputLayout icajaNombre;
    private TextInputLayout icajaPrecio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.dv.productomaterial.R.layout.activity_agregar_producto);

        cajaCodigo = (EditText)findViewById(com.example.dv.productomaterial.R.id.txtCodigo);
        cajaNombre= (EditText)findViewById(com.example.dv.productomaterial.R.id.txtNombre);
        cajaPrecio = (EditText)findViewById(com.example.dv.productomaterial.R.id.txtPrecio);

        icajaCodigo = (TextInputLayout) findViewById(com.example.dv.productomaterial.R.id.codigoProducto);
        icajaNombre = (TextInputLayout)findViewById(com.example.dv.productomaterial.R.id.nombreProducto);
        icajaPrecio = (TextInputLayout)findViewById(com.example.dv.productomaterial.R.id.precioProducto);
        guardado = false;

        cajaCodigo.addTextChangedListener(new TextWatcherPersonalizado(icajaCodigo,getResources().getString(com.example.dv.productomaterial.R.string.mensaje_error_codigo)) {
            @Override
            public boolean estaVacio(Editable s) {
                if(TextUtils.isEmpty(s) && !guardado) return true;
                else return false;
            }
        });

        cajaNombre.addTextChangedListener(new TextWatcherPersonalizado(icajaNombre,getResources().getString(com.example.dv.productomaterial.R.string.mensaje_error_nombre)) {
            @Override
            public boolean estaVacio(Editable s) {
                if(TextUtils.isEmpty(s)&& !guardado) return true;
                else return false;
            }
        });

        cajaPrecio.addTextChangedListener(new TextWatcherPersonalizado(icajaPrecio,getResources().getString(com.example.dv.productomaterial.R.string.mensaje_error_precio)) {
            @Override
            public boolean estaVacio(Editable s) {
                if(TextUtils.isEmpty(s)&& !guardado) return true;
                else return false;
            }
        });

    }

    public int fotoAleatoria(){
        int fotos[] = {com.example.dv.productomaterial.R.drawable.images, com.example.dv.productomaterial.R.drawable.images2, com.example.dv.productomaterial.R.drawable.images3};
        int numero = (int)(Math.random() * 3);
        return fotos[numero];
    }

    public void guardar(View v)  {
        String urlfoto,codigo,nombre,precio,idfoto;
        Producto p;
        int foto;

        if(validarTodo()){
            codigo = cajaCodigo.getText().toString();
            nombre = cajaNombre.getText().toString();
            precio = cajaPrecio.getText().toString();

            foto = fotoAleatoria();
            Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),foto);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
            byte[] imagenBytes = baos.toByteArray();
            urlfoto = Base64.encodeToString(imagenBytes,Base64.DEFAULT);
            try {
                baos.close();
            }catch (Exception e){

            }
                idfoto=String.valueOf(foto);
            p = new Producto(urlfoto,codigo,nombre,precio,idfoto);
            p.guardar(getApplicationContext());

            InputMethodManager imp =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imp.hideSoftInputFromWindow(cajaCodigo.getWindowToken(),0);
            Snackbar.make(v,getResources().getString(com.example.dv.productomaterial.R.string.mensaje_exitoso_guardar),Snackbar.LENGTH_SHORT).show();
            guardado= true;
            limpiar();
        }
    }

    public void limpiar(){
        cajaCodigo.setText("");
        cajaNombre.setText("");
        cajaPrecio.setText("");
        cajaCodigo.requestFocus();
        guardado = false;
    }

    public boolean validarTodo(){
        if(cajaCodigo.getText().toString().isEmpty()){
            icajaCodigo.setError(getResources().getString(com.example.dv.productomaterial.R.string.mensaje_error_codigo));
            cajaCodigo.requestFocus();
            return false;
        }
        if(cajaNombre.getText().toString().isEmpty()){
            icajaNombre.setError(getResources().getString(com.example.dv.productomaterial.R.string.mensaje_error_nombre));
            cajaNombre.requestFocus();
            return false;
        }
        if(cajaPrecio.getText().toString().isEmpty()){
            icajaPrecio.setError(getResources().getString(com.example.dv.productomaterial.R.string.mensaje_error_precio));
            cajaPrecio.requestFocus();
            return false;
        }
        return true;
    }

    public void onBackPressed(){
        finish();
        Intent i = new Intent(AgregarProducto.this,Principal.class);
        startActivity(i);
    }
}
