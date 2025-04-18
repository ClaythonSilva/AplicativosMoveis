package com.example.exercicios_aula;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText nome;
    private EditText cpf;
    private EditText telefone;
    private AlunoDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nome =     findViewById(R.id.editTextText);

        cpf =     findViewById(R.id.editTextText2);

        telefone =     findViewById(R.id.editTextText3);

        dao =new AlunoDao(this);
    }

    public void salvar(View view){
        String nomeDigitado = nome.getText().toString().trim();
        String cpfDigitado = cpf.getText().toString().trim();
        String telefoneDigitado = telefone.getText().toString().trim();

        if (nomeDigitado.isEmpty() || cpfDigitado.isEmpty() || telefoneDigitado.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!dao.validaCpf(cpfDigitado)){
            Toast.makeText(this, "CPF Invalido, digite novamente.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(dao.cpfExistente(cpfDigitado)){
            Toast.makeText(this, "CPF duplicado, insira um CPF diferente.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!dao.validaTelefone(telefoneDigitado)){
            Toast.makeText(this, "Telefone invalido! Use o formato correto: (XX) 9XXXX-XXXX", Toast.LENGTH_SHORT).show();
            return;
        }

        Aluno a = new Aluno();
        a.setNome(nome.getText().toString());
        a.setCpf(cpf.getText().toString());
        a.setTelefone(telefone.getText().toString());
        long id = dao.inserir(a);
        Toast.makeText(this, "Aluno inserido com id: ", Toast.LENGTH_SHORT).show();
    }
    public void irParaListar(View view) {
        Intent intent = new Intent(this, ListarAlunosActivity.class);
        startActivity(intent);
    }
}