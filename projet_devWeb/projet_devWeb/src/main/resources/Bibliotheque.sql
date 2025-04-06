
CREATE TABLE IF NOT EXISTS Livre (
  id INT PRIMARY KEY,
  nom VARCHAR(50),
  auteur VARCHAR(50),
  genre VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS Utilisateur (
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
  niveau ENUM('DEBUTANT', 'INTERMEDIAIRE', 'AVANCE','EXPERT') DEFAULT 'DEBUTANT',
  role ENUM('SIMPLE', 'COMPLEXE', 'ADMINISTRATEUR') DEFAULT 'SIMPLE'
);


CREATE TABLE IF NOT EXISTS Salle (
  id INT PRIMARY KEY AUTO_INCREMENT,
  nom VARCHAR(50),
  capacite INT
);

CREATE TABLE IF NOT EXISTS Objet (
  id INT PRIMARY KEY AUTO_INCREMENT,
  nom VARCHAR(50),
  `type` VARCHAR(100),
  idSalle INT,
  FOREIGN KEY (idSalle) REFERENCES Salle(id)
);

CREATE TABLE IF NOT EXISTS DemandeAjoutObjet (
  id INT PRIMARY KEY AUTO_INCREMENT,
  nomObjet VARCHAR(100),
  `description` TEXT,
  idUtilisateur INT,
  idSalle INT,
  status ENUM('EN_ATTENTE', 'ACCEPTEE', 'REFUSEE') DEFAULT 'EN_ATTENTE',
  dateDemande DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (idUtilisateur) REFERENCES Utilisateur(id),
  FOREIGN KEY (idSalle) REFERENCES Salle(id)
);

CREATE TABLE IF NOT EXISTS Utilisation (
  idUtilisateur INT,
  idObjet INT,
  etat ENUM('Eteint', 'Allumé'),
  CONSTRAINT pk_Utilisation PRIMARY KEY (idUtilisateur, idObjet),
  FOREIGN KEY (idObjet) REFERENCES Objet(id),
  FOREIGN KEY (idUtilisateur) REFERENCES Utilisateur(id)
);

CREATE TABLE IF NOT EXISTS Reservation (
  dateReservation DATE,
  idUtilisateur INT,
  idSalle INT,
  CONSTRAINT pk_Reservation PRIMARY KEY (idUtilisateur, idSalle, dateReservation),
  FOREIGN KEY (idUtilisateur) REFERENCES Utilisateur(id),
  FOREIGN KEY (idSalle) REFERENCES Salle(id)
);

CREATE TABLE IF NOT EXISTS Emprunt (
  debutEmprunt DATE,
  idLivre INT,
  idUtilisateur INT,
  CONSTRAINT pk_Emprunt PRIMARY KEY (idLivre, idUtilisateur, debutEmprunt),
  FOREIGN KEY (idLivre) REFERENCES Livre(id),
  FOREIGN KEY (idUtilisateur) REFERENCES Utilisateur(id)
);

-- INSERTIONS



-- Insertion d'une salle
INSERT IGNORE INTO Salle (id, nom, capacite) VALUES (1, 'Salle Informatique', 30);

-- Insertion d’un utilisateur AVANCE
INSERT IGNORE INTO Utilisateur (id, nom, prenom, email, motDePasse, sexe, dateNaissance, role, verifie)
VALUES (1, 'Martin', 'Paul', 'paul.martin@example.com', 'password', 'Homme', '1990-05-12', 'AVANCE', true);

-- Insertion d’une demande d’ajout d’objet
INSERT IGNORE INTO DemandeAjoutObjet (nomObjet, description, idUtilisateur, idSalle, status, dateDemande)
VALUES ('Vidéo Projecteur', 'Un projecteur pour la salle', 1, 1, 'EN_ATTENTE', NOW());
