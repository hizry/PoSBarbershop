package com.akarvisi.barbercamp;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.akarvisi.barbercamp.adapter.EmployeeAdapter;
import com.akarvisi.barbercamp.database.AppDatabase;
import com.akarvisi.barbercamp.domain.Employee;


import java.util.List;

public class EmployeesActivity extends AppCompatActivity implements EmployeeAdapter.ActionCallback{

    private EmployeeAdapter _employeeAdapter;
    private  AppDatabase _db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EmployeesActivity.this, EmployeeFormActivity.class));
            }
        });

        _db = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DBName).build();
        _employeeAdapter = new EmployeeAdapter(this, EmployeesActivity.this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_viewEmployee);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(_employeeAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public  void onResume(){
        super.onResume();
        loadEmployees();
        Log.d("TEST","RESUME RUN");
    }

    public void loadEmployees(){
        new AsyncTask<Void, Void, List<Employee>>() {
            @Override
            protected List<Employee> doInBackground(Void... params) {
                Log.d("Log Load","Capsters");
                List<Employee> s = _db.GetEmployeeDao().GetEmployee();
                return _db.GetEmployeeDao().GetEmployee();
            }

            @Override
            protected void onPostExecute(List<Employee> capsters) {
                _employeeAdapter.setProducts(capsters);
            }
        }.execute();
    }

    @Override
    public void onEdit(Employee product) {

    }

    @Override
    public void deleteProduct(Employee product) {

    }






}
