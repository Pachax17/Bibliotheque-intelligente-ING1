@Controller
public class ContactController {

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("biblioContact", new ContactSection("Bibliothèque", List.of(
                new ContactMethod("fa-phone", "Téléphone : 01 34 25 60 00"),
                new ContactMethod("fa-envelope", "Email : bibliotheque@cyu.fr"),
                new ContactMethod("fa-map-marker-alt", "Adresse : Mail des Cerclades, 95000 Cergy")
        )));

        model.addAttribute("adminContact", new ContactSection("Administration", List.of(
                new ContactMethod("fa-phone", "Directeur : 01 34 25 60 01"),
                new ContactMethod("fa-phone", "Responsable collections : 01 34 25 60 02"),
                new ContactMethod("fa-phone", "Service technique : 01 34 25 60 03")
        )));

        model.addAttribute("message", new MessageForm()); // pour le formulaire
        return "contact";
    }

    @PostMapping("/contact")
    public String handleForm(@ModelAttribute MessageForm message) {
        // traitement du message
        return "redirect:/contact?sent=true";
    }
}
