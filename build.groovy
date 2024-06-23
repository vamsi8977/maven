pipeline {
  agent any
  options {
    buildDiscarder(logRotator(numToKeepStr:'5' , artifactNumToKeepStr: '5'))
    timestamps()
    }
  stages {
    stage('CheckOut') {
      steps {
        echo 'Checking out project from Bitbucket....'
        cleanWs()
        git(url: 'git@github.com:vamsi8977/maven.git', branch: 'main')
      }
    }
    stage('Build') {
      steps {
        script {
          sh "mvn clean install"
        }
      }
    }
  }
  post {
    success {
      echo "The build passed."
      archiveArtifacts artifacts: "target/*.jar"
    }
    failure {
      echo "The build failed."
    }
    cleanup {
      deleteDir()
    }
  }
}
