import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.user.ApplicationUser
import org.apache.directory.groovyldap.LDAP
import com.atlassian.jira.issue.util.DefaultIssueChangeHolder
import com.atlassian.jira.issue.ModifiedValue
import com.atlassian.jira.bc.project.component.ProjectComponent
import com.atlassian.jira.security.roles.ProjectRoleManager

ApplicationUser user = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser()
final Thread thd = Thread.currentThread()
final ClassLoader oldTccl = thd.getContextClassLoader()
thd.setContextClassLoader(ComponentAccessor.class.getClassLoader())
def ldap =  LDAP.newInstance('ldap', 'user', 'password')
def userString = 'CN='+user.getDisplayName()+',OU'
def user = ldap.read(userString)
def managerUser = ldap.read(manager.toString())['mailNickname']
thd.setContextClassLoader(oldTccl)
