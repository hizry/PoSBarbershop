package com.akarvisi.barbercamp;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.GridView;

import com.akarvisi.barbercamp.adapter.ProductSearchAdapter;

import com.akarvisi.barbercamp.domain.Product;
import com.google.gson.Gson;
import java.util.Arrays;
import java.util.List;



public class TransactionActivity extends AppCompatActivity implements ProductSearchAdapter.ActionCallback  {

    private ProductSearchAdapter _productSearchAdapter;
    public static  final String INITIAL_NAME = "INITIAL_NAME";
    private String _initialName;
    private  List<Product> _products;
    private List<Product> _productLineItem;

    public static Bundle newInstanseBundle(String initialName){
        Bundle bundle = new Bundle();
        bundle.putString(INITIAL_NAME, initialName);
        return bundle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        if (intent.hasExtra(INITIAL_NAME))
        {
            this._initialName = intent.getStringExtra(INITIAL_NAME);
        }

        if (intent.hasExtra("PRODUCTS")){
            String arrayAsString = getIntent().getExtras().getString("PRODUCTS");
            this._products = Arrays.asList(new Gson().fromJson(arrayAsString, Product[].class));
        }

       // getActionBar().setTitle("Hello world App");
        getSupportActionBar().setTitle( "Transaksi: " + _initialName);

        _productSearchAdapter = new ProductSearchAdapter(this, TransactionActivity.this);

        //LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("custom-message"));
    }

//    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            // Get extra data included in the Intent
//            String ItemName = intent.getStringExtra("item");
//        }
//    };

    

    @Override
    public void onSelect(Product product) {

    }

    @Override
    public  void onResume(){
        super.onResume();
        loadSearchProduct();
    }

    private void loadSearchProduct() {
        new AsyncTask<Void, Void, List<Product>>() {
            @Override
            protected List<Product> doInBackground(Void... params) {
                Log.d("Log Load","Products");
                return _products;
            }

            @Override
            protected void onPostExecute(List<Product> products)
            {
                Product[] array = new Product[products.size()];
                products.toArray(array); // fill the array

                GridView gridView = (GridView) findViewById(R.id.gridviewProducts);
                _productSearchAdapter.setProducts(array);
                gridView.setAdapter(_productSearchAdapter);
        }
        }.execute();
    }


}
