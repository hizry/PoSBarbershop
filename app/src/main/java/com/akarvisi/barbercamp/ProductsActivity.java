package com.akarvisi.barbercamp;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.akarvisi.barbercamp.adapter.ProductAdapter;
import com.akarvisi.barbercamp.database.AppDatabase;
import com.akarvisi.barbercamp.domain.Product;
import com.akarvisi.barbercamp.helper.ImageSaver;

import java.util.List;

public class ProductsActivity extends AppCompatActivity implements ProductAdapter.ActionCallback {

    ProductAdapter _productAdapter;
    AppDatabase _db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductsActivity.this, ProductFormActivity.class));
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
        _db = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DBName).build();
        _productAdapter = new ProductAdapter(this, ProductsActivity.this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(_productAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public  void onResume(){
        super.onResume();
        loadProducts();
        Log.d("TEST","RESUME RUN");
    }

    private void loadProducts() {
        new AsyncTask<Void, Void, List<Product>>() {
            @Override
            protected List<Product> doInBackground(Void... params) {
                Log.d("Log Load","Products");
                return _db.GetProductDao().GetProduct();
            }

            @Override
            protected void onPostExecute(List<Product> products) {
                for (Product item : products)
                {
                    Bitmap bitmap = new ImageSaver(getApplicationContext()).
                            setFileName(item.getImagePath()).
                            setDirectoryName("images").
                            load();
                    if (bitmap != null)
                    {
                        item.setImageProduct(bitmap);
                    }

                }

                _productAdapter.setProducts(products);
            }
        }.execute();
    }

    @Override
    public void onEdit(Product product) {
        Intent intent = new Intent(this, ProductFormActivity.class);
        intent.putExtras(ProductFormActivity.newInstanseBundle(product.getId()));
        startActivity(intent);
    }

    public void deleteProduct(Product note) {
        new AsyncTask<Product, Void, Void>() {
            @Override
            protected Void doInBackground(Product... params) {
                _db.GetProductDao().DeleteAll(params);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                loadProducts();
            }
        }.execute(note);
    }
}
