package com.example.exercicios_aula;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AlunoDao {
    private Conexao conexao;
    private SQLiteDatabase banco;
    //context é usado para a conexão

    private static final Pattern TELEFONE_PATTERN = Pattern.compile("^\\(?([1-9]{2})\\)?\\s?9[0-9]{4}-?[0-9]{4}$");

    public boolean validaTelefone(String telefone){
        if(telefone == null) return false;

        telefone = telefone.replaceAll("[^0-9]", "");

        return telefone.length() == 11 && TELEFONE_PATTERN.matcher(telefone).matches();
    }

    public boolean validaCpf(String CPF){
        System.out.println("String de entrada do método: " + CPF);

        CPF = CPF.replaceAll("[^0-9]", "");

        if (CPF.length() != 11){
            return false;
        }

        if (CPF.matches("(\\d)\\1{10}")){
            return false; // CPF com todos os dígitos iguais é inválido
        }

        char dig10, dig11;
        int soma, num, peso, resto;

        try{
            // Cálculo do primeiro dígito verificador
            soma = 0;
            peso = 10;

            for (int i = 0; i < 9; i++) {
                num = CPF.charAt(i) - '0';  // converte o char para int
                soma += (num * peso);
                peso--;
            }

            resto = soma % 11;
            dig10 = (resto < 2) ? '0' : (char) ((11 - resto) + '0');

            // Cálculo do segundo dígito verificador
            soma = 0;
            peso = 11;

            for (int i = 0; i < 10; i++) {
                num = CPF.charAt(i) - '0';
                soma += (num * peso);
                peso--;
            }

            resto = soma % 11;
            dig11 = (resto < 2) ? '0' : (char) ((11 - resto) + '0');

            // Verifica se os dois dígitos verificadores são válidos
            return (dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10));

        } catch (Exception e){
            return false;
        }
    }


    public AlunoDao(Context context){
        conexao = new Conexao(context); //criei uma conexao
        banco = conexao.getWritableDatabase(); //iniciar um banco de dados para escrita
    }
    //método para inserir - PARTE I
    public long inserir(Aluno aluno){ // long porque retorna o id do aluno
        if(!cpfExistente(aluno.getCpf())){
            ContentValues values = new ContentValues();
            values.put("nome", aluno.getNome());
            values.put("cpf", aluno.getCpf());
            values.put("telefone", aluno.getTelefone());
            return banco.insert("aluno", null, values);
        }
        else{
            return -1;
        }
    }

    public boolean cpfExistente(String cpf){
        Cursor cursor = banco.query("aluno", new String[]{"id"}, "cpf = ?", new String[]{cpf}, null, null, null);
        boolean cpfExiste = cursor.getCount() > 0;
        cursor.close();
        return cpfExiste;
    }


    //método para consultar PARTE II
    public List<Aluno> obterTodos(){
        List<Aluno> alunos = new ArrayList<>();
        //cursor aponta para as linhas retornadas
        Cursor  cursor = banco.query("aluno", new String[]{"id", "nome", "cpf", "telefone"},
                null, null,null,null,null); //nome da tabela, nome das colunas, completa com null o método
        //que por padrão pede esse número de colunas obrigatórias
        while(cursor.moveToNext()){ //verifica se consegue mover para o próximo ponteiro ou linha
            Aluno a = new Aluno();
            a.setId(cursor.getInt(0)); // new String[]{"id", "nome", "cpf", "telefone"}, id é coluna '0'
            a.setNome(cursor.getString(1)); // new String[]{"id", "nome", "cpf", "telefone"}, nome é coluna '1'
            a.setCpf(cursor.getString(2)); // new String[]{"id", "nome", "cpf", "telefone"}, cpf é coluna '2'
            a.setTelefone(cursor.getString(3)); // new String[]{"id", "nome", "cpf", "telefone"}, telefone é coluna '3'
            alunos.add(a);
        }
        return alunos;
    }
}
