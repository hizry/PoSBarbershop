package com.akarvisi.barbercamp.domain;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hizry on 3/21/2018.
 */
@Entity(tableName = "Employee")
public class Employee
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    private int id;

    @ColumnInfo(name = "FullName")
    private  String fullName;

    @ColumnInfo(name = "InitialName")
    private  String initialName;

    @ColumnInfo(name = "BirthDate" )
    private String birthDate;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getInitialName() {
        return this.initialName;
    }

    public void setInitialName(String initialName) {
        this.initialName = initialName;
    }

}
