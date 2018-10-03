package com.akarvisi.barbercamp;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import com.akarvisi.barbercamp.adapter.OperatorAdapter;
import com.akarvisi.barbercamp.database.AppDatabase;
import com.akarvisi.barbercamp.domain.Employee;
import com.akarvisi.barbercamp.domain.Operator;
import com.akarvisi.barbercamp.domain.Product;
import com.google.gson.Gson;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OperatorAdapter.ActionCallback {
    private OperatorAdapter _operatorAdapter;
    private AppDatabase _db;
    private List<Product> _products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        _db = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DBName).build();
        _operatorAdapter = new OperatorAdapter(this,MainActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_products) {
            startActivity(new Intent(this, ProductsActivity.class));
            return true;
        }else if (id == R.id.action_employees)
        {
            startActivity(new Intent(this, EmployeesActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public  void onResume(){
        super.onResume();
        loadEmployees();
        loadProducts();
        Log.d("TEST","RESUME RUN");
    }

    private void loadEmployees() {
        new AsyncTask<Void, Void, List<Employee>>() {
            @Override
            protected List<Employee> doInBackground(Void... params) {
                Log.d("Log Load","Products");
                return _db.GetEmployeeDao().GetAll();
            }

            @Override
            protected void onPostExecute(List<Employee> employees)
            {
                Employee[] array = new Employee[employees.size()];
                employees.toArray(array); // fill the array

                GridView gridView = (GridView) findViewById(R.id.gridview);
                _operatorAdapter.setEmployees(array);
                gridView.setAdapter(_operatorAdapter);
            }
        }.execute();
    }

    private void loadProducts() {
        new AsyncTask<Void, Void, List<Product>>() {
            @Override
            protected List<Product> doInBackground(Void... params) {
                Log.d("Log Load","Products");
                return _db.GetProductDao().GetAll();
            }

            @Override
            protected void onPostExecute(List<Product> products)
            {
               _products = products;
            }
        }.execute();
    }


    @Override
    public void onSelect(Employee employee) {
        Intent intent = new Intent(this, TransactionActivity.class);
         intent.putExtras(TransactionActivity.newInstanseBundle(employee.getFullName()));
        Log.d("Log Load","Select EMployee" + employee.getFullName());

        String arrayAsString = new Gson().toJson(this._products);
        intent.putExtra("PRODUCTS", arrayAsString);
        startActivity(intent);
    }
}

