package com.akarvisi.barbercamp;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.akarvisi.barbercamp.database.AppDatabase;
import com.akarvisi.barbercamp.domain.Product;
import com.akarvisi.barbercamp.helper.ImageSaver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ProductFormActivity extends AppCompatActivity implements View.OnClickListener{

    public static  final String EXTRA_PRODUCT_ID = "EXTRA_PRODUCT_ID";
    private AppDatabase _db;
    private Product _product;
    private TextView _product_name;
    private  TextView _price;
    private TextView _stock;
    private  TextView _commision;
    private  int _product_id;
    private  String _image_path;
    public static final int GET_FROM_GALLERY = 3;
    private ImageView _image_view;
    private ImageSaver _image_saver;
    private Bitmap _bitmap_product;
    private String _images_directory = "images";

    public static Bundle newInstanseBundle(int productId){
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_PRODUCT_ID, productId);
        return bundle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_PRODUCT_ID)){
            this._product_id = intent.getIntExtra(EXTRA_PRODUCT_ID, -1);
        }

        _db = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DBName).build();
        _product_name = (TextView) findViewById(R.id.productName);
        _price = (TextView) findViewById(R.id.price);
        _stock = (TextView) findViewById(R.id.stock);
        _commision = (TextView) findViewById(R.id.capsterCommision);
        _image_view = (ImageView) findViewById(R.id.imageView);

        findViewById(R.id.save).setOnClickListener(this);
        findViewById(R.id.selectImage).setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProduct();
    }

    private void loadProduct() {
        new AsyncTask<Integer, Void, Product>() {
            @Override
            protected Product doInBackground(Integer... params) {
                return _db.GetProductDao().FindBy(params[0]);
                //return new Product();
            }

            @Override
            protected void onPostExecute(Product product) {
                if (_product_id != 0) {
                    setProduct(product);
                }
            }
        }.execute(_product_id);
    }

    private void setProduct(Product product) {
        this._product = product;
        _product_name.setText(product.getProductName());
        _price.setText(Integer.toString(product.getPrice()) );
        _stock.setText(Integer.toString(product.getStock()));
        _commision.setText(Integer.toString(product.getCapsterCommision()));
        _image_path = product.getImagePath();

        Bitmap imageProduct2 = new ImageSaver(getApplicationContext()).
                setFileName(product.getImagePath()).
                setDirectoryName("images").
                load();

        if (imageProduct2 != null)
        {
            if(_bitmap_product != null){
                _image_view.setImageBitmap(_bitmap_product);
            }else {
                _image_view.setImageBitmap(imageProduct2);
                _bitmap_product = imageProduct2;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            try {
                Uri imageUri = data.getData();
                InputStream imageStream = getContentResolver().openInputStream(imageUri);
                _bitmap_product = BitmapFactory.decodeStream(imageStream);
                _image_view.setImageBitmap(_bitmap_product);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save:
                saveProduct();
                break;
            case R.id.selectImage:
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
                break;
        }
    }

    private void saveProduct() {
        if (_product == null) {
            _product = new Product();
        }
        _product.setProductName(_product_name.getText().toString().trim());
        _product.setPrice(Integer.parseInt( _price.getText().toString().trim()));
        _product.setStock(Integer.parseInt( _stock.getText().toString().trim()));
        _product.setCapsterCommision(Integer.parseInt(_commision.getText().toString().trim()));
        final String imageName = _product_name.getText().toString().trim().replace(" ", "")+ _price.getText().toString().trim().replace(" ", "") + ".png";
        _product.setImagePath(imageName);

        new AsyncTask<Product, Void, Void>() {
            @Override
            protected Void doInBackground(Product... params) {
                Product saveNote = params[0];

                if (saveNote.getId() > 0) {

                    Product oldProduct = _db.GetProductDao().FindBy(saveNote.getId());
                    if (oldProduct.getImagePath().trim() != "" && oldProduct.getImagePath() != null){
                        File dir = getFilesDir();
                        File file = new File(_images_directory, oldProduct.getImagePath());
                        boolean deleted = file.delete();
                    }
                    Log.d("List",_product.toString());
                    _db.GetProductDao().UpdateAll(saveNote);
                    new ImageSaver(getApplicationContext()).setFileName(imageName).
                            setDirectoryName(_images_directory).
                            save(_bitmap_product);
                } else {
                    Log.d("List",_product.toString());
                    _db.GetProductDao().InsertAll(saveNote);
                    new ImageSaver(getApplicationContext()).setFileName(imageName).
                            setDirectoryName(_images_directory).
                            save(_bitmap_product);
                }

                return null;
            }


            @Override
            protected void onPostExecute(Void aVoid) {
                setResult(RESULT_OK);
                finish();
            }
        }.execute(_product);
    }
}
