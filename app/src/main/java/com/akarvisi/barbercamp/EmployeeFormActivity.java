package com.akarvisi.barbercamp;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.akarvisi.barbercamp.database.AppDatabase;
import com.akarvisi.barbercamp.domain.Employee;

public class EmployeeFormActivity extends AppCompatActivity implements View.OnClickListener {
    private Employee _employee;
    private TextView _fullName;
    private  TextView _initialName;
    private  TextView _birthDate;
    private AppDatabase _db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        _fullName = (TextView) findViewById(R.id.FullName);
        _initialName = (TextView) findViewById(R.id.InitialName);
        _birthDate = (TextView) findViewById(R.id.BirthDate);

        _db = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DBName).build();


        findViewById(R.id.Save).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Save:
                SaveCapster();
                break;
        }
    }

    public void SaveCapster(){
        if (_employee == null) {
            _employee = new Employee();
        }
        _employee.setFullName(_fullName.getText().toString().trim());
        _employee.setBirthDate(_birthDate.getText().toString().trim());
        _employee.setInitialName(_initialName.getText().toString().trim());

        new AsyncTask<Employee, Void, Void>()
        {
            @Override
            protected Void doInBackground(Employee... params) {
                Employee employee = params[0];
                if (employee.getId() > 0) {
                    // Log.d("List",_employee.toString());
                    _db.GetEmployeeDao().UpdateAll(employee);

                } else {
                    // Log.d("List",_employee.toString());
                    _db.GetEmployeeDao().InsertAll(employee);
                }
                return  null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                setResult(RESULT_OK);
                finish();
            }
        }.execute(_employee);
    }

}
