package com.example.projet_devWeb.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Lampe")
public class Lampe extends Objet {

    @Column(nullable = false)
    private int luminosite;

    public int getLuminosite() {
        return luminosite;
    }

    public void setLuminosite(int luminosite) {
        this.luminosite = luminosite;
    }
}