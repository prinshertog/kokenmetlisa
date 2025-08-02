#!/bin/bash
echo "Cleanup before start"
cd /home/jan/Github/kokenmetlisa
docker compose down

echo "Starting postgres for backend build"
cd /home/jan/Github/kokenmetlisa/postgresqlForKokenmetlisa/
docker compose up -d

echo "Running maven build"
cd /home/jan/Github/kokenmetlisa/backend
mvn clean package

echo "Stopping postgres"
cd /home/jan/Github/kokenmetlisa/postgresqlForKokenmetlisa
docker compose down

echo "Building frontend"
cd /home/jan/Github/kokenmetlisa/frontend
npm run build

#echo "Pruning all images of docker"
#docker system prune --all --force

echo "Build docker image for frontend"
cd /home/jan/Github/kokenmetlisa/frontend
docker build . -t kokenmetlisa-frontend

echo "Build docker image for backend"
cd /home/jan/Github/kokenmetlisa/backend
docker build . -t kokenmetlisa-backend

echo "Starting project docker compose"
cd /home/jan/Github/kokenmetlisa
docker compose up --force-recreate -d

