<?php
namespace App\DTO;

use DateTime;
use Doctrine\Common\Collections\Collection;
use Symfony\Component\Validator\Constraints\Date;

class CommandeListDto {
    public int $id;
    public string $nomClient;
    public string $telephone;
    public Collection $CommandeItems;
    public String $type;
    public String $etat;
    public ?\DateTimeImmutable $date;
}