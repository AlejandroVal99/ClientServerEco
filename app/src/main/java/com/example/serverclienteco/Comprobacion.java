package com.example.serverclienteco;

import java.util.UUID;

public class Comprobacion {
    private boolean registrado;
    private String id;

    public Comprobacion(boolean registrado) {
        this.registrado = registrado;
        id = UUID.randomUUID().toString();
    }

    public boolean isRegistrado() {
        return registrado;
    }

    public void setRegistrado(boolean registrado) {
        this.registrado = registrado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
