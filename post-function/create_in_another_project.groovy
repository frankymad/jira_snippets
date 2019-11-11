import com.atlassian.crowd.embedded.api.User
import com.atlassian.jira.issue.IssueManager
import com.atlassian.jira.issue.link.IssueLinkManager
import com.atlassian.jira.issue.link.IssueLinkType
import com.atlassian.jira.issue.link.IssueLinkTypeManager
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.Issue
import com.atlassian.jira.issue.MutableIssue
import com.atlassian.jira.issue.CustomFieldManager
import com.atlassian.jira.ComponentManager
import com.atlassian.jira.issue.IssueFactory

def user = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser()
def issueFactory = ComponentAccessor.getIssueFactory()
def subTaskManager = ComponentAccessor.getSubTaskManager()
def constantManager = ComponentAccessor.getConstantsManager()
def issueManager = ComponentAccessor.getIssueManager()
def projectMgr = ComponentAccessor.getProjectManager()
def authenticationContext = ComponentAccessor.getJiraAuthenticationContext()
def issueLinkManager = ComponentAccessor.getIssueLinkManager()
def descr = issue.getDescription()
def dd = issue.getDueDate()
def repo = issue.getReporter()
def compon = issue.getComponents()
def summm = issue.getSummary()

try
{
def issueObject = issueFactory.getIssue()
issueObject.setProjectId(PROJECT_ID_INT)
issueObject.setIssueTypeId(ISSUE_ID_STRING)
issueObject.setSummary("TEST :"+issue.getSummary())
issueObject.setDescription(descr)
issueObject.setPriority(issue.getPriority())
issueObject.setReporter(issue.getReporter())
issueManager.createIssueObject(authenticationContext.getLoggedInUser(), issueObject)
issueLinkManager.createIssueLink(issue.getId(),issueObject.getId(),10003,1,issue.getAssignee())
}
catch(Exception ex) {println("Catching the exception");}
finally {println("The final block");}
