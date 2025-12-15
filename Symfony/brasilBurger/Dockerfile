FROM php:8.4-cli

# Dépendances système
RUN apt-get update && apt-get install -y \
    git unzip libzip-dev libicu-dev libpq-dev \
    && docker-php-ext-install intl pdo pdo_mysql pdo_pgsql zip \
    && rm -rf /var/lib/apt/lists/*

# Installer Composer
COPY --from=composer:2 /usr/bin/composer /usr/bin/composer

WORKDIR /app
COPY . .

# Installer dépendances Symfony (prod)
RUN composer install --no-dev --optimize-autoloader

# Permissions Symfony
RUN mkdir -p var && chmod -R 777 var

# Render écoute ici
EXPOSE 10000

# Serveur HTTP
CMD ["php", "-S", "0.0.0.0:10000", "-t", "public"]