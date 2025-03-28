-- LIVRES
INSERT INTO Livre (id, nom, auteur, genre) VALUES
(1, 'Le Petit Prince', 'Antoine de Saint-Exupéry', 'Fiction'),
(2, '1984', 'George Orwell', 'Dystopie'),
(3, 'Clean Code', 'Robert C. Martin', 'Informatique');

-- UTILISATEURS
INSERT INTO Utilisateur (id, nom, prenom, email, motDePasse, typeUtilisateur) VALUES
(1, 'Dupont', 'Alice', 'alice@example.com', '1234', 'lecteur'),
(2, 'Martin', 'Bob', 'bob@example.com', 'abcd', 'bibliothecaire'),
(3, 'Durand', 'Claire', 'claire@example.com', 'pass', 'visiteur');

-- SALLES
INSERT INTO Salle (id, nom, capacite) VALUES
(1, 'Salle de lecture', 30),
(2, 'Salle multimédia', 20);

-- OBJETS CONNECTÉS
INSERT INTO Objet (id, nom, idSalle) VALUES
(1, 'Lampe intelligente', 1),
(2, 'Capteur de température', 2);

-- UTILISATION OBJETS
INSERT INTO Utilisation (idUtilisateur, idObjet, etat) VALUES
(1, 1, 'Allumé'),
(2, 2, 'Eteint');

-- RÉSERVATIONS
INSERT INTO Reservation (dateReservation, idUtilisateur, idSalle) VALUES
('2024-05-01', 1, 1),
('2024-05-02', 3, 2);

-- EMPRUNTS
INSERT INTO Emprunt (debutEmprunt, idLivre, idUtilisateur) VALUES
('2024-04-01', 1, 1),
('2024-04-05', 2, 2);
