package com.akarvisi.barbercamp.adapter;

/**
 * Created by hizry on 1/27/2018.zz
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.akarvisi.barbercamp.domain.Product;
import com.akarvisi.barbercamp.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<Product> _products = new ArrayList<>();
    private  List<Bitmap> _productImages = new ArrayList<>();
    private  final  ActionCallback _callback;
    private  Context _context;


    public ProductAdapter(@NonNull ActionCallback callback, Context context) {
        this._callback = callback;
        _context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                _callback.onEdit(_products.get(viewHolder.getAdapterPosition()));
//            }
//        });


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        DecimalFormat myFormater;
        final Product item_model= _products.get(position);
        myFormater = new DecimalFormat("#,###,###,###");
        holder.title.setText(item_model.getProductName());
        holder.price.setText("Rp. "+myFormater.format(item_model.getPrice()));
        holder.stock.setText("Stok: "+String.valueOf(item_model.getStock()));
        holder.capsterCommision.setText("Komisi: Rp. "+ String.valueOf(myFormater.format(item_model.getCapsterCommision())));
       // holder.imageName.setText(String.valueOf(item_model.getImagePath()));
        holder.imageViewProduct.setImageBitmap(item_model.getImageProduct());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _callback.onEdit(_products.get(holder.getAdapterPosition()));
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(_context, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(_context);
                }
                builder.setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                _callback.deleteProduct(_products.get(holder.getAdapterPosition()));
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });


//        Bitmap bitmap = new ImageSaver(android.content.ContextWrapper.getApplicationContext()).
//                setFileName("Test5.png").
//                setDirectoryName("images").
//                load();
//        if (bitmap != null)
//        {
//            _imageView.setImageBitmap(bitmap);
//        }
    }

    @Override
    public int getItemCount() {
        return _products.size();
    }

    public Product getProduct(int position) {
        return _products.get(position);
    }

    public void setProducts(@NonNull List<Product> products) {
        this._products = products;
        notifyDataSetChanged();
    }

    public interface ActionCallback {
        void onEdit(Product product);
        void deleteProduct(Product product);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, price, stock, capsterCommision, edit, delete;
       // TextView imageName;
        ImageView imageViewProduct;
        ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.productName);
            price= (TextView) itemView.findViewById(R.id.price);
            stock = (TextView) itemView.findViewById(R.id.stock);
            capsterCommision =(TextView) itemView.findViewById(R.id.capsterCommision);
            edit = (TextView) itemView.findViewById(R.id.edit);
            delete = (TextView) itemView.findViewById(R.id.delete);
           // imageName = (TextView) itemView.findViewById(R.id.imageName);
            imageViewProduct = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }


}


