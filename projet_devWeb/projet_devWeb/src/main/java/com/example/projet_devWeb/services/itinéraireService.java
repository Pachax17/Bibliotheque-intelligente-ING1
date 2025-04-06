package com.bibliotheque.service;

import org.springframework.stereotype.Service;
import com.bibliotheque.model.Adresse;

@Service
public class ItineraireService {

    public Adresse getAdresseBibliotheque() {
        return new Adresse(
            "CY Bibliothèque universitaire - Site des Cerclades",
            "Mail des Cerclades",
            "95000 Cergy"
        );
    }

    public String getLienGoogleMaps() {
        return "https://www.google.com/maps/dir/CY+Tech,+Avenue+du+Parc,+Cergy/CY+Biblioth%C3%A8que+universitaire+-+Site+des+Cerclades,+Mail+des+Cerclades,+95000+Cergy";
    }

    public String getDescription() {
        return "Bienvenue dans notre bibliothèque, un lieu où la passion pour la lecture et la découverte prend vie. "
             + "Ici, chaque livre est une porte ouverte vers de nouveaux horizons, chaque page une invitation à explorer, "
             + "à apprendre et à rêver. Nous vous offrons un accès à une riche collection de livres, de documents et de "
             + "ressources numériques, soigneusement sélectionnés pour nourrir votre curiosité et enrichir vos connaissances. "
             + "Naviguez à votre rythme, laissez-vous guider par vos envies et trouvez des trésors littéraires qui sauront éveiller votre imagination. "
             + "Que vous soyez à la recherche de savoir, de détente ou d'inspiration, notre bibliothèque est un espace où chaque visite est une nouvelle aventure.";
    }
}
