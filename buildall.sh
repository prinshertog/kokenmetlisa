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

#echo "Pruning all images of docker"
#docker system prune --all --force

echo "Starting project docker compose"
cd /home/jan/Github/kokenmetlisa
docker compose up --build --force-recreate -d