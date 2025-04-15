package com.example.trabalho_15_04_mobile;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity
public class Pet {
    @PrimaryKey
    @NonNull
    public String cpf;
    public String nome;
    public String telefone;
}