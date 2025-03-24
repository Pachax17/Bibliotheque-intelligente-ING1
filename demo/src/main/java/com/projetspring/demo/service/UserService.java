package com.projetspring.demo.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projetspring.demo.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Service permettant de gérer les utilisateurs stockés dans un fichier JSON.
 * Il offre des fonctionnalités pour récupérer, ajouter et supprimer des utilisateurs.
 */

@Service
public class UserService {
    private final String FILE_PATH = "users.json"; // Chemin du fichier JSON contenant les utilisateurs
    private final ObjectMapper objectMapper = new ObjectMapper(); // Permet la conversion entre objets Java et JSON
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // Permet de sécuriser les mots de passe

    /**
     * Récupère la liste des utilisateurs stockés dans le fichier JSON.
     * Si le fichier n'existe pas encore, il est créé et initialisé avec une liste vide.
     *
     * return -> Liste des utilisateurs
     * throws IOException -> en cas de problème de lecture du fichier
     */
    public List<User> getUsers() throws IOException {
        File file = new File(FILE_PATH);

        // Vérifie si le fichier existe, sinon le crée avec une liste vide
        if (!file.exists()) {
            file.createNewFile();
            objectMapper.writeValue(file, List.of());
        }

        // Lecture des utilisateurs depuis le fichier JSON
        return objectMapper.readValue(file, new TypeReference<>() {});
    }

    /**
     * Sauvegarde la liste des utilisateurs dans le fichier JSON.
     *
     * param users -> Liste des utilisateurs à enregistrer
     * throws IOException -> en cas d'erreur lors de l'écriture dans le fichier
     */
    public void saveUsers(List<User> users) throws IOException {
        objectMapper.writeValue(new File(FILE_PATH), users);
    }

    /**
     * Ajoute un nouvel utilisateur à la liste et l'enregistre dans le fichier JSON.
     * - Le mot de passe est haché avant l'enregistrement pour des raisons de sécurité.
     * - Un identifiant unique (UUID) est généré pour l'utilisateur.
     *
     * param user -> L'utilisateur à ajouter
     * return -> L'utilisateur ajouté avec son ID et son mot de passe sécurisé
     * throws IOException -> en cas de problème d'écriture dans le fichier JSON
     */
    public User addUser(User user) throws IOException {
        // Hachage du mot de passe pour éviter de le stocker en clair
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        // Attribution d'un identifiant unique
        user.setId(UUID.randomUUID().toString());

        // Ajout de l'utilisateur à la liste existante
        List<User> users = getUsers();
        users.add(user);

        // Sauvegarde des modifications
        saveUsers(users);

        return user;
    }

    /**
     * Supprime un utilisateur en fonction de son ID.
     * Si l'utilisateur est trouvé et supprimé, la liste mise à jour est enregistrée dans le fichier JSON.
     *
     * param id -> Identifiant unique de l'utilisateur à supprimer
     * return true -> si l'utilisateur a été supprimé, false s'il n'a pas été trouvé
     * throws IOException -> en cas de problème lors de l'écriture du fichier JSON
     */
    public boolean deleteUser(String id) throws IOException {
        // Récupère la liste des utilisateurs
        List<User> users = getUsers();

        // Supprime l'utilisateur dont l'ID correspond
        boolean removed = users.removeIf(user -> user.getId().equals(id));

        // Si un utilisateur a été supprimé, on met à jour le fichier JSON
        if (removed) {
            saveUsers(users);
        }

        return removed;
    }
}
