pipeline {
     agent none
    }
     stages {
          stage("Compile") {
          agent {
                  docker { image "jenkins/agent:jdk17-preview"}
                  }
               steps {
                    sh "./gradlew compileJava"
               }
          }
          stage("Unit test") {
          agent {
                           docker { image "jenkins/agent:jdk17-preview"}
                           }
               steps {
                    sh "./gradlew test"
               }
          }
//           stage("Code coverage") {
//                          steps {
//                               sh "./gradlew jacocoTestReport"
//                               sh "./gradlew jacocoTestCoverageVerification"
//                          }
//                     }
          stage("Code coverage") {
           agent {
                            docker { image "jenkins/agent:jdk17-preview"}
                            }
               steps {

                    sh "./gradlew jacocoTestReport"
                    publishHTML (target: [
                         reportDir: 'build/reports/jacoco/test/html',
                         reportFiles: 'index.html',
                         reportName: "JaCoCo Report"
                    ])
                    sh "./gradlew jacocoTestCoverageVerification"
               }
          }
          stage("Static code analysis") {
           agent {
                            docker { image "jenkins/agent:jdk17-preview"}
                            }
                         steps {

                              sh "./gradlew checkstyleMain"
                              publishHTML (target: [
                                   reportDir: 'build/reports/checkstyle/',
                                   reportFiles: 'main.html',
                                   reportName: "Checkstyle Report"
                              ])
                         }
                    }
          stage("Package") {
           agent {
                            docker { image "jenkins/agent:jdk17-preview"}
                            }
                        steps {

                            sh "./gradlew build"
                        }
          }
          stage("Docker build") {
                    agent { docker { image "docker:stable-dind" } }
                        steps {
                            sh "docker build -t deyam/calculator ."
                        }
          }

     }
     post{
             success{
                 slackSend( channel: "#ais-dev-status", token: "OneWdUWsb4mN7gpzzwX6kmAK", color: "good", message: "The pipeline ${currentBuild.fullDisplayName} Succesfully Completed..")
             }
             failure{
                 slackSend( channel: "#ais-dev-status", token: "OneWdUWsb4mN7gpzzwX6kmAK", color: "good", message: "The pipeline ${currentBuild.fullDisplayName} Failed..")
             }
         }
}