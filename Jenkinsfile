
pipeline {
    agent any

    tools {
        maven 'MAVEN_HOME'
    }

    stages {

        stage('Build') {
            steps {
                dir('APIautomationCW') {
                    git branch: 'master',
                            url: 'https://github.com/sureshlokare1989/APIautomationCW.git'

                    bat 'mvn clean package -Dmaven.test.failure.ignore=true'
                }
            }

            post {
                always {
                    junit allowEmptyResults: true,
                            testResults: 'APIautomationCW/target/surefire-reports/TEST-*.xml'
                }

                success {
                    archiveArtifacts artifacts: 'APIautomationCW/target/*.jar',
                            allowEmptyArchive: true
                }
            }
        }

        stage('Deploy to QA') {
            steps {
                echo 'Deploying to QA environment'
            }
        }

        stage('Regression Automation Test') {
            steps {
                catchError(buildResult: 'SUCCESS',
                        stageResult: 'FAILURE') {

                    dir('PlaywrightPOMSeries') {
                        git branch: 'master',
                                url: 'https://github.com/sureshlokare1989/PlaywrightPOMSeries.git'

                        bat 'mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_regression.xml'
                    }
                }
            }

            post {
                always {
                    junit allowEmptyResults: true,
                            testResults: 'PlaywrightPOMSeries/target/surefire-reports/TEST-*.xml'
                }
            }
        }

        stage('Publish Extent Report') {
            steps {
                publishHTML([
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'PlaywrightPOMSeries/build',
                        reportFiles: 'TestExecutionReport.html',
                        reportName: 'HTML Extent Report',
                        reportTitles: ''
                ])
            }
        }
    }
}
