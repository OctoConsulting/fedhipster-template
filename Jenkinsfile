pipeline {
  agent {
    kubernetes {
      label 'pipeline-slave'
      defaultContainer 'jnlp'
      yamlFile 'templates/podtemplate.yaml'
    }
  }
  environment {
    APP_NAME = "app-image"
    IMAGE_VERSION = "$BUILD_NUMBER"
    DEPLOYMENT_NAME = "app"
    CONTAINER_NAME = "app"
    NAMESPACE = sh(
                returnStdout: true,
                script: '''
                  GIT_BRANCH=$(git symbolic-ref --short HEAD)
                  if   [ ${GIT_BRANCH:0:3} = dev ]; then echo "dev"
                  elif [ ${GIT_BRANCH} = stage ];   then echo "stage"
                  elif [ ${GIT_BRANCH} = master ];  then echo "prod"
                  else echo "dev"
                  fi
                ''').toLowerCase().trim()
    APP_URL = "http://${DEPLOYMENT_NAME}.${NAMESPACE}.svc.cluster.local:80"
    SONAR_URL = "http://cicd-sonarqube.default.svc.cluster.local:9000"
  }
  stages {
    stage('Setup') {
      steps{
        container('docker'){ sh 'dockerd &> dockerd-logfile &' }
      }
    }
    stage('Build & Test') {
      steps{
        container('maven'){
          sh '''
            apt-get update && apt-get install nodejs -y
            MAVEN_OPTS="$MAVEN_OPTS XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=2"
            mvn -Pprod verify -DskipITs sonar:sonar \
              -Dsonar.host.url=$SONAR_URL \
              -Dsonar.projectKey=app-${NAMESPACE} \
              -Dsonar.projectName=app-${NAMESPACE}
          '''
        }
      }
    }
    stage('Docker') {
      steps{
        container('docker'){
          sh '''
            cp target/*.jar src/main/docker
            docker build -t $APP_NAME:latest -t $APP_NAME:$IMAGE_VERSION src/main/docker/.
          '''
          script {
            docker.withRegistry('https://$IMAGE_REPO', 'ecr:us-east-1:eks-keys') {
              docker.image('$APP_NAME').push('$NAMESPACE-$IMAGE_VERSION')
              docker.image('$APP_NAME').push('$NAMESPACE-latest')
            }
          }
        }
      }
    }
    stage('Deploy') {
      options { timeout( time: 10, unit: 'MINUTES' ) }
      steps{
        container('kubectl'){
          sh '''
            kubectl -n $NAMESPACE --record deploy/$DEPLOYMENT_NAME \
              set image $CONTAINER_NAME=$IMAGE_REPO/$APP_NAME:$NAMESPACE-$IMAGE_VERSION
            kubectl -n $NAMESPACE rollout status deploy/$DEPLOYMENT_NAME
            kubectl -n $NAMESPACE get svc $DEPLOYMENT_NAME -o json | grep hostname | sed 's|.*: \\(.*\\)|\\1|;s/"//g' \
              > app-elb.txt
            kubectl -n $NAMESPACE get svc keycloak -o json | grep hostname | sed 's|.*: \\(.*\\)|\\1|;s/"//g' \
              > kc-elb.txt
          '''
        }
      }
    }
    stage('Post') {
      when { expression { (env.NAMESPACE == "dev") || (env.NAMESPACE == "stage") } }
      parallel {
        stage('508 Test') {
          when { expression { env.NAMESPACE == "stage" } }
          steps{
            container('maven'){
              sh '''
                APP_ELB=`cat app-elb.txt`
                apt-get install npm -y
                apt-get install gconf-service libasound2 libatk1.0-0 libatk-bridge2.0-0 libc6 libcairo2 libcups2 libdbus-1-3 libexpat1 libfontconfig1 libgcc1 libgconf-2-4 libgdk-pixbuf2.0-0 libglib2.0-0 libgtk-3-0 libnspr4 libpango-1.0-0 libpangocairo-1.0-0 libstdc++6 libx11-6 libx11-xcb1 libxcb1 libxcomposite1 libxcursor1 libxdamage1 libxext6 libxfixes3 libxi6 libxrandr2 libxrender1 libxss1 libxtst6 ca-certificates fonts-liberation libappindicator1 libnss3 lsb-release xdg-utils wget -y
                npm install -g pa11y-ci pa11y-ci-reporter-html --unsafe-perm=true
                pa11y-ci -c pa11y.json --json http://${APP_ELB}:80 | tee pa11y-ci-results.json
                pa11y-ci-reporter-html -d pa11y-html
              '''
              publishHTML target: [allowMissing: false, alwaysLinkToLastBuild: true, keepAll: true,
                reportDir: 'pa11y-html',
                reportFiles: 'index.html',
                reportName: "508 Test"
              ]
            }
          }
        }
        stage('Pen Test') {
          when { expression { env.NAMESPACE == "stage" } }
          steps {
            container('owasp'){
              sh '''
                APP_ELB=`cat app-elb.txt`
                zap-baseline.py -r zap-report.html -t http://${APP_ELB}:80 || return_code=$?
                echo "exit value was  - " $return_code
                mkdir owasp
                cp "/zap/wrk/zap-report.html" ${WORKSPACE}/owasp
              '''
              publishHTML target: [
                allowMissing: false, alwaysLinkToLastBuild: true, keepAll: true,
                reportDir: "owasp",
                reportFiles: 'zap-report.html',
                reportName: 'Penetration Test'
              ]
            }
          }
        }
        stage('E2E Test') {
        when { expression { env.NAMESPACE == "stage" } }
          steps {
            container('selenium'){
              sh '''
                APP_ELB=`cat app-elb.txt`
                cd integration-tests/rs-e2e-test
                mvn verify -Dwebdriver.base.url=http://${APP_ELB}:80/ || return_code=$?
                echo "exit value was  - " $return_code
              '''
              publishHTML target: [
                allowMissing: false, alwaysLinkToLastBuild: true, keepAll: true,
                reportDir: 'integration-tests/rs-e2e-test/target/site/serenity',
                reportFiles: 'index.html',
                reportName: 'E2E Test'
              ]
            }
          }
        }
      }
    }
    stage('Perf Test') {
      when { expression { env.NAMESPACE == "stage"} }
      steps {
        container('jmeter'){
          sh '''
            APP_ELB=`cat app-elb.txt`
            KC_ELB=`cat kc-elb.txt`
            cd integration-tests/rs-performance-test
            jmeter -n -t rs-performance-test.jmx -JAppURL=${APP_ELB} -JKcURL=${KC_ELB} -l results/rs.jtl -e -o results
          '''
          publishHTML target: [
            allowMissing: false, alwaysLinkToLastBuild: true, keepAll: true,
            reportDir: 'integration-tests/rs-performance-test/results',
            reportFiles: 'index.html',
            reportName: 'Performance Test'
          ]
        }
      }
    }
  }
}
