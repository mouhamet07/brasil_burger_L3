<?php
namespace App\Security;

use Symfony\Component\PasswordHasher\PasswordHasherInterface;

class PasswordHasher implements PasswordHasherInterface
{
    public function hash(string $plainPassword): string
    {
        return hash('sha256', $plainPassword);
    }

    public function verify(string $hashedPassword, string $plainPassword): bool
    {
        return hash('sha256', $plainPassword) === $hashedPassword;
    }

    public function needsRehash(string $hashedPassword): bool
    {
        return false;
    }
}
