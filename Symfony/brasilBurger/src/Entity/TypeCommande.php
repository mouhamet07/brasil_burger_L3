<?php

namespace App\Entity;

enum TypeCommande: string
{
    case SUR_PLACE = 'sur_place';
    case A_RECUPERER = 'a_recuperer';
    case LIVRAISON = 'livraison';
}
