package com.german.proyectofinal.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.german.proyectofinal.activities.DataCoopsActivity;
import com.german.proyectofinal.Producto;
import com.german.proyectofinal.R;

import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder> {
    private List<Producto> mData;
    private LayoutInflater mInflater;
    private Context context;

    public ProductoAdapter(List<Producto> itemList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
    }

    @Override
    public int getItemCount(){
        return mData.size();
    }

    @Override
    public ProductoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.product_list, null);
        return new ProductoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProductoAdapter.ViewHolder holder, final int position){
        holder.bindData(mData.get(position));
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView coop, nombre;

        ViewHolder(View itemView){
            super(itemView);
            nombre = itemView.findViewById(R.id.txtNombreProducto);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DataCoopsActivity.class);
                    intent.putExtra("producto", nombre.getText());
                    context.startActivity(intent);
                }

            });
        };

        void bindData(final Producto item){
            nombre.setText(item.getNombre());
            //desc.setText(item.getDescripcion());
        }
    }
}
