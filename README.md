### Test Jenkins shared library for subversion handling

Sample Jenkinsfile:
```groovy
// SVN repository URL
def url = 'https://...'

// Jenkins credential storing username and password for svn auth
def credential = ''

stage('svn checkout') {
    subversion.checkout('dir')
}

stage('update svn directory') {
    sh 'rsync ... dir/'
    subversion.update('dir')
}

stage('commit changes') {
    subversion.commit('dir')
}
```
