#!/bin/bash

echo "🍷 Démarrage de Wine E-commerce en mode développement"

# Fonction pour démarrer le backend
start_backend() {
    echo "🚀 Démarrage du backend Spring Boot..."
    cd backend
    ./mvnw spring-boot:run &
    BACKEND_PID=$!
    cd ..
}

# Fonction pour démarrer le frontend
start_frontend() {
    echo "⚛️ Démarrage du frontend Next.js..."
    cd frontend
    npm run dev &
    FRONTEND_PID=$!
    cd ..
}

# Fonction pour arrêter les processus
cleanup() {
    echo "🛑 Arrêt des services..."
    if [ ! -z "$BACKEND_PID" ]; then
        kill $BACKEND_PID 2>/dev/null
    fi
    if [ ! -z "$FRONTEND_PID" ]; then
        kill $FRONTEND_PID 2>/dev/null
    fi
    exit 0
}

# Capturer Ctrl+C
trap cleanup SIGINT

# Vérifier les dépendances
echo "📦 Vérification des dépendances..."

# Backend
if [ ! -f "backend/mvnw" ]; then
    echo "❌ Backend non trouvé. Assurez-vous d'être dans le bon répertoire."
    exit 1
fi

# Frontend
if [ ! -f "frontend/package.json" ]; then
    echo "❌ Frontend non trouvé."
    exit 1
fi

if [ ! -d "frontend/node_modules" ]; then
    echo "📦 Installation des dépendances frontend..."
    cd frontend && npm install && cd ..
fi

# Démarrer les services
start_backend
sleep 5
start_frontend

echo "✅ Services démarrés!"
echo "🌐 Frontend: http://localhost:3000"
echo "🚀 Backend API: http://localhost:8080/api"
echo "📚 Swagger UI: http://localhost:8080/swagger-ui/index.html"
echo "🗃️ H2 Console: http://localhost:8080/h2-console"
echo ""
echo "Appuyez sur Ctrl+C pour arrêter les services..."

# Attendre indéfiniment
wait