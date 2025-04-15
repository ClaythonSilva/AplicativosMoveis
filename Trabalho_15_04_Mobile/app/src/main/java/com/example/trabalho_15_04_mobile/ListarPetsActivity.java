package com.example.trabalho_15_04_mobile;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import java.util.List;

public class ListarPetsActivity extends AppCompatActivity {

    private ListView listView;
    private AppDatabase db;
    private Button buttonVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_pets);

        listView = findViewById(R.id.listViewPets);
        buttonVoltar = findViewById(R.id.buttonVoltar);
        db = AppDatabase.getDatabase(this);

        buttonVoltar.setOnClickListener(view -> {
            getOnBackPressedDispatcher().onBackPressed();
        });

        new Thread(() -> {
            List<Pet> pets = db.petDao().getAllPets();
            runOnUiThread(() -> {
                ArrayAdapter<Pet> adapter = new ArrayAdapter<>(ListarPetsActivity.this,
                        android.R.layout.simple_list_item_1, pets);
                listView.setAdapter(adapter);
            });
        }).start();
    }
}