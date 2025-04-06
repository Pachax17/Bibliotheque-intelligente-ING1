package com.bibliotheque.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ItineraireController {

    @GetMapping("/itineraire")
    public String afficherItineraire(Model model) {
        Adresse adresse = new Adresse(
            "CY Bibliothèque universitaire - Site des Cerclades",
            "Mail des Cerclades",
            "95000 Cergy"
        );

        String lienGoogleMaps = "https://www.google.com/maps/dir/CY+Tech,+Avenue+du+Parc,+Cergy/CY+Biblioth%C3%A8que+universitaire+-+Site+des+Cerclades,+Mail+des+Cerclades,+95000+Cergy/@49.036866,2.0697963,16z/data=!3m1!4b1!4m14!4m13!1m5!1m1!1s0x47e6f5265bbc2f79:0x301dd6c7102e1852!2m2!1d2.0697464!2d49.0350546!1m5!1m1!1s0x47e666d9cfccb2b9:0xed21d43690e4ec68!2m2!1d2.08082!2d49.0372083!3e3?hl=fr&entry=ttu";

        String description = "Bienvenue dans notre bibliothèque, un lieu où la passion pour la lecture et la découverte prend vie. "
                           + "Ici, chaque livre est une porte ouverte vers de nouveaux horizons, chaque page une invitation à explorer, "
                           + "à apprendre et à rêver. Nous vous offrons un accès à une riche collection de livres, de documents et de "
                           + "ressources numériques, soigneusement sélectionnés pour nourrir votre curiosité et enrichir vos connaissances. "
                           + "Naviguez à votre rythme, laissez-vous guider par vos envies et trouvez des trésors littéraires qui sauront éveiller votre imagination. "
                           + "Que vous soyez à la recherche de savoir, de détente ou d'inspiration, notre bibliothèque est un espace où chaque visite est une nouvelle aventure.";

        model.addAttribute("adresse", adresse);
        model.addAttribute("mapLink", lienGoogleMaps);
        model.addAttribute("aPropos", description);

        return "itineraire"; // Correspond au nom du fichier HTML Thymeleaf : itineraire.html
    }
}

// ✅ Classe Adresse
class Adresse {
    private String nom;
    private String rue;
    private String codePostalVille;

    public Adresse(String nom, String rue, String codePostalVille) {
        this.nom = nom;
        this.rue = rue;
        this.codePostalVille = codePostalVille;
    }

    public String getNom() {
        return nom;
    }

    public String getRue() {
        return rue;
    }

    public String getCodePostalVille() {
        return codePostalVille;
    }
}
