import com.atlassian.jira.component.ComponentAccessor;
import org.apache.directory.groovyldap.LDAP;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

def string = ""
def user = uriInfo.getQueryParameters()['user'].toString().replace('[', ' ').replace(']', ' ').trim().toLowerCase();
def password = uriInfo.getQueryParameters()['password'].toString().replace('[', ' ').replace(']', ' ').trim();
final Thread thd = Thread.currentThread();
final ClassLoader oldTccl = thd.getContextClassLoader();
thd.setContextClassLoader(ComponentAccessor.class.getClassLoader());
def domain = 'domain'
def ldapUser =  LDAP.newInstance('ldap', user+'@'+domain, password);

try {
   user = ldapUser.read()
} catch(Exception ex) {
  if (ex.toString() == "java.lang.NullPointerException"){
    string = "true"
  }
  else {
    string = "false"
  }
}
finally {
  thd.setContextClassLoader(oldTccl);
}

return Response.ok(string).build()
