package com.projetspring.demo.controller;

import com.projetspring.demo.model.User;
import com.projetspring.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * Contrôleur REST pour gérer les opérations sur les utilisateurs.
 * Ce contrôleur permet de récupérer, ajouter et supprimer des utilisateurs via des requêtes HTTP.
 */
@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*") // Permet les requêtes depuis le frontend
public class UserController {
    private final UserService userService;

    /**
     * Constructeur du contrôleur qui injecte le service UserService.
     *
     * @param userService Service de gestion des utilisateurs
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Récupère la liste de tous les utilisateurs.
     *
     * return -> Liste des utilisateurs
     * throws IOException -> en cas de problème de lecture du fichier JSON
     */
    @GetMapping
    public List<User> getUsers() throws IOException {
        return userService.getUsers();
    }

    /**
     * Ajoute un nouvel utilisateur à la liste.
     *
     * param user -> Objet utilisateur envoyé en format JSON dans la requête
     * return -> L'utilisateur ajouté avec son ID généré
     * throws IOException -> en cas de problème d'accès au fichier JSON
     */
    @PostMapping
    public User addUser(@RequestBody User user) throws IOException {
        return userService.addUser(user);
    }

    /**
     * Supprime un utilisateur en fonction de son ID.
     *
     * param id -> Identifiant unique de l'utilisateur à supprimer (fourni dans l'URL)
     * return true -> si l'utilisateur a été supprimé, false sinon
     * throws IOException -> en cas de problème d'accès au fichier JSON
     */
    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable String id) throws IOException {
        return userService.deleteUser(id);
    }
}
