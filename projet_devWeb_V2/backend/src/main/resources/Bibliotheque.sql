DROP DATABASE IF EXISTS Bibliotheque;
CREATE DATABASE Bibliotheque;
USE Bibliotheque;

DROP TABLE IF EXISTS Livre;
DROP TABLE IF EXISTS Utilisateur;
DROP TABLE IF EXISTS Utilisation;
DROP TABLE IF EXISTS ObjetConnecté;
DROP TABLE IF EXISTS Salle;
DROP TABLE IF EXISTS Reservation;
DROP TABLE IF EXISTS Emprunt;

CREATE TABLE Livre(
	id INT PRIMARY KEY,
    nom VARCHAR(50),
    auteur VARCHAR(50),
    genre VARCHAR(50));

CREATE TABLE Utilisateur (
  id INT PRIMARY KEY ,
  nom VARCHAR(50),
  prenom VARCHAR(50),
  email VARCHAR(100) UNIQUE,
  motDePasse VARCHAR(255),
  typeUtilisateur ENUM('visiteur', 'lecteur', 'bibliothecaire', 'administrateur'),
  dateInscription DATETIME DEFAULT CURRENT_TIMESTAMP);
  
  CREATE TABLE Salle(
	id INT PRIMARY KEY,
    nom VARCHAR(50),
    capacite INT);
  
CREATE TABLE Objet(
	id INT PRIMARY KEY,
    nom VARCHAR(50),
    idSalle INT,
    FOREIGN KEY (idSalle) REFERENCES Salle(id));
  
CREATE TABLE Utilisation(
    idUtilisateur INT,
    idObjet INT,
    etat ENUM('Eteint','Allumé'),
    CONSTRAINT pk_Utilisation PRIMARY KEY (idUtilisateur, idObjet),
	FOREIGN KEY (idObjet) REFERENCES Objet(id),
    FOREIGN KEY (idUtilisateur) REFERENCES Utilisateur(id));
    
CREATE TABLE Reservation(
	dateReservation DATE,
    idUtilisateur INT,
    idSalle INT,
    CONSTRAINT pk_Reservation PRIMARY KEY (idUtilisateur, idSalle, dateReservation),
    FOREIGN KEY (idUtilisateur) REFERENCES Utilisateur(id),
    FOREIGN KEY (idSalle) REFERENCES Salle(id));
    
CREATE TABLE Emprunt(
	debutEmprunt DATE,
    idLivre INT,
    idUtilisateur INT,
    CONSTRAINT pk_Emprunt PRIMARY KEY (idLivre, idUtilisateur, debutEmprunt),
    FOREIGN KEY (idLivre) REFERENCES Livre(id),
    FOREIGN KEY (idUtilisateur) REFERENCES Utilisateur(id));
    

