package com.example.bibliotheque.service;

import com.example.bibliotheque.model.Horaire;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AgendaService {

    public List<Horaire> getHorairesPeriodeScolaire() {
        return Arrays.asList(
                new Horaire("Lundi", "9h00 - 12h00", "14h00 - 18h00"),
                new Horaire("Mardi", "9h00 - 12h00", "14h00 - 18h00"),
                new Horaire("Mercredi", "9h00 - 12h00", "14h00 - 18h00"),
                new Horaire("Jeudi", "9h00 - 12h00", "14h00 - 18h00"),
                new Horaire("Vendredi", "9h00 - 12h00", "14h00 - 18h00"),
                new Horaire("Samedi", "10h00 - 13h00", "Fermé"),
                new Horaire("Dimanche", "Fermé", "")
        );
    }

    public List<Horaire> getHorairesVacances() {
        return Arrays.asList(
                new Horaire("Lundi", "10h00 - 12h00", "14h00 - 17h00"),
                new Horaire("Mardi", "10h00 - 12h00", "14h00 - 17h00"),
                new Horaire("Mercredi", "10h00 - 12h00", "14h00 - 17h00"),
                new Horaire("Jeudi", "10h00 - 12h00", "14h00 - 17h00"),
                new Horaire("Vendredi", "10h00 - 12h00", "14h00 - 17h00"),
                new Horaire("Samedi", "10h00 - 12h00", "Fermé"),
                new Horaire("Dimanche", "Fermé", "")
        );
    }
}
