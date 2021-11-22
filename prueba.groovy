def GIT_REPO = 'turepogit'
def HOST = ['none','dh1','manager']

/* Scripted properties. */
properties([
    parameters([    
        choice(
            name: 'HOST',
            description: '',
            choices: HOST
        ),

        choice(
            name: 'STATUS',
            description: 'defina el estado del servicio',
            choice: STATUS
        )

    ])
])

pipeline {
   agent { label "${params.HOST}" }
   stages {
        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM', 
                branches: [[name: '*/master']], 
                doGenerateSubmoduleConfigurations: false, 
                extensions: [], 
                submoduleCfg: [],
                userRemoteConfigs: [[credentialsId: 'git', url: 'https://github.com/Basermc/prueba.git']]])
                sh "ls -lart ./*"
                sh "cp ./prueba.groovy /home/jenkins"
            }
        }
       stage('test') {
            steps {
                
                sh "echo 1234 | sudo -S docker ps"
            }
        }
       stage('build') {
            steps {
                
                sh "echo 1234 | sudo -S docker build -t simple-nginx ."
            }
        }
       stage('RUN') {
            steps {
                
                sh "echo 1234 | sudo -S docker run -d -p 8080:80  --rm --name simple-nginx-running-app simple-nginx  "
            }
        }
       stage('curl') {
            steps {
                
                sh "curl -I 192.168.1.67:8080 "
            }
        }
       stage('clean') {
            steps {
                
                sh "echo 1234 | sudo -S docker stop simple-nginx-running-app "
            }
        }
    }
}

