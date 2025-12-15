<?php

namespace App\Controller;

use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

class HomeController
{
    #[Route('/home/index', name: 'home')]
    public function index(): Response
    {
        return new Response('<h1>✅ Symfony fonctionne sur Render</h1>');
    }
}
