<?php

namespace App\Entity;

enum RoleUser: string
{
    case GESTIONNAIRE = 'gestionnaire';
    case CLIENT = 'client';
}
