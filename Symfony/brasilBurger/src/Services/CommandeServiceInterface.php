<?php
namespace App\Services;

use App\Entity\Commande;

interface CommandeServiceInterface{
    function getAllCommandes() : array;
    function getCommandeById(int $id) : ?Commande;
    function annulerCommande(int $id) : ?bool;
    function terminerCommande(int $id) : ?bool;
}