-- =========================================
-- DATABASE : BRASIL BURGER
-- SGBD : PostgreSQL
-- =========================================

DROP DATABASE IF EXISTS brasilBurger;
CREATE DATABASE brasilBurger;
\c brasilBurger;

-- ===============================
-- ENUMS
-- ===============================
CREATE TYPE mode_paiement AS ENUM ('OM', 'WAVE');
CREATE TYPE role_user AS ENUM ('GESTIONNAIRE', 'CLIENT');
CREATE TYPE type_commande AS ENUM ('SUR_PLACE', 'A_RECUPERER', 'LIVRAISON');
CREATE TYPE etat_commande AS ENUM ('EN_ATTENTE', 'EN_COURS', 'TERMINEE', 'ANNULEE');
CREATE TYPE categorie_complement AS ENUM ('FRITES', 'BOISSON');
CREATE TYPE type_commande_item AS ENUM ('BURGER', 'MENU', 'COMPLEMENT');

-- ===============================
-- TABLES
-- ===============================

-- User
CREATE TABLE "user" (
    id SERIAL PRIMARY KEY,
    nom_complet VARCHAR(150) NOT NULL,
    telephone VARCHAR(20) UNIQUE NOT NULL CHECK (telephone <> ''),
    email VARCHAR(150) UNIQUE NOT NULL CHECK (email <> ''),
    password VARCHAR(255) NOT NULL,
    role role_user NOT NULL,
    etat BOOLEAN NOT NULL DEFAULT TRUE
);

-- Zone
CREATE TABLE zone (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prix_livraison NUMERIC(10,2) NOT NULL CHECK (prix_livraison >= 0),
    etat BOOLEAN NOT NULL DEFAULT TRUE
);

-- Livreur
CREATE TABLE livreur (
    id SERIAL PRIMARY KEY,
    nom_complet VARCHAR(150) NOT NULL,
    telephone VARCHAR(20) UNIQUE NOT NULL CHECK (telephone <> ''),
    zone_id INT NOT NULL REFERENCES zone(id) ON DELETE CASCADE,
    etat BOOLEAN NOT NULL DEFAULT TRUE
);

-- Burger
CREATE TABLE burger (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(150) NOT NULL,
    prix NUMERIC(10,2) NOT NULL CHECK (prix >= 0),
    image TEXT,
    etat BOOLEAN NOT NULL DEFAULT TRUE
);

-- Menu
CREATE TABLE menu (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(150) NOT NULL,
    image TEXT,
    etat BOOLEAN NOT NULL DEFAULT TRUE,
    montant NUMERIC(10,2) NOT NULL CHECK (montant >= 0),
);

-- Complement
CREATE TABLE complement (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(150) NOT NULL,
    prix NUMERIC(10,2) NOT NULL CHECK (prix >= 0),
    image TEXT,
    etat BOOLEAN NOT NULL DEFAULT TRUE,
    categorie categorie_complement NOT NULL
);

-- MenuComplement (ManyToMany)
CREATE TABLE menu_complement (
    menu_id INT NOT NULL REFERENCES menu(id) ON DELETE CASCADE,
    complement_id INT NOT NULL REFERENCES complement(id) ON DELETE CASCADE,
    PRIMARY KEY(menu_id, complement_id)
);

-- Commande
CREATE TABLE commande (
    id SERIAL PRIMARY KEY,
    dateCommande TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    etat etat_commande NOT NULL DEFAULT 'EN_ATTENTE',
    type type_commande NOT NULL,
    montant_total NUMERIC(10,2) NOT NULL CHECK (montant_total >= 0),
    client_id INT NOT NULL REFERENCES "user"(id),
    zone_id INT REFERENCES zone(id),
    livreur_id INT REFERENCES livreur(id)
);

-- CommandeItem
CREATE TABLE commande_item (
    id SERIAL PRIMARY KEY,
    commande_id INT NOT NULL REFERENCES commande(id) ON DELETE CASCADE,
    type type_commande_item NOT NULL,
    produit_id INT NOT NULL,
    quantite INT NOT NULL CHECK (quantite > 0),
    prix_unitaire NUMERIC(10,2) NOT NULL CHECK (prix_unitaire >= 0)
);

-- Paiement
CREATE TABLE paiement (
    id SERIAL PRIMARY KEY,
    date_paiement TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    montant NUMERIC(10,2) NOT NULL CHECK (montant >= 0),
    mode mode_paiement NOT NULL,
    commande_id INT NOT NULL UNIQUE REFERENCES commande(id) ON DELETE CASCADE
);

-- ===============================
-- INDEXES
-- ===============================
CREATE INDEX idx_commande_client ON commande(client_id);
CREATE INDEX idx_commande_etat ON commande(etat);
CREATE INDEX idx_commande_date ON commande(dateCommande);
CREATE INDEX idx_commande_type ON commande(type);
CREATE INDEX idx_commande_zone ON commande(zone_id);

CREATE INDEX idx_item_commande ON commande_item(commande_id);
CREATE INDEX idx_item_type ON commande_item(type);
