<?php
namespace App\DTO;

use Doctrine\Common\Collections\Collection;

class CommandeListDto {
    public int $id;
    public string $nomClient;
    public string $telephone;
    public Collection $CommandeItems;
    public String $type;
    public String $etat;
    public ?\DateTimeImmutable $date;
}