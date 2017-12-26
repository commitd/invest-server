pipeline {
    agent { 
        docker {
            image 'maven:3-jdk-8'
            args '--volumes-from ci3_cache_1'
        }
    }
    environment {
        NEXUS_PASSWORD = credentials('NEXUS_PASSWORD')
        NEXUS_AUTH = credentials('NEXUS_AUTH')
        SONAR_PASSWORD = credentials('SONAR_PASSWORD')
    }
    stages {
        stage('env') {
            steps {
                sh 'printenv'
            }
        }
        stage('build') {
            steps {
                sh 'mvn -s $MAVEN_SETTINGS  -B clean install'
            }
        }
        stage('quality') {
            steps {
                sh 'mvn -s $MAVEN_SETTINGS -B sonar:sonar'
            }
        }
        stage('deploy') {
            steps {
                sh 'mvn -s $MAVEN_SETTINGS  -B deploy'
            }
        }
    }
    post {
        always {
            junit '**/target/surefire-reports/*.xml'
        }
        success {
            slackSend channel: '#invest_ci',
                  color: 'good',
                  message: "The pipeline ${currentBuild.fullDisplayName} completed successfully."
        }
        unstable {
            slackSend channel: '#invest_ci',
                  color: 'warning',
                  message: "The pipeline ${currentBuild.fullDisplayName} failed tests."
        }
        failure {
            slackSend channel: '#invest_ci',
                  color: 'danger',
                  message: "The pipeline ${currentBuild.fullDisplayName} failed."
        }
    }
}