package com.example.paises;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.PlaceHolderView;

import java.util.ArrayList;

public class AdaptadorPaises extends PlaceHolderView.Adapter<AdaptadorPaises.ViewHolderPaises> implements View.OnClickListener {

    Context context;
    ArrayList<Paises> listaPaises;
    private View.OnClickListener listener;

    public AdaptadorPaises(ArrayList<Paises> listaPaises) {
        this.listaPaises = listaPaises;
    }

    @Override
    public ViewHolderPaises onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ly_paises,null,false);
        view.setOnClickListener(this);
        return new ViewHolderPaises(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderPaises holder, int position) {
        holder.nombrePais.setText(listaPaises.get(position).getNombre());

        Glide.with(this.context)
                .load(listaPaises.get(position).getUrl())
                .into(holder.imagenPais);
    }

    public AdaptadorPaises(ArrayList<Paises> listaDatos, Context contexto){
        this.context = contexto;
        this.listaPaises = listaDatos;
    }

    @Override
    public int getItemCount() {
        return listaPaises.size();
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    public class ViewHolderPaises extends RecyclerView.ViewHolder {
        TextView nombrePais;
        ImageView imagenPais;

        public ViewHolderPaises(final View itemView) {
            super(itemView);
            nombrePais=(TextView)itemView.findViewById(R.id.lblNombrePais);
            imagenPais=(ImageView)itemView.findViewById(R.id.imgBanderaPais);
        }
    }
}

