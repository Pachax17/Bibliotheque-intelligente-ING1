
-- pour jeter les anciennces tables 
DROP TABLE IF EXISTS Objet;
DROP TABLE IF EXISTS Salle;
-- TABLES

CREATE TABLE Livre (
  id INT PRIMARY KEY,
  nom VARCHAR(50),
  auteur VARCHAR(50),
  genre VARCHAR(50)
);

CREATE TABLE Utilisateur (
  id INT PRIMARY KEY AUTO_INCREMENT,
  nom VARCHAR(50),
  prenom VARCHAR(50),
  email VARCHAR(191) UNIQUE,
  motDePasse VARCHAR(255),
  sexe VARCHAR(10),
  dateNaissance DATE,
  dateInscription DATETIME DEFAULT CURRENT_TIMESTAMP,
  token VARCHAR(255),
  verifie BOOLEAN DEFAULT FALSE,
  derniereConnexion DATETIME,
  points INT DEFAULT 0,
  role ENUM('SIMPLE', 'AVANCE', 'ADMINISTRATEUR') DEFAULT 'SIMPLE'
);

CREATE TABLE Salle (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    capacite INT NOT NULL
);


CREATE TABLE Objet (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    derniere_interaction DATETIME,
    etat_fonctionnement BOOLEAN NOT NULL,
    etat_utilisation BOOLEAN NOT NULL,
    salle_id BIGINT NOT NULL,
    FOREIGN KEY (salle_id) REFERENCES Salle(id) ON DELETE CASCADE
);

CREATE TABLE Utilisation (
  idUtilisateur INT,
  idObjet INT,
  etat ENUM('Eteint', 'Allumé'),
  CONSTRAINT pk_Utilisation PRIMARY KEY (idUtilisateur, idObjet),
  FOREIGN KEY (idObjet) REFERENCES Objet(id),
  FOREIGN KEY (idUtilisateur) REFERENCES Utilisateur(id)
);

CREATE TABLE Reservation (
  dateReservation DATE,
  idUtilisateur INT,
  idSalle INT,
  CONSTRAINT pk_Reservation PRIMARY KEY (idUtilisateur, idSalle, dateReservation),
  FOREIGN KEY (idUtilisateur) REFERENCES Utilisateur(id),
  FOREIGN KEY (idSalle) REFERENCES Salle(id)
);

CREATE TABLE Emprunt (
  debutEmprunt DATE,
  idLivre INT,
  idUtilisateur INT,
  CONSTRAINT pk_Emprunt PRIMARY KEY (idLivre, idUtilisateur, debutEmprunt),
  FOREIGN KEY (idLivre) REFERENCES Livre(id),
  FOREIGN KEY (idUtilisateur) REFERENCES Utilisateur(id)
);
