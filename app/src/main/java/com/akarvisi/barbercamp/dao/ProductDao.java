package com.akarvisi.barbercamp.dao;

/**
 * Created by hizry on 1/27/2018.
 */

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomWarnings;
import android.arch.persistence.room.Update;
import android.util.Log;

import com.akarvisi.barbercamp.domain.Product;

import java.util.List;

/**
 * Created by hizry on 12/25/2017.
 */
@Dao
public interface ProductDao {
    @Query("select * from Product where product.Id = :Id")
    Product FindBy(int Id);
    @Insert
    long Insert(Product product);

    @Insert
    void InsertAll(Product... products);

    @Update
    void UpdateAll(Product... products);

    @Query("SELECT * FROM Product")
    List<Product> GetAll();

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT product.Id as Id, product.ProductName as ProductName, product.Price as Price , product.Stock as Stock, product.CapsterCommision as CapsterCommision, product.ImagePath FROM Product ")
    List<Product> GetProduct();

    @Delete
    void DeleteAll(Product... products);
}
