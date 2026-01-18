properties([
    parameters([
        string(name: 'TAGS',
               defaultValue: 'smoke',
               description: 'Test tags to run: smoke, regress, api, etc.')
    ])
])

pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
                sh 'chmod +x gradlew'
            }
        }

        stage('Run Tests') {
            steps {
                sh "./gradlew clean test -Ptags='${params.TAGS}'"
            }
        }
    }

    post {
        always {
            allure includeProperties: false,
                   results: [[path: 'build/allure-results']]
        }
    }
}