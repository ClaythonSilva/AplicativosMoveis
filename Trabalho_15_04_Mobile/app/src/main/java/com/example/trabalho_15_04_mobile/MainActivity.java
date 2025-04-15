package com.example.trabalho_15_04_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNome, editTextCpf, editTextTelefone;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNome = findViewById(R.id.editTextNome);
        editTextCpf = findViewById(R.id.editTextCpf);
        editTextTelefone = findViewById(R.id.editTextTelefone);

        db = AppDatabase.getDatabase(this);
    }

    public void cadastrarPet(View view) {
        String nome = editTextNome.getText().toString();
        String cpf = editTextCpf.getText().toString();
        String telefone = editTextTelefone.getText().toString();

        Pet pet = new Pet();
        pet.nome = nome;
        pet.cpf = cpf;
        pet.telefone = telefone;

        new Thread(() -> {
            db.petDao().insert(pet);
            runOnUiThread(() -> Toast.makeText(MainActivity.this, "PET Cadastrado!", Toast.LENGTH_SHORT).show());
        }).start();
    }

    public void listarPets(View view) {
        Intent intent = new Intent(MainActivity.this, ListarPetsActivity.class);
        startActivity(intent);
    }
}