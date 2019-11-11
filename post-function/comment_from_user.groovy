import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.IssueManager
import com.atlassian.jira.issue.Issue
import com.atlassian.jira.issue.comments.CommentManager
import com.atlassian.jira.user.ApplicationUser

Issue issue = ComponentAccessor.getIssueManager().getIssueObject("$ISSUE-KEY$")
Issue parentIssue = issue.getParentObject()
def assignee = issue.getAssignee() *пользователь, на которого назначен тикет
//ApplicationUser currentUser = ComponentAccessor.getJiraAuthenticationContext().loggedInUser *текущий пользователь

def commentManager = ComponentAccessor.commentManager
commentManager.create(parentIssue, assignee, "This is a new comment by ${currentUser.name}", false)
