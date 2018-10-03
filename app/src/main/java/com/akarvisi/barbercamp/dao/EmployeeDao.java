package com.akarvisi.barbercamp.dao;

/**
 * Created by hizry on 3/21/2018.
 */

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomWarnings;
import android.arch.persistence.room.Update;

import com.akarvisi.barbercamp.domain.Employee;


import java.util.List;

@Dao
public interface EmployeeDao
{
    @Query("select * from Employee where Employee.Id = :Id")
    Employee FindBy(int Id);

    @Insert
    long Insert(Employee employee);

    @Insert
    void InsertAll(Employee... employee);

    @Update
    void UpdateAll(Employee... employee);

    @Query("SELECT * FROM Employee")
    List<Employee> GetAll();

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT Employee.Id as Id, Employee.FullName as FullName, Employee.InitialName as InitialName, Employee.BirthDate FROM Employee")
    List<Employee> GetEmployee();

    @Delete
    void DeleteAll(Employee... employee);
}
