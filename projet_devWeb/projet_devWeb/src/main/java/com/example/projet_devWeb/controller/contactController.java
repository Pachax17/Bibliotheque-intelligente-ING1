package com.bibliotheque.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class ContactController {

    @GetMapping("/contact")
    public String getContactPage(Model model) {
        ContactSection bibliotheque = new ContactSection("Bibliothèque", List.of(
                new ContactMethod("Téléphone", "01 34 25 60 00"),
                new ContactMethod("Email", "bibliotheque@cyu.fr"),
                new ContactMethod("Adresse", "Mail des Cerclades, 95000 Cergy")
        ));

        ContactSection administration = new ContactSection("Administration", List.of(
                new ContactMethod("Directeur", "01 34 25 60 01"),
                new ContactMethod("Responsable collections", "01 34 25 60 02"),
                new ContactMethod("Service technique", "01 34 25 60 03")
        ));

        model.addAttribute("sections", List.of(bibliotheque, administration));
        model.addAttribute("messageForm", new MessageForm());
        return "contact";
    }
}

// 👇 Les classes non publiques peuvent rester dans le même fichier

class ContactSection {
    private String title;
    private List<ContactMethod> methods;

    public ContactSection(String title, List<ContactMethod> methods) {
        this.title = title;
        this.methods = methods;
    }

    public String getTitle() { return title; }
    public List<ContactMethod> getMethods() { return methods; }
}

class ContactMethod {
    private String label;
    private String value;

    public ContactMethod(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() { return label; }
    public String getValue() { return value; }
}

class MessageForm {
    private String name;
    private String email;
    private String subject;
    private String message;

    // Getters & Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
