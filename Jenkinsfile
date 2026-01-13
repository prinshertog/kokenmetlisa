pipeline {
    agent any

    stages {
        stage('Git Checkout') {
            steps {
                script {
                    git branch: 'dev',
                        credentialsId: 'github-prinshertog',
                        url: 'git@github.com:prinshertog/kokenmetlisa.git'
                }
            }
        }
        
        stage('Starting postgres for backend build') {
            steps {
                sh 'cd test-db && docker compose up -d'
            }
        }
        
        stage('Running maven build') {
            steps {
                sh 'cd backend && mvn clean package -Dspring.datasource.url=jdbc:postgresql://docker:5432/dishes'
            }
        }
        
        stage('Stopping postgres') {
            steps {
                sh 'cd test-db && docker compose down'
            }
        }
        
        stage('Building frontend') {
            steps {
                sh 'cd frontend && npm i && npm run build'
            }
        }
        
        stage('Build docker image for backend unstable') {
            when {
                branch 'dev'
            }
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'docker-prinshertog',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh '''
                      echo "$DOCKER_PASS" | docker login \
                        --username "$DOCKER_USER" \
                        --password-stdin
                    '''
                    sh 'cd backend && docker build . -t "prinshertog/kokenmetlisa-backend:v2-unstable" && docker push prinshertog/kokenmetlisa-backend:v2-unstable'
                }
            }
        }
        
        stage('Build docker image for frontend unstable') {
            when {
                branch 'dev'
            }
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'docker-prinshertog',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh '''
                      echo "$DOCKER_PASS" | docker login \
                        --username "$DOCKER_USER" \
                        --password-stdin
                    '''
                    sh 'cd frontend && docker build . -t "prinshertog/kokenmetlisa-frontend:v2-unstable" && docker push prinshertog/kokenmetlisa-frontend:v2-unstable'
                }        
            }
        }

        stage('Build docker image for backend stable') {
            when {
                branch 'main'
            }
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'docker-prinshertog',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh '''
                      echo "$DOCKER_PASS" | docker login \
                        --username "$DOCKER_USER" \
                        --password-stdin
                    '''
                    sh 'cd backend && docker build . -t "prinshertog/kokenmetlisa-backend:v2-stable" && docker push prinshertog/kokenmetlisa-backend:v2-stable'
                }
            }
        }
        
        stage('Build docker image for frontend stable') {
            when {
                branch 'main'
            }
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'docker-prinshertog',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh '''
                      echo "$DOCKER_PASS" | docker login \
                        --username "$DOCKER_USER" \
                        --password-stdin
                    '''
                    sh 'cd frontend && docker build . -t "prinshertog/kokenmetlisa-frontend:v2-stable" && docker push prinshertog/kokenmetlisa-frontend:v2-stable'
                }        
            }
        }
    }
    post {
        success {
            githubNotify(context: 'Jenkins', status: 'SUCCESS', description: 'Build passed')
        }
        failure {
            githubNotify(context: 'Jenkins', status: 'FAILURE', description: 'Build failed')
        }
    }
}
