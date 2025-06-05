#!/bin/bash
echo "Cleanup before start"
cd /home/jan/Github/kokenmetlisa
docker compose down

echo "Starting postgres for backend build"
cd ~/Docker/postgresql
docker compose up -d

echo "Running maven build"
cd /home/jan/Github/kokenmetlisa/backend
mvn clean package

echo "Stopping postgres"
cd ~/Docker/postgresql
docker compose down

echo "Building frontend"
cd /home/jan/Github/kokenmetlisa/frontend
npm run build

cd /home/jan/Github/kokenmetlisa/backend
docker build . -t "prinshertog/kokenmetlisa-backend"
docker push prinshertog/kokenmetlisa-backend

cd /home/jan/Github/kokenmetlisa/frontend
docker build . -t "prinshertog/kokenmetlisa-frontend"
docker push prinshertog/kokenmetlisa-frontend