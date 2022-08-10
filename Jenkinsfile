pipeline {
     agent { label 'dind'}
     stages {
          stage("Compile") {
               steps {
                    sh "./gradlew compileJava"
               }
          }
          stage("Unit test") {
               steps {
                    sh "./gradlew test"
               }
          }
          stage("Code coverage") {
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
                        steps {
                            sh "./gradlew build"
                        }
          }
          stage("Docker build") {
                        steps {
                            sh "docker build -t deya/calculator ."
                        }
          }
          stage("Docker push") {
               steps {

                withCredentials([usernamePassword(credentialsId: 'DOCKER', passwordVariable: 'pass', usernameVariable: 'user')]) {
                                   sh """
                                   docker login --username ${env.user} -p ${env.pass}
                                   """
                               }

                    sh "docker push deya/calculator"
               }
          }
          stage("Deploy to staging") {
               steps {
                    sh "docker run -d --rm -p 8765:8080 --name calculator deya/calculator"
               }
          }
          stage("Acceptance test") {
               steps {
                    sleep 60
                    sh "chmod +x acceptance_test.sh && ./acceptance_test.sh"
               }
          }
     }
     post{
     always {
               sh "docker stop calculator"
          }
             success{
                 slackSend( channel: "#ais-dev-status", token: "OneWdUWsb4mN7gpzzwX6kmAK", color: "good", message: "The pipeline ${currentBuild.fullDisplayName} Succesfully Completed..")
             }
             failure{
                 slackSend( channel: "#ais-dev-status", token: "OneWdUWsb4mN7gpzzwX6kmAK", color: "bad", message: "The pipeline ${currentBuild.fullDisplayName} Failed..")
             }
         }
}