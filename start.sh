#!/bin/bash

echo "🍷 Démarrage de Wine E-commerce Platform"

# Vérifier si Docker est installé
if ! command -v docker &> /dev/null; then
    echo "❌ Docker n'est pas installé. Veuillez l'installer d'abord."
    exit 1
fi

# Vérifier si Docker Compose est installé
if ! command -v docker-compose &> /dev/null; then
    echo "❌ Docker Compose n'est pas installé. Veuillez l'installer d'abord."
    exit 1
fi

echo "🐳 Démarrage avec Docker Compose..."
docker-compose up --build

echo "✅ Application démarrée!"
echo "🌐 Frontend: http://localhost:3000"
echo "🚀 Backend API: http://localhost:8080/api"
echo "🗃️ H2 Console (dev): http://localhost:8080/h2-console"