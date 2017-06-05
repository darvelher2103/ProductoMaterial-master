package com.example.dv.productomaterial;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Lab Software 1 on 01/06/2017.
 */

public class AdaptadorProducto extends RecyclerView.Adapter<AdaptadorProducto.ProductoViewHolder> {

    private ArrayList<Producto> productos;
    private OnProductoClickListener clickListener;

    public AdaptadorProducto(ArrayList<Producto> productos, OnProductoClickListener clickListener){
        this.productos=productos;
        this.clickListener=clickListener;
    }

    @Override
    public AdaptadorProducto.ProductoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(com.example.dv.productomaterial.R.layout.cardview_producto,parent,false);
        return new ProductoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdaptadorProducto.ProductoViewHolder holder, int position) {
        final Producto p = productos.get(position);
        //holder.foto.setImageResource(Integer.parseInt(p.getUrlfoto()));
        Picasso.with(holder.view.getContext()).load(p.getUrlfoto()).into(holder.foto);

        holder.codigo.setText(p.getCodigo());
        holder.nombre.setText(p.getNombre());
        holder.precio.setText(p.getPrecio());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onProductoClick(p);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public static class ProductoViewHolder extends RecyclerView.ViewHolder{
        private ImageView foto;
        private TextView codigo;
        private TextView nombre;
        private TextView precio;
        private View view;

        public ProductoViewHolder(View itemView) {
            super(itemView);
            view= itemView;
            foto = (ImageView)itemView.findViewById(com.example.dv.productomaterial.R.id.foto);
            codigo = (TextView)itemView.findViewById(com.example.dv.productomaterial.R.id.txtCodigoPro);
            nombre = (TextView) itemView.findViewById(com.example.dv.productomaterial.R.id.txtNombrePro);
            precio = (TextView)itemView.findViewById(com.example.dv.productomaterial.R.id.txtPrecioPro);
        }
    }

    public interface OnProductoClickListener{
        void onProductoClick(Producto p);
    }

}
