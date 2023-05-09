package com.example.crudpimenta.model;

import java.io.Serializable;

public class Usuario implements Serializable {
    private int id;
    private String nome;

    private String cpf;
    private String telefone;
    public Usuario(int id, String nome) {
        this.id = id;
        this.nome = nome;

    }
    public Usuario(int id, String nome, String cpf,String telefone) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;

    }

    public Usuario(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
