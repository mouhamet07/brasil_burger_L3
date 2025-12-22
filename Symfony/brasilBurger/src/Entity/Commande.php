<?php

namespace App\Entity;

use App\Repository\CommandeRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: CommandeRepository::class)]
class Commande
{
    public const TYPE_SUR_PLACE = 'sur_place';
    public const TYPE_A_RECUPERER = 'a_recuperer';
    public const TYPE_LIVRAISON = 'livraison';

    public const ETAT_EN_ATTENTE = 'en_attente';
    public const ETAT_EN_COURS = 'en_cours';
    public const ETAT_TERMINEE = 'terminee';
    public const ETAT_ANNULEE = 'annulee';

    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column]
    private ?\DateTimeImmutable $dateCommande = null;

    #[ORM\Column]
    private ?bool $etat = null;

    #[ORM\Column(length: 255)]
    private ?string $type = null;

    #[ORM\Column]
    private ?float $montantTotal = null;

    /**
     * @var Collection<int, CommandeItem>
     */
    #[ORM\OneToMany(targetEntity: CommandeItem::class, mappedBy: 'commande')]
    private Collection $commandeItems;

    #[ORM\OneToOne(mappedBy: 'commande', targetEntity: Paiement::class)]
    private ?Paiement $paiement = null;


    public function __construct()
    {
        $this->commandeItems = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getDateCommande(): ?\DateTimeImmutable
    {
        return $this->dateCommande;
    }

    public function setDateCommande(\DateTimeImmutable $dateCommande): static
    {
        $this->dateCommande = $dateCommande;

        return $this;
    }

    public function isEtat(): ?bool
    {
        return $this->etat;
    }

    public function setEtat(bool $etat): static
    {
        $this->etat = $etat;

        return $this;
    }

    public function getType(): ?string
    {
        return $this->type;
    }

    public function setType(string $type): static
    {
        $this->type = $type;

        return $this;
    }

    public function getMontantTotal(): ?float
    {
        return $this->montantTotal;
    }

    public function setMontantTotal(float $montantTotal): static
    {
        $this->montantTotal = $montantTotal;

        return $this;
    }

    /**
     * @return Collection<int, CommandeItem>
     */
    public function getCommandeItems(): Collection
    {
        return $this->commandeItems;
    }

    public function addCommandeItem(CommandeItem $commandeItem): static
    {
        if (!$this->commandeItems->contains($commandeItem)) {
            $this->commandeItems->add($commandeItem);
            $commandeItem->setCommande($this);
        }

        return $this;
    }

    public function removeCommandeItem(CommandeItem $commandeItem): static
    {
        if ($this->commandeItems->removeElement($commandeItem)) {
            // set the owning side to null (unless already changed)
            if ($commandeItem->getCommande() === $this) {
                $commandeItem->setCommande(null);
            }
        }

        return $this;
    }

    public function getPaiement(): ?Paiement
    {
        return $this->paiement;
    }


    public function setPaiement(Paiement $paiement): self
    {
        $this->paiement = $paiement;
        return $this;
    }

}
