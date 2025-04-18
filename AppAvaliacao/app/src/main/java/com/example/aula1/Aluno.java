package com.example.aula1;

import java.io.Serializable;

public class Aluno implements Serializable {
    int id;
    String nome;
    String CPF;
    String telefone;
    private byte[] fotoBytes;

    public Aluno(String nome, String CPF, String telefone) {
        this.nome = nome;
        this.CPF = CPF;
        this.telefone = telefone;
    }

    public Aluno() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public byte[] getFotoBytes() {
        return fotoBytes;
    }

    public void setFotoBytes(byte[] fotoBytes) {
        this.fotoBytes = fotoBytes;
    }

    @Override
    public String toString() {
        return "Nome: " + this.nome + " \nCPF: " + this.CPF + " \nTelefone: " + this.telefone;
    }
}
