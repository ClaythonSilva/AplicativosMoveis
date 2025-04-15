package com.example.trabalho_15_04_mobile;
import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Pet.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PetDao petDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "pet_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}