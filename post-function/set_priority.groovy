import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.priority.Priority
import com.atlassian.jira.event.type.EventDispatchOption

def issue = ComponentAccessor.getIssueManager().getIssueObject("ISSUE")

issue.setPriorityId("1")
def user = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser()
ComponentAccessor.getIssueManager().updateIssue(user, issue, EventDispatchOption.ISSUE_UPDATED, false)
