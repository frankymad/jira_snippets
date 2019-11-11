import com.atlassian.jira.ComponentManager
import com.atlassian.mail.Email
import com.atlassian.jira.component.ComponentAccessor

def issue = ComponentAccessor.getIssueManager().getIssueObject("ISSUE")
def bc = "BC"
Email email = new Email(bc)
email.setMimeType("text/html")
email.setBcc(bc)
def text = ""
email.setSubject(issue.key + " " + issue.summary + " Created")
text = issue.reporter.displayName.toString() + " created an issue " + issue.key.toString() + " " + issue.summary.toString()
email.setBody(text)
ComponentAccessor.getMailServerManager().getDefaultSMTPMailServer().send(email)
