<?php
namespace App\DTO;

use DateTime;

class CommandeSearchDto {
    public ?string $client = null;
    public ?\DateTimeImmutable $date = null;
    public ?string $etat = null;
    public ?string $type = null;
}