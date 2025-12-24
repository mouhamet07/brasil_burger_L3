<?php
namespace App\DTO;

use DateTime;

class CommandeSearchDto {
    public ?string $nomClient = null;
    public ?string $dateCmd = null;
    public ?string $etatCmd = null;
    public ?string $typeCmd = null;
}