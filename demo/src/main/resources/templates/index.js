// Fonction pour récupérer la liste des utilisateurs depuis l'API
async function fetchUsers() {
    // Effectue une requête GET pour récupérer les utilisateurs stockés dans l'API
    const response = await fetch('http://localhost:8080/users');
    const users = await response.json(); // Convertit la réponse en format JSON

    // Sélectionne l'élément <ul> qui contiendra la liste des utilisateurs
    const usersList = document.getElementById('users');
    usersList.innerHTML = ''; // Vide la liste avant de l'afficher (pour éviter les doublons)

    // Boucle sur chaque utilisateur récupéré depuis l'API
    users.forEach(user => {
        const li = document.createElement('li'); // Crée un élément <li> pour chaque utilisateur
        li.textContent = `${user.nom} ${user.prenom}`; // Affiche le nom et prénom de l'utilisateur

        // Création d'un bouton "Supprimer"
        const deleteButton = document.createElement('button');
        deleteButton.textContent = "Supprimer";
        deleteButton.onclick = () => deleteUser(user.id); // Associe la fonction deleteUser() au clic

        li.appendChild(deleteButton); // Ajoute le bouton au <li>
        usersList.appendChild(li); // Ajoute le <li> à la liste des utilisateurs
    });
}

// Fonction pour ajouter un utilisateur via l'API
async function addUser(event) {
    event.preventDefault(); // Empêche le rechargement de la page lors de la soumission du formulaire

    // Récupère le formulaire et les valeurs des champs en supprimant les espaces inutiles
    const form = document.getElementById('userForm');
    const nom = document.getElementById('nom').value.trim();
    const prenom = document.getElementById('prenom').value.trim();
    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value.trim();
    const naissance = document.getElementById('naissance').value.trim();

    // Vérifie que tous les champs sont remplis avant d'envoyer la requête
    if (!nom || !prenom || !email || !password || !naissance) return;

    // Création d'un objet utilisateur avec les données du formulaire
    const newUser = {
        nom,
        prenom,
        email,
        password,
        naissance
    };

    // Envoi des données à l'API via une requête POST
    await fetch('http://localhost:8080/users', {
        method: 'POST', // Méthode HTTP POST pour ajouter un nouvel utilisateur
        headers: { 'Content-Type': 'application/json' }, // Définit le type de contenu envoyé
        body: JSON.stringify(newUser) // Convertit l'objet JavaScript en JSON
    });

    fetchUsers(); // Met à jour la liste des utilisateurs après l'ajout

    // Réinitialisation du formulaire après avoir ajouté l'utilisateur
    form.reset();
}

// Fonction pour supprimer un utilisateur via l'API
async function deleteUser(id) {
    // Envoi une requête DELETE pour supprimer l'utilisateur avec l'ID correspondant
    await fetch(`http://localhost:8080/users/${id}`, { method: 'DELETE' });

    fetchUsers(); // Met à jour la liste des utilisateurs après la suppression
}

// Charger la liste des utilisateurs au chargement de la page
fetchUsers();

// Ajout d'un événement au formulaire pour appeler la fonction addUser() lors de la soumission
document.getElementById('userForm').addEventListener('submit', addUser);

// Ajout de l'événement "Entrée" sur les champs du formulaire pour ajouter un utilisateur en appuyant sur "Entrer" du clavier
document.getElementById('userForm').addEventListener('keydown', function(event) {
    if (event.key === "Enter") {
        addUser(event);  // Exécute la fonction addUser() si l'utilisateur appuie sur "Enter"
    }
});
