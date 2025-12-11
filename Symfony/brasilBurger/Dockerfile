# Étape 1 : Builder
FROM php:8.2-fpm AS build
WORKDIR /var/www

# Installer les dépendances
RUN apt-get update && apt-get install -y \
    git unzip libicu-dev libonig-dev libzip-dev zip postgresql-client \
    && docker-php-ext-install intl pdo pdo_mysql pdo_pgsql mbstring zip \
    && rm -rf /var/lib/apt/lists/*

# Installer Composer
COPY --from=composer:2 /usr/bin/composer /usr/bin/composer

# Copier le code et installer les dépendances
COPY Symfony/brasilBurger/ ./
RUN composer install --no-dev --optimize-autoloader

# Étape 2 : Runtime
FROM php:8.2-fpm
WORKDIR /var/www

# Installer les dépendances runtime
RUN apt-get update && apt-get install -y \
    postgresql-client \
    && docker-php-ext-install pdo pdo_pgsql \
    && rm -rf /var/lib/apt/lists/*

COPY --from=build /var/www /var/www

# Définir les permissions
RUN chown -R www-data:www-data /var/www

EXPOSE 9000
CMD ["php-fpm"]
