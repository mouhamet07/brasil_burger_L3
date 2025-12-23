<?php

namespace App\Entity;

enum ModePaiement: string
{
    case OM = 'om';
    case WAVE = 'wave';
}
