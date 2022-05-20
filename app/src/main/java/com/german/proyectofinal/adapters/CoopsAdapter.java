package com.german.proyectofinal.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.german.proyectofinal.Cooperativa;
import com.german.proyectofinal.activities.CoopsActivity;
import com.german.proyectofinal.R;

import java.util.List;

public class CoopsAdapter extends RecyclerView.Adapter<CoopsAdapter.ViewHolder>  {
    private List<Cooperativa> mData;
    private LayoutInflater mInflater;
    private Context context;

    public CoopsAdapter(List<Cooperativa> itemList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
    }

    @Override
    public int getItemCount(){
        return mData.size();
    }

    @Override
    public CoopsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.coop_list, null);
        return new CoopsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CoopsAdapter.ViewHolder holder, final int position){
        holder.bindData(mData.get(position));
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombre, desc;

        ViewHolder(View itemView){
            super(itemView);
            nombre = itemView.findViewById(R.id.txtNombreCoop);
            desc = itemView.findViewById(R.id.txtUsuario);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, CoopsActivity.class);
                    Log.d("asdf", nombre.getText().toString());
                    intent.putExtra("nombre", nombre.getText().toString());
                    context.startActivity(intent);
                }

                });
            };

        void bindData(final Cooperativa item){
            nombre.setText(item.getNombre());
            //desc.setText(item.getDescripcion());
        }
    }

}
