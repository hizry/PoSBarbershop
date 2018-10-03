package com.akarvisi.barbercamp.domain;

/**
 * Created by hizry on 1/27/2018.
 */


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;

/**
 * Created by hizry on 12/25/2017.
 */
//@Entity(tableName = "Product")
@Entity(tableName = "Product")
public class Product {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    private int id;

    @ColumnInfo(name = "ProductName")
    private String productName;

    @ColumnInfo(name = "Price")
    private int price;

    @ColumnInfo(name = "Stock")
    private int stock;

    @ColumnInfo(name = "CapsterCommision")
    private int capsterCommision;

    @ColumnInfo(name = "ImagePath")
    private String imagePath;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock(){ return  this.stock;}

    public void  setStock(int stock){
        this.stock = stock;
    }

    @Embedded
    private Bitmap imageProduct;
    public Bitmap getImageProduct(){ return  this.imageProduct;}
    public void  setImageProduct(Bitmap imageProduct){
        this.imageProduct = imageProduct;
    }

    public int getCapsterCommision(){ return  this.capsterCommision;}

    public void  setCapsterCommision(int capsterCommision){
        this.capsterCommision = capsterCommision;
    }
}
