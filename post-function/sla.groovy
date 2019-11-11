import com.atlassian.jira.ComponentManager
import com.atlassian.jira.issue.Issue
import com.atlassian.jira.issue.CustomFieldManager
import com.atlassian.jira.issue.fields.CustomField
import java.text.SimpleDateFormat
import java.text.DateFormat
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.comments.CommentManager

def dateup = ComponentAccessor.getCustomFieldManager().getCustomFieldObject("customfield_10941")
def valuedu = issue.getCustomFieldValue(dateup)
issue.setCreated(valuedu)

def baseURL = "JSD/rest/servicedesk/1/servicedesk/sla/admin/task/destructive/reconstruct?force=false";
def body_req = '["'+issue.toString()+'"]'
URL url = new URL(baseURL);
HttpURLConnection connection=(HttpURLConnection)url.openConnection();
connection.requestMethod = "POST";
connection.doOutput = true;
connection.setRequestProperty("Content-Type", "application/json");
connection.setRequestProperty("Authorization", "Basic");
connection.outputStream.withWriter{
        it.write(body_req)
        it.flush()
      }
connection.connect();
