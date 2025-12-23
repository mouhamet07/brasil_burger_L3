<?php

namespace App\Entity;

enum TypeCommandeItem: string
{
    case BURGER = 'burger';
    case MENU = 'menu';
    case COMPLEMENT = 'complement';
}
