package com.example.trabalho_15_04_mobile;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface PetDao {
    @Insert
    void insert(Pet pet);

    @Query("SELECT * FROM Pet")
    List<Pet> getAllPets();
}