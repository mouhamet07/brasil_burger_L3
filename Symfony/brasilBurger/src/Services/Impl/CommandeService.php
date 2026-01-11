<?php

namespace App\Services\Impl;

use App\Entity\Commande;
use App\Entity\EtatCommande;
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
        $commande = $this->getCommandeById($id);
        if (!$commande) {
            return null;
        }
        if ($commande->getEtat()?->value === EtatCommande::TERMINEE->value) {
            return false;
        }
        $commande->setEtat(EtatCommande::ANNULEE);
        $this->em->flush();
        return true;
    }
    public function terminerCommande(int $id): ?bool
    {
        $commande = $this->getCommandeById($id);
        if (!$commande) {
            return null;
        }
        if ($commande->getEtat()?->value === EtatCommande::ANNULEE->value) {
            return false;
        }
        $commande->setEtat(EtatCommande::TERMINEE);
        $this->em->flush();
        return true;
    }
    public function getFilteredCommandes(array $filters, int $limit, int $offset): array
    {
        $qb = $this->commandeRepository->createQueryBuilder('c')
            ->orderBy('c.dateCommande', 'DESC')
            ->setFirstResult($offset)
            ->setMaxResults($limit);
        if (!empty($filters['client'])) {
            $qb->join('c.client', 'client')
                ->andWhere('LOWER(client.nomComplet) LIKE :client')
                ->setParameter('client', '%'.strtolower($filters['client']).'%');
        }
        if (!empty($filters['etat'])) {
            $etat = $filters['etat'];
            if (!is_string($etat)) {
                $etat = $etat->value;
            }
            $qb->andWhere('c.etat = :etat')->setParameter('etat', $etat);
        }
        if (!empty($filters['type'])) {
            $type = $filters['type'];
            if (!is_string($type)) {
                $type = $type->value;
            }
            $qb->andWhere('c.type = :type')->setParameter('type', $type);
        }
        if (!empty($filters['dateDebut']) || !empty($filters['dateFin']) || !empty($filters['date'])) {
            $dateDebut = $filters['dateDebut'] ?? $filters['date'] ?? null;
            $dateFin = $filters['dateFin'] ?? $filters['date'] ?? null;
            $start = null;
            $end = null;
            if ($dateDebut !== null) {
                $start = $dateDebut instanceof \DateTimeImmutable
                    ? $dateDebut->setTime(0,0,0)
                    : (new \DateTimeImmutable($dateDebut->format('Y-m-d')))->setTime(0,0,0);
            }
            if ($dateFin !== null) {
                $end = $dateFin instanceof \DateTimeImmutable
                    ? $dateFin->setTime(0,0,0)->modify('+1 day')
                    : (new \DateTimeImmutable($dateFin->format('Y-m-d')))->setTime(0,0,0)->modify('+1 day');
            }
            if ($start !== null && $end !== null) {
                $qb->andWhere('c.dateCommande >= :dateDebut')
                    ->andWhere('c.dateCommande < :dateFinPlusOne')
                    ->setParameter('dateDebut', $start)
                    ->setParameter('dateFinPlusOne', $end);
            } elseif ($start !== null) {
                $end = $start->modify('+1 day');
                $qb->andWhere('c.dateCommande >= :dateDebut')
                    ->andWhere('c.dateCommande < :dateFinPlusOne')
                    ->setParameter('dateDebut', $start)
                    ->setParameter('dateFinPlusOne', $end);
            } elseif ($end !== null) {
                $qb->andWhere('c.dateCommande < :dateFinPlusOne')
                    ->setParameter('dateFinPlusOne', $end);
            }
        }
        return $qb->getQuery()->getResult();
    }
    public function countCommandes(array $filters): int
    {
        $qb = $this->commandeRepository->createQueryBuilder('c')
            ->select('COUNT(c.id)');
        if (!empty($filters['client'])) {
            $qb->join('c.client', 'client')
                ->andWhere('LOWER(client.nomComplet) LIKE :client')
                ->setParameter('client', '%'.strtolower($filters['client']).'%');
        }
        if (!empty($filters['etat'])) {
            $etat = $filters['etat'];
            if (!is_string($etat)) {
                $etat = $etat->value;
            }
            $qb->andWhere('c.etat = :etat')->setParameter('etat', $etat);
        }
        if (!empty($filters['type'])) {
            $type = $filters['type'];
            if (!is_string($type)) {
                $type = $type->value;
            }
            $qb->andWhere('c.type = :type')->setParameter('type', $type);
        }
        if (!empty($filters['dateDebut']) || !empty($filters['dateFin']) || !empty($filters['date'])) {
            $dateDebut = $filters['dateDebut'] ?? $filters['date'] ?? null;
            $dateFin = $filters['dateFin'] ?? $filters['date'] ?? null;
            $start = null;
            $end = null;
            if ($dateDebut !== null) {
                $start = $dateDebut instanceof \DateTimeImmutable
                    ? $dateDebut->setTime(0,0,0)
                    : (new \DateTimeImmutable($dateDebut->format('Y-m-d')))->setTime(0,0,0);
            }
            if ($dateFin !== null) {
                $end = $dateFin instanceof \DateTimeImmutable
                    ? $dateFin->setTime(0,0,0)->modify('+1 day')
                    : (new \DateTimeImmutable($dateFin->format('Y-m-d')))->setTime(0,0,0)->modify('+1 day');
            }
            if ($start !== null && $end !== null) {
                $qb->andWhere('c.dateCommande >= :dateDebut')
                    ->andWhere('c.dateCommande < :dateFinPlusOne')
                    ->setParameter('dateDebut', $start)
                    ->setParameter('dateFinPlusOne', $end);
            } elseif ($start !== null) {
                $end = $start->modify('+1 day');
                $qb->andWhere('c.dateCommande >= :dateDebut')
                ->andWhere('c.dateCommande < :dateFinPlusOne')
                    ->setParameter('dateDebut', $start)
                    ->setParameter('dateFinPlusOne', $end);
            } elseif ($end !== null) {
                $qb->andWhere('c.dateCommande < :dateFinPlusOne')
                    ->setParameter('dateFinPlusOne', $end);
            }
        }
        return (int) $qb->getQuery()->getSingleScalarResult();
    }
}
