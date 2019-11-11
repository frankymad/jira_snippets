import com.atlassian.jira.event.issue.AbstractIssueEventListener
import com.atlassian.jira.event.issue.IssueEvent
import com.atlassian.jira.ComponentManager
import com.atlassian.jira.issue.Issue
import com.atlassian.jira.issue.IssueManager
import com.atlassian.jira.issue.MutableIssue
import com.atlassian.jira.issue.customfields.CustomFieldType
import com.atlassian.jira.issue.CustomFieldManager
import com.atlassian.jira.issue.fields.CustomField
import com.atlassian.mail.Email
import com.atlassian.mail.server.MailServerManager
import com.atlassian.mail.server.SMTPMailServer
import org.apache.log4j.Category
import com.atlassian.jira.user.util.UserUtil
import com.atlassian.jira.component.ComponentAccessor;
import java.text.SimpleDateFormat
import java.sql.Timestamp
import java.text.DateFormat
import java.util.Date

def issue = event.issue as Issue
def issueEvent = event
def usersList = ["BCC"]

if (issue.reporter.name.toString().toLowerCase() in usersList) {
    MailServerManager mailServerManager = ComponentAccessor.getMailServerManager()
    SMTPMailServer mailServer = mailServerManager.getDefaultSMTPMailServer()
  	def bc = []
	usersList.each{
  		def user = ComponentAccessor.getUserManager().getUserByKey(it)
  		bc = bc + user.emailAddress
	}
	def bcc = bc.join(", ")
    Email email = new Email(bcc)
    email.setMimeType("text/html")
    email.setBcc(bcc)
  	def text = ""
    switch (event.eventTypeId){
        case 1:
            email.setSubject(issue.key + " " + issue.summary + " Created")
            text = issue.reporter.displayName.toString() + " created an issue " + issue.key.toString() + " " + issue.summary.toString() + "<br>" + issue.description.toString()
      		break;
        case 6:
            email.setSubject(issue.key + " " + issue.summary + " Commented");
            text = event.comment.authorFullName.toString() + " leave a comment in issue " + issue.key.toString() + " " + issue.summary.toString() + "<br>" + event.comment.authorFullName.toString() + " " + event.comment.body.toString()
      		break;
        case 7:
      		email.setSubject(issue.key + " " + issue.summary + " Reopened");
            text = issue.reporter.displayName.toString() + " reopened an issue " + issue.key.toString() + " " + issue.summary.toString() + "<br>" + issue.reporter.displayName.toString() + " set priority level to " + issue.priority.name.toString()
      		break;
    }
    email.setBody(text)
    mailServer.send(email)
}
else {
  return "No"
}
