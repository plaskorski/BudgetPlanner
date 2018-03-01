pipeline {
  agent {
    docker {
      image 'build'
      args ' bnm nm'
    }
    
  }
  stages {
    stage('Deploy CI/CD Swarms') {
      steps {
        input(message: 'How many nodes in test stage?', id: 'nnodestext', ok: 'ok', submitter: 'me', submitterParameter: 'n')
      }
    }
  }
}