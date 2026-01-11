<?php

namespace App\Form;

use App\DTO\CommandeSearchDto;
use App\Entity\EtatCommande;
use App\Entity\TypeCommande;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\EnumType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class CommandeSearchType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('nomClient', TextType::class, [
                'required' => false,
                'attr' => [
                    'placeholder' => 'Client',
                    'autocomplete' => 'off',
                    'class' => 'form-control'
                ]
            ])
            ->add('etatCmd', EnumType::class, [
                'class' => EtatCommande::class,
                'required' => false,
                'placeholder' => 'État de la commande',
                'choice_label' => function (EtatCommande $choice) {
                    return match ($choice) {
                        EtatCommande::EN_COURS => 'En cours',
                        EtatCommande::TERMINEE => 'Terminée',
                        EtatCommande::ANNULEE => 'Annulée',
                        EtatCommande::EN_ATTENTE => 'En attente',
                    };
                },
                'attr' => [
                    'class' => 'form-control'
                ]
            ])
            ->add('typeCmd', EnumType::class, [
                'class' => TypeCommande::class,
                'required' => false,
                'placeholder' => 'Type de commande',
                'choice_label' => function (TypeCommande $choice) {
                    return match ($choice) {
                        TypeCommande::SUR_PLACE => 'Sur place',
                        TypeCommande::A_RECUPERER => 'À emporter',
                        TypeCommande::LIVRAISON => 'Livraison',
                    };
                },
                'attr' => [
                    'class' => 'form-control'
                ]
            ])
            ->add('dateDebut', DateType::class, [
                'required' => false,
                'widget' => 'single_text',
                'input' => 'datetime_immutable',
                'attr' => [
                    'placeholder' => 'Date de début',
                    'class' => 'form-control',
                    'autocomplete' => 'off'
                ]
            ])
            ->add('dateFin', DateType::class, [
                'required' => false,
                'widget' => 'single_text',
                'input' => 'datetime_immutable',
                'attr' => [
                    'placeholder' => 'Date de fin',
                    'class' => 'form-control',
                    'autocomplete' => 'off'
                ]
            ]);
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => CommandeSearchDto::class,
            'attr' => [
                'data-turbo' => 'false'
            ]
        ]);
    }
}
