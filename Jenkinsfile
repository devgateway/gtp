pipeline {
    agent any
    tools {
        maven 'maven-3.6.1'
    }
    options {
        skipStagesAfterUnstable()
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn -B test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }
        stage('Deploy') {
            input {
                message "Continue?"
                parameters { booleanParam(name: 'RECREATE_DB', defaultValue: false, description: 'Recreate database') }
            }
            steps {
                sh './jenkins/deploy.sh'
            }
        }
    }
}
