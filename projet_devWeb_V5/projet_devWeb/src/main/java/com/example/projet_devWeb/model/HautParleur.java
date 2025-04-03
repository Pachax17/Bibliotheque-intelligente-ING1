package com.example.projet_devWeb.model;

import jakarta.persistence.*;

@Entity
@Table(name = "HautParleur")
public class HautParleur extends Objet {

    @Column(nullable = false)
    private int volume;

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}