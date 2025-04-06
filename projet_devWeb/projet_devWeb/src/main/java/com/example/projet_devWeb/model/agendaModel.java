package com.example.bibliotheque.model;

public class Horaire {
    private String jour;
    private String matin;
    private String apresMidi;

    public Horaire(String jour, String matin, String apresMidi) {
        this.jour = jour;
        this.matin = matin;
        this.apresMidi = apresMidi;
    }

    public String getJour() {
        return jour;
    }

    public String getMatin() {
        return matin;
    }

    public String getApresMidi() {
        return apresMidi;
    }
}
