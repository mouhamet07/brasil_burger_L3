<?php
namespace App\DTO;

use Doctrine\Common\Collections\Collection;

class DashboardDto {
    public int $nbrCmdEnCours;
    public int $nbrCmdTerminee;
    public int $nbrCmdAnnulee;
    public float $recettes;
    public Collection $commandesRecentes;
}