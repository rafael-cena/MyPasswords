package com.example.mypasswords;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class MyPassword implements Serializable {
    private String local, usuario, senha;

    public MyPassword(String local, String usuario, String senha) {
        this.local = local;
        this.usuario = usuario;
        this.senha = senha;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @NonNull
    @Override
    public String toString() {
        return local + " | " + usuario + " | " + senha;
    }
}
