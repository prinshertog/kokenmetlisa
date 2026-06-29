pipeline {
    agent any

    stages {
        stage('Git checkout.') {
            steps {
                checkout scm
            }
        }
        
        stage('Building backend') {
            steps {
                sh 'cd backend && mvn clean package -DskipTests -Dspring.datasource.url=jdbc:postgresql://docker:5432/dishes'
            }
        }
    
        stage('Building frontend') {
            steps {
                sh 'cd frontend && npm i && npm run build'
            }
        }

        stage('Build docker image for branch backend') {
            when {
                not {
                    anyOf {
                        branch 'dev'
                        branch 'main'
                    }
                }
            }
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'docker-prinshertog',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    script {
                        sh '''
                        echo "$DOCKER_PASS" | docker login \
                            --username "$DOCKER_USER" \
                            --password-stdin
                        '''

                        sh """
                            cd backend
                            docker build . -t prinshertog/kokenmetlisa-backend:${env.BRANCH_NAME}
                            docker push prinshertog/kokenmetlisa-backend:${env.BRANCH_NAME}
                        """
                    }
                }
            }
        }

        stage('Build docker image for branch frontend') {
            when {
                not {
                    anyOf {
                        branch 'dev'
                        branch 'main'
                    }
                }
            }
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'docker-prinshertog',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    script {
                        sh '''
                        echo "$DOCKER_PASS" | docker login \
                            --username "$DOCKER_USER" \
                            --password-stdin
                        '''

                        sh """
                            cd frontend
                            docker build . -t prinshertog/kokenmetlisa-frontend:${env.BRANCH_NAME}
                            docker push prinshertog/kokenmetlisa-frontend:${env.BRANCH_NAME}
                        """
                    }
                }
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
}
