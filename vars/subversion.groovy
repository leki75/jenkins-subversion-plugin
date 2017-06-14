def subversion implements Serializable {
    private String args;

    def checkout(url, cred, dir) {
        withCredentials([usernamePassword(credentialsId: cred, usernameVariable: 'SVN_USER', passwordVariable: 'SVN_PASS')]) {
            args = "--non-interactive --trust-server-cert --username ${env.SVN_USER} --password ${env.SVN_PASS}".toString()
        }

        sh "svn checkout ${args} ${url} ${dir}"
    }

    def update(dir) {
        sh "cd ${dir} && svn status | awk '/^!/{print "delete "$2}/^\\?/{print "add "$2}' | while read command; do svn $command; done"
    }

    def commit(dir, msg) {
        def status = sh(script: "cd ${dir} && svn status", returnStdout: true).split('\n')

        if (status.size() > 1 || status[0] != '') {
            sh "cd ${dir} && svn commit ${args} --message '${msg}'"
        } else {
            echo 'Nothing to commit!'
        }
    }
}
// vim: ts=4:sw=4:sts=4:et:ft=groovy
