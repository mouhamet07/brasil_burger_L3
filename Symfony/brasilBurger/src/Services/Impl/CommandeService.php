<?php

namespace App\Services\Impl;

use App\Entity\Commande;
use App\Repository\CommandeRepository;
use App\Services\CommandeServiceInterface;
use Doctrine\ORM\EntityManagerInterface;

class CommandeService implements CommandeServiceInterface
{
    public function __construct(
        private CommandeRepository $commandeRepository,
        private EntityManagerInterface $em
    ) {}

    public function getAllCommandes(): array
    {
        return $this->commandeRepository->findAll();
    }

    public function getCommandeById(int $id): ?Commande
    {
        return $this->commandeRepository->find($id);
    }

    public function annulerCommande(int $id): ?bool
    {
        $commande = $this->commandeRepository->find($id);
        if (!$commande) {
            return null;
        }
        if ($commande->getEtat() === 'terminee') {
            return false;
        }
        $commande->setEtat('annulee');
        $this->em->flush();
        return true;
    }

    public function terminerCommande(int $id): ?bool
    {
        $commande = $this->commandeRepository->find($id);
        if (!$commande) {
            return null;
        }
        if ($commande->getEtat() === 'annulee') {
            return false;
        }
        $commande->setEtat('terminee');
        $this->em->flush();
        return true;
    }
}
