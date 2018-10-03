package com.akarvisi.barbercamp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.akarvisi.barbercamp.R;
import com.akarvisi.barbercamp.domain.Product;

/**
 * Created by hizry on 5/10/2018.
 */

public class ProductSearchAdapter extends BaseAdapter {
    private Context _context;
    private Product[] _products;
    private  final ActionCallback _callback;

    public interface  ActionCallback{
        void onSelect(Product product);
    }

    public ProductSearchAdapter(@NonNull ActionCallback callback, Context context)
    {
        _callback = callback;
        _context = context;
    }

    @Override
    public int getCount() {
        return _products.length;
    }

    public void setProducts (@NonNull Product[] products)
    {
        this._products = products;
       // notifyDataSetChanged();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final  Product product = _products[position];
        if (convertView == null){
            final  LayoutInflater layoutInflater = LayoutInflater.from(_context);
            convertView = layoutInflater.inflate(R.layout.item_product_search, null);

            final  TextView initialProduct = (TextView) convertView.findViewById(R.id.productInitial);
            final  TextView priceIniTial = (TextView) convertView.findViewById(R.id.shortPrice);
            final  TextView productId = (TextView) convertView.findViewById(R.id.productId);

            final ViewHolder viewHolder = new ViewHolder(initialProduct, priceIniTial, productId);
            convertView.setTag(viewHolder);
        }

        final ViewHolder viewHolder = (ViewHolder)convertView.getTag();
        viewHolder._productId.setText(Integer.toString(product.getId()) );
        viewHolder._shortPrice.setText(Integer.toString(product.getPrice()));
        viewHolder._productInitial.setText(product.getProductName());


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product product1 = _products[position];
                _callback.onSelect(product1);
                Log.d("Log Load","Products"+ product1.getProductName());
            }
        });

        return convertView;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions(){
        return  new CharSequence[0];
    }

    private  class ViewHolder
    {
        private final TextView _productInitial;
        private final TextView _shortPrice;
        private final TextView _productId;


        public ViewHolder (TextView productInitial, TextView shortPrice, TextView productId){
           _productInitial = productInitial;
           _shortPrice = shortPrice;
           _productId = productId;
        }
    }
}
