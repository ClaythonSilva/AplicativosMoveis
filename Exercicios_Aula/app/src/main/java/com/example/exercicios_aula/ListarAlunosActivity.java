package com.example.exercicios_aula;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.exercicios_aula.Aluno;
import com.example.exercicios_aula.AlunoDao;

import java.util.ArrayList;
import java.util.List;
//PARTE 2
public class ListarAlunosActivity extends AppCompatActivity {
    private ListView listView;
    private AlunoDao dao;
    private List<Aluno> alunos;
    private List<Aluno> alunosFiltrados = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_alunos);
        //vincular variaveis com os campos do layout
        listView = findViewById(R.id.lista_alunos);
        dao = new AlunoDao(this);
        alunos = dao.obterTodos(); //todos alunos
        alunosFiltrados.addAll(alunos); //só os alunos que foram consultados
        //ArrayAdapter já vem pronto no android para colocar essa lista de alunos na listview
        ArrayAdapter<Aluno> adaptador = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunos);
        //colocar na listView o adaptador
        listView.setAdapter(adaptador);
    }
    public void voltarParaCadastro(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
