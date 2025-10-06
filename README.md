# Wine E-commerce Platform 🍷

Plateforme e-commerce moderne pour la vente de vins en ligne, développée avec une architecture monolithe modulaire.

## 🏗️ Architecture

- **Backend**: Spring Boot 3.x avec architecture modulaire
- **Frontend**: Next.js 14+ avec TypeScript
- **Base de données**: PostgreSQL (H2 pour le développement)
- **Authentification**: JWT
- **Containerisation**: Docker & Docker Compose

## 📁 Structure du projet

```
wine-ecommerce/
├── backend/                    # Application Spring Boot
│   ├── src/main/java/com/wine/ecommerce/
│   │   ├── core/              # Configuration centrale, entités de base
│   │   ├── user/              # Gestion des utilisateurs et authentification
│   │   ├── wine/              # Catalogue des vins
│   │   ├── inventory/         # Gestion du stock
│   │   ├── cart/              # Panier d'achat
│   │   ├── order/             # Gestion des commandes
│   │   ├── review/            # Avis clients
│   │   └── config/            # Configuration Spring Security & JWT
│   ├── src/main/resources/
│   │   ├── application.yml    # Configuration principale
│   │   ├── application-dev.yml # Configuration développement
│   │   └── data.sql          # Données d'exemple
│   └── pom.xml               # Dépendances Maven
├── frontend/                  # Application Next.js
│   ├── src/
│   │   ├── app/              # Pages et layouts (App Router)
│   │   ├── components/       # Composants réutilisables
│   │   ├── lib/              # Utilitaires et configuration API
│   │   ├── providers/        # Providers React Query
│   │   └── types/            # Types TypeScript
│   ├── package.json
│   └── next.config.ts
├── docker-compose.yml         # Configuration Docker Compose
├── start.sh                   # Script de démarrage avec Docker
├── dev.sh                     # Script de démarrage en mode développement
└── README.md
```

## 🚀 Démarrage rapide

### Option 1: Mode développement (recommandé)
```bash
# Cloner le projet
git clone <repository-url>
cd wine-ecommerce

# Démarrer en mode développement
./dev.sh
```

### Option 2: Avec Docker
```bash
# Démarrer avec Docker Compose
./start.sh
```

### Option 3: Manuel
```bash
# Backend
cd backend
./mvnw spring-boot:run

# Frontend (dans un autre terminal)
cd frontend
npm install
npm run dev
```

## 🌐 URLs d'accès

- **Frontend**: http://localhost:3000
- **Backend API**: http://localhost:8080/api
- **H2 Console** (dev): http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: *(vide)*

## 📊 Fonctionnalités

### ✅ Implémentées
- 🔐 Authentification JWT
- 👤 Gestion des utilisateurs (inscription, connexion)
- 🍷 Catalogue des vins avec filtres
- 🏪 Gestion du stock
- 🛒 Panier d'achat
- 📝 Commandes
- ⭐ Système d'avis clients
- 🎨 Interface utilisateur moderne avec Tailwind CSS

### 🔄 API Endpoints

#### Authentification
- `POST /auth/signin` - Connexion
- `POST /auth/signup` - Inscription

#### Vins
- `GET /wines` - Liste des vins (avec pagination et filtres)
- `GET /wines/{id}` - Détail d'un vin
- `GET /wines/search` - Recherche de vins
- `POST /wines` - Créer un vin (ADMIN/MANAGER)
- `PUT /wines/{id}` - Modifier un vin (ADMIN/MANAGER)
- `DELETE /wines/{id}` - Supprimer un vin (ADMIN)

## 🛠️ Technologies

### Backend
- **Spring Boot 3.2.0** - Framework principal
- **Spring Security** - Sécurité et authentification
- **Spring Data JPA** - Accès aux données
- **JWT** - Authentification stateless
- **PostgreSQL** - Base de données de production
- **H2** - Base de données de développement
- **Maven** - Gestion des dépendances

### Frontend
- **Next.js 15** - Framework React
- **TypeScript** - Typage statique
- **Tailwind CSS** - Framework CSS
- **React Query** - Gestion d'état serveur
- **Axios** - Client HTTP

## 🔧 Configuration

### Variables d'environnement Backend

```yaml
# application.yml
app:
  jwt:
    secret: "votre-secret-jwt-très-long"
    expiration: 86400000 # 24 heures

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/wine_ecommerce
    username: wine_user
    password: wine_password
```

### Variables d'environnement Frontend

```env
NEXT_PUBLIC_API_URL=http://localhost:8080/api
```

## 🧪 Tests

```bash
# Backend
cd backend
./mvnw test

# Frontend
cd frontend
npm test
```

## 📦 Production

### Build
```bash
# Backend
cd backend
./mvnw clean package

# Frontend
cd frontend
npm run build
```

### Docker
```bash
# Construire les images
docker-compose build

# Démarrer en production
docker-compose up -d
```

## 🤝 Contribution

1. Fork le projet
2. Créer une branche feature (`git checkout -b feature/nouvelle-fonctionnalite`)
3. Commit les changements (`git commit -m 'Ajouter nouvelle fonctionnalité'`)
4. Push vers la branche (`git push origin feature/nouvelle-fonctionnalite`)
5. Ouvrir une Pull Request

## 📝 License

Ce projet est sous licence MIT.

## 👨‍💻 Développement

### Prérequis
- Java 21+
- Node.js 18+
- PostgreSQL (pour la production)
- Docker & Docker Compose (optionnel)

### Structure modulaire
Le projet suit une architecture modulaire avec séparation claire des responsabilités :
- **Core** : Configuration transversale
- **User** : Gestion des utilisateurs et authentification
- **Wine** : Métier des vins
- **Inventory** : Gestion des stocks
- **Cart** : Logique du panier
- **Order** : Gestion des commandes
- **Review** : Système d'avis