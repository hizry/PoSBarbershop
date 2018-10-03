package com.akarvisi.barbercamp.database;

/**
 * Created by hizry on 1/27/2018.
 */

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.akarvisi.barbercamp.dao.EmployeeDao;
import com.akarvisi.barbercamp.dao.EmployeeDao;
import com.akarvisi.barbercamp.dao.ProductDao;
import com.akarvisi.barbercamp.domain.Employee;
import com.akarvisi.barbercamp.domain.Product;

/**
 * Created by hizry on 12/25/2017.
 */
@Database(entities = {Product.class, Employee.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {

    public  static  final  String DBName = "KasirBarber";

    public abstract ProductDao GetProductDao();

    public abstract EmployeeDao GetEmployeeDao();

}
