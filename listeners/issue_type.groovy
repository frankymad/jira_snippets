import com.atlassian.jira.issue.Issue
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.event.type.EventDispatchOption
import com.atlassian.jira.issue.security.IssueSecurityLevelManager
import com.atlassian.jira.issue.security.IssueSecuritySchemeManager

// According to issue type
def issue = event.issue as Issue
if (issue.issueType.name == 'Issue Type') {
	issue.setSecurityLevelId(10102)
	def user = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser()
	ComponentAccessor.getIssueManager().updateIssue(user, issue, EventDispatchOption.ISSUE_UPDATED, false)
}
else {
	issue.setSecurityLevelId(10103)
	def user = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser()
	ComponentAccessor.getIssueManager().updateIssue(user, issue, EventDispatchOption.ISSUE_UPDATED, false)
}
