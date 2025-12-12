# Étape 1 : Builder
FROM php:8.2-fpm AS build
WORKDIR /var/www

# Installer les dépendances build
RUN apt-get update && apt-get install -y \
    git unzip libicu-dev libonig-dev libzip-dev zip \
    postgresql-client libpq-dev \
    && docker-php-ext-install intl pdo pdo_mysql pdo_pgsql mbstring zip \
    && rm -rf /var/lib/apt/lists/*

# Installer Composer
COPY --from=composer:2 /usr/bin/composer /usr/bin/composer

# Copier le code Symfony
COPY . ./

# Installer les dépendances PHP
RUN composer install --no-dev --optimize-autoloader

# Étape 2 : Runtime
FROM php:8.2-fpm
WORKDIR /var/www

# Installer les libs runtime
RUN apt-get update && apt-get install -y \
    postgresql-client libpq-dev \
    && docker-php-ext-install pdo pdo_pgsql \
    && rm -rf /var/lib/apt/lists/*

# Copier le résultat du build
COPY --from=build /var/www /var/www

# Permissions
RUN chown -R www-data:www-data /var/www

EXPOSE 9000
CMD ["php-fpm"]
